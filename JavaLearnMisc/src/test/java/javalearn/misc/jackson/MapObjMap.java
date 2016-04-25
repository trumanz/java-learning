package javalearn.misc.jackson;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MapObjMap {

	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public static class  Book {
	
		public Book(String author, String category){
			this.author = author;
			this.categories = category;
		}
		public String getAuthor(){
			return author;
		}
		public String getCategory() {
			return categories;
		}
		private String author;
		private String categories;
	}
	
	static public class Library{
		public Map<String, Integer> id_price = new HashMap<String, Integer>();
		public Map<String, Book> id_book = new HashMap<String, Book>();
		 
		
	}
	
	@Test
	public void JsonWriteTest() throws IOException{
		
		ObjectMapper  objMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;

		Book book = new Book("truman", "Mybook");
		Library library = new Library();
		library.id_price.put("id1", 100);
		library.id_book.put("id1", book);
		
		 
		String json = objMapper.writeValueAsString(library);
		logger.info(json);
		
		 JsonNode jsonNode = objMapper.readTree(json);
		 
		 Iterator<Entry<String, JsonNode>> fields = jsonNode.findValue("id_price").fields();
		 Assert.assertTrue(fields.hasNext());
		 Entry<String, JsonNode> field = fields.next();
		 String key =  field.getKey();
		 JsonNode node = field.getValue();
		 Assert.assertEquals(key, "id1");
		 Assert.assertEquals(100, node.asInt());
		 Assert.assertFalse(fields.hasNext());
		 
		 
		 fields = jsonNode.findValue("id_book").fields();
		 Assert.assertTrue(fields.hasNext());
		 field = fields.next();
		 key =  field.getKey();
		 node = field.getValue();
		 Assert.assertEquals(key, "id1");
		 Assert.assertEquals("truman", node.findValue("author").asText());
		 Assert.assertEquals("Mybook", node.findValue("category").asText());
		 Assert.assertFalse(fields.hasNext());
		 
		  
		 
	}
	
	
	 
	
	
}
