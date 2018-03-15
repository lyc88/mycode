package com.whyuan;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.javaapi.producer.Producer;

import java.util.Properties;
/**1.Prodecer可按自定义规则发送消息到各分区
 * 2.可异步发送：（优点：大大减少IO操作）
 *        策略一：消息到达某一数量（100条消息）发送，
 *        策略二：缓存固定时间（5S）后发送，
 * */
public class KafkaProducer extends Thread
{
    private final Producer<Integer, String> producer;
    private final String topic;
    //声明连接属性
    private final Properties props = new Properties();
    public KafkaProducer(String topic)
    {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", KafkaProperties.zkConnect);
        producer = new Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
    }
    @Override
    public void run() {
        int messageNo = 1;
        while (true)
        {
            String messageStr = new String("Message_" + messageNo);
            //指定生产的主题topic ,内容
            producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
            System.out.println("Send:" + messageStr);

            messageNo++;
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}