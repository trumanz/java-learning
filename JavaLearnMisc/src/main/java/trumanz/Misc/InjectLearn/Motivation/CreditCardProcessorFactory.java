package trumanz.Misc.InjectLearn.Motivation;

public class CreditCardProcessorFactory {
	private static CreditCardProcessor instance;
	
	public static void setInstance(CreditCardProcessor processor){
		instance = processor;
	}
	
	public static CreditCardProcessor getInstance(){
		return instance;
	}
}
