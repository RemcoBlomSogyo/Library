package nl.sogyo.library.model.register;

import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.library.model.LibraryHelper;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;

public class RegisterHelper extends LibraryHelper {
	
	private RegisterMessage registerMessage;
	
	public RegisterHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		super(idToken);
	}
	
	public RegisterMessage registerUser() {
		System.out.println("registerUser");
		try {
			User sogyoUser = new User(googleUser);
			System.out.println(sogyoUser.getGoogleUserId());
			System.out.println(sogyoUser.getGivenName());
			System.out.println(sogyoUser.getEmail());
			System.out.println("registerUser");
			registerMessage = databaseHandler.insertUserIfNotInTable(sogyoUser);
		} catch (IllegalArgumentException e) {
			registerMessage = new RegisterMessage(false, false, "Email is not a Sogyo account", "", "", (byte) 0);
		}
		return registerMessage;
	}
}
