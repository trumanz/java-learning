package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Pig implements Animal {
	
	protected SkinColor skinColor;
	
	@Inject
	public Pig(@Named("Red") SkinColor skinColor){
		this.skinColor = skinColor;
	}
	public String typeName() {
		return skinColor.color() + "Pig";
	}

}
