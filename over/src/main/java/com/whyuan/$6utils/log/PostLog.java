package com.whyuan.$6utils.log;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.whyuan.$6utils.monitor.UMetrics;
import com.whyuan.$6utils.thread.ThreadUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostLog implements Log<HttpPost, List<Map<String, Object>>> {

    private final static Logger logger = LoggerFactory.getLogger(PostLog.class);

    private MetricRegistry metric = UMetrics.create(logger, "PostLog", 30, TimeUnit.SECONDS);
    LinkedBlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<>();

    private static final int sinkMaxSize = 1024 * 100;


    private String url;
    private String topic;


    public PostLog(String url, String topic) {

        this.url = "http://" + url;
        this.topic = topic;
        metric.register("logger.post.queue", (Gauge<Integer>) () -> queue.size());
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
    public void index(HttpPost httpPost, List<Map<String, Object>> datas) {
        try {
            long timestamp = System.currentTimeMillis();
            Map<String, Object> headers = new HashMap<>();
            headers.put("topic", topic);
            headers.put("timestamp", timestamp);

            byte[] jdata = FlumeUtils.buildFlumeData(headers, datas, false);
            httpPost.setEntity(new ByteArrayEntity(jdata, ContentType.APPLICATION_JSON));
            FlumeUtils.execute(httpPost);

        } catch (Exception e) {
            logger.error("" + e);
        }

    }

    private static final AtomicBoolean isRunning = new AtomicBoolean(true);
    private static final long interval = TimeUnit.SECONDS.toMillis(30);

    class Task implements Runnable {

        private List<Map<String, Object>> datas = new ArrayList<>();
        private long lastTime = 0l;

        @Override
        public void run() {
            lastTime = System.currentTimeMillis();

            while (isRunning.get())
                try {
                    long current = System.currentTimeMillis();
                    Map<String, Object> map = queue.poll(10, TimeUnit.MILLISECONDS);
                    if (map == null) {
                        if (current - lastTime > interval && !datas.isEmpty()) { // 定时提交
                            index(new HttpPost(url), datas);
                            metric.counter("post.sink.total").inc(datas.size());
                            datas.clear();
                            lastTime = current;
                        }
                        continue;
                    }
                    datas.add(map);
                    if (datas.size() >= 30) { // 定量提交
                        index(new HttpPost(url), datas);
                        metric.counter("post.sink.total").inc(datas.size());
                        datas.clear();
                        lastTime = current;
                    }
                } catch (Exception e) {
                    logger.error("Commit document failed !", e);
                }
        }

    }

}
