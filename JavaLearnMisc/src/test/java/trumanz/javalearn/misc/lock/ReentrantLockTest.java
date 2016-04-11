package trumanz.javalearn.misc.lock;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class ReentrantLockTest {
	
	@Test
	public void testLockHoldCount(){
		ReentrantLock lock = new ReentrantLock();
		
		lock.lock();
		Assert.assertEquals(1, lock.getHoldCount());
		lock.unlock();
		
		lock.lock();
		lock.lock();
		Assert.assertEquals(2, lock.getHoldCount());
		lock.unlock();
		lock.unlock();
		Assert.assertEquals(0, lock.getHoldCount());
	}
	
	ReentrantLock glock = new ReentrantLock();
	CyclicBarrier gbarrier = new CyclicBarrier(2);
 	

	
	
	@Test
	public void multThreadLockTest() throws InterruptedException, BrokenBarrierException{
		
		Thread thread = new Thread(){
			@Override
			public void run(){
				glock.lock();
				glock.lock();
				try {
					//System.out.println("Thread locked");
					gbarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				//System.out.println("Thread unlocking");
				//lable2
				try {
					//System.out.println("Thread locked");
					gbarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				glock.unlock();
				glock.unlock();
				try {
					gbarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		};
		
		thread.start();
		gbarrier.await(); //wait  thread locked
		Assert.assertFalse(glock.tryLock());
		 
		gbarrier.await();// let thread run again from lable2
		gbarrier.await();//wait thread unlocked
		
		Assert.assertTrue(glock.tryLock());
		
		
	}

}
