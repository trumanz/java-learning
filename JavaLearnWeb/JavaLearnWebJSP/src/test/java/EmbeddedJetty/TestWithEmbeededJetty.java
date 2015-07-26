package EmbeddedJetty;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestWithEmbeededJetty {
	
	private static Server server = null;
	private static Logger logger = Logger.getLogger(TestWithEmbeededJetty.class);
	
	@BeforeClass
	public static void setUP() throws Exception
	{
		startJetty();
	}
	@AfterClass
	public static void tearDown() throws Exception{
		stopJetty();
	}

	private static void startJetty() throws Exception{
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");

		String warFilePath = "./src/main/webapp";
		server = new Server(8080);
		logger.info("test");
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

		logger.info("test");
		server.start();
		logger.info("test");
	}
	
	
	private static void stopJetty() throws Exception{	
		//server.stop();
		server.join();
	}

	
	
	
	@Test
	public void getTest() throws Exception {
		
		logger.info("test");
		HttpClient client = new HttpClient();
		client.start();
		Response response = client.GET("http://localhost:8080");
		Assert.assertEquals(200, response.getStatus());		
	}
	
	@Test
	public  void setUpSelenium(){
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://127.0.0.1:8080/";
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl + "/");
		
		driver.findElement(By.name("addCookieName")).clear();
		driver.findElement(By.name("addCookieName")).sendKeys("CookieName");
		driver.findElement(By.name("addCookieValue")).clear();
		driver.findElement(By.name("addCookieValue")).sendKeys("CookieValue");
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		System.out.println("get1=" + driver.findElement(By.name("addCookieName")).getText());
		
	
		
		//driver.quit();
	}


}
