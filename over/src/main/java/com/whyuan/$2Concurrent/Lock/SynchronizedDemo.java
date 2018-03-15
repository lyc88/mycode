package com.whyuan.$2Concurrent.Lock;

/**
 * 多线程之间抢夺cpu进行工作
 * 并发：对于一个共享资源的读和写操作的同时进行，或者多个写操作并发进行。
 * 锁机制一：Synchronized(obj)
 * 考虑的问题：什么锁？锁哪里？
 * 锁对象选择问题：即obj:
 *   原则1：选共享资源：如本类中的成员变量name，
 *   原则2：新建对象锁：public static Object obj=new Object();
 *   原则3：类的字节码：LockDemo1.class   //因为.java文件加载到内存编译成.class文件唯一
 * @author asus
 *
 */
public class SynchronizedDemo {
    public static String name = "李雷";
    public static String gender = "男";

    public static void main(String[] args) {
        new Thread(new T15()).start();
        new Thread(new T16()).start();
    }

}

class T16 implements Runnable{

    @Override
    public void run() {
        while(true){
            synchronized (SynchronizedDemo.class) {
                if ("李雷".equals(SynchronizedDemo.name)) {
                    SynchronizedDemo.name = "韩梅梅";
                    SynchronizedDemo.gender = "女";
                } else {
                    SynchronizedDemo.name = "李雷";
                    SynchronizedDemo.gender = "男";
                }
            }
        }
    }

}
class T15 implements Runnable{

    @Override
    public void run() {
        while(true){
            synchronized (SynchronizedDemo.class) {
                System.out.println(SynchronizedDemo.name + ":" +SynchronizedDemo.gender);
            }
        }
    }

}
