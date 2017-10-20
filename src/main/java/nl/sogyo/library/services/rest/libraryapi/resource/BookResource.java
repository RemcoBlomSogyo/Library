package nl.sogyo.library.services.rest.libraryapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.command.CommandHelper;
import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

@Path("book")
public class BookResource {
	
	@GET 
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BookInfo getBookInfo(@PathParam("id") int id) {
		System.out.println("test");
		QueryHelper queryHelper = new QueryHelper();
		BookInfo bookInfo = queryHelper.getBookInfo(id);
		return bookInfo;
	}

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddBookMessage addBook(BookFormInput bookFormInput) {
		System.out.println("test rest");
		System.out.println("Title: " + bookFormInput.getTitle());
		System.out.println("subTitle: " + bookFormInput.getSubtitle());
		System.out.println("author fore1: " + bookFormInput.getAuthorForename1());
		System.out.println("author sur1: " + bookFormInput.getAuthorSurname1());
		System.out.println("author fore2: " + bookFormInput.getAuthorForename2());
		System.out.println("author sur2: " + bookFormInput.getAuthorSurname2());
		System.out.println("author fore3: " + bookFormInput.getAuthorForename3());
		System.out.println("author sur3: " + bookFormInput.getAuthorSurname3());
		System.out.println("category: " + bookFormInput.getCategory());
		System.out.println("publisher: " + bookFormInput.getPublisher());
		System.out.println("pages: " + bookFormInput.getPages());
		System.out.println("language: " + bookFormInput.getLanguage());
		System.out.println("isbn: " + bookFormInput.getIsbn());
		System.out.println("year: " + bookFormInput.getYearFirstPublication());
		
		CommandHelper library = new CommandHelper();
		AddBookMessage addBookMessage = library.addBook(bookFormInput);
		System.out.println("addBookmessage: " + addBookMessage.getMessage());
		return addBookMessage;
	}
	
	@PUT 
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public EditBookMessage editBook(@PathParam("id") int id, BookFormInput bookFormInput) {
		CommandHelper library = new CommandHelper();
		EditBookMessage editBookMessage = library.editBook(id, bookFormInput);
		return editBookMessage;
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public DeleteBookMessage deleteBook(@PathParam("id") int id) {
		CommandHelper library = new CommandHelper();
		DeleteBookMessage deleteBookMessage = library.deleteBook(id);
		return deleteBookMessage;
	}
	
	@POST
	@Path("{bookId}/copy")
	@Consumes("application/json")
	@Produces("application/json")
	public AddCopyMessage addCopy(@PathParam("bookId") int bookId) {
		CommandHelper library = new CommandHelper();
		AddCopyMessage addCopyMessage = library.addCopy(bookId);
		return addCopyMessage;
	}
	
	@DELETE
	@Path("{bookId}/copy")
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteCopyMessage deleteCopy(@PathParam("bookId") int bookId) {
		CommandHelper library = new CommandHelper();
		DeleteCopyMessage deleteCopyMessage = library.deleteCopy(bookId);
		System.out.println(" " + deleteCopyMessage.getCommandSucceeded()
				+ deleteCopyMessage.getCopiesOfBook()
				+ deleteCopyMessage.getMessage());
		return deleteCopyMessage;
	}
}