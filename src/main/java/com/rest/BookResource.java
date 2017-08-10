package com.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.model.Book;
import com.model.Library;

@Path("books")
public class BookResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAllBooks() {
		List<Book> allBooks = Library.getAllBooks();
		
		String response = "<ul>";
		
		for (Book book : allBooks) {
			response += ("<li>" + book.getId() + " - " + book.getTitle() + "</li>");
		}
		
		response += "</ul>";
		return response;
	}
}
