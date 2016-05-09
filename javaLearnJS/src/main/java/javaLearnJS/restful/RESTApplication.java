package javaLearnJS.restful;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;

import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class RESTApplication extends ResourceConfig {

	private static Logger logger = Logger.getLogger(RESTApplication.class);
	
	
	public static class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

		private ObjectMapper defaultObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
				.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

		public ObjectMapper getContext(Class<?> arg0) {

			return defaultObjectMapper;
		}

	}

	
 	
	public RESTApplication(){
		
		logger.info("RESTApplication() created");
		
		packages(this.getClass().getPackage().toString());
		register(ObjectMapperProvider.class);
		register(JacksonFeature.class);
		
	}
	
}
