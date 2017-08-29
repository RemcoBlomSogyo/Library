package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.command.Library;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.SuccessMessage;

@Path("book")
public class BookResource {
	
//	@GET
//	@Produces("application/json")
//	public List<Book> getBooks(@QueryParam("title") String titleInput,
//			@QueryParam("author") String authorInput, 
//			@QueryParam("isbn") String isbnInput) {
//		return Library.getBooks(titleInput, authorInput, isbnInput);
//	}
	
	@GET @Path("/add")
	@Produces("application/json")
	public SuccessMessage testBook(BookFormInput bookFormInput) {
	
		boolean commandSucceeded = false;

		return new SuccessMessage(commandSucceeded);
	}

	@POST @Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public SuccessMessage addBook(BookFormInput bookFormInput) {
		//ObjectMapper mapper = new ObjectMapper();
//		boolean commandSucceeded = Library.addBook(addBookInput);
//		return new SuccessMessage(commandSucceeded);
		System.out.println(bookFormInput.getTitle());
		System.out.println(bookFormInput.getSubtitle());
		System.out.println(bookFormInput.getAuthorForname());
		System.out.println(bookFormInput.getAuthorSurname());
		System.out.println(bookFormInput.getCategory());
		System.out.println(bookFormInput.getPublisher());
		System.out.println(bookFormInput.getYearFirstPublication());
		System.out.println(bookFormInput.getIsbn());
		System.out.println(bookFormInput.getPages());
		System.out.println(bookFormInput.getLanguage());
		
		SuccessMessage successMessage = Library.addBook(bookFormInput);
		return successMessage;
	}
}