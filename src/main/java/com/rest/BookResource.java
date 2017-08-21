package com.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.model.Book;
import com.model.Library;
import com.model.SuccessMessage;

@Path("book")
public class BookResource {

	@POST @Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public SuccessMessage addBook(Book book) {
		boolean commandSucceeded = Library.addBook(book);
		return new SuccessMessage(commandSucceeded);
	}
}