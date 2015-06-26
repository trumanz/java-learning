package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;

public class Pig implements Animal {


	@Inject
	public Pig(){
		
	}
	public String typeName() {
		return "Pig";
	}

}
