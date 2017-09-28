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
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
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
		AddBookMessage addBookMessage = Library.addBook(bookFormInput);
		return addBookMessage;
	}
	
	@PUT 
	@Consumes("application/json")
	@Produces("application/json")
	public EditBookMessage editBook(BookFormInput bookFormInput) {
		EditBookMessage editBookMessage = Library.editBook(bookFormInput);
		return editBookMessage;
	}
	
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteBookMessage deleteCopy(BookId copyCommand) {
		DeleteBookMessage deleteBookMessage = Library.deleteBook(copyCommand);
		return deleteBookMessage;
	}
}