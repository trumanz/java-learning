package trumanz.javaLearnJersey.ProvidersTest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class ProviderReader implements MessageBodyReader<Integer> {
	private static Logger logger = Logger.getLogger(ObjectProviderWriter4DoubleJson.class);
	
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		logger.info("called");
		// return false,  jetty has default entity providers
		return false;
	}

	public Integer readFrom(Class<Integer> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
					throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
