package nl.sogyo.library.model.helper;

import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.oauth.javagooglesignin.exception.EmailNotVerifiedException;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;
import nl.sogyo.oauth.javagooglesignin.OauthHelper;

public final class TokenParser {

	private static final String GOOGLE_CLIENT_ID = "1000284014442-khpk4ottrb9c0njde9ho3nbqrkdo7p99.apps.googleusercontent.com";
	
	private TokenParser() {}
	
	public static User getUserFromToken(String idToken) throws IOException, GeneralSecurityException, 
			EmailNotVerifiedException, InvalidTokenException {
		GoogleUser googleUser;
		if (idToken == null) {
			throw new InvalidTokenException();
		} else {
			String idTokenWithoutBearer = removeBearerFromToken(idToken);
			System.out.println(idTokenWithoutBearer);
			googleUser = new OauthHelper(GOOGLE_CLIENT_ID).getUserFromToken(idTokenWithoutBearer);
		}
		User user = new User(googleUser);
		return user;
	}
	
	private static String removeBearerFromToken(String idToken) {
		return idToken.substring(idToken.indexOf(" ") + 1);
	}
}
