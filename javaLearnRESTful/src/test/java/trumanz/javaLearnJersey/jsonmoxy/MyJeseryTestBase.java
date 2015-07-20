package trumanz.javaLearnJersey.jsonmoxy;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;



public class MyJeseryTestBase extends JerseyTest {
	


	public MoxyJsonConfig createMoxyJsonConfig(){
		MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
		Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
		namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
		return moxyJsonConfig;
	}
	

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);

		
		return new ResourceConfig().packages(this.getClass().getPackage().getName())
				.register(createMoxyJsonConfig().resolver());
	}

	@Override
	protected void configureClient(ClientConfig config) {
	
		config.register(createMoxyJsonConfig().resolver());
	}

	

}
