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
import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

public class BookCommandRestTest extends JerseyTest {

	private static final BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void postAndDeleteValidBook() {
	    Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
	    if (!addBookMessage.getCommandSucceeded()) {
	    	Assert.fail();
	    } else {
		    Response deleteBookResponse = target("book").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
		    DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
		    Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
	    }
	}
	
	@Test
	public void postUpdateAndDeleteValidBook() {
	    Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
	    if (!addBookMessage.getCommandSucceeded()) {
	    	Assert.fail();
	    } else {
	    	BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
		    Response editBookResponse = target("book").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(bookFormInput));
		    EditBookMessage editBookMessage = editBookResponse.readEntity(EditBookMessage.class);
		    if (!editBookMessage.getCommandSucceeded()) {
		    	deleteBook(addBookMessage.getBookId());
		    	Assert.fail();
		    } else {
			    Response deleteBookResponse = target("book").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
			    DeleteBookMessage deleteBookMessage = deleteBookResponse.readEntity(DeleteBookMessage.class);
			    Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
		    }
	    }
	}
	
	@Test
	public void postBookWithInvalidIsbnGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890125");
	    Response response = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    if (addBookMessage.getCommandSucceeded()) {
	    	deleteBook(addBookMessage.getBookId());
	    }
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void postBookWithoutAuthorSurnameGivesCommandSucceededIsFalse() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "", "REST", "Appress", "9784567890120");
	    Response response = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    if (addBookMessage.getCommandSucceeded()) {
	    	deleteBook(addBookMessage.getBookId());
	    }
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void updateNotExistingBookGivesCommandSucceededIsFalse() {
	    Response response = target("book").path("10000000").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(bookFormInput));
	    EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
	    Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void deleteNotExistingBookGivesCommandSucceededIsFalse() {
	    Response response = target("book").path("10000000").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
	    Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
	
	private void deleteBook(int bookId) {
		target("/book").path(Integer.toString(bookId)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	}
}
