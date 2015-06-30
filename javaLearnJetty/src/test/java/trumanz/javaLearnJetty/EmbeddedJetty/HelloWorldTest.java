package trumanz.javaLearnJetty.EmbeddedJetty;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class HelloWorldTest

{

	@SuppressWarnings("deprecation")
	@Test
	public void testHelloWorldHandler() throws Exception {

		final String greeting = "HelloWorld";
		final String body = "testBody";

		Server server = new Server(8080);
		server.setHandler(new HelloHandler(greeting, body));
		server.start();
		HttpClient client = new DefaultHttpClient();

		try {
			HttpGet mockRequest = new HttpGet("Http://localhost:8080");
			HttpResponse mockrReponse = client.execute(mockRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(mockrReponse.getEntity().getContent()));

			Assert.assertEquals("<h1>" + greeting + "</h1>", rd.readLine());
			Assert.assertEquals(body, rd.readLine());
		} catch (Exception e) {
			throw e;
		} finally {
			client.getConnectionManager().shutdown();
			server.stop();
			server.join();
		}
		// server.dumpStdErr();
	}
	
	
}
