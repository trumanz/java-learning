package javalearn.misc.jackson;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DurationDeserialize extends JsonDeserializer<Duration> {
	@Override
	public Duration deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonToken currentToken = jp.getCurrentToken();

		if (currentToken.equals(JsonToken.VALUE_STRING)) {
			String text = jp.getText().trim();
			if (text != null) {
				String[] ts = text.split(":");
				if(ts.length == 3){
					if( ts[0].matches("^\\d+$") && ts[1].matches("^\\d+$") && ts[2].matches("^\\d+$")){
					 
						Duration d = Duration.ofHours(Integer.parseInt(ts[0]))
								.plusMinutes(Integer.parseInt(ts[1]))
								.plusSeconds(Integer.parseInt(ts[2]));
						return d;	
					}
					
				}
			}

			throw ctxt.weirdStringException(text, Duration.class, "Only pattren %H:%M:%S supported");
		} else if (currentToken.equals(JsonToken.VALUE_NULL)) {
			return getNullValue();
		}

		throw ctxt.mappingException(Boolean.class);
	}

	@Override
	public Duration getNullValue() {
		return null;
	}

}
