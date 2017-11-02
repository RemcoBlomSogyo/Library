package nl.sogyo.library.model;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.helper.TokenParser;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;

import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.helper.TokenParser.TEST_ID_TOKEN_2;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenTest {

	@Test
	public void tokenIsNullGivesInvalidTokenException() {
		boolean trySucceeded = false;
		try {
			TokenParser.getGoogleUserFromToken(null);
			trySucceeded = true;
		} catch (InvalidTokenException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
		if (trySucceeded) {
			Assert.fail();
		}
	}
	
	@Test
	public void fakeTokenGivesInvalidTokenException() {
		boolean trySucceeded = false;
		try {
			TokenParser.getGoogleUserFromToken("abcdefghijklm0123456789");
			trySucceeded = true;
		} catch (InvalidTokenException e) {
			System.out.println("InvalidTokenException");
			Assert.assertTrue(true);
		} catch (IOException e) {
			System.out.println("IOException");
			Assert.fail();
		} catch (GeneralSecurityException e) {
			System.out.println("GeneralSecurityException");
			Assert.fail();
		} catch (EmailNotVerifiedException e) {
			System.out.println("EmailNotVerifiedException");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
			Assert.fail();
		}
		if (trySucceeded) {
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
