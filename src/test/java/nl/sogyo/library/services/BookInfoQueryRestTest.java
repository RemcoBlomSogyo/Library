package nl.sogyo.library.services;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;

public class BookInfoQueryRestTest extends JerseyTest {

	private static final String idToken = "Bearer testIdToken";
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void getBookInfoOfProRestfulApis() {
		String output = target("/book").path("46").request().header(ContainerRequest.AUTHORIZATION, idToken).get(String.class);
		Assert.assertTrue(output.contains("RESTful") && output.contains("9781484226643") 
				&& output.contains("Design, Build and Integrate with REST, JSON, XML and JAX-RS") && output.contains("Engels"));
	}
	
	@Test
	public void requestForNotExistingIdGivesEmptyResults() {
		String output = target("/book").path("10000000").request().header(ContainerRequest.AUTHORIZATION, idToken).get(String.class);
		Assert.assertTrue(output.contains("\"publisher\":{\"id\":0,\"name\":\"\"},\"subtitle\":\"\",\"title\":\"\",\"yearFirstPublication\":0}"));
	}
}
