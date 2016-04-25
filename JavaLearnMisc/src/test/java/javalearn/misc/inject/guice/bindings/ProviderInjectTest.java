package javalearn.misc.inject.guice.bindings;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;


public class ProviderInjectTest {
	
	@Test
	public void test(){
		Injector injector = Guice.createInjector(new AbstractModule(){
			protected void configure() {
			
			}
		});
		A a1 = injector.getInstance(A.class);
		E e1 = a1.getE();
		A a2 = injector.getInstance(A.class);
		E e2 = a2.getE();
		
		Assert.assertTrue(a1 != a2);
		Assert.assertTrue(e1 != e2);
		
	}
	
	static class A{
		@Inject
		Provider<E> ep;
		
		public E getE(){
			return ep.get();
		}
		
	}
	
	static class E{
		
	}
	
	

}
