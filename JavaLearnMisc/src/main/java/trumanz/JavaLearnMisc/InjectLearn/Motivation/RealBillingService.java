package trumanz.JavaLearnMisc.InjectLearn.Motivation;

public class RealBillingService  implements BillingService {

	public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
		CreditCardProcessor processor = CreditCardProcessorFactory.getInstance();
		
		processor.charge(creditCard, order.getAmount());
		return null;
	}
	

}
