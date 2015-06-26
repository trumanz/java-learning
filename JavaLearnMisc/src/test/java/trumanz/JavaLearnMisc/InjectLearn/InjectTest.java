package trumanz.JavaLearnMisc.InjectLearn;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;



/**
 * Unit test for simple App.
 */
public class InjectTest 
    
{
	public static Logger logger = Logger.getLogger(InjectTest.class);
	
	@SuppressWarnings("deprecation")
	@Test
	public void moduelInject(){
		//The BillingModule register the  BillingService to RealBillingService
		Injector injector = Guice.createInjector(new AnimalModule());
		Animal  animal = injector.getInstance(Animal.class);
		
		logger.info("animal is :" + animal.toString());
		
		Assert.assertTrue(Pig.class.isInstance(animal));
	}
	
	
	
	

}
  
