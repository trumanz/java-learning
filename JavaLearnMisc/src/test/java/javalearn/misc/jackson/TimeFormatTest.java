package javalearn.misc.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class TimeFormatTest {

	
	public static class MyObj{
		public Date dateTime = new Date();
	}
	
	
	@Test
	public void TestTimeForamt() throws JsonProcessingException{
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		
		ObjectWriter writer = new ObjectMapper().setDateFormat(df).writerWithDefaultPrettyPrinter();
		System.out.println(writer.writeValueAsString(new MyObj()));
	}
	
}
