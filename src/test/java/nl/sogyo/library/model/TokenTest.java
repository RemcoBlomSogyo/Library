package nl.sogyo.library.model;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.helper.TokenParser;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

public class TokenTest {

	@Test
	public void tokenIsNullGivesInvalidTokenException() {
		try {
			TokenParser.getGoogleUserFromToken(null);
		} catch (InvalidTokenException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void tokenIsFakeTokenGivesInvalidTokenException() {
		try {
			TokenParser.getGoogleUserFromToken("abcdefghijklm0123456789");
		} catch (InvalidTokenException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		} 
	}
	
	@Test
	public void testToken1ReturnsTestDeTestGoogleUser() {
		try {
			GoogleUser googleUser = TokenParser.getGoogleUserFromToken(TEST_ID_TOKEN_1);
			Assert.assertEquals("tdtest@sogyo.nl", googleUser.getEmail());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testToken2ReturnsTestVanTestGoogleUser() {
		try {
			GoogleUser googleUser = TokenParser.getGoogleUserFromToken(TEST_ID_TOKEN_2);
			Assert.assertEquals("tvtest@sogyo.nl", googleUser.getEmail());
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
