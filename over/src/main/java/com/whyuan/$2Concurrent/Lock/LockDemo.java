package com.whyuan.$2Concurrent.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁机制二：Lock接口,实现类ReentrantLock()
 * 重进入锁：全局锁.
 * 优点:比Synchronized更加灵活，精细。
 * @author asus
 *
 */
public class LockDemo {

    public static String name = "李雷";
    public static String gender = "男";

    public static void main(String[] args) {
        //重入锁
        Lock lock = new ReentrantLock();

        new Thread(new T17(lock)).start();
        new Thread(new T18(lock)).start();
    }

}

class T17 implements Runnable{
    private Lock lock = null;
    public T17(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            if ("李雷".equals(LockDemo.name)) {
                LockDemo.name = "韩梅梅";
                LockDemo.gender = "女";
            } else {
                LockDemo.name = "李雷";
                LockDemo.gender = "男";
            }
            lock.unlock();
        }
    }

}
class T18 implements Runnable{
    private Lock lock = null;
    public T18(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            System.out.println(LockDemo.name + ":" +LockDemo.gender);
            lock.unlock();
        }
    }

}
