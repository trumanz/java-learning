package EmbeddedJetty;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Assert;
import org.junit.Test;

public class TestWithEmbeededJetty {

	public static Server startServerWithWar(String warFilePath) throws Exception {
		Server server = new Server(8080);

		// setup JMX
		MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addBean(mbContainer);

		// set war
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(new File(warFilePath).getAbsolutePath());


		// set up the jsp container
		Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);

		classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");
        webapp.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );
        
        

		server.setHandler(webapp);

		server.start();
	
		return server;
	}

	@Test
	public void test() throws Exception {
		
		String webAppPath = "path/webapp.war";
		webAppPath = "./src/main/webapp";
		
		System.out.println("====");
		System.out.println(new File(webAppPath).getAbsolutePath().toString());
		System.out.println("====");
		
		Server server = TestWithEmbeededJetty.
				startServerWithWar(webAppPath);
		
		HttpClient client = new HttpClient();
		client.start();
		
		Response response = client.GET("http://localhost:8080");
		
		Assert.assertEquals(200, response.getStatus());
	
		
		///server.stop();
		server.join();
		
	}

}
