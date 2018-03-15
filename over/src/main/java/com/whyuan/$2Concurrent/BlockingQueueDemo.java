package com.whyuan.$2Concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//双端阻塞式队列
//抛异常：add remove
//阻塞：  put take
//超时：  offer poll
public class BlockingQueueDemo {
    public static void main(String[] args) {
        //ArrayBlocking底层是数组必须指定队列的大小，无法创建无边界的队列
		BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

        //LinkedBlocingQueue底层是链表，可以设置边界大小也可以不设置，如果不设置默认大小为Integer.MaxValue，基本上可以认为是一个无限制的队列
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        new Thread(new Producer(linkedBlockingQueue)).start();//负责生产：从左侧插入线性队列
        new Thread(new Consumer(linkedBlockingQueue)).start();//负责消费：从右侧移除线性队列
    }
}

class Producer implements Runnable{
    private BlockingQueue<String> queue = null;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for(int i = 0;i<Integer.MAX_VALUE;i++){
                String str = "x"+i;
                queue.put(str);
                System.out.println(Thread.currentThread().getName()+"生产者生产了数据："+str);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
class Consumer implements Runnable{
    private BlockingQueue<String> queue = null;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for(int i = 0;i<Integer.MAX_VALUE;i++){
                String str = queue.take();
                System.out.println(Thread.currentThread().getName()+"消费者消费了数据："+str);
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
