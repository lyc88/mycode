package com.gupao.vip.mic.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JmsTopicSender {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("" +
                "tcp://192.168.158.130:61616");
        Connection connection=null;
        try {
            //创建连接
            connection=connectionFactory.createConnection();
            connection.start();

            Session session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建队列（如果队列已经存在则不会创建， first-queue是队列名称）
            //destination表示目的地
            Destination destination=session.createTopic("first-topic");
            //创建消息发送者
            MessageProducer producer=session.createProducer(destination);
            TextMessage textMessage=session.createTextMessage("今天心情，晴转多云");
            producer.send(textMessage);
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
