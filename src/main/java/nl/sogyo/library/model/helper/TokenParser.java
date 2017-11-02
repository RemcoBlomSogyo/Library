package nl.sogyo.library.model.helper;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;
import nl.sogyo.oauth.javagooglesignin.OauthHelper;

public final class TokenParser {

	private static final String CLIENT_SECRET_FILE = "C:/Users/rblom/Documents/apache-tomcat/webapps/SogyoLibrary/src/main/resources/google-api/"
			+ "client_secret_1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com.json";
	private static final String TEST_ID_TOKEN = "Bearer testIdToken";
	
	private TokenParser() {}
	
	public static GoogleUser getGoogleUserFromToken(String idToken) throws IOException, GeneralSecurityException, 
			EmailNotVerifiedException, InvalidTokenException {
		File file = new File(CLIENT_SECRET_FILE);
		System.out.println(idToken);
		GoogleUser googleUser;
		if (isTest(idToken)) {
			googleUser = new GoogleUser("000000000000000000000", "Test", "van Test", "tvtest@sogyo.nl");
		} else {
			String idTokenWithoutBearer = removeBearerFromToken(idToken);
			googleUser = new OauthHelper(file).getUserFromToken(idTokenWithoutBearer);
			System.out.println("tokenparser geen exception");
		}
		return googleUser;
	}
	
	private static String removeBearerFromToken(String idToken) {
		return idToken.substring(idToken.indexOf(" ") + 1);
	}
	
	private static boolean isTest(String idToken) {
		return idToken.equals(TEST_ID_TOKEN);
	}
}
