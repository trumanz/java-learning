package trumanz.Misc.InjectLearn.Motivation;

public class PizzaOrder {
	private int count = 0;
	public PizzaOrder(int i) {
		this.count = i;
	}
	public int getAmount(){
		return count;
	}
}
