package trumanz.JavaLearnMisc.InjectLearn;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * Unit test for simple App.
 */
public class BindingTest

{
	public static Logger logger = Logger.getLogger(BindingTest.class);

	private Injector injector = null;

	@SuppressWarnings("deprecation")
	@Before
	public void setUP() {
		Assert.assertNull(injector);
		injector = Guice.createInjector(new BindingModule());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test1LinkedBinding() {
		// The BillingModule register the BillingService to RealBillingService
		Animal animal = injector.getInstance(Animal.class);

		Assert.assertEquals("BlackHuskyDog", animal.typeName());
		Assert.assertNotSame(animal.getClass(), Dog.class);
		Assert.assertTrue(Dog.class.isInstance(animal));
		Assert.assertTrue(HuskyDog.class.isInstance(animal));

		SkinColor color = injector.getInstance(SkinColor.class);
		Assert.assertTrue(BlackSkinColor.class.isInstance(color));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void test2BindAnnotation() {
		Animal animal = injector.getInstance(Pig.class);
		Assert.assertEquals(animal.typeName(), "RedPig");

	}

	@Test
	public void test3InstanceBindings() {

		String jdbc = injector.getInstance(Key.get(String.class,
				Names.named("JDBC URI")));
		Assert.assertEquals(jdbc, "jdbc:mysql://localhost/dbname");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test4ProvidesMethods() {

		Integer integer = injector.getInstance(Integer.class);
		Assert.assertEquals(integer.intValue(), 1000);

		Float f = injector
				.getInstance(Key.get(Float.class, Names.named("1.0")));
		Assert.assertEquals(f.floatValue(), 1.0, 0.00002);

		f = injector.getInstance(Key.get(Float.class, Names.named("2.0")));
		Assert.assertEquals(f.intValue(), 2.0, 0.00002);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void test5ProviderBindings() {
		Float f = injector.getInstance(Float.class);
		Assert.assertEquals(f.intValue(), 3.0, 0.00002);
	}

	
	
	
}
