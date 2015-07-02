package trumanz.javaLearnJersey.ProvidersTest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/test")
public class MyResource {
	@GET
	@Path("/GetDoubleJson")
	@Produces(MediaType.APPLICATION_JSON)
	public Double getData() {
		
		return new Double(1.1);
	}
	
	@POST
	@Path("/PowerInteger")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Integer getInteger(Integer input){
		return new Integer(input.intValue() * input.intValue());
	}

}
