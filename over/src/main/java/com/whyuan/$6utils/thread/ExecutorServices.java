package com.whyuan.$6utils.thread;
import org.apache.lucene.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ExecutorServices {
	private static final Logger logger = LoggerFactory.getLogger(ExecutorServices.class);
	public static final int processors = Runtime.getRuntime().availableProcessors();
	public static final RejectedExecutionHandler handler =  new RejectedExecutionHandler() {
		@Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
	};

	public static ThreadPoolExecutor newThreadPoolExecutor(String threadNamePrefix) {
		return newThreadPoolExecutor(processors, threadNamePrefix);
	}

	public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize, int maxCoreNum, String threadNamePrefix) {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				corePoolSize,
				maxCoreNum,
				60L,
				TimeUnit.SECONDS,
				workQueue,
				new NamedThreadFactory(threadNamePrefix)
		);
		return executor;
	}

	public static ThreadPoolExecutor newThreadPoolExecutor(int maxCoreNum, String threadNamePrefix) {
		return newThreadPoolExecutor(processors, maxCoreNum, threadNamePrefix);
	}

	/**
	 * with block
	 *
	 * @param maxCoreNum
	 * @param threadNamePrefix
	 * @return
	 */
	public static final ThreadPoolExecutor newThreadPoolExecutorWB(String threadNamePrefix) {
		return newThreadPoolExecutorWB(processors, threadNamePrefix);
	}

	/**
	 * with block
	 *
	 * @param maxCoreNum
	 * @param threadNamePrefix
	 * @return
	 */
	public static final ThreadPoolExecutor newThreadPoolExecutorWB(int maxCoreNum, String threadNamePrefix) {
		return newThreadPoolExecutorWB(maxCoreNum, maxCoreNum, threadNamePrefix);
	}

	/**
	 * with block
	 *
	 * @param maxCoreNum
	 * @param threadNamePrefix
	 * @return
	 */
	public static final ThreadPoolExecutor newThreadPoolExecutorWB(int corePoolSize, int maxCoreNum, String threadNamePrefix) {
		final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maxCoreNum);
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(
				corePoolSize,
				maxCoreNum,
				60L,
				TimeUnit.SECONDS,
				workQueue,
				new NamedThreadFactory(threadNamePrefix),
				handler
		);
		return executor;
	}

	/**
	 * with block
	 *
	 * @param maxCoreNum
	 * @param threadNamePrefix
	 * @return
	 */
	public static final ThreadPoolExecutor newThreadPoolExecutorWB(
			final int corePoolSize,
			final int maxCoreNum,
			final int queueSize,
			final String threadNamePrefix) {
		final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(queueSize);
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(
				corePoolSize,
				maxCoreNum,
				60L,
				TimeUnit.SECONDS,
				workQueue,
				new NamedThreadFactory(threadNamePrefix),
				handler
		);
		return executor;
	}

	/**
	 * for hbase.
	 *
	 * corePoolSize availableProcessors
	 * maximumPoolSize Integer.MAX_VALUE
	 * keepAliveTime 10min
	 *
	 * @return ExecutorService
	 */
	public static final ThreadPoolExecutor hbaseExecutor() {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				processors,
				Integer.MAX_VALUE,
				600L,
				TimeUnit.SECONDS,
				workQueue,
				new NamedThreadFactory("hbase")
		);
		return executor;
	}
}
