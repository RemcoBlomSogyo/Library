package nl.sogyo.library.services.logic;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.logic.command.CommandHelper;
import nl.sogyo.library.model.logic.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

@Path("books")
public class BooksResource extends RestResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookPreview> getBooks(@QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("isbn") String isbn) {
		List<BookPreview> books;
		try {
			QueryHelper queryHelper = new QueryHelper(idToken);
			books = queryHelper.getBooks(title, author, isbn);
		} catch (Exception e) {
			books = new ArrayList<BookPreview>();
		}
		return books;
	}
	
	@GET 
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BookInfo getBookInfo(@PathParam("id") int id) {
		System.out.println("testclass");
		BookInfo bookInfo;
		try {
			QueryHelper queryHelper = new QueryHelper(idToken);
			bookInfo = queryHelper.getBookInfo(id);
		} catch (Exception e) {
			bookInfo = new BookInfo();
		}
		return bookInfo;
	}

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddBookMessage addBook(BookFormInput bookFormInput) {
		AddBookMessage addBookMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			addBookMessage = commandHelper.addBook(bookFormInput);
		} catch (Exception e) {
			addBookMessage = new AddBookMessage(0);
		}
		return addBookMessage;
	}
	
	@PUT 
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditBookMessage editBook(@PathParam("id") int id, BookFormInput bookFormInput) {
		EditBookMessage editBookMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			editBookMessage = commandHelper.editBook(id, bookFormInput);
		} catch (Exception e) {
			editBookMessage = new EditBookMessage(false);
		}
		return editBookMessage;
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteBookMessage deleteBook(@PathParam("id") int id) {
		DeleteBookMessage deleteBookMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			deleteBookMessage = commandHelper.deleteBook(id);
		} catch (Exception e) {
			deleteBookMessage = new DeleteBookMessage(false);
		}
		return deleteBookMessage;
	}
	
	@POST
	@Path("{bookId}/copies")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddCopyMessage addCopy(@PathParam("bookId") int bookId) {
		AddCopyMessage addCopyMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			addCopyMessage = commandHelper.addCopy(bookId);
		} catch (Exception e) {
			addCopyMessage = new AddCopyMessage(false, 0);
		}
		return addCopyMessage;
	}
	
	@DELETE
	@Path("{bookId}/copies")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteCopyMessage deleteCopy(@PathParam("bookId") int bookId) {
		DeleteCopyMessage deleteCopyMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			deleteCopyMessage = commandHelper.deleteCopy(bookId);
		} catch (Exception e) {
			deleteCopyMessage = new DeleteCopyMessage(false, 0);
		}
		return deleteCopyMessage;
	}
}
