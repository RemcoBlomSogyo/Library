package nl.sogyo.library.services.rest.libraryapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;

@Path("books")
public class BooksResource {
	
	@GET
	@Produces("application/json")
	public List<BookPreview> getBooks(@QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("isbn") String isbn) {
		QueryHelper queryHelper = new QueryHelper();
		List<BookPreview> books = queryHelper.getBooks(title, author, isbn);
		return books;
	}
}
