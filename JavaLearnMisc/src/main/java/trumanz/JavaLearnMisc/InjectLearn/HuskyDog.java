package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;

public class HuskyDog extends  Dog{

	@Inject
	public HuskyDog(SkinColor skinColor) {
		super(skinColor);
		// TODO Auto-generated constructor stub
	}

	public String typeName() {
		return skinColor.color() + "HuskyDog";
	}

}
