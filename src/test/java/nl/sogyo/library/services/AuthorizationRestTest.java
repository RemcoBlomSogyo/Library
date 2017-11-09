package nl.sogyo.library.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;
import nl.sogyo.library.services.rest.libraryapi.resource.BooksResource;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

public class AuthorizationRestTest extends JerseyTest {
	
	private static final BookFormInput addBookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
	private static final BookFormInput editBookFormInput = new BookFormInput("title2", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BooksResource.class);
	}
	
	@Test
	public void getRequestForBookInfoWithIdTokenWithUserType1GivesBookInfo() {
		String output = target("books").path("47").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).get(String.class);
		Assert.assertTrue(output.contains("Getting Started with Polymer") && output.contains("9781785889370") 
				&& output.contains("Arshak") && output.contains("Engels"));
	}
	
	@Test
	public void getRequestForBookInfoWithIdTokenWithUserType2GivesBookInfo() {
		String output = target("books").path("47").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
		Assert.assertTrue(output.contains("Getting Started with Polymer") && output.contains("9781785889370") 
				&& output.contains("Arshak") && output.contains("Engels"));
	}
	
	@Test
	public void getRequestForBookInfoWithNoIdTokenGivesNoBookInfo() {
		String output = target("books").path("47").request().get(String.class);
		Assert.assertEquals("{\"copiesAvailable\":0}", output);
	}
	
	@Test
	public void getRequestForBookInfoWithFakeIdTokenGivesNoBookInfo() {
		String output = target("books").path("47").request().header(ContainerRequest.AUTHORIZATION, "test").get(String.class);
		Assert.assertEquals("{\"copiesAvailable\":0}", output);
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType1GivesCommandSucceededIsFalse() {
		Response response = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		if (addBookMessage.getCommandSucceeded()) {
			deleteBook(addBookMessage.getBookId());
		}
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void addingBookWithNoIdTokenGivesCommandSucceededIsFalse() {
		Response response = target("books").request().post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		if (addBookMessage.getCommandSucceeded()) {
			deleteBook(addBookMessage.getBookId());
		}
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void addingBookWithFakeIdTokenGivesCommandSucceededIsFalse() {
		Response response = target("books").request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		if (addBookMessage.getCommandSucceeded()) {
			deleteBook(addBookMessage.getBookId());
		}
		Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response response = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
		deleteBook(addBookMessage.getBookId());
		Assert.assertTrue(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueEditingWithIdToken1GivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response editBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).put(Entity.json(editBookFormInput));
			EditBookMessage editBookMessage = editBookResponse.readEntity(EditBookMessage.class);
			
			if (editBookMessage.getCommandSucceeded()) {
				deleteBook(addBookMessage.getBookId());
				Assert.fail();
			} else {
				Response deleteBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueEditingWithFakeIdTokenGivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response editBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").put(Entity.json(editBookFormInput));
			EditBookMessage editBookMessage = editBookResponse.readEntity(EditBookMessage.class);
			
			if (editBookMessage.getCommandSucceeded()) {
				deleteBook(addBookMessage.getBookId());
				Assert.fail();
			} else {
				Response deleteBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueEditingWithNoIdTokenGivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response editBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().put(Entity.json(editBookFormInput));
			EditBookMessage editBookMessage = editBookResponse.readEntity(EditBookMessage.class);
			
			if (editBookMessage.getCommandSucceeded()) {
				deleteBook(addBookMessage.getBookId());
				Assert.fail();
			} else {
				Response deleteBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueEditingWithIdToken2GivesCommandSucceededIsTrueDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response editBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(editBookFormInput));
			EditBookMessage editBookMessage = editBookResponse.readEntity(EditBookMessage.class);
			
			if (!editBookMessage.getCommandSucceeded()) {
				deleteBook(addBookMessage.getBookId());
				Assert.fail();
			} else {
				Response deleteBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response deleteBookResponse = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
			DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
			Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueDeletingWithIdToken1GivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response deleteBookResponse1 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_1).delete();
			DeleteBookMessage deleteBookMessage1 = deleteBookResponse1.readEntity(DeleteBookMessage.class);

			if (deleteBookMessage1.getCommandSucceeded()) {
				Assert.fail();
			} else {
				Response deleteBookResponse2 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage2 = deleteBookResponse2.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage2.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueDeletingWithFakeIdTokenGivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response deleteBookResponse1 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, "fakeIdToken").delete();
			DeleteBookMessage deleteBookMessage1 = deleteBookResponse1.readEntity(DeleteBookMessage.class);
			
			if (deleteBookMessage1.getCommandSucceeded()) {
				Assert.fail();
			} else {
				Response deleteBookResponse2 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage2 = deleteBookResponse2.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage2.getCommandSucceeded());
			}
		}		
	}
	
	@Test
	public void addingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrueDeletingWithNoIdTokenGivesCommandSucceededIsFalseDeletingBookWithIdTokenWithUserType2GivesCommandSucceededIsTrue() {
		Response addBookResponse = target("books").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(addBookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		
		if (!addBookMessage.getCommandSucceeded()) {
			Assert.fail();
		} else {
			Response deleteBookResponse1 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().delete();
			DeleteBookMessage deleteBookMessage1 = deleteBookResponse1.readEntity(DeleteBookMessage.class);
			
			if (deleteBookMessage1.getCommandSucceeded()) {
				Assert.fail();
			} else {
				Response deleteBookResponse2 = target("books").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
				DeleteBookMessage deleteBookMessage2 = deleteBookResponse2.readEntity(DeleteBookMessage.class);
				Assert.assertTrue(deleteBookMessage2.getCommandSucceeded());
			}
		}		
	}

	private void deleteBook(int bookId) {
		target("books").path(Integer.toString(bookId)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	}
}
