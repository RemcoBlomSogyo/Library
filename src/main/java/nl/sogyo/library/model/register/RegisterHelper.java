package nl.sogyo.library.model.register;

import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.library.model.LibraryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;
import nl.sogyo.oauth.javagooglesignin.exception.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;

public class RegisterHelper extends LibraryHelper {
	
	private RegisterMessage registerMessage;
	
	public RegisterHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		super(idToken);
	}
	
	public RegisterMessage registerUser() {
		registerMessage = databaseHandler.insertUserIfNotInTable(user);
		return registerMessage;
	}
}
