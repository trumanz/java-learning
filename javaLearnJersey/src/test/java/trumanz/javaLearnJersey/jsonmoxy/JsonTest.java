package trumanz.javaLearnJersey.jsonmoxy;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

@Path("test")
public class JsonTest extends JerseyTest {

	@XmlRootElement
	static class TestBean {
		public int id;
		public String name;

		public TestBean(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public TestBean() {
			this(-1, "none");
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || this.getClass() != o.getClass())
				return false;
			TestBean that = (TestBean) o;
			if (this.id == that.id && this.name.equals(that.name))
				return true;
			return false;
		}
	}

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);

		MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
		Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
		namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');

		return new ResourceConfig().packages(this.getClass().getPackage().getName())
				.register(moxyJsonConfig.resolver());
	}

	@Override
	protected void configureClient(ClientConfig config) {
		MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
		Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
		namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');

		config.register(moxyJsonConfig.resolver());
	}

	//static!!!, This object will be create on each request.
	private static TestBean myBean = new TestBean();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TestBean updateTestBean(TestBean s) {
		TestBean tmp = myBean;
		myBean = s;
		return tmp;
	}

	@Test
	public void test() {

		Response response = target("test").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(new TestBean(1, "first"), MediaType.APPLICATION_JSON_TYPE), Response.class);

		Assert.assertEquals(200, response.getStatus());
		TestBean bean = response.readEntity(TestBean.class);
		Assert.assertEquals(-1, bean.id);
		Assert.assertEquals("none", bean.name);
		Assert.assertTrue(bean.equals(new TestBean(-1, "none")));
		Assert.assertEquals(bean, new TestBean(-1, "none"));
		

		bean = target("test").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(new TestBean(2, "sedond"), MediaType.APPLICATION_JSON_TYPE), TestBean.class);

		Assert.assertEquals(bean, new TestBean(1, "first"));
	
	}

}
