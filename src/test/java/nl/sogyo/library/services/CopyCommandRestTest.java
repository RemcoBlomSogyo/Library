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
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

public class CopyCommandRestTest extends JerseyTest {
	
	private static final BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void addAndDeleteCopyOfProRESTfulAPIsGivesCommandSucceededIsTrue() {
	    Response response1 = target("book").path("46/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = response1.readEntity(AddCopyMessage.class);
	    if(!addCopyMessage.getCommandSucceeded()) {
	    	Assert.fail();
	    } else {
		    Response response2 = target("book").path("46/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
		    DeleteCopyMessage deleteCopyMessage = response2.readEntity(DeleteCopyMessage.class);
		    Assert.assertTrue(deleteCopyMessage.getCommandSucceeded());
	    }
	}
	
	@Test
	public void addCopyOfNotExistingBookGivesCommandSucceededIsFalse() {
	    Response response = target("book").path("10000000/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = response.readEntity(AddCopyMessage.class);
	    Assert.assertFalse(addCopyMessage.getCommandSucceeded());
	}
	
	@Test
	public void deleteCopyOfNotExistingBookGivesCommandSucceededIsFalse() {
	    Response response = target("book").path("10000000/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteCopyMessage deleteCopyMessage = response.readEntity(DeleteCopyMessage.class);
	    Assert.assertFalse(deleteCopyMessage.getCommandSucceeded());
	}
	
	@Test
	public void addBookAnddeleteCopyGivesCommandSucceededIsFalseDeleteBookGivesCommandSucceededIsTrue() {
	    Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
	    if (!addBookMessage.getCommandSucceeded()) {
	    	Assert.fail();
	    } else {
		    Response deleteCopyResponse = target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
		    DeleteCopyMessage deleteCopyMessage = deleteCopyResponse.readEntity(DeleteCopyMessage.class);
		    
		    if (deleteCopyMessage.getCommandSucceeded()) {
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
	public void copiesAvailableOfCreatedBookIsZero() {
		Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		String output = target("book").path(Integer.toString(addBookMessage.getBookId())).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
	    Assert.assertTrue(output.contains("\"copiesAvailable\":0"));
	}
	
	@Test
	public void copiesAvailableOfCreatedBookIsOneIfOneCopyIsAdded() {
		Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		Response addCopyResponse = target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = addCopyResponse.readEntity(AddCopyMessage.class);
	    Assert.assertEquals(addCopyMessage.getCopiesOfBook(), 1);
	}
	
	@Test
	public void copiesAvailableOfCreatedBookIsTwoIfTwoTimesCopyIsAdded() {
		Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
		Response addCopyResponse = target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = addCopyResponse.readEntity(AddCopyMessage.class);
	    Assert.assertEquals(addCopyMessage.getCopiesOfBook(), 2);
	}
	
	@Test
	public void copiesAvailableOfCreatedBookIsOneIfOneCopyIsDeletedAfterTwoCopiesAreAdded() {
		Response addBookResponse = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
		AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
		target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
		target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
		Response deleteCopyResponse = target("book").path(addBookMessage.getBookId() + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteCopyMessage deleteCopyMessage = deleteCopyResponse.readEntity(DeleteCopyMessage.class);
	    Assert.assertEquals(deleteCopyMessage.getCopiesOfBook(), 1);
	}
	
	private void deleteBook(int bookId) {
		target("/book").path(Integer.toString(bookId)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	}
}
