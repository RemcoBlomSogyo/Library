package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		System.out.println("test");
		return "Hello from REST";
	}
}