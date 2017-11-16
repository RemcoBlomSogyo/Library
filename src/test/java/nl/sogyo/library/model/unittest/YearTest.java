package nl.sogyo.library.model.unittest;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.helper.InputValidator;

public class YearTest {
	
	@Test
	public void year1999IsValidYear() {
		String year = "1999";
		boolean result = InputValidator.validateYear(year);
		Assert.assertTrue(result);
	}
	
	@Test
	public void year3261IsNotValidYear() {
		String year = "3261";
		boolean result = InputValidator.validateYear(year);
		Assert.assertFalse(result);
	}
	
	@Test
	public void yearWithLettersAndNumbersIsNotValidYear() {
		String year = "u5l1j8";
		boolean result = InputValidator.validateYear(year);
		Assert.assertFalse(result);
	}
}
