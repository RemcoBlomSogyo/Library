package nl.sogyo.library.services.rest.libraryapi.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.command.CommandHelper;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditUsersMessage;

@Path("users")
public class UsersResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditUsersMessage editUsers(List<User> users) {
		System.out.println("test");
		EditUsersMessage editUsersMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			editUsersMessage = commandHelper.editUsers(users);
		} catch (Exception e) {
			editUsersMessage = new EditUsersMessage(false);
		}
		return editUsersMessage;
	}
}
