package com.whyuan;
public class KafkaDemo {
    public static void main(String[] args) {
        /*//生产数据的线程
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();*/
        //消费数据的线程
        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
        consumerThread.start();
    }

}
