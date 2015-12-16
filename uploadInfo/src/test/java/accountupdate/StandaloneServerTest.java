package accountupdate;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StandaloneServerTest {
	private static Server server = null;
	private static Logger logger = Logger.getLogger(StandaloneServerTest.class);

	public static void setJSPSupoort(ContextHandlerCollection c) {

		System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");

		WebAppContext jsp = new WebAppContext();
		jsp.setContextPath("/uploadInfo");
		jsp.setWar(new File("./src/main/webapp").getAbsolutePath());

		jsp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

		c.addHandler(jsp);
	}

	private static void setRESTSupport(ContextHandlerCollection c) {

		final ResourceConfig application = new ResourceConfig().packages(RESTApi.class.getPackage().getName())
				.register(MyObjectMapperProvider.class).register(JacksonFeature.class);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/uploadInfo");

		ServletHolder shJersey = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer(application));
		shJersey.setInitOrder(0);
		context.addServlet(shJersey, "/api/*");

		c.addHandler(context);
	}

	@BeforeClass
	public static void setUP() throws Exception {

		server = new Server(8080);
		ContextHandlerCollection c = new ContextHandlerCollection();
		setJSPSupoort(c);
		setRESTSupport(c);
		server.setHandler(c);
		server.start();
		logger.info("server started");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(3000 * 1000);
		logger.info("server stopping");
		server.stop();
		server.join();
		logger.info("server stopped");
	}

	@Test
	public void testJSON() throws Exception {
		Client client = ClientBuilder.newClient();

		Entity<AccountUpdateInfoEntity> reqEntity = Entity.entity(
				new AccountUpdateInfoEntity("111", "currName", "currentEmail", "emcID", "emcEmail"),
				MediaType.APPLICATION_JSON_TYPE);

		Response response = client.target("http://localhost:8080/api/accountinfo")
				.request(MediaType.APPLICATION_JSON_TYPE).post(reqEntity);

		logger.info(response.readEntity(String.class));
		Assert.assertEquals(200, response.getStatus());

	}

	@Test
	public void concurrencyTest() throws Exception {

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 0; i++) {

			executorService.submit(new Runnable() {

				public void run() {
					Client client = ClientBuilder.newClient();

					for (int i = 0; i < 10; i++) {

						Entity<AccountUpdateInfoEntity> reqEntity = Entity.entity(
								new AccountUpdateInfoEntity(String.valueOf(i), "currName" + i, "currentEmail" + i, "emcID" + i, "emcEmail" + i),
								MediaType.APPLICATION_JSON_TYPE);

						Response response = client.target("http://localhost:8080/api/accountinfo")
								.request(MediaType.APPLICATION_JSON_TYPE).post(reqEntity);

						logger.info(response.readEntity(String.class));
						Assert.assertEquals(200, response.getStatus());
					}

				}

			});
		}

		executorService.shutdown();

	}
}
