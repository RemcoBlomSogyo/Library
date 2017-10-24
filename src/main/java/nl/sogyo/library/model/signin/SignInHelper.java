package nl.sogyo.library.model.signin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.SignInCode;

public class SignInHelper {

	private DatabaseHandler databaseHandler;
	private static final String CLIENT_SECRET_FILE = "C:/Users/rblom/Documents/apache-tomcat/webapps/SogyoLibrary/src/main/resources/"
			+ "client_secret_1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com.json";
	private static final String TOKEN_SERVER_ENCODED_URL = "https://www.googleapis.com/oauth2/v4/token";
	private static final String REDIRECT_URI = "http://localhost:8081";
	
	public SignInHelper() {
		databaseHandler = new DatabaseHandler();
	}
	
	public int signInUser(SignInCode signInCode) {
		User googleUser = null;
		try {
			googleUser = getUserFromGoogle(signInCode);
		} catch (FileNotFoundException e) {
			// client secret file has not been found
			return 1;
		} catch (IOException e) {
			// google has not sent a token
			return 2;
		} catch (IllegalArgumentException e) {
			// google user is not sogyo account
			return 3;
		} catch (RuntimeException e) {
			// user is not verified by google
			return 4;
		}
		
		User libraryUser = databaseHandler.getUserWithId(googleUser);
		if (libraryUser == null) {
			libraryUser = databaseHandler.createProfile(googleUser);
		}

		
		
		return 0;
	}
	
	private User getUserFromGoogle(SignInCode signInCode) throws IOException {
		String code = signInCode.getCode();
	
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), 
				new FileReader(CLIENT_SECRET_FILE));
		GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
				new NetHttpTransport(),
				JacksonFactory.getDefaultInstance(),
				TOKEN_SERVER_ENCODED_URL,
				clientSecrets.getDetails().getClientId(),
				clientSecrets.getDetails().getClientSecret(),
				code,
				REDIRECT_URI)  
				.execute();
		GoogleIdToken idToken = tokenResponse.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();
		
		if (isEmailVerified(payload)) {
			String googleUserId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");
			User user = new User(googleUserId, name, email);
			return user;
		} else {
			throw new RuntimeException("Email is not verified by Google");
		}
	}
	
	private boolean isEmailVerified(GoogleIdToken.Payload payload) {
		return Boolean.valueOf(payload.getEmailVerified());
	}
}
