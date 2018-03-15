package com.whyuan.$2Concurrent.Lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**读写锁：若俩线程只对共享资源进行读读操作，传统锁将加锁 ，而加读写锁对于读读操作是共享的，提高多线程同时读共享资源的效率
 * 读 读 共享
 * 写 读 排斥
 * 写 写 排斥
 * “读写锁，有写才排斥”
 * @author asus
 *
 */
public class ReadWriteLockDemo {

    public static String name = "李雷";
    public static String gender = "男";

    public static void main(String[] args) {

        ReadWriteLock lock = new ReentrantReadWriteLock();

        new Thread(new T19(lock)).start();
        new Thread(new T20(lock)).start();
        new Thread(new T20(lock)).start();
    }

}

class T19 implements Runnable{
    private ReadWriteLock lock = null;
    public T19(ReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            lock.writeLock().lock();
            if ("李雷".equals(ReadWriteLockDemo.name)) {
                ReadWriteLockDemo.name = "韩梅梅";
                ReadWriteLockDemo.gender = "女";
            } else {
                ReadWriteLockDemo.name = "李雷";
                ReadWriteLockDemo.gender = "男";
            }
            lock.writeLock().unlock();
        }
    }

}
class T20 implements Runnable{
    private ReadWriteLock lock = null;
    public T20(ReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            lock.readLock().lock();
            System.out.println(Thread.currentThread()+":"+ReadWriteLockDemo.name + ":" +ReadWriteLockDemo.gender);
            lock.readLock().unlock();
        }
    }

}

