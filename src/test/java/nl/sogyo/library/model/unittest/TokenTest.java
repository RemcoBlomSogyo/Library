package nl.sogyo.library.model.unittest;

import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_1;
import static nl.sogyo.library.model.logic.helper.TokenParser.TEST_ID_TOKEN_2;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.helper.TokenParser;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;

public class TokenTest {

	@Test
	public void tokenIsNullGivesInvalidTokenException() {
		boolean trySucceeded = false;
		try {
			TokenParser.getUserFromToken(null);
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
			TokenParser.getUserFromToken("abcdefghijklm0123456789");
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
	public void testToken1ReturnsTestDeTestGoogleUser() {
		try {
			User user = nl.sogyo.library.model.logic.helper.TokenParser.getUserFromToken(TEST_ID_TOKEN_1);
			Assert.assertEquals("tdtest@sogyo.nl", user.getEmail());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testToken2ReturnsTestVanTestGoogleUser() {
		try {
			User user = nl.sogyo.library.model.logic.helper.TokenParser.getUserFromToken(TEST_ID_TOKEN_2);
			Assert.assertEquals("tvtest@sogyo.nl", user.getEmail());
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
