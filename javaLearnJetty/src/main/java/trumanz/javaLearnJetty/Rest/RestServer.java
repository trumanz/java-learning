package trumanz.javaLearnJetty.Rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class RestServer {
	public static void main(String[] args) throws Exception {

		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.packages", "rest");
		servletHolder.setInitParameter(
				"com.sun.jersey.api.json.POJOMappingFeature", "true");

		Server server = new Server(9999);
		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(servletHolder, "/*");
		server.start();
		server.join();

	}

}
