package javalearn.misc.jackson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class TimeFormatTest {

	public static class MyObj {
		public Date dateTime = new Date();
		// /public OffsetDateTime offsetDateTime =
		// OffsetDateTime.parse("2007-12-03T10:15:30+01:00");
		// @JsonDeserialize(using = DurationDeserialize.class)
		// @JsonSerialize(using = DurationSerialize.class)
		public Duration duration = Duration.ofHours(136).plusMinutes(21).plusSeconds(5);
		// public Duration duration =
		// Duration.ofMinutes(20).plusHours(2).plusDays(2).plusDays(20);
		
		public String toString(){
			 StringBuilder str = new StringBuilder();
			 str.append("dateTime=[").append(dateTime.toString()).append("],");
			 str.append("duration=[").append(duration.toString()).append("],");
			 return str.toString();
		}
	}

	@Test
	public void TestTimeForamt() throws IOException {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");

		ObjectMapper objMapper = new ObjectMapper();
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			objMapper.setDateFormat(df);
		}
		{
			SimpleModule timeModule = new SimpleModule("MyModule");
			timeModule.addSerializer(new DurationSerialize());
			timeModule.addDeserializer(Duration.class, new DurationDeserialize());
			objMapper.registerModule(timeModule);
		}
		
		ObjectWriter writer = objMapper.writerWithDefaultPrettyPrinter();
		
		String str = writer.writeValueAsString(new MyObj());
		
		System.out.println(str);
		
		MyObj obj = objMapper.readValue(str, MyObj.class);
		
		System.out.println(obj);
	}

}
