package com.whyuan.$2Concurrent.ExecutorService;

import java.util.concurrent.*;

/*
* 定时执行者服务：
*
* */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(5);
        //延迟5秒后执行
        ScheduledFuture scheduledFuture =
                scheduledExecutorService.schedule(new Callable() {
                                                      public Object call() throws Exception {
                                                          System.out.println("Executed!");
                                                          return "Called!";
                                                      }
                                                  },
                        5,
                        TimeUnit.SECONDS);
        //get（）阻塞方法，获得任务执行的结果，
        System.out.println("result="+scheduledFuture.get());
        System.out.println("-----获得结果后关闭定时执行者服务-----");
        scheduledExecutorService.shutdown();
    }
}
