package nl.sogyo.library.services.rest.libraryapi.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.query.QueryHelper;

@Path("users")
public class UsersResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@GET
	@Produces("application/json")
	public List<User> getAllUsers() {
		List<User> users;
		try {
			QueryHelper queryHelper = new QueryHelper(idToken);
			users = queryHelper.getAllUsers();
		} catch (Exception e) {
			users = new ArrayList<User>();
		}
		return users;
	}
}
