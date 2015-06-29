package trumanz.javaLearnJetty.Rest;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class RestServerTest {
	@Test
	void test() {
		Client client = Client.create();
		

		WebResource webResource = client
				.resource("http://localhost:9999/employee/getEmployee");
		
		//webResource.acc
	}

}
