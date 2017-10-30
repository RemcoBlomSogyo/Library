package nl.sogyo.library.services;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resource.BooksResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class BooksQueryRestTest extends JerseyTest {
	
//	@Override
//	public Application configure() {
//		enable(TestProperties.LOG_TRAFFIC);
//		enable(TestProperties.DUMP_ENTITY);
//		return new ResourceConfig(BooksResource.class);
//	}
//	
//	@Test
//	public void searchForBooksWithTitlesWithAGivesStatus200() {
//		Response output = target("books").queryParam("title", "a").queryParam("author", "").queryParam("isbn", "").request().get();
//		System.out.println(output.getStatus());
//		Assert.assertEquals(200, output.getStatus());
//	}
//	
//	@Test
//	public void searchForBooksWithoutQueryValuesGivesEmptyArray() {
//		String output = target("books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "").request().get(String.class);
//		Assert.assertEquals("[]", output);
//	}
//	
//	@Test
//	public void searchForBooksWithNotExistingIsbnGivesEmptyArray() {
//		String output = target("books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889372").request().get(String.class);
//		Assert.assertEquals("[]", output);
//	}
//	
//	@Test
//	public void searchForBooksWithExistingIsbnGivesJsonOfBook() {
//		System.out.println(target("books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889370"));
//		String output = target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889370").request().get(String.class);
//		Assert.assertTrue(output.contains("Getting Started with Polymer"));
//	}
//	
//	@Test
//	public void searchForBooksOfArshakGivesGettingStartedWithPolymer() {
//		String output = target("books").queryParam("title", "").queryParam("author", "Arshak").queryParam("isbn", "").request().get(String.class);
//		Assert.assertTrue(output.contains("Getting Started with Polymer"));
//	}
//	
//	@Test
//	public void searchForBooksOfArshakKhachatrianGivesGettingStartedWithPolymer() {
//		String output = target("books").queryParam("title", "").queryParam("author", "Arshak Khachatrian").queryParam("isbn", "").request().get(String.class);
//		Assert.assertTrue(output.contains("Getting Started with Polymer"));
//	}
//	
//	@Test
//	public void searchForBooksOfNotExistingAuthorGivesEmptyArray() {
//		String output = target("books").queryParam("title", "").queryParam("author", "Schaapgeitkoekip").queryParam("isbn", "").request().get(String.class);
//		Assert.assertEquals("[]", output);
//	}
//	
//	@Test
//	public void searchForBooksOfNotExistingForenameAndSurnameGivesEmptyArray() {
//		String output = target("books").queryParam("title", "").queryParam("author", "Henk Westbroek").queryParam("isbn", "").request().get(String.class);
//		Assert.assertEquals("[]", output);
//	}
//	
//	@Test
//	public void searchForTitlePolymerGivesGettingStartedPolymerInJson() {
//		String output = target("books").queryParam("title", "polymer").queryParam("author", "").queryParam("isbn", "").request().get(String.class);
//		Assert.assertTrue(output.contains("Getting Started with Polymer"));
//	}
//	
//	@Test
//	public void searchForNotExistingWordInAnyTitleGivesEmptyArray() {
//		String output = target("books").queryParam("title", "jongbelegenkaas").queryParam("author", "").queryParam("isbn", "").request().get(String.class);
//		Assert.assertEquals("[]", output);
//	}
//	
//	@Test
//	public void searchForExactlyTheSameTitleGivesTheBookInJson() {
//		String output = target("books").queryParam("title", "Pro RESTful APIs").queryParam("author", "").queryParam("isbn", "").request().get(String.class);
//		Assert.assertTrue(output.contains("Pro RESTful APIs"));
//	}
}
