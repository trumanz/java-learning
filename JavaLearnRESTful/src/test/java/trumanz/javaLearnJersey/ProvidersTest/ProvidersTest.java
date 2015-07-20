package trumanz.javaLearnJersey.ProvidersTest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProvidersTest {
	private Server server;

	@Before
	public void setUp() throws Exception {

		ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);

		ServletHolder sh = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);

		sh.setInitParameter(ServerProperties.PROVIDER_PACKAGES, this.getClass().getPackage().getName());
		sh.setInitOrder(0);
		
		root.addServlet(sh, "/*");

		server = new Server(8080);
		server.setHandler(root);

		server.start();
	
		 
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}
	
	@Test
	public void testObjectProvider4DoubleJson(){
		Client client = ClientBuilder.newClient();
		
		Response response = client.target("http://localhost:8080/test/GetDoubleJson")
				.request().get(Response.class);
		
		String content = response.readEntity(String.class);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("{doulbe:1.1}", content);
	}
	
	@Test
	public void testIntegerProvider4IntegerText(){
		Client client = ClientBuilder.newClient();
		
		Response response = client.target("http://localhost:8080/test/PowerInteger").request()
				.accept(MediaType.TEXT_PLAIN).
				post(Entity.entity(new Integer(8), MediaType.TEXT_PLAIN), Response.class);
		String content = response.readEntity(String.class);
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("64", content);
	}

}
