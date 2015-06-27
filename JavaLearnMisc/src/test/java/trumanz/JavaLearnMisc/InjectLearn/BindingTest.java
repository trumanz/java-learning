package trumanz.JavaLearnMisc.InjectLearn;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * Unit test for simple App.
 * http://searchcode.com/codesearch/view/20625377/
 */
public class BindingTest

{
	public static Logger logger = Logger.getLogger(BindingTest.class);

	//private Injector injector = null;

	@SuppressWarnings("deprecation")
	@Before
	public void setUP() {
		//Assert.assertNull(injector);
		//injector = Guice.createInjector(new BindingModule());
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void test1LinkedBinding() {
		// The BillingModule register the BillingService to RealBillingService
		
		Injector injector =  Guice.createInjector(new AbstractModule(){

			@Override
			protected void configure() {
				bind(Throwable.class).to(Exception.class);
				bind(Exception.class).to(IOException.class);
				bind(Object.class).to(String.class);
			}
			
		});
		Throwable throwable = injector.getInstance(Throwable.class);

		Assert.assertNotSame(throwable.getClass(), Exception.class);
		Assert.assertTrue(Exception.class.isInstance(throwable));
		Assert.assertTrue(IOException.class.isInstance(throwable));
		
		Object obj = injector.getInstance(Object.class);
		Assert.assertTrue(String.class.isInstance(obj));

	}

		
	@SuppressWarnings("deprecation")
	@Test
	public void test2BindAnnotation() {
		
		Injector injector  = Guice.createInjector(new AbstractModule(){

			@Override
			protected void configure() {
				bind(Runnable.class).to(TestRunnable.class);
				bind(Object.class).annotatedWith(Names.named("ObjType")).to(String.class);
			}
			
		});
		Runnable runnalbe = injector.getInstance(Runnable.class);
		Assert.assertEquals(runnalbe.getClass(), TestRunnable.class);
		Assert.assertEquals(((TestRunnable)runnalbe).getObj().getClass(), String.class);
	}

	@Test
	public void test3InstanceBindings() {
		
		Injector injector = Guice.createInjector(new AbstractModule(){

			@Override
			protected void configure() {
				bind(String.class).annotatedWith(Names.named("JDBC URI")).toInstance("jdbc:mysql://localhost/dbname");
			}
		});

		String jdbc = injector.getInstance(Key.get(String.class,
				Names.named("JDBC URI")));
		Assert.assertEquals(jdbc, "jdbc:mysql://localhost/dbname");
				
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test4ProvidesMethods() {
		
		Injector injector = Guice.createInjector(new AbstractModule(){

			@Override
			protected void configure() {}
			
			//ProvidesMethods, The return type must be unique unless use Named
			@Provides
			Integer getOneThousand(){ return new Integer(1000); }
			
			@Provides @Named("1.0")
			Float getOne(){ return new Float(1.0);}
			
			@Provides @Named("2.0")
			Float getTow(){ return new Float(2.0);}
			
		});

		Integer integer = injector.getInstance(Integer.class);
		Assert.assertEquals(integer.intValue(), 1000);

		Float f = injector
				.getInstance(Key.get(Float.class, Names.named("1.0")));
		Assert.assertEquals(f.floatValue(), 1.0, 0.00002);

		f = injector.getInstance(Key.get(Float.class, Names.named("2.0")));
		Assert.assertEquals(f.floatValue(), 2.0, 0.00002);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void test5ProviderBindings() {
		
		Injector injector  = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(Object.class).toProvider( new Provider<Float>(){
					public Float get() {
						// TODO Auto-generated method stub
						return new Float(8.88f);
					}
				});
			}
			
		});
		Object f = injector.getInstance(Object.class);
		Assert.assertEquals(f.getClass(), Float.class);
		Assert.assertEquals(8.88f, ((Float)f).floatValue(), 0.00002);
	}

	@Test
	public void test6UntargetedBindings() {
		//TODO, no understanding the document
		//https://github.com/google/guice/wiki/UntargettedBindings
	}
	
	
	@Test
	public void test7ContructorBindings(){
		Injector injector  = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(String.class).toInstance("5.0");
				try{
					bind(Double.class).toConstructor(Double.class.getConstructor(String.class));
				} catch(NoSuchMethodException e){
					addError(e);
				}
			}
		});
		Double d = injector.getInstance(Double.class);
		Assert.assertEquals(d.intValue(), 5.0, 0.00002);
	}
	
	@Test
	public void test8BuiltInBindings(){
		//TODO 
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test9JustInTimeBindgs(){
		Injector injector  = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {}
		});
		
		Animal  animal =  injector.getInstance(Animal.class);
		Assert.assertEquals(animal.getClass(), AnimalDog.class);
		
		Plant plant = injector.getInstance(Plant.class);
		Assert.assertEquals(plant.getClass(), PlantTree.class);
		
	}
	
}
