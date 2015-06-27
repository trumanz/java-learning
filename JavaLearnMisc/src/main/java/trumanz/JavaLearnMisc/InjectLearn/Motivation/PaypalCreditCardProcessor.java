package trumanz.JavaLearnMisc.InjectLearn.Motivation;

public class PaypalCreditCardProcessor implements CreditCardProcessor {

	public boolean charge(CreditCard creditCard, int amount) {
		if(creditCard.getLine() < amount){
			return false;
		} else {
			creditCard.subLine(amount);
			return true;
		}
		 
	}

}
