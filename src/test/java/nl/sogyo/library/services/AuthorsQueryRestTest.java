package nl.sogyo.library.services;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resource.AuthorsResource;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

public class AuthorsQueryRestTest extends JerseyTest {
	
	private static final String emptyJsonArray = "[]";

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(AuthorsResource.class);
	}
	
	@Test
	public void requestForAuthorsWithTestIdToken1ReturnsAllAuthors() {
		String output = target("authors").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).get(String.class);
		Assert.assertTrue(output.contains("{\"forename\":\"Arshak\",")
				&& output.contains(",\"surname\":\"Khachatrian\"}")
				&& output.contains("{\"forename\":\"Sanjay\",")
				&& output.contains(",\"surname\":\"Patni\"}"));
	}
	
	@Test
	public void requestForAuthorsWithTestIdToken2ReturnsAllAuthors() {
		String output = target("authors").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
		Assert.assertTrue(output.contains("{\"forename\":\"Arshak\",")
				&& output.contains(",\"surname\":\"Khachatrian\"}")
				&& output.contains("{\"forename\":\"Sanjay\",")
				&& output.contains(",\"surname\":\"Patni\"}"));
	}
	
	@Test
	public void requestForAuthorsWithFakeIdTokenReturnsNoAuthors() {
		String output = target("authors").request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").get(String.class);
		Assert.assertEquals(emptyJsonArray, output);
	}
	
	@Test
	public void requestForAuthorsWithoutIdTokenReturnsNoAuthors() {
		String output = target("authors").request().get(String.class);
		Assert.assertEquals(emptyJsonArray, output);
	}
}
