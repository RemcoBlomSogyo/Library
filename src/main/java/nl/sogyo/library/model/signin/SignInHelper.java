package nl.sogyo.library.model.signin;

import java.io.File;
import java.io.IOException;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.SignInCode;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.OauthHelper;

public class SignInHelper {

	private DatabaseHandler databaseHandler;
	private static final String CLIENT_SECRET_FILE = "C:/Users/rblom/Documents/apache-tomcat/webapps/SogyoLibrary/src/main/resources/"
			+ "client_secret_1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com.json";
	private static final String REDIRECT_URI = "http://localhost:8081";
	
	public SignInHelper() {
		databaseHandler = new DatabaseHandler();
	}
	
	public int signInUser(SignInCode signInCode) {
		GoogleUser googleUser = null;
		String authorizationCode = signInCode.getCode();
		try {
			File file = new File(CLIENT_SECRET_FILE);
			OauthHelper oauthHelper = new OauthHelper(file, REDIRECT_URI);
			googleUser = oauthHelper.getUserFromGoogle(authorizationCode);
		} catch (IOException e) {
			// google has not sent a token
			return 1;
		} catch (EmailNotVerifiedException e) {
			return 2;
		}
		
		User sogyoUser = null;
		String userName = googleUser.getGivenName() + " " + googleUser.getFamilyName();
		try {
			sogyoUser = new User(googleUser.getUserId(), userName, googleUser.getEmail());
		} catch (IllegalArgumentException e) {
			return 3;
		}
		
		User libraryUser = databaseHandler.getUserWithId(sogyoUser);
		if (libraryUser == null) {
			libraryUser = databaseHandler.createProfile(sogyoUser);
		}

		return 0;
	}
}
