package trumanz.javaLearnJetty.EmbeddedJetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;

public class WebService {

	@Test
	public void test() 
	{

		Logger logger = Logger.getLogger(this.getClass());
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setParentLoaderPriority(true);
		webapp.setResourceBase("./src/test/webapp2");
		webapp.setAttribute("testAttribute","testValue");
		
		
	
		Server server = new Server(8080);
		server.setHandler(webapp);
		
		try {
			logger.info("start");
			server.start();
			logger.info("after start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
