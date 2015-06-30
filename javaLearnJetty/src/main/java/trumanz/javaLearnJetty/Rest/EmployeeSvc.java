package trumanz.javaLearnJetty.Rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/employee")
public class EmployeeSvc {

	@GET
	@Path("/getEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee(){
		return new Employee();
	}
}
