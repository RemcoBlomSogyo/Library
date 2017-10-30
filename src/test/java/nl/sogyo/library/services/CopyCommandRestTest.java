package nl.sogyo.library.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
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
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.resource.BookResource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CopyCommandRestTest extends JerseyTest {
//	
//	@Override
//	public Application configure() {
//		enable(TestProperties.LOG_TRAFFIC);
//		enable(TestProperties.DUMP_ENTITY);
//		return new ResourceConfig(BookResource.class);
//	}
//	
//	@Test
//	public void test01AddCopyOfProRESTfulAPIs() {
//	    Response response = target("book").path("46/copy").request().post(null);
//	    AddCopyMessage addCopyMessage = response.readEntity(AddCopyMessage.class);
//	    Assert.assertTrue(addCopyMessage.getCommandSucceeded());
//	}
//	
//	@Test
//	public void test02DeleteCopyOfProRESTfulAPIs() {
//	    Response response = target("book").path("46/copy").request().delete();
//	    DeleteCopyMessage deleteCopyMessage = response.readEntity(DeleteCopyMessage.class);
//	    Assert.assertTrue(deleteCopyMessage.getCommandSucceeded());
//	}
//	
//	@Test
//	public void test03AddCopyOfNotExistingBook() {
//	    Response response = target("book").path("10000000/copy").request().post(null);
//	    AddCopyMessage addCopyMessage = response.readEntity(AddCopyMessage.class);
//	    Assert.assertFalse(addCopyMessage.getCommandSucceeded());
//	}
//	
//	@Test
//	public void test04DeleteCopyOfNotExistingBook() {
//	    Response response = target("book").path("10000000/copy").request().delete();
//	    DeleteCopyMessage deleteCopyMessage = response.readEntity(DeleteCopyMessage.class);
//	    Assert.assertFalse(deleteCopyMessage.getCommandSucceeded());
//	}
//	
//	@Test
//	public void test05DeleteCopyOfBookWithNoCopies() {
//		BookFormInput bookFormInput = new BookFormInput("title", "Sanjay", "Patni", "REST", "Appress", "9784567890120");
//	    Response addBookResponse = target("book").request().post(Entity.json(bookFormInput));
//	    AddBookMessage addBookMessage = addBookResponse.readEntity(AddBookMessage.class);
//	    
//	    Response deleteCopyResponse = target("book").path(addBookMessage.getBookId() + "/copy").request().delete();
//	    DeleteCopyMessage deleteCopyMessage = deleteCopyResponse.readEntity(DeleteCopyMessage.class);
//	    
//	    target("book").path(Integer.toString(addBookMessage.getBookId())).request().delete();
//	    Assert.assertFalse(deleteCopyMessage.getCommandSucceeded());
//	}
}
