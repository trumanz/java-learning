package javaLearnJS;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper defaultObjectMapper = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT);

	
	public ObjectMapper getContext(Class<?> arg0) {
		return defaultObjectMapper;
	}

	

}
