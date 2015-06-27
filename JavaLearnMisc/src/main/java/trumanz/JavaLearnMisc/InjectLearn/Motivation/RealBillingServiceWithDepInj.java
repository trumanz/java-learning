package trumanz.JavaLearnMisc.InjectLearn.Motivation;

import com.google.inject.Inject;

public class RealBillingServiceWithDepInj  implements BillingService {
	
	private final CreditCardProcessor processor;
	
	@Inject
	public RealBillingServiceWithDepInj(CreditCardProcessor processor){
		this.processor = processor;
	}

	public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
		
		processor.charge(creditCard, order.getAmount());
		return null;
	}
	

}