package trumanz.javaLearnJetty.EmbeddedJetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler {
	
	final String greeting;
	final String body;
	public HelloHandler(String greeting, String body)
	{
		this.greeting = greeting;
		this.body = body;
	}

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		 response.setContentType("text/html;charset=utf-8");
		 response.setStatus(HttpServletResponse.SC_OK);
	     response.getWriter().println("<h1>" + this.greeting + "</h1>");
	     response.getWriter().println(this.body);
	     baseRequest.setHandled(true);
	}
	
	/*
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		server.setHandler(new HelloWorldHandler("xx","yy"));
		server.start();
		server.dumpStdErr();
		server.join();
	}
	*/

}
