package javalearn.misc.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

public class IntrruptTask {

	boolean  finished = false;
	
	@Test
	public void interrupt() throws InterruptedException  {
		
		final Object obj = new Object();
		final CyclicBarrier barrier_InterrputRunningThread = new CyclicBarrier(2);
		final CyclicBarrier barrier_WaitNotify = new CyclicBarrier(2);
		ExecutorService exeService = Executors.newCachedThreadPool();
		
	
		finished = false;
		
		Future<?> future = exeService.submit(new Runnable(){

			public void run() {
				try {
					barrier_InterrputRunningThread.await();
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
				try {
					Thread.sleep(1000*10);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
				try {
					barrier_WaitNotify.await();
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
				synchronized(obj){
					
					finished =  true;
					obj.notify();
				}
				
			}
			
		});
		
		try {
			barrier_InterrputRunningThread.await();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		future.cancel(true);
		
		try {
			future.get(1, TimeUnit.SECONDS);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e){
			
			//finished = true;
		}
		
		synchronized(obj){
			try {
				barrier_WaitNotify.await();
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
			Assert.assertFalse(finished);
		 
				obj.wait();
			 
		}
		
		
	}
}
		
