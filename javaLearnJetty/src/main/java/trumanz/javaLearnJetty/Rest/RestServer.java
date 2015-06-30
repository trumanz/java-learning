package trumanz.javaLearnJetty.Rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

//import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletContainer;

@Path("/")
public class RestServer {
	Server server = null;

	public RestServer(){
		
	}
	
	public RestServer(int port) {
		server = new Server(port);
	}

	public void start() throws Exception {

		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.packages",
				"trumanz.javaLearnJetty.Rest");
		servletHolder.setInitParameter(
				"com.sun.jersey.api.json.POJOMappingFeature", "true");

		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(servletHolder, "/*");
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
		server.join();
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	String test(){
		return "test";
	}
	

}
