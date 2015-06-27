package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Duck implements Animal {
	
	protected SkinColor skinColor;
	
	@Inject
	public Duck(@Named("Duck") SkinColor skinColor){
		this.skinColor = skinColor;
	}
	public String typeName() {
		return skinColor.color() + "Duck";
	}

}