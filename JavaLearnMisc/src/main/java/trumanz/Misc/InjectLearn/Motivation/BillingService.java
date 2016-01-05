package trumanz.Misc.InjectLearn.Motivation;

public interface BillingService {
	Receipt chargeOrder(PizzaOrder order, CreditCard creditCard);
}
