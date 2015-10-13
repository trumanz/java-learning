package javaLearnJS;



import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
//import org.eclipse.jetty.client.api.Response;
import org.junit.AfterClass;
import org.junit.Assert;

@Path("/")
public class FirstPackageTest {
	private static Server server = null;
	private static Logger logger = Logger.getLogger(FirstPackageTest.class);

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getContent() {
		return "StringforTest--5454872487542467873524";
	}

	@BeforeClass
	public static void setUP() throws Exception {
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");

		server = new Server(8080);

		logger.info("Start testing");

		ContextHandlerCollection c = new ContextHandlerCollection();

		// 1. set jsp
		WebAppContext jsp = new WebAppContext();
		jsp.setContextPath("/");
		jsp.setWar(new File("./src/main/webapp").getAbsolutePath());

		jsp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

		// server.setHandler(jsp);
		c.addHandler(jsp);

		// 2.
		ServletContextHandler restContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		restContext.setContextPath("/api");
		ServletHolder servletHolder = restContext.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");

		servletHolder.setInitOrder(0);
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				FirstPackageTest.class.getCanonicalName());
		
		c.addHandler(restContext);

		server.setHandler(c);

		server.start();
		logger.info("server start");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// Thread.sleep(100*1000);
		logger.info("server stop");
		server.stop();
		server.join();
		logger.info("server stopped");
	}

	@Test
	public void testJSPHello() throws Exception {
		HttpClient client = new HttpClient();
		client.start();

		ContentResponse response = client.GET("http://localhost:8080");

		Assert.assertEquals(200, response.getStatus());

		Assert.assertThat(response.getContentAsString(),
				CoreMatchers.containsString("HeaderForTest-57216571327567421"));
		client.stop();

	}

	@Test
	public void testRestful() throws Exception {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/api").request().get(Response.class);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("StringforTest--5454872487542467873524", response.readEntity(String.class));
	}

}
