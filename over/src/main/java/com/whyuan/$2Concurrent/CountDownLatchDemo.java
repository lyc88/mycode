package com.whyuan.$2Concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 线程递减锁（闭锁）：模拟买菜做饭
 * 要求：必须等线程执行完（做饭材料准备好）才开始执行主线程代码（做饭）
 * CountDownLatch是一个并发构造，它允许一个或多个线程等待一系列指定操作的完成。
 * “通过调用 await() 方法之一，线程可以阻塞等待这一数量到达零”
 * @author asus
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(3);//指定需要等待几个线程

        new Thread(new T3(cdl)).start();//1秒
        new Thread(new T4(cdl)).start();//2秒
        new Thread(new T5(cdl)).start();//3秒

        //await方法将会产生阻塞，直到cdl中的数字被递减为0才会释放阻塞，继续执行后续代码
        cdl.await();
        //要求：必须等线程执行完（做饭材料准备好）才开始执行主线程代码（做饭）
        System.out.println("开始做饭。。。");
    }
}

/**
 * 买锅3秒
 */
class T5 implements Runnable{
    private CountDownLatch cdl = null;
    public T5(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("T5买锅回来了。。。。");
            cdl.countDown();//执行完将闭锁空间减1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 买米2秒
 */
class T4 implements Runnable{
    private CountDownLatch cdl = null;
    public T4(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("T4买米回来了。。。。");
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 买菜1秒
 */
class T3 implements Runnable{
    private CountDownLatch cdl = null;
    public T3(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("T3买菜回来了。。。。");
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}