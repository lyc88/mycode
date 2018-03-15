package threadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.Runnable;//注意此包得手动引入，否则88行代码：(java.lang.Runnable)runnable.run();

/**
 * ThreadPool 具体实现
 *
 */
public class ThreadPoolManager implements ThreadPool{
	//默认的工作线程个数
	private static int workerNumber=5;
	//工作线程数组
	WorkThread[] workThreads;
	//正在执行的任务数量,volatile保证该变量在各线程可见，并不保证互斥性。
	//功能：保证一定能看到最新的工作线程数。
	private static volatile int executeTaskNumber=0;
	//任务队列，作为一个缓存，List线程不安全。
	private BlockingQueue<Runnable> taskQueue=new LinkedBlockingQueue<Runnable>();
	//原子操作线程数量
	private AtomicLong threadNum=new AtomicLong();
	private static ThreadPoolManager threadPoolManager;

	//门面模式的构造方法：
	//优势：可接受各种参数,参数为空，就给它传一个默认的进去。
	//具体逻辑由有参数的构造方法实现。
	private ThreadPoolManager() {
		this(workerNumber);
	}

	//负责工作线程的初始化工程和启动。
	private ThreadPoolManager(int newWorkerNumber) {
		if(newWorkerNumber>0){
			this.workerNumber=newWorkerNumber;
		}
		workThreads=new WorkThread[newWorkerNumber];
		for(int i=0;i<newWorkerNumber;i++){
			workThreads[i]=new WorkThread();//将工作线程实例化
			//标志该线程
			workThreads[i].setName("ThreadPool-worker"+threadNum.incrementAndGet());
			System.out.println("init Thread："+(i+1)+"/"+(newWorkerNumber)+" ----Current Thread name："+workThreads[i].getName());
			workThreads[i].start();
		}
	}

	//负责生产任务将任务加入队列
	@Override
	public void execute(Runnable task) {
		synchronized (taskQueue) {
			try {
				taskQueue.put(task);//任务生产
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			taskQueue.notifyAll();//释放条件队列锁
		}
	}

	@Override
	public void execute(Runnable[] tasks) {
		synchronized (taskQueue) {
			for(Runnable task:tasks){
				try {
					taskQueue.put(task);//任务生产
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			taskQueue.notifyAll();//释放条件队列锁
		}
	}

	@Override
	public void execute(List<Runnable> tasks) {
		synchronized (taskQueue) {
			for(Runnable task:tasks){
				try {
					taskQueue.put(task);//任务生产
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			taskQueue.notifyAll();//释放条件队列锁
		}
	}

	@Override
	public int getExecuteTaskNumber() {
		return executeTaskNumber;
	}

	@Override
	public int getWaitTaskNumber() {
		return taskQueue.size();
	}

	@Override
	public int getWorkThreadNumber() {
		return workerNumber;
	}

	//jdk提供的获取线程池的方式
	public static ThreadPool getThreadPool(){
		return getThreadPool(workerNumber);
	}
	public static ThreadPool getThreadPool(int newWorkerNumber){
		if(newWorkerNumber<=0){//如果获取数小于或者等于0，则给它默认的workerNumber
			newWorkerNumber=workerNumber;
		}
		if(threadPoolManager==null){
			threadPoolManager=new ThreadPoolManager(newWorkerNumber);
		}
		return threadPoolManager;
	}

	//Tomcat 线程池关闭方法
	@Override
	public void destroy() {
		//不断地监控线程状态
		while(!taskQueue.isEmpty()){//任务队列不为空
			try {
				Thread.sleep(5);
				//可能在最后一次睡眠中的这段时间，已经处理完了任务队列里的任务。
				if(taskQueue.isEmpty()){
					System.out.println("During the last sleep, the task of consuming the task queue...The recovery operation will be performed");//最后一次睡眠期间消费完任务队列里的任务......将进行回收操作
				}else{
					System.out.println("Ongoing monitoring of thread status...");//正在持续监控线程状态中......
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//此处开始：代表任务队列已为空
		for(int i=0;i<workerNumber;i++){
			workThreads[i].stopWorker();//isRunning=false;
			workThreads[i]=null;//工作线程对象置为空，保证加快回收
		}
		threadPoolManager=null;//线程池对象置为空，
		taskQueue.clear();//任务队列清空。
		System.out.println("Resource recovery...");//资源回收完毕......
	}

	/**
	 * 内部类，工作线程
	 * 用于消费任务队列中的任务
	 */
	private class WorkThread extends Thread{
		//该工作线程是否有效，用于结束该工作线程
		private boolean isRunning=true;

		/*
		 * 若任务队列不为空，则取出任务执行，且任务执行数+1,runnale对象重置为null(控制程序循环)
		 * 若任务队列为空，则等待。
		 */
		@Override
		public void run() {
			//接收队列当中的任务对象,任务对象为Runnable类型。
			Runnable runnable=null;
			while(isRunning){
				synchronized(taskQueue){
					while(isRunning && taskQueue.isEmpty()){//工作线程有效&&任务队列为空：等待
						try{
							taskQueue.wait(20);
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
					if(!taskQueue.isEmpty()){//任务队列不为空：取出执行
						try{
							runnable=taskQueue.take();
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
					if(runnable !=null){//任务不为空：执行
						runnable.run();
					}
					executeTaskNumber++;
					runnable=null;
				}
			}

		}
		//停止工作，让该线程自然执行完run方法，自然结束
		public void stopWorker(){
			isRunning=false;
		}

	}

	@Override
	public String toString() {
		return "当前工作线程数为："+workerNumber+
				"。已经完成任务数为："+executeTaskNumber+
				"。等待任务数为："+taskQueue.size();
	}

}
