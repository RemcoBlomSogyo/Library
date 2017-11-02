package nl.sogyo.library.services.rest.libraryapi.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;

import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;

@Path("books")
public class BooksResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@GET
	@Produces("application/json")
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
}
