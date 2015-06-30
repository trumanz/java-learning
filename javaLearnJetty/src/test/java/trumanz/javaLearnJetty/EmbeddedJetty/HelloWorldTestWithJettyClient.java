package trumanz.javaLearnJetty.EmbeddedJetty;


import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;

import org.junit.Assert;

public class HelloWorldTestWithJettyClient {
	@Test
	public void testHelloServlet2() throws Exception{
		
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		
		handler.addServletWithMapping(HelloServlet.class, "/*");
		
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
