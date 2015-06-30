package trumanz.javaLearnJetty.Rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;



public class RestServerTest {
	@SuppressWarnings("deprecation")
	@Test
	public void testGet() throws Exception {
		
		RestServer restServer = new RestServer(9999);
		restServer.start();
		
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget  = client.target("http://localhost:9999");
		Response response = webTarget.path("/test").request().get(Response.class);
		
		Assert.assertEquals(200, response.getStatus());
		
		String entity  = response.readEntity(String.class);
		
		Assert.assertEquals("test", entity);
		
		restServer.stop();
		
	}

}
