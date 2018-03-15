package com.whyuan.$6utils.log;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.whyuan.$6utils.monitor.UMetrics;
import com.whyuan.$6utils.thread.ThreadUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SolrLog implements Log<CloudSolrClient, List<SolrInputDocument>> {

    private static final Logger logger = LoggerFactory.getLogger(SolrLog.class);
    private MetricRegistry metric = UMetrics.create(logger, "SolrLog", 30, TimeUnit.SECONDS);

    LinkedBlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<>();
    private CloudSolrClient client;
    private Set<String> fields;
    private static final int sinkMaxSize = 1024 * 100;

    public SolrLog(String zkHost, String collection) {

        client = new CloudSolrClient(zkHost);
        client.setDefaultCollection(collection);
        client.connect();

        Schema schema = SolrUtils.getSchema(client);
        fields = schema.getFields().stream().map(Field::getName).collect(Collectors.toSet());

        metric.register("logger.solr.queue", (Gauge<Integer>) () -> queue.size());

        new Thread(new Task(), THREAD_NAME).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> isRunning.set(false)));
    }

    @Override
    public void put(Map<String, Object> src) {

        try {
            if (queue.size() > sinkMaxSize) {//日志队列超过sinkMaxSize 检查检查消费的线程是否死掉
                //获取监控线程
                Thread target = ThreadUtils.getThreadByName(THREAD_NAME);
                //如果线程死掉
                if (target == null) {
                    logger.warn("写日志的线程死掉了[target == null],新启动新的线程去写日志。");
                    new Thread(new Task(), THREAD_NAME).start();
                } else if (!target.isAlive()) {
                    logger.warn("写日志的线程死掉了[target.isAlive={}],新启动新的线程去写日志。", target.isAlive());
                    new Thread(new Task(), THREAD_NAME).start();
                } else {
                    logger.error("What the Fuck !!! 不知道是什么情况，线程还在消耗不了数据。");
                }
            }

            //超过数量就不消耗
            if (queue.size() <= sinkMaxSize) {
                queue.put(src);
            } else {
                //记录日志???
            }

        } catch (InterruptedException e) {
            logger.error("Put logger to logger'queue is interrupted ! " + e);
        } catch (Exception e) {
            logger.error("Put logger to logger'queue throw Exception ! " + e);
        }

    }

    @Override
    public void index(CloudSolrClient client, List<SolrInputDocument> docs) {

        try {
            client.add(docs);
            client.commit();
        } catch (SolrServerException e) {
            logger.error("SolrServerException" + e);
        } catch (IOException e) {
            logger.error("IOException" + e);
        } catch (Exception e) {
            logger.error("Exception" + e);
        }

    }


    private static final AtomicBoolean isRunning = new AtomicBoolean(true);

    private static final long interval = TimeUnit.SECONDS.toMillis(30);

    class Task implements Runnable {

        private List<SolrInputDocument> docs = new ArrayList<>();
        private long lastTime = 0l;

        @Override
        public void run() {
            lastTime = System.currentTimeMillis();

            while (isRunning.get())
                try {
                    long current = System.currentTimeMillis();
                    Map<String, Object> map = queue.poll(10, TimeUnit.MILLISECONDS);
                    if (map == null) {
                        if (current - lastTime > interval && !docs.isEmpty()) { // 定时提交
                            index(client, docs);
                            metric.counter("solr.sink.total").inc(docs.size());
                            docs.clear();
                            lastTime = current;
                        }
                        continue;
                    }
                    docs.add(mapToDocument(map));
                    if (docs.size() >= 1000) { // 定量提交
                        index(client, docs);
                        metric.counter("solr.sink.total").inc(docs.size());
                        docs.clear();
                        lastTime = current;
                    }
                } catch (Exception e) {
                    logger.error("Commit document failed !", e);
                }
        }

        private SolrInputDocument mapToDocument(Map<String, Object> map) {

            SolrInputDocument doc = new SolrInputDocument();
//            String id = UUID.randomUUID().toString().replace("-", "");
//            doc.setField("ID", id);

            map.forEach((k, v) -> {
                if (fields.contains(k)) {
                    doc.setField(k, v);
                }
            });

            return doc;
        }

    }

}