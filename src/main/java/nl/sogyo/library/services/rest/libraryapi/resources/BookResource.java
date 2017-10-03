package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import nl.sogyo.library.model.command.Library;
import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookId;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

@Path("book")
public class BookResource {
	
	@GET 
	@Path("{id}")
	@Produces("application/json")
	public BookInfo getBookInfo(@PathParam("id") int id) {
		BookInfo bookInfo = QueryHelper.getBookInfo(id);
		return bookInfo;
	}

	@POST 
	@Consumes("application/json")
	@Produces("application/json")
	public AddBookMessage addBook(BookFormInput bookFormInput) {
		System.out.println("test rest");
		System.out.println("Title: " + bookFormInput.getTitle());
		AddBookMessage addBookMessage = Library.addBook(bookFormInput);
		System.out.println("addBookmessage: " + addBookMessage.getMessage());
		return addBookMessage;
	}
	
	@PUT 
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public EditBookMessage editBook(@PathParam("id") int id, BookFormInput bookFormInput) {
		EditBookMessage editBookMessage = Library.editBook(id, bookFormInput);
		return editBookMessage;
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public DeleteBookMessage deleteBook(@PathParam("id") int id) {
		DeleteBookMessage deleteBookMessage = Library.deleteBook(id);
		return deleteBookMessage;
	}
	
	@POST
	@Path("{bookId}/copy")
	@Consumes("application/json")
	@Produces("application/json")
	public AddCopyMessage addCopy(@PathParam("bookId") int bookId) {
		AddCopyMessage addCopyMessage = Library.addCopy(bookId);
		return addCopyMessage;
	}
	
	@DELETE
	@Path("{bookId}/copy")
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteCopyMessage deleteCopy(@PathParam("bookId") int bookId) {
		DeleteCopyMessage deleteCopyMessage = Library.deleteCopy(bookId);
		System.out.println(" " + deleteCopyMessage.getCommandSucceeded()
				+ deleteCopyMessage.getCopiesOfBook()
				+ deleteCopyMessage.getMessage());
		return deleteCopyMessage;
	}
}