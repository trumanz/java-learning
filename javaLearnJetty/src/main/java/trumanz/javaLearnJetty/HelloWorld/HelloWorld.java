package trumanz.javaLearnJetty.HelloWorld;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

/**
 * Hello world!
 *
 */
public class HelloWorld extends AbstractHandler
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        Server server = new Server(8080);
        ContextHandler context = new ContextHandler();
        context.setContextPath("/hello");
        context.setHandler(new HelloWorld());
        server.setHandler(context);
        //server.setHandler(new HelloWorld());
        
        server.start();
        server.join();
    }

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>Hello World!!!</h1>");
	}
}
