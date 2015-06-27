package trumanz.JavaLearnMisc.InjectLearn.Motivation;

public class CreditCard {

	private int creditLine = 100;
	public CreditCard(String string, int i, int j) {
		// TODO Auto-generated constructor stub
	}
	public int getLine() {
		
		return creditLine;
	}
	public void subLine(int amount) {
		creditLine = creditLine - amount;
		
	}

}
