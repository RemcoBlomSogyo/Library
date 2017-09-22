package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.POST;
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
import nl.sogyo.library.services.rest.libraryapi.json.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.AddCopyMessage;
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
	
//	@GET @Path("/add")
//	@Produces("application/json")
//	public SuccessMessage testBook(BookFormInput bookFormInput) {
//	
//		boolean commandSucceeded = false;
//
//		return new SuccessMessage(commandSucceeded);
//	}
	
	@GET 
	@Path("{id}")
	@Produces("application/json")
	public BookInfo getBookInfo(@PathParam("id") int id) {
		System.out.println("getBookInfo " + id);
		BookInfo bookInfo = QueryHelper.getBookInfo(id);
		return bookInfo;
	}

	@POST 
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
	
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteBookMessage deleteCopy(BookId copyCommand) {
		System.out.println("deleteBook " + copyCommand.getBookId());
		DeleteBookMessage deleteBookMessage = Library.deleteBook(copyCommand);
		System.out.println(" " + deleteBookMessage.getCommandSucceeded()
				+ deleteBookMessage.getCopiesOfBook()
				+ deleteBookMessage.getMessage());
		return deleteBookMessage;
	}
}