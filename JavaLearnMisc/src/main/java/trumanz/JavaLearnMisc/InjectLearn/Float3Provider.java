package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Provider;

public class Float3Provider implements Provider<Float>{

	public Float get() {
		// TODO Auto-generated method stub
		return new Float(3.0);
	}

}
