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
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;
import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookCommandRestTest extends JerseyTest {
	
	private static int id;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void test01PostValidBook() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	    Response response = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    id = addBookMessage.getBookId();
	    Assert.assertTrue(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test02UpdateCreatedBook() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
	    Response response = target("book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(bookFormInput));
	    EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
	    Assert.assertTrue(editBookMessage.getCommandSucceeded());
	}
	
	// four copy tests for copiesAvailable 
	@Test
	public void test03CopiesAvailableOfCreatedBookIsZero() {
	    String output = target("book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).get(String.class);
	    Assert.assertTrue(output.contains("\"copiesAvailable\":0"));
	}
	
	@Test
	public void test04CopiesAvailableOfCreatedBookIsOneIfOneCopyIsAdded() {
	    Response response = target("book").path(id + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = response.readEntity(AddCopyMessage.class);
	    Assert.assertEquals(addCopyMessage.getCopiesOfBook(), 1);
	}
	
	@Test
	public void test05CopiesAvailableOfCreatedBookIsTwoIfAnotherCopyIsAdded() {
		Response response = target("book").path(id + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(null);
	    AddCopyMessage addCopyMessage = response.readEntity(AddCopyMessage.class);
	    Assert.assertEquals(addCopyMessage.getCopiesOfBook(), 2);
	}
	
	@Test
	public void test06CopiesAvailableOfCreatedBookIsOneIfOneCopyIsDeleted() {
	    Response response = target("book").path(id + "/copy").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteCopyMessage deleteCopyMessage = response.readEntity(DeleteCopyMessage.class);
	    Assert.assertEquals(deleteCopyMessage.getCopiesOfBook(), 1);
	}
	
	@Test
	public void test07DeleteCreatedBook() {
	    Response response = target("book").path(Integer.toString(id)).request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
	    Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test08PostBookWithInvalidIsbn() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890125");
	    Response response = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test09PostBookWithoutAuthorSurname() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "", "REST", "Appress", "9784567890120");
	    Response response = target("book").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test10UpdateNotExistingBook() {
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	    Response response = target("book").path("10000000").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).put(Entity.json(bookFormInput));
	    EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
	    if (editBookMessage == null) {
	    	System.out.println("editbookmessage is null");
	    }
	    Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test11DeleteNotExistingBook() {
	    Response response = target("book").path("10000000").request().header(ContainerRequest.AUTHORIZATION, TEST_ID_TOKEN_2).delete();
	    DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
	    Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
	
//	@Test
//	public void test10PostValidBookWithNewAuthorCategoryAndPublisher() {
//		BookFormInput bookFormInput = new BookFormInput("Philips", "Philip", "Philips", "Elektronica", "Philips BV", "9784567890120");
//	    Response response = target("book").request().post(Entity.json(bookFormInput));
//	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
//	    Assert.assertTrue(addBookMessage.getCommandSucceeded());
//	}
//	
//	@Test
//	public void test11PostValidBookWithNewAuthorCategoryAndPublisher() {
//		BookFormInput bookFormInput = new BookFormInput("Kaas", "Een melkproduct", "Komijn", "Kaas", 
//				"Brie", "Kaasje", "Oude", "Kazen", "Elektronica", "Philips BV", "2000", "9784567890120", "100", "Hongaars");
//	    Response response = target("book").request().post(Entity.json(bookFormInput));
//	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
//	    Assert.assertTrue(addBookMessage.getCommandSucceeded());
//	}
}
