package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class AnimalModule extends AbstractModule  {

	@Override
	protected void configure() {
		bind(Animal.class).to(Pig.class);
		bind(Animal.class).annotatedWith(Names.named("Dog")).to(Dog.class);
	}

}
