package nl.sogyo.library.model.helper;

import java.time.Year;

public class InputValidator {
	
	private static final int ISBN_LENGTH = 13;
	
	public static boolean validateIsbn(String isbnInput) {
		if (stringHasOnlyNumbers(isbnInput) && stringHasThirteenChars(isbnInput)) {
			return checkDigitIsCorrect(isbnInput);
		} else {
			return false;
		}
	}
	
	public static boolean validateYear(String yearInput) {
		return yearInput.isEmpty() || (stringHasOnlyNumbers(yearInput) && numberIsNotNegative(yearInput) && yearIsNotInFuture(yearInput));
	}
	
	public static boolean validatePages(String pagesInput) {
		return pagesInput.isEmpty() || (stringHasOnlyNumbers(pagesInput) && numberIsNotNegative(pagesInput));
	}
	
	private static boolean stringHasOnlyNumbers(String isbnInput) {
		return isbnInput.matches("[0-9]+");
	}
	
	private static boolean stringHasThirteenChars(String isbnInput) {
		return isbnInput.length() == ISBN_LENGTH;
	}
	
	private static boolean checkDigitIsCorrect(String isbnInput) {
		int calculatedCheckDigit = Math.abs(((Character.getNumericValue(isbnInput.charAt(1)) 
				+ Character.getNumericValue(isbnInput.charAt(3))
				+ Character.getNumericValue(isbnInput.charAt(5))
				+ Character.getNumericValue(isbnInput.charAt(7))
				+ Character.getNumericValue(isbnInput.charAt(9))
				+ Character.getNumericValue(isbnInput.charAt(11))) * 3
				+ Character.getNumericValue(isbnInput.charAt(0))
				+ Character.getNumericValue(isbnInput.charAt(2))
				+ Character.getNumericValue(isbnInput.charAt(4))
				+ Character.getNumericValue(isbnInput.charAt(6))
				+ Character.getNumericValue(isbnInput.charAt(8))
				+ Character.getNumericValue(isbnInput.charAt(10)))
				% 10 - 10);
		
		if (calculatedCheckDigit == 10) {
			calculatedCheckDigit = 0;
		}
		
		int checkDigitInString = Character.getNumericValue(isbnInput.charAt(12));
		return calculatedCheckDigit == checkDigitInString;
	}
	
	private static boolean numberIsNotNegative(String numberInput) {
		return Integer.parseInt(numberInput) >= 0;
	}
	
	private static boolean yearIsNotInFuture(String yearInput) {
		return Integer.parseInt(yearInput) <= Year.now().getValue();
	}
}
