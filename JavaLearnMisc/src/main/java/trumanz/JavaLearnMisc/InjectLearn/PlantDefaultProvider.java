package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Provider;

public class PlantDefaultProvider implements Provider<Plant>{

	public Plant get() {
		// TODO Auto-generated method stub
		return new PlantTree();
	}

}


