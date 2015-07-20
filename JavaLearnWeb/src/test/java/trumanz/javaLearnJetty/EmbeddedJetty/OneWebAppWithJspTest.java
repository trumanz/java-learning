package trumanz.javaLearnJetty.EmbeddedJetty;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;

public class OneWebAppWithJspTest {

	public static void startServer() throws Exception {
		Server server = new Server(8080);

		// setup JMX
		MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addBean(mbContainer);

		// set war
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(new File("/home/truman/tmp/simple/target/simple.war").getAbsolutePath());


		// set up the jsp container
		Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);

		classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");
        webapp.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );
        
        

		server.setHandler(webapp);

		server.start();
		server.join();
	}

	@Test
	public void test() throws Exception {
		OneWebAppWithJspTest.startServer();
	}

}
