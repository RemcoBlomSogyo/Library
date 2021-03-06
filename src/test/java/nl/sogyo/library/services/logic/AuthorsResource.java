package nl.sogyo.library.services.logic;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.logic.query.QueryHelper;

@Path("authors")
public class AuthorsResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Author> getAllAuthors() {
		List<Author> authors;
		try {
			QueryHelper queryHelper = new QueryHelper(idToken);
			authors = queryHelper.getAllAuthors();
		} catch (Exception e) {
			authors = new ArrayList<Author>();
		}
		return authors;
	}
}
