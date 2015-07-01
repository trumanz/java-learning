package trumanz.javaLearnJersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.server.ServerProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

@Path("/apis")

public class RestTestBase {


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

}
