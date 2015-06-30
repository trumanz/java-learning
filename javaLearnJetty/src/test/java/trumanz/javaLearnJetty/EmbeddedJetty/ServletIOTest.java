package trumanz.javaLearnJetty.EmbeddedJetty;

import java.io.IOException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Assert;
import org.junit.Test;

public class ServletIOTest {
	
	public static Logger logger = Logger.getLogger(ServletIOTest.class);
	
	@SuppressWarnings("serial")
	static public class IOServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("text");
			
			logger.warn("Thread id=" + Thread.currentThread().getId());
		}

	}

	@Test
	public void IOTest() throws Exception {
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);

		handler.addServletWithMapping(IOServlet.class, "/*");

		server.start();
		HttpClient client = new HttpClient();
		client.setFollowRedirects(false);
		client.start();
		try {
			//client.
			ContentResponse response = client.GET(new String("http://127.0.0.1:8080"));
			Assert.assertEquals(200, response.getStatus());
			Assert.assertEquals("text\n", response.getContentAsString());
		} catch (Exception e) {
			throw e;
		} finally {
			client.stop();
			server.stop();
			server.join();
		}
	
	}

}
