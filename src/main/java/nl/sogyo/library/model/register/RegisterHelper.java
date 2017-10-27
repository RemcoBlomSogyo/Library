package nl.sogyo.library.model.register;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.IdToken;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;
import nl.sogyo.oauth.javagooglesignin.*;

public class RegisterHelper {

	private DatabaseHandler databaseHandler;
	private RegisterMessage registerMessage;
	private static final String CLIENT_SECRET_FILE = "C:/Users/rblom/Documents/apache-tomcat/webapps/SogyoLibrary/src/main/resources/google-api/"
			+ "client_secret_1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com.json";
	
	public RegisterHelper() {
		databaseHandler = new DatabaseHandler();
	}
	
	public RegisterMessage registerUser(IdToken idToken) {
		try {
			GoogleUser googleUser = getGoogleUserFromToken(idToken);
			User sogyoUser = new User(googleUser);
			System.out.println(sogyoUser.getGoogleUserId());
			System.out.println(sogyoUser.getGivenName());
			System.out.println(sogyoUser.getEmail());
			registerMessage = databaseHandler.insertUserIfNotInTable(sogyoUser);
		} catch (IOException e) {
			registerMessage = new RegisterMessage(false, false, "Could not verify token at Google");
		} catch (EmailNotVerifiedException e) {
			registerMessage = new RegisterMessage(false, false, "Email is not verified by Google");
		} catch (IllegalArgumentException e) {
			registerMessage = new RegisterMessage(false, false, "Email is not a Sogyo account");
		} catch (Exception e) {
			registerMessage = new RegisterMessage(false, false, "ID token is invalid");
		}
		return registerMessage;
	}
	
	private GoogleUser getGoogleUserFromToken(IdToken idToken) throws IOException, GeneralSecurityException, 
			EmailNotVerifiedException, InvalidTokenException {
		String idTokenString = idToken.getIdTokenString();
		File file = new File(CLIENT_SECRET_FILE);
		GoogleUser googleUser = new OauthHelper(file).getUserFromToken(idTokenString);
		return googleUser;
	}
}
