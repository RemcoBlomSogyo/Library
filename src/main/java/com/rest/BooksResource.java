package com.rest;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.model.Book;
import com.model.Library;

@Path("books")
public class BooksResource {
	
//	public BookResource() {
//		
//	}
	
	@GET
	@Produces("application/json")
	public Collection<Book> getAllBooks() {
		List<Book> allBooks = Library.getAllBooks();
		for (Book book : allBooks) {
			System.out.println(book.getId() + " " + book.getTitle());
		}
//		allBooks = Library.getAllBooks();
		
//		String response = "<ul>";
//		
//		for (Book book : allBooks) {
//			response += ("<li>" + book.getId() + " - " + book.getTitle() + "</li>");
//		}
//		
//		response += "</ul>";
//		return response;
		return allBooks;
	}
}
