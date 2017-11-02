package nl.sogyo.library.model.register;

import nl.sogyo.library.model.LibraryHelper;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;

public class RegisterHelper extends LibraryHelper {
	
	private RegisterMessage registerMessage;
	
	public RegisterHelper(String idToken) {
		super(idToken);
	}
	
	public RegisterMessage registerUser() {
		try {
			User sogyoUser = new User(googleUser);
			System.out.println(sogyoUser.getGoogleUserId());
			System.out.println(sogyoUser.getGivenName());
			System.out.println(sogyoUser.getEmail());
			registerMessage = databaseHandler.insertUserIfNotInTable(sogyoUser);
		} catch (IllegalArgumentException e) {
			registerMessage = new RegisterMessage(false, false, "Email is not a Sogyo account");
		}
		return registerMessage;
	}
}
