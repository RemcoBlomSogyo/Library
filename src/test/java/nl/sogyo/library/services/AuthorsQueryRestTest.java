package nl.sogyo.library.services;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resource.AuthorsResource;

public class AuthorsQueryRestTest extends JerseyTest {
	
	private static final String idToken = "Bearer testIdToken";

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(AuthorsResource.class);
	}
	
	@Test
	public void searchForBooksWithoutQueryValuesGivesEmptyArray() {
		String output = target("authors").request().header(ContainerRequest.AUTHORIZATION, idToken).get(String.class);
		Assert.assertTrue(output.contains("{\"forename\":\"Arshak\",")
				&& output.contains(",\"surname\":\"Khachatrian\"}")
				&& output.contains("{\"forename\":\"Sanjay\",")
				&& output.contains(",\"surname\":\"Patni\"}"));
	}
}
