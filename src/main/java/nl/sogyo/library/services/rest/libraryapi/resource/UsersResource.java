package nl.sogyo.library.services.rest.libraryapi.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.command.CommandHelper;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.query.QueryHelper;
import nl.sogyo.library.model.register.RegisterHelper;
import nl.sogyo.library.services.rest.libraryapi.json.message.BorrowCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditUsersMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;

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
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RegisterMessage registerUser() {
		RegisterMessage registerMessage;
		try {
			RegisterHelper registerHelper = new RegisterHelper(idToken);
			registerMessage = registerHelper.registerUser();
		} catch (Exception e) {
			System.out.println("exception register: " + e.getMessage());
			registerMessage = new RegisterMessage(false, false, "Token is not verified", 0, "", "", (byte) 0);
		}
		return registerMessage;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditUsersMessage editUsers(List<User> users) {
		EditUsersMessage editUsersMessage;
		try {
			CommandHelper commandHelper = new CommandHelper(idToken);
			editUsersMessage = commandHelper.editUsers(users);
		} catch (Exception e) {
			editUsersMessage = new EditUsersMessage(false);
		}
		return editUsersMessage;
	}
	
	@POST
	@Path("{userId}/books/{bookId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BorrowCopyMessage borrowCopy(@PathParam("userId") int userId, @PathParam("bookId") int bookId) {
		BorrowCopyMessage borrowCopyMessage;
		try {
			System.out.println("borrow rest try 1");
			CommandHelper commandHelper = new CommandHelper(idToken);
			System.out.println("borrow rest try 2");
			borrowCopyMessage = commandHelper.borrowCopy(userId, bookId);
			System.out.println("borrow rest try 3");
		} catch (Exception e) {
			System.out.println("borrow rest catch 1");
			borrowCopyMessage = new BorrowCopyMessage(false, 0, 0);
		}
		return borrowCopyMessage;
	}
}
