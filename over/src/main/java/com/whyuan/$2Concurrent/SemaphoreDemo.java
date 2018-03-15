package com.whyuan.$2Concurrent;

import java.util.concurrent.Semaphore;

/**
 * Semaphore音译为"旗语"，专业术语“信号量”
 * acquire():拿票
 * release():还票
 * 1.用于保护一个重要（代码）部分防止一次超过N个线程进入
 * 2.在俩个线程之间发送信号。
 * 模拟重点保护景点中南海只能同时供3名游客凭票游览，进去从Semaphore拿票，出来还票给Semaphore
 * @author asus
 *
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //true表示游客需要排队领票，不加则谁抢到谁进。
        Semaphore semaphore = new Semaphore(3,true);

        for(int i=1;i<Integer.MAX_VALUE;i++){
            new Thread(new T11(semaphore,i)).start();
        }

    }
}

class T11 implements Runnable{
    private Semaphore semaphore = null;
    private int i;

    public T11(Semaphore semaphore,int i) {
        this.semaphore = semaphore;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("游客"+i+"得到了票，开始游览中南海。。。");
            Thread.sleep(i*1000);
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}