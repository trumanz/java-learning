package javalearn.misc.jackson;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DurationSerialize extends JsonSerializer<Duration> {
	 
	@Override
	public void serialize(Duration value, JsonGenerator jgen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(value.toHours());
		strBuilder.append(":");
		strBuilder.append(value.toMinutes()%60);
		strBuilder.append(":");
		strBuilder.append(value.getSeconds()%60);
		jgen.writeString(strBuilder.toString());
	}
	
	 public Class<Duration> handledType() { return  Duration.class; }

}
