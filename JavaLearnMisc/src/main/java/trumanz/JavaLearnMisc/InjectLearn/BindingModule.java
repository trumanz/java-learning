package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class BindingModule extends AbstractModule  {

	@Override
	protected void configure() {
		
		//Linked Bindings, if Animal required, HushkyDog will be return;
		bind(Animal.class).to(Dog.class);
		bind(Dog.class).to(HuskyDog.class);
		bind(SkinColor.class).to(BlackSkinColor.class);
		
		
		//Annotation Binding, Pig Class consturcotr will use Red
		bind(SkinColor.class).annotatedWith(Names.named("Black")).to(BlackSkinColor.class);
		bind(SkinColor.class).annotatedWith(Names.named("Red")).to(RedSkinColor.class);
		
		//Instance Binding
		bind(String.class).annotatedWith(Names.named("JDBC URI")).toInstance("jdbc:mysql://localhost/dbname");
		
		
		//Provider bindings
		bind(Float.class).toProvider(Float3Provider.class);
		
	}
	
	//ProvidesMethods, The return type must be unique unless use Named
	@Provides
	Integer getOneThousand(){
		return new Integer(1000);
	}
	
	@Provides @Named("1.0")
	Float getOne(){
		return new Float(1.0);
	}
	
	@Provides @Named("2.0")
	Float getTow(){
		return new Float(2.0);
	}
	

	
	

}
