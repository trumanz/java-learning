package accountupdate;

import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

 
 
public class RESTApplication extends ResourceConfig {

	private static Logger logger = Logger.getLogger(RESTApplication.class);
	
	public RESTApplication(){
		
		logger.info("RESTApplication() created");
		
		packages(this.getClass().getPackage().toString());
		register(MyObjectMapperProvider.class);
		register(JacksonFeature.class);
		
	}
	

}
