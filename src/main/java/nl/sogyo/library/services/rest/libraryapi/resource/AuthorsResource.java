package nl.sogyo.library.services.rest.libraryapi.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;

import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.query.QueryHelper;

@Path("authors")
public class AuthorsResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@GET
	@Produces("application/json")
	public List<Author> getAllAuthors() {
		QueryHelper queryHelper = new QueryHelper(idToken);
		List<Author> authors = queryHelper.getAllAuthors();
		return authors;
	}
}
