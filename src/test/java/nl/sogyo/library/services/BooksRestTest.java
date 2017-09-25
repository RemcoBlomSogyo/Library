package nl.sogyo.library.services;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.services.rest.libraryapi.resources.BooksResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class BooksRestTest extends JerseyTest {
	
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
	public void searchForBooksWithExistingIsbnGivesOnlyJsonOfBook() {
		System.out.println(target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889370"));
		String output = target("/books").queryParam("title", "").queryParam("author", "").queryParam("isbn", "9781785889370").request().get(String.class);
		Assert.assertTrue(output.contains("Polymer"));
	}
	
	@Test
	public void getBookInfoOfProRestfulApis() {
//		System.out.println("test");
//	    Client client = ClientBuilder.newClient();
//	    WebTarget target = null;
//	    try {
//	    	target=client.target(new URI("http://localhost:9998/SogyoLibrary/rest/book/46"));
//	    }catch (Exception e){}
//	    Builder builder =   target.request();
//	        //Response response  = builder.get();
//	    String result  = builder.get(String.class);
//		System.out.println("output: " + result);
		
		System.out.println(target("/book").path("46"));
		String output = target("/book").path("46").request().get(String.class);
		Assert.assertTrue(output.contains("RESTful") && output.contains("9781484226643") 
				&& output.contains("Design, Build and Integrate with REST, JSON, XML and JAX-RS") && output.contains("Engels"));
		
//		//		.request().get(String.class);
//		
//		
//		Assert.assertTrue(output.contains("RESTful") && output.contains("9781484226643") 
//				&& output.contains("Design, Build and Integrate with REST, JSON, XML and JAX-RS") && output.contains("Engels"));
	}
}
