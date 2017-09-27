package nl.sogyo.library.services.rest.libraryapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;

@Path("books")
public class BooksResource {
	
	public BooksResource() {
		//register(CORSResponseFilter.class);
	}
	
//	@GET
//	@Produces("application/json")
//	public Collection<TestBook> getAllBooks() {
//		List<TestBook> allBooks = QueryHelper.getAllBooks();
//		for (TestBook testBook : allBooks) {
//			System.out.println(testBook.getId() + " " + testBook.getTitle());
//		}
////		allBooks = Library.getAllBooks();
//		
////		String response = "<ul>";
////		
////		for (Book book : allBooks) {
////			response += ("<li>" + book.getId() + " - " + book.getTitle() + "</li>");
////		}
////		
////		response += "</ul>";
////		return response;
//		return allBooks;
//	}
	
	@GET
	@Produces("application/json")
	public List<BookPreview> getBooks(@QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("isbn") String isbn) {
		System.out.println("query");
		System.out.println(title);
		System.out.println(author);
		System.out.println(isbn);

		List<BookPreview> books = QueryHelper.getBooks(title, author, isbn);
		return books;
	}
}
