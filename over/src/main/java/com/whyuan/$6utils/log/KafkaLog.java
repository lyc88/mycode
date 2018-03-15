package com.whyuan.$6utils.log;

import com.alibaba.fastjson.JSON;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Strings;
import com.whyuan.$6utils.monitor.UMetrics;
import com.whyuan.$6utils.thread.ThreadUtils;
import com.whyuan.$6utils.zk.ZkUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaLog implements Log<KafkaProducer<String, String>, ProducerRecord<String, String>> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLog.class);
    private MetricRegistry metric = UMetrics.create(logger, "KafkaLog", 30, TimeUnit.SECONDS);

    LinkedBlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<>();
    private KafkaProducer<String, String> kafkaProducer;
    private Set<String> fields;
    private static final int sinkMaxSize = 1024 * 100;

    private String topic;
    private String zkHost;

    public KafkaLog(String topic, String zkHost) {
        this.topic = topic;
        this.zkHost = zkHost;

        try {
            String services = ZkUtils.kafkaServices(zkHost);
            services.substring(0, services.lastIndexOf(","));
            if (Strings.isNullOrEmpty(services)) throw new Exception("");
            logger.info("Kafka Service : [{}]", services);

            final Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, services);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaProducer = new KafkaProducer<>(props);
        } catch (Exception e) {
            logger.error("" + e);
        }
        metric.register("logger.kafka.queue", (Gauge<Integer>) () -> queue.size());
        new Thread(new Task(), THREAD_NAME).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> isRunning.set(false)));

    }

    @Override
    public void put(Map<String, Object> src) {

        try {
            if (queue.size() > sinkMaxSize) {//日志队列超过sinkMaxSize 检查检查消费的线程是否死掉
                //获取监控线程
                Thread target = ThreadUtils.getThreadByName(THREAD_NAME);
                if (target == null) {//如果线程死掉
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
            logger.error("Put logger to logger'queue is interrupted" + e);
        } catch (Exception e) {
            logger.error("Put logger to logger'queue throw Exception" + e);
        }
    }

    @Override
    public void index(KafkaProducer<String, String> producer, ProducerRecord<String, String> record) {
        producer.send(record);
    }

    private static final AtomicBoolean isRunning = new AtomicBoolean(true);

    class Task implements Runnable {
        @Override
        public void run() {
            while (isRunning.get()) try {
                Map<String, Object> map = queue.poll(10, TimeUnit.MILLISECONDS);
                if (map != null && !map.isEmpty()){
                    index(kafkaProducer, new ProducerRecord<>(topic, JSON.toJSONString(map)));
                    metric.counter("kafka.sink.total").inc();
                }
            } catch (Exception e) {
                logger.error("Commit record failed !", e);
            }
        }
    }

}
