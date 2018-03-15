package com.whyuan.$2Concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏：是一个所有线程必须等待的一个栅栏，直到所有线程到达这里，然后所有线程才可以继续做之后的事情
 * 模拟一群马在栅栏处等待，所有到达栅栏处一起开跑。
 * @author asus
 *
 * “通过调用 CyclicBarrier 对象的 await() 方法，两个线程可以实现互相等待。一旦 N 个线程
		在等待 CyclicBarrier 达成，所有线程将被释放掉去继续运行”
 *
 */
public class CyclicBarrierDemo {
	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(3);

		new Thread(new T6(cb)).start();
		new Thread(new T7(cb)).start();
		new Thread(new T8(cb)).start();
	}
}

class T8 implements Runnable{
	private CyclicBarrier cb = null;

	public T8(CyclicBarrier cb) {
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			System.out.println("T8到达了栅栏。。开始等待。。");
			cb.await();
			System.out.println("T8被释放了。。接着执行。。。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class T7 implements Runnable{
	private CyclicBarrier cb = null;

	public T7(CyclicBarrier cb) {
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			System.out.println("T7到达了栅栏。。开始等待。。");
			cb.await();
			System.out.println("T7被释放了。。接着执行。。。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class T6 implements Runnable{
	private CyclicBarrier cb = null;

	public T6(CyclicBarrier cb) {
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("T6到达了栅栏。。开始等待。。");
			cb.await();//栅栏处等待
			System.out.println("T6被释放了。。接着执行。。。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}