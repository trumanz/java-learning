package javalearn.misc.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;



public class CSVTest {
	 
	@JsonPropertyOrder({ "country", "age", })
	public static class Attribute{
		public int age;
		public String country;
		public Attribute(int age, String country){
			this.age = age;
			this.country =country;
		}
		public Attribute(){}
		
		@Override
		public boolean equals(Object obj){
			if(obj == null) return false;
			if(this.getClass() != obj.getClass())  return false;
			final Attribute oth = (Attribute)obj;
			if(this.age != oth.age) return false;
			if(this.country == null ? (oth.country != null) : (!this.country.equals(oth.country))) return false;

			return true;
		}
		
		
	}
	
	public static class Father {
		public String fId  = "Fid";
		public String fName = "FName";
	}
	
	@JsonPropertyOrder({ "fId", "fName", "id", "atrribute", "aName", })
	public static class CSVLine  extends Father {
		@JsonProperty("id")
		public int xid;
		public String aName;
		@JsonUnwrapped
		public Attribute atrribute;
		public CSVLine(int id, String name, int age, String country){
			this.xid  = id;
			this.aName = name;
			atrribute = new Attribute(age, country);
		}
		public CSVLine(){}
		
		@Override
		public boolean equals(Object obj){
			if(obj == null) return false;
			if(this.getClass() != obj.getClass())  return false;
			final CSVLine oth = (CSVLine)obj;
			if(this.xid != oth.xid) return false;
			if(this.aName == null ? (oth.aName != null) : (!this.aName.equals(oth.aName))) return false;

			if(this.atrribute == null ? (oth.atrribute != null) : (!this.atrribute.equals(oth.atrribute))) return false;
			
			return true;
		}
		
	}

	

	@Test
	public void test() throws IOException {
		CSVLine csvObj = new CSVLine(1, "truman", 2, "china");
		String line = this.genericFormatToString(csvObj);
		System.out.println(line);
		Assert.assertEquals("Fid,FName,1,china,2,truman\n", line);
		
		
		CSVLine csvObj2 = genericFormatFromString(line, CSVLine.class);
		csvObj2.equals(csvObj);
	}

	private <T> String genericFormatToString(T obj) throws JsonProcessingException {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.schemaFor(obj.getClass()).withHeader().withoutHeader();
		System.out.println(csvSchema.toString());
		
		ObjectWriter objWriter = csvMapper.writer(csvSchema);
		return objWriter.writeValueAsString(obj);

	}
	
	
	private <T> T genericFormatFromString(String src, Class<T>  classType) throws IOException {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.schemaFor(classType).withHeader().withoutHeader();
		System.out.println(csvSchema.toString());
		
		ObjectReader objReader = csvMapper.reader(csvSchema).forType(classType);
		
		return objReader.readValue(src);

	}
	
	

}
