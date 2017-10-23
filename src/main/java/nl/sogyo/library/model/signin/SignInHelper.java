package nl.sogyo.library.model.signin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.SignInCode;

public class SignInHelper {

//	private DatabaseHandler databaseHandler;
	
	public SignInHelper() {
//		databaseHandler = new DatabaseHandler();
	}
	
	public int signInUser(SignInCode signInCode) {
		User user = getUserFromGoogle();
		
		return 1;
	}
	
	private User getUserFromGoogle(SignInCode signInCode) {
		String code = signInCode.getCode();
		
		String path = new File("").getAbsolutePath();
		System.out.println(path);
		//for(String fileNames : file.list()) System.out.println(fileNames);
		
//		C:\Users\rblom\Documents\apache-tomcat\webapps\SogyoLibrary\src\main\resources
		String CLIENT_SECRET_FILE = "C:/Users/rblom/Documents/apache-tomcat/webapps/SogyoLibrary/src/main/resources/client_secret_1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com.json";

		GoogleIdToken idToken = null;
		// Exchange auth code for access token
		
		try {
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), 
					new FileReader(CLIENT_SECRET_FILE));
			System.out.println("a");
			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
					new NetHttpTransport(),
					JacksonFactory.getDefaultInstance(),
					"https://www.googleapis.com/oauth2/v4/token",
					clientSecrets.getDetails().getClientId(),
					clientSecrets.getDetails().getClientSecret(),
					code,
					"http://localhost:8081")  // Specify the same redirect URI that you use with your web
		                             // app. If you don't have a web version of your app, you can
		                             // specify an empty string.
					.execute();
			System.out.println("b");
//			String accessToken = tokenResponse.getAccessToken();
//
//			// Use access token to call API
//			GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
//			Drive drive =
//			    new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
//			        .setApplicationName("Auth Code Exchange Demo")
//			        .build();
//			File file = drive.files().get("appfolder").execute();

			// Get profile info from ID token
			idToken = tokenResponse.parseIdToken();
			System.out.println("c");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return 0;
		}

		GoogleIdToken.Payload payload = idToken.getPayload();
		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String locale = (String) payload.get("locale");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");
		
		System.out.println(userId);
		System.out.println(email);
		System.out.println(emailVerified);
		System.out.println(name);
		System.out.println(pictureUrl);
		System.out.println(locale);
		System.out.println(familyName);
		System.out.println(givenName);
		
	}
}
