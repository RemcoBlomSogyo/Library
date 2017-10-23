package nl.sogyo.library.model;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.signin.SignInHelper;
import nl.sogyo.library.services.rest.libraryapi.json.SignInCode;

public class SignInTest {

	@Test
	public void isbnOfGettingStartedWithPolymerIsCorrect() {
		SignInCode signInCode = new SignInCode("eofij2389");
		SignInHelper helper = new SignInHelper();
		Assert.assertEquals(1, helper.signInUser(signInCode));
	}
}
