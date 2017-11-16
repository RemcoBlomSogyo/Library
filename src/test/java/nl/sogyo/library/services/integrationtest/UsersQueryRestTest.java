package nl.sogyo.library.services.integrationtest;

import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_2;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.logic.UsersResource;

public class UsersQueryRestTest extends JerseyTest {
	
	private static final String emptyJsonArray = "[]";

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(UsersResource.class);
	}
	
	@Test
	public void requestForUsersWithTestIdToken1ReturnsNoUsers() {
		String output = target("users").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).get(String.class);
		Assert.assertEquals(emptyJsonArray, output);
	}
	
	@Test
	public void requestForUsersWithTestIdToken2ReturnsAllUsers() {
		String output = target("users").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
		Assert.assertTrue(output.contains("\"givenName\":\"Test\"")
				&& output.contains(",\"familyName\":\"de Test\"")
				&& output.contains("\"familyName\":\"van Test\"")
				&& output.contains("\"email\":\"tdtest@sogyo.nl\"")
				&& output.contains("\"email\":\"tvtest@sogyo.nl\""));
	}
	
	@Test
	public void requestForUsersWithFakeIdTokenReturnsNoUsers() {
		String output = target("users").request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").get(String.class);
		Assert.assertEquals(emptyJsonArray, output);
	}
	
	@Test
	public void requestForUsersWithoutIdTokenReturnsNoUsers() {
		String output = target("users").request().get(String.class);
		Assert.assertEquals(emptyJsonArray, output);
	}
}
