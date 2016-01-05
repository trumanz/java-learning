package trumanz.JavaLearnMisc.InjectLearn.Motivation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import trumanz.Misc.InjectLearn.Motivation.BillingInjModule;
import trumanz.Misc.InjectLearn.Motivation.BillingService;
import trumanz.Misc.InjectLearn.Motivation.CreditCard;
import trumanz.Misc.InjectLearn.Motivation.CreditCardProcessorFactory;
import trumanz.Misc.InjectLearn.Motivation.PaypalCreditCardProcessor;
import trumanz.Misc.InjectLearn.Motivation.PizzaOrder;
import trumanz.Misc.InjectLearn.Motivation.RealBillingService;
import trumanz.Misc.InjectLearn.Motivation.Receipt;

public class RealBillingServiceTest {
	
	private final PizzaOrder order = new PizzaOrder(100);
	private final CreditCard creditCard = new CreditCard("1234", 11, 2010);
	
	private final PaypalCreditCardProcessor processor = new PaypalCreditCardProcessor();
	
	@Before
	public void setUp(){
		CreditCardProcessorFactory.setInstance(processor);
	}
	
	@Test
	public void testSuccessfulCharge(){
		RealBillingService billingService = new RealBillingService();
		Receipt receipt = billingService.chargeOrder(order, creditCard);
		
	}
	
	@Test
	public void testWithInj(){
		Injector injector = Guice.createInjector(new BillingInjModule());
		BillingService billingService  = injector.getInstance(BillingService.class);
		Receipt receipt = billingService.chargeOrder(order, creditCard);
	}

}
