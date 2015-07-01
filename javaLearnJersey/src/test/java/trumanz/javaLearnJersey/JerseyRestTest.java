package trumanz.javaLearnJersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.junit.Assert;
import org.junit.Test;

@Path("/apis")
@XmlRootElement
public class JerseyRestTest {
	
	public String name;
	public int id;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String listApis() {
		return "nothing";
	}
	

	@Test
	public void restfulTest() throws Exception {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		
		Server server = new Server(8080);
		server.setHandler(context);
		
		ServletHolder servletHolder = context.addServlet(
				org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		servletHolder.setInitOrder(0);
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				JerseyRestTest.class.getCanonicalName());

		
		server.start();

		Client client = ClientBuilder.newClient();

		Response response = client.target("http://localhost:8080/apis").request().get(Response.class);
		
		String content = response.readEntity(String.class);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("nothing", content);
		
		server.stop();
		
		
	}
	
	
	@Test
	public void restfulTest2() throws Exception {

		///ambari-server style
		ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ServletHolder sh = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
		//the pakckage taht contain application-specific resources and providers.,
		sh.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "trumanz.javaLearnJersey");
		sh.setInitOrder(0);
		
		root.addServlet(sh,"/api/v1/*");
		
		Server server = new Server(8080);
		server.setHandler(root);
		
	
		server.start();

		Client client = ClientBuilder.newClient();

		Response response = client.target("http://localhost:8080/api/v1/apis").request().get(Response.class);
		
		String content = response.readEntity(String.class);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("nothing", content);
		
		
	
		
		
		server.start();
	}

}
