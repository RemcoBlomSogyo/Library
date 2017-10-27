package nl.sogyo.library.services.rest.libraryapi.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.register.RegisterHelper;
import nl.sogyo.library.services.rest.libraryapi.json.IdToken;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;

@Path("register")
public class RegisterResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RegisterMessage registerUser(IdToken idToken) {
		System.out.println("post signin");
		RegisterHelper registerHelper = new RegisterHelper();
		RegisterMessage registerMessage = registerHelper.registerUser(idToken);
		return registerMessage;
	}
}
