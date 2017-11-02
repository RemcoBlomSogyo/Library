package nl.sogyo.library.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;
import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorizationTest extends JerseyTest {
	
	private static int id;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void test01GetRequestForBookInfoWithIdTokenWithUserType1GivesBookInfo() {
		String output = target("/book").path("47").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).get(String.class);
		Assert.assertTrue(output.contains("Getting Started with Polymer") && output.contains("9781785889370") 
				&& output.contains("Arshak") && output.contains("Engels"));
	}
	
	@Test
	public void test02GetRequestForBookInfoWithIdTokenWithUserType2GivesBookInfo() {
		String output = target("/book").path("47").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
		Assert.assertTrue(output.contains("Getting Started with Polymer") && output.contains("9781785889370") 
				&& output.contains("Arshak") && output.contains("Engels"));
	}
	
	@Test
	public void test03GetRequestForBookInfoWithNoIdTokenGivesNoBookInfo() {
		String output = target("/book").path("47").request().get(String.class);
		Assert.assertEquals("{\"copiesAvailable\":0}", output);
	}
	
	@Test
	public void test04GetRequestForBookInfoWithFakeIdTokenGivesNoBookInfo() {
		String output = target("/book").path("47").request().header(ContainerRequest.AUTHORIZATION, "test").get(String.class);
		Assert.assertEquals("{\"copiesAvailable\":0}", output);
	}
	
	@Test
	public void test05AddingBookWithIdTokenWithUserType1GivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test06AddingBookWithNoIdTokenGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").request().post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test07AddingBookWithFakeIdTokenGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test08AddingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		id = addBookMessage.getBookId();
		Assert.assertTrue(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test09EditingBookWithIdTokenWithUserType1GivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title2", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).put(Entity.json(bookFormInput));
		EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
		Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test10EditingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		BookFormInput bookFormInput = new BookFormInput("title2", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(bookFormInput));
		EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
		Assert.assertTrue(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test11EditingBookWithNoIdTokenGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title2", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").path(Integer.toString(id)).request().put(Entity.json(bookFormInput));
		EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
		Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test12EditingBookWithFakeIdTokenGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title2", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").put(Entity.json(bookFormInput));
		EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
		Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test13DeletingBookWithIdTokenWithUserType1GivesCommandSucceededIsFalse() {
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).delete();
		DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
		Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test14DeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
		DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
		Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test15DeletingBookWithNoIdTokenGivesCommandSucceededIsFalse() {
		Response response = target("/book").path(Integer.toString(id)).request().delete();
		DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
		Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test16DeletingBookWithFakeIdTokenGivesCommandSucceededIsFalse() {
		Response response = target("/book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").delete();
		DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
		Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
}
