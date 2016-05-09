package javaLearnJS.restful;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

import javaLearnJS.client.Me;

@Path("/json_schema/")
public class MeSchema {

	
	 
	@GET
	@Path("/me")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSchema() throws JsonProcessingException   {
		ObjectMapper mapper = new ObjectMapper();
		SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
		mapper.acceptJsonFormatVisitor(mapper.constructType(Me.class), visitor);
		JsonSchema jsonSchema = visitor.finalSchema();
		System.out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
	}

	 
}
