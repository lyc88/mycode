package threadPool;

import java.util.List;

/**
 *面向接口编程，定义线程池的功能实现
 *
 */
public interface ThreadPool {
    //执行一个Runnable类型的任务
    void execute(Runnable task);

    void execute(Runnable[] tasks);

    void execute(List<Runnable> tasks);

    //返回执行任务的个数
    int getExecuteTaskNumber();

    //返回任务队列的个数(还没有处理的任务)
    int getWaitTaskNumber();

    //返回工作线程个数
    int getWorkThreadNumber();

    //关闭线程池
    void destroy();

}
