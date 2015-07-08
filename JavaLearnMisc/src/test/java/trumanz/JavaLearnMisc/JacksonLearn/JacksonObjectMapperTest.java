package trumanz.JavaLearnMisc.JacksonLearn;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapperTest {

	private ObjectMapper  objMapper = new ObjectMapper();
	private JsonFactory  jsonFactory = new JsonFactory();
	private Logger logger = Logger.getLogger(this.getClass());
	
	static public class TestBean{
		private int id;
		public String name;
		public String address[];
		public int getXId() {
			return id;
		}
	
		public void setXId(int id) {
			this.id = id;
		}
		public  TestBean(){
			this.id = 99;
			this.name = "NoneName";
			this.address = new String[2];
			this.address[0] = "office address";
			this.address[1] = "home address";
		}
		
	}
	
	@Test
	public void JsonWriteTest() throws IOException{
		
		TestBean bean = new TestBean();
		String json = objMapper.writeValueAsString(bean);
		logger.info(json);
		
		 JsonNode jsonNode = objMapper.readTree(json);
		 Assert.assertEquals(99, jsonNode.findValue("xid").asInt());
		 Assert.assertEquals("NoneName", jsonNode.findValue("name").asText());
		 Assert.assertEquals(2, jsonNode.findValue("address").size());

		 Assert.assertEquals("office address", jsonNode.findValue("address").get(0).asText());
		 Assert.assertEquals("home address", jsonNode.findValue("address").get(1).asText());
		 
	}
	
	
	@Test
	public void JsonReadTest() throws IOException{
		
		//String json = "{"name":"NoneName","address":["office address","home address"],"xid":99}"
		String json = "{\"name\":\"Xman\", \"xid\":101}";
				
		
	
		TestBean bean = objMapper.readValue(json, TestBean.class);
		
	
		 Assert.assertEquals(101, bean.id);
		 Assert.assertEquals("Xman", bean.name);
		
		 Assert.assertEquals("office address", bean.address[0]);
		 Assert.assertEquals("Xman", bean.name);
		 
		 
		json = "{\"address\":[\"Aaddress\"]}";
		bean = objMapper.readValue(json, TestBean.class);
		 Assert.assertEquals(1, bean.address.length);
		 Assert.assertEquals("Aaddress", bean.address[0]);
		 
	}
	

	
}
