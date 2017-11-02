package nl.sogyo.library.services.rest.libraryapi.resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.register.RegisterHelper;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;

@Path("register")
public class RegisterResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION)
	private String idToken;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RegisterMessage registerUser() {
		System.out.println("post signin");
		RegisterHelper registerHelper = new RegisterHelper(idToken);
		RegisterMessage registerMessage = registerHelper.registerUser();
		return registerMessage;
	}
}
