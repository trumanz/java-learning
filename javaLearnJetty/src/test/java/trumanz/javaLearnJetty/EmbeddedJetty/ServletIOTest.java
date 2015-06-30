package trumanz.javaLearnJetty.EmbeddedJetty;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Assert;
import org.junit.Test;

public class ServletIOTest {

	public static Logger logger = Logger.getLogger(ServletIOTest.class);

	@SuppressWarnings("serial")
	static public class IOServlet extends HttpServlet {
		AtomicInteger count = new AtomicInteger(0);

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("text");

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.getInputStream();

			request.getContentLength();
			byte[] buf = new byte[100];
			int len = request.getInputStream().read(buf, 0, buf.length - 1);
			buf[len] = '\0';
			String str = new String(buf, 0, len);
			int currentCount = count.addAndGet(1);
			logger.info("Thread id=" + Thread.currentThread().getId() + " ,content=" + str + " ,currentCount="
					+ currentCount);
		}

	}

	@Test
	public void IOTest() throws Exception {
		/// Server server = new Server(8080);
		Server server = new Server();
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setAcceptQueueSize(500);
		httpConnector.setIdleTimeout(3000);
		httpConnector.setPort(8080);

		server.addConnector(httpConnector);

		ServletHandler handler = new ServletHandler();

		server.setHandler(handler);

		handler.addServletWithMapping(IOServlet.class, "/*");

		server.start();

		{
			HttpClient httpClient = new HttpClient();
			httpClient.setFollowRedirects(false);

			logger.info(httpClient.getMaxRequestsQueuedPerDestination());
			logger.info(httpClient.getMaxConnectionsPerDestination());
			logger.info(httpClient.getMaxRedirects());
			logger.info(httpClient.getResponseBufferSize());
			logger.info(httpClient.getRequestBufferSize());
			httpClient.setMaxConnectionsPerDestination(512);
			// httpClient.setMaxRequestsQueuedPerDestination(512);
		}

		List<HttpClient> httpClients = new LinkedList<HttpClient>();
		
		for (int i = 0; i < 5; i++) {
			HttpClient httpClient = new HttpClient();
			httpClient.setMaxConnectionsPerDestination(512);
			httpClient.start();
			httpClients.add(httpClient);
		}

		try {
			// client.
			int reqeustCountEachClient = 100; // httpClient can not execeed 200
												// async request.
			int millis = 100;
			final AtomicInteger countFinished = new AtomicInteger(0);
			final AtomicInteger countStared = new AtomicInteger(0);
			long startTime = System.nanoTime();
			final CyclicBarrier barrier = new CyclicBarrier(reqeustCountEachClient * httpClients.size() + 1);
			for (HttpClient httpClient : httpClients) {
				for (int i = 0; i < reqeustCountEachClient; i++) {
					String content = "RequstIndex_" + i;
					Request request = httpClient.newRequest("http://localhost:8080").timeout(3, TimeUnit.SECONDS)
							.content(new StringContentProvider(content), "text/plain");

					request.send(new Response.CompleteListener() {
						public void onComplete(Result result) {
							result.isSucceeded();
							result.getResponse().getStatus();

							try {
								logger.info("countFinished=" + countFinished.addAndGet(1));
								barrier.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				}
			}

			barrier.await();

			long estimatedTime = System.nanoTime() - startTime;

			logger.info("estimatedTime=" + estimatedTime / (1000 * 1000) + "ms");

		} catch (Exception e) {
			throw e;
		} finally {
			for (HttpClient httpClient : httpClients) {
				httpClient.stop();
			}
			server.stop();
			server.join();
		}

	}

}
