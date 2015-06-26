package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;
import com.google.inject.name.Named;


public class Dog implements Animal {


	@Inject
	public Dog(@Named("Dog") int x){
		
	}
	
	public String typeName() {
		return "Dog";
	}

}
