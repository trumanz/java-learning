package javaLearnJS;

import org.junit.Test;

 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import javaLearnJS.client.Me;

public class GenerateJsonSchema {
	@Test
	public void genrateSchema() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
		mapper.acceptJsonFormatVisitor(mapper.constructType(Me.class), visitor);
		JsonSchema jsonSchema = visitor.finalSchema();
		System.out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
	}

}
