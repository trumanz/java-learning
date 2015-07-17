package trumanz.JavaLearnMisc.InjectLearn;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;



public class SingletonTest {
	
	@Test
	public void test(){
		

		 A.instanceCount  = 0;
		
		Injector injector  = Guice.createInjector( new AbstractModule(){

			@Override
			protected void configure() {
			
			}
			
		});
		
		
		
		D d1 = injector.getInstance(D.class);
		D d2 = injector.getInstance(D.class);
		Assert.assertNotNull(d1.a);
		Assert.assertTrue(d1 != d2);
		Assert.assertTrue(d1.a == d2.a);
		Assert.assertEquals(1,  A.instanceCount);
		
	}
	
	
	@Test
	public void testWithMultiInjector(){
		A.instanceCount = 0;
		
		Injector injector1  = Guice.createInjector( new AbstractModule(){

			@Override
			protected void configure() {
			
			}
			
		});
		Injector injector2  = Guice.createInjector( new AbstractModule(){

			@Override
			protected void configure() {
			
			}
			
		});
		
		
		Assert.assertEquals(0,  A.instanceCount);
		
		D d1 = injector1.getInstance(D.class);
		D d2 = injector2.getInstance(D.class);
		Assert.assertNotNull(d1.a);
		Assert.assertTrue(d1 != d2);
		Assert.assertTrue(d1.a != d2.a);
		Assert.assertEquals(2,  A.instanceCount);
		
	}
	
	static class D{
		@Inject
		public A a;
		
	};
	
	@Singleton
	static class A{
		static int instanceCount = 0;
		int instanedId = instanceCount++;
		
	}

}
