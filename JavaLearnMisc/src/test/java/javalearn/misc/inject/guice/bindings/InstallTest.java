package javalearn.misc.inject.guice.bindings;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class InstallTest  {

	public static interface MyInterface{
		String getPar();
	}
	
	public static class MyImpl implements MyInterface{

		final String par;
		
		@AssistedInject
		public MyImpl(@Assisted String par, Injector injector){
			this.par = par;
		}
		@AssistedInject
		public MyImpl(Injector injector){
			this.par = null;
		}
		public String getPar() {
			return par;
		}
	}
	
	public static  interface MyFactory{
		MyInterface create(String par);
		MyInterface createWithoutPar();
	}


	@Singleton
	static class MyClass{
	@Inject
	private MyFactory myFactory;
	}
	
	
	//static Injector injector = Guice.createInjector(new InstallTest());
	@Test 
	public void test(){
		Injector injector = Guice.createInjector(new AbstractModule(){

			@Override
			protected void configure() {
				install(new FactoryModuleBuilder()
						.implement(MyInterface.class, MyImpl.class)
						.build(MyFactory.class));
				
			}
			
		});
		
		MyClass my = injector.getInstance(MyClass.class);
			
		Assert.assertTrue(my.myFactory != null);
		
		MyInterface inf = my.myFactory.create("Hehe");
		Assert.assertEquals("Hehe", inf.getPar());
		
	}

}
