package trumanz.javaLearnJersey.ProvidersTest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class ObjectProviderWriter4DoubleJson implements MessageBodyWriter<Object>{
	private static Logger logger = Logger.getLogger(ObjectProviderWriter4DoubleJson.class);

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		
		// TODO Auto-generated method stub
		logger.info("called");
		logger.info("type= " + type.toString());
		logger.info("genericType=" + genericType.toString());
		
		for(Annotation ann : annotations){
			logger.info("annotation=" + ann.toString());
		}
		logger.info("mediaType=" + mediaType.toString());
		
		//Just suport Date with Json format
		if(type != Double.class){
			logger.info("Double.class.toString=" + Double.class.toString() );
			return false;
		}
		if(!mediaType.equals(MediaType.APPLICATION_JSON_TYPE)){
			logger.info("MediaType.APPLICATION_JSON_TYPE=" + MediaType.APPLICATION_JSON_TYPE.toString());
			return false;
		}
		
		
		return true;
	}

	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@SuppressWarnings("deprecation")
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {
		
		//It should testing on isWriteable before call this funciton
		assert(t.getClass() == Double.class);
		assert(mediaType == MediaType.APPLICATION_JSON_TYPE);
		
		logger.info("start write "  + t.getClass().getName() +  " with " + mediaType.getType());
		
		Double e = (Double)t;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{").append("doulbe:" + e.toString()).append("}");
		
		entityStream.write(strBuilder.toString().getBytes());
		
		
	}

}
