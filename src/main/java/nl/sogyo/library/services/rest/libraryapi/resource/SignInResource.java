package nl.sogyo.library.services.rest.libraryapi.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import nl.sogyo.library.model.signin.SignInHelper;
import nl.sogyo.library.services.rest.libraryapi.json.SignInCode;

@Path("signin")
public class SignInResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public int signInUser(SignInCode signInCode) {
		SignInHelper signInHelper = new SignInHelper();
		int userInfo = signInHelper.signInUser(signInCode);
		return userInfo;
	}
}
