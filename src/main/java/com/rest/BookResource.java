package com.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("books")
public class BookResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		System.out.println("test");
		return "Hello from REST";
	}
}
