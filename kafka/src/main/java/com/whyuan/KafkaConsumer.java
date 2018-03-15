package com.whyuan;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 推还是拉（Push  or  pull）
 * Consumer以拉取的方式（pull,fetch）从broker消费数据
 *  优点 1、(避免了push方式下，推送速率>消费速率 的尴尬)
 *      2、consumer可自主决定是否批量拉取数据
 *
 * 消费状态跟踪
 * Consumer自己来维护消息的offset
 *  优点 1、服务端不用再去维护大量状态数据
 *      2、consumer可以把offset调成一个老的值，重新消费
 */
public class KafkaConsumer extends Thread
{
    private final ConsumerConnector consumer;
    private final String topic;
    public KafkaConsumer(String topic)
    {
        //创建消费者，连接kafka
        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = topic;
    }
    private static ConsumerConfig createConsumerConfig()
    {
        //声明连接属性
        Properties props = new Properties();
        //声明zk
        props.put("zookeeper.connect", KafkaProperties.zkConnect);
        // 必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据
        props.put("group.id", KafkaProperties.groupId);
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        //分区中的每个消息都有一个连续的序列号叫做offset,用来在分区中唯一的标识这个消息。
        props.put("auto.offset.reset", "smallest");
        return new ConsumerConfig(props);
    }
    @Override
    public void run() {
        //消费数据
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        //创建消息流
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> next = it.next();
            byte[] message = next.message();
            String str = new String(message);
            System.out.println("receive：" + str);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
