package com.whyuan.$2Concurrent.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//1.java.util.concurrent.ExecutorService 接口表示一个异步执行机制，使我们能够在后台执行任务。因此一个 ExecutorService 很类似于一个线程池。
//2.实现类：  ThreadPoolExecutor   ScheduledThreadPoolExecutor
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        /*
        线程池的5种创建方式
         */
        //核心池=最大池=1，工作队列LinkedBlockingQueue无边界
        //只有一个线程的线程池，因此所有提交的任务是顺序执行，处理不过来的任务进入FIFO队列等待执行
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        //核心池=最大池=指定大小，工作队列LinkedBlockingQueue无边界：
        //创建一个固定大小的线程池，使用无边界的阻塞式队列存放任务
        //拥有固定线程数的线程池，如果没有任务执行，那么线程会一直等待
        //在构造函数中的参数4是线程池的大小，你可以随意设置，也可以和cpu的数量保持一致，
        // 获取cpu的数量int cpuNums = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);

        //核心池0，最大池无边，保活时间1分钟，工作队列1：
        //线程池里有很多线程需要同时执行，老的可用线程将被新的任务触发重新执行，如果线程超过60秒内没执行，那么将被终止并从池中删除，
        //对于执行很多短期异步任务的程序而言，这些线程池通常可以提高程序性能
        ExecutorService executorService3 = Executors.newCachedThreadPool();


        //用来调度即将执行的任务的线程池，（周期性线程任务）
        ExecutorService executorService4 = Executors.newScheduledThreadPool(10);

        //只有一个线程，用来调度执行将来的任务
        ExecutorService executorService5=Executors.newSingleThreadScheduledExecutor();
    }
}

/*
3.提交任务的方法：
  execute(Runnable)
  submit(Runnable)     返回一个Future对象，用来检查任务是否执行完毕
  submit(Callable)     Callable call()的结果可以通过返回的Future对象进行获取
  invokeAny(...)
  invokeAll(...)
*
* invokeAny() 方法要求一系列的 Callable 或者其子接口的实例对象。调用这个方法并不会返
回一个 Future，但它返回其中一个 Callable 对象的结果。无法保证返回的是哪个 Callable
的结果 - 只能表明其中一个已执行结束。
如果其中一个任务执行结束(或者抛了一个异常)，其他 Callable 将被取消。
*
* invokeAll() 方法将调用你在集合中传给 ExecutorService 的所有 Callable 对象。invokeAll()
返回一系列的 Future 对象，通过它们你可以获取每个 Callable 的执行结果。
记住，一个任务可能会由于一个异常而结束，因此它可能没有 "成功"。无法通过一个 Future
对象来告知我们是两种结束中的哪一种。
*
4.关闭
shutdown():不再接受新的任务，当前所有任务执行完毕再关闭。
shutdownNow();强制关闭，会终止当前执行中的任务。
* */
