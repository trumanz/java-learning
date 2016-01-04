package trumanz.JavaLearnMisc.ConcurrencyTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class NotifyTest {

	@Test
	public void waitAfterNotify() throws InterruptedException, BrokenBarrierException {
		final Object obj = new Object();
		final CyclicBarrier barrier = new CyclicBarrier(2);
		ExecutorService exeService = Executors.newCachedThreadPool();

		exeService.submit(new Runnable() {
			public void run() {

				synchronized (obj) {
					obj.notify();
				}

				try {
					barrier.await();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		barrier.await();
		synchronized (obj) {
			long m1 = System.currentTimeMillis();
			obj.wait(1000 * 1);
			m1 = System.currentTimeMillis() - m1;
			Assert.assertTrue(m1 >= 1000);
		}

	}
	
	
	
	@Test
	public void waitBeforeNotify() throws InterruptedException, BrokenBarrierException {
		final Object obj = new Object();
		final CyclicBarrier barrier = new CyclicBarrier(2);
		ExecutorService exeService = Executors.newCachedThreadPool();

		exeService.submit(new Runnable() {
			public void run() {
				try {
					barrier.await();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				synchronized (obj) {
					obj.notify();
				}

				
			}
		});
		
		synchronized (obj) {
			barrier.await();
			long m1 = System.currentTimeMillis();
			obj.wait(1000 * 1);
			m1 = System.currentTimeMillis() - m1;
			Assert.assertTrue(m1 < 20);
		}

	}
}
