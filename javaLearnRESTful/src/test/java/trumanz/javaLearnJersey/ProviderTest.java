package trumanz.javaLearnJersey;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@Provider
//@Consumes({MediaType.APPLICATION_JSON, "text/json"})
//@Produces({MediaType.APPLICATION_JSON, "text/json"})
public class ProviderTest implements MessageBodyReader<Object>, MessageBodyWriter<Object>{
	

	static final Gson gson = new GsonBuilder().create();
	
	//start MessageBodyReader function
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
					throws IOException, WebApplicationException {
		
		Reader reader = new InputStreamReader(entityStream);
		try{
			return gson.fromJson(reader, genericType);
		} finally {
			reader.close();
		}
	}
	//end MessageBodyReader function

	//start MessageBodyWriter function
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {
		Writer writer = new OutputStreamWriter(entityStream);
		try{
			gson.toJson(t, genericType, writer);
		} finally {
			writer.close();
		}
	}
	//end MessageBodyWriter function
	
	
	@Test
	public void testProvider(){
		
	}

}
