package nl.sogyo.library.services.integrationtest;

import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_2;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditUsersMessage;
import nl.sogyo.library.services.logic.UsersResource;

public class UsersCommandRestTest extends JerseyTest {

	private static List<User> users;
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(UsersResource.class);
	}
	
	@Before
	public void getUsers() {
		try {
			Response response = target("users").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get();
			users = response.readEntity(new GenericType<List<User>>(){});
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void updateUsersWithTestIdToken1ReturnsCommandSucceededIsFalse() {
		Response response = target("users").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).put(Entity.json(new Gson().toJson(users)));
		EditUsersMessage editUsersMessage = response.readEntity(EditUsersMessage.class);
		Assert.assertFalse(editUsersMessage.isCommandSucceeded());
	}
	
	@Test
	public void updateUsersWithTestIdToken2ReturnsCommandSucceededIsTrue() {
		Response response = target("users").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(new Gson().toJson(users)));
		EditUsersMessage editUsersMessage = response.readEntity(EditUsersMessage.class);
		Assert.assertTrue(editUsersMessage.isCommandSucceeded());
	}
}
