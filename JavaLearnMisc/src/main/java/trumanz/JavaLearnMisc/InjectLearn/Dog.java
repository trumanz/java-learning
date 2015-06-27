package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;
import com.google.inject.name.Named;


public class Dog implements Animal {
	
	protected SkinColor skinColor;

	@Inject
	public Dog(SkinColor skinColor){
		this.skinColor = skinColor;
	}
	
	
	public String typeName() {
		return skinColor.color() + "Dog";
	}

}
