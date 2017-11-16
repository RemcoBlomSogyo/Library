package nl.sogyo.library.model.unittest;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.helper.InputValidator;

public class IsbnTest {

	@Test
	public void isbnOfGettingStartedWithPolymerIsCorrect() {
		String isbn = "9781785889370";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertTrue(result);
	}
	
	@Test
	public void isbnOfGettingStartedWithPolymerWithOtherCheckNumberIsIncorrect() {
		String isbn = "9781785889375";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
	
	@Test
	public void isbnOfProRestfulApisIsCorrect() {
		String isbn = "9781484226643";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertTrue(result);
	}
	
	@Test
	public void isbnOfProRestfulApisWithOtherCheckNumberIsIncorrect() {
		String isbn = "9781484226647";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
	
	@Test
	public void emptyStringIsIncorrect() {
		String isbn = "";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
	
	@Test
	public void stringWithTenRandomNumbersAndLettersIsIncorrect() {
		String isbn = "1y4l2q9b8j";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
	
	@Test
	public void stringWithThirteenRandomNumbersAndLettersIsIncorrect() {
		String isbn = "152370usgje";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
	
	@Test
	public void stringWithTenRandomNumbersIsIncorrect() {
		String isbn = "1234567890";
		boolean result = InputValidator.validateIsbn(isbn);
		Assert.assertFalse(result);
	}
}
