package dependencyInjection.guice.bindings;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class SingletonTest {

	static class D {
		@Inject
		public A a;// This object will be assign by a Injector if D object was created by injector

	};

	@Singleton
	static class A {
		static int instanceCount = 0;
		int instanedId = instanceCount++;

	}

	@Test
	public void getInstanceByInjector() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
			}
		});

		Assert.assertEquals(0, A.instanceCount);

		D d1 = injector.getInstance(D.class);
		D d2 = injector.getInstance(D.class);
		Assert.assertNotNull(d1.a);
		Assert.assertTrue(d1 != d2);
		Assert.assertTrue(d1.a == d2.a);
		Assert.assertEquals(1, A.instanceCount);

		Injector otherInjecor = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
			}
		});

		// each Injector will own one Singleton object
		D d3 = otherInjecor.getInstance(D.class);
		Assert.assertTrue(d1.a != d3.a);
		Assert.assertEquals(2, A.instanceCount);

	}
}
