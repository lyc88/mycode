package com.whyuan.$2Concurrent;

import java.util.concurrent.Exchanger;

/**
 * 交换机：表示一种俩个线程可以进行互相交换对象的汇合点。
 * 模拟俩人在一个地点交换暗号
 * @author asus
 * “表示一种两个线程可以进行互相交换对象的会和点”
 *
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new T9(exchanger)).start();
        new Thread(new T10(exchanger)).start();
    }
}

class T10 implements Runnable{
    private Exchanger<String> exchanger = null;

    public T10(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("T10到达了交换机。。。");
            String result = exchanger.exchange("小鸡炖蘑菇");
            System.out.println("T10收到了数据："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class T9 implements Runnable{
    private Exchanger<String> exchanger = null;

    public T9(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("T9到达了交换机。。。");
            String result = exchanger.exchange("天王盖地虎");
            System.out.println("T9收到了数据："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}