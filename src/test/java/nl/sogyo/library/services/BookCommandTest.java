package nl.sogyo.library.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import nl.sogyo.library.services.rest.libraryapi.resources.BookResource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookCommandTest extends JerseyTest {
	
	private static int id;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BookResource.class);
	}
	
	@Test
	public void test01PostValidBook() {
		System.out.println("1");
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	    Response response = target("book").request().post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    System.out.println("System.out id =" + addBookMessage.getBookId());
	    id = addBookMessage.getBookId();
	    Assert.assertTrue(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test02UpdateCreatedBook() {
		System.out.println("2");
		System.out.println("id ="  + id);
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "Polymer", "Appress", "9784567890120");
	    Response response = target("book").path(Integer.toString(id)).request().put(Entity.json(bookFormInput));
	    EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
	    Assert.assertTrue(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test03DeleteCreatedBook() {
		System.out.println("3");
	    Response response = target("book").path(Integer.toString(id)).request().delete();
	    DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
	    Assert.assertTrue(deleteBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test04PostBookWithInvalidIsbn() {
		System.out.println("4");
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890125");
	    Response response = target("book").request().post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test05PostBookWithoutAuthorSurname() {
		System.out.println("5");
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "", "REST", "Appress", "9784567890120");
	    Response response = target("book").request().post(Entity.json(bookFormInput));
	    AddBookMessage addBookMessage = response.readEntity(AddBookMessage.class);
	    Assert.assertFalse(addBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test06UpdateNotExistingBook() {
		System.out.println("6");
		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
	    Response response = target("book").path("10000000").request().put(Entity.json(bookFormInput));
	    EditBookMessage editBookMessage = response.readEntity(EditBookMessage.class);
	    Assert.assertFalse(editBookMessage.getCommandSucceeded());
	}
	
	@Test
	public void test07DeleteNotExistingBook() {
		System.out.println("7");
	    Response response = target("book").path("10000000").request().delete();
	    DeleteBookMessage deleteBookMessage = response.readEntity(DeleteBookMessage.class);
	    Assert.assertFalse(deleteBookMessage.getCommandSucceeded());
	}
}
