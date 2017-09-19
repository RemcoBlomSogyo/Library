package nl.sogyo.library;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resources.BooksResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class RestTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BooksResource.class);
	}
	
	@Test
	public void searchForBooksWithTitlesWithAGivesStatus200() {
		Response output = target("/books").queryParam("title", "a").queryParam("author", "").queryParam("isbn", "").request().get();
		System.out.println(output.getStatus());
		Assert.assertEquals(200, output.getStatus());
	}
	
	@Test
	public void searchForBooksWithoutQueryValuesGivesEmptyArray() {
		String output = target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "").request().get(String.class);
		Assert.assertEquals("[]", output);
	}
	
	@Test
	public void searchForBooksWithNotExistingIsbnGivesEmptyArray() {
		String output = target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889372").request().get(String.class);
		Assert.assertEquals("[]", output);
	}
	
	@Test
	public void searchForBooksWithExistingIsbnGivesJsonOfBook() {
		String output = target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889370").request().get(String.class);
		Assert.assertTrue(output.contains("Polymer"));
	}
}
