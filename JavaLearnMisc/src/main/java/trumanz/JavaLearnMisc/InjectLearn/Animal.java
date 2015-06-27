package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.ImplementedBy;

// equivalent to   bind(Animal.class).to(Dog.class);
@ImplementedBy(Dog.class)
public interface  Animal {
	
	public String typeName();
	
}
