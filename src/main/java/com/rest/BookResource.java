package com.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.model.Book;
import com.model.Library;
import com.rest.json.SuccessMessage;
import com.rest.json.AddBookInput;

@Path("book")
public class BookResource {
	
//	@GET
//	@Produces("application/json")
//	public List<Book> getBooks(@QueryParam("title") String titleInput,
//			@QueryParam("author") String authorInput, 
//			@QueryParam("isbn") String isbnInput) {
//		return Library.getBooks(titleInput, authorInput, isbnInput);
//	}

//	@POST @Path("/add")
//	@Consumes("application/json")
//	@Produces("application/json")
//	public SuccessMessage addBook(AddBookInput addBookInput) {
//		boolean commandSucceeded = Library.addBook(addBookInput);
//		return new SuccessMessage(commandSucceeded);
//	}
}