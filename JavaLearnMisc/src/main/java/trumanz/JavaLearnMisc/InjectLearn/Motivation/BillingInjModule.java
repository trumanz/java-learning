package trumanz.JavaLearnMisc.InjectLearn.Motivation;

import com.google.inject.AbstractModule;

public class BillingInjModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
		bind(BillingService.class).to(RealBillingServiceWithDepInj.class);
	}
	
	
}
