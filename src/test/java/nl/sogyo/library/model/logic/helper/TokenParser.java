package nl.sogyo.library.model.logic.helper;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;

public class TokenParser {
	
	public static final String TEST_ID_TOKEN_1 = "Bearer testIdToken1";
	public static final String TEST_ID_TOKEN_2 = "Bearer testIdToken2";
	
	private TokenParser() {}
	
	public static User getUserFromToken(String idToken) throws InvalidTokenException {
		User user;
		if (idToken == null) {
			throw new InvalidTokenException();
		}
		
		if (isTestWithIdToken1(idToken)) {
			user = new User(6, "111111111111111111111", "Test", "de Test", "tdtest@sogyo.nl", (byte) 1);
		} else if (isTestWithIdToken2(idToken)) {
			user = new User(5, "000000000000000000000", "Test", "van Test", "tvtest@sogyo.nl", (byte) 2);
		} else {
			throw new InvalidTokenException();
		}
		return user;
	}
	
	private static boolean isTestWithIdToken1(String idToken) {
		return idToken.equals(TEST_ID_TOKEN_1);
	}
	
	private static boolean isTestWithIdToken2(String idToken) {
		return idToken.equals(TEST_ID_TOKEN_2);
	}
}
