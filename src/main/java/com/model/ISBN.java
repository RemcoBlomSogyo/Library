package com.model;

public class ISBN {
	
	private String ISBN;
	private final int ISBN_LENGTH = 13;
	
	public ISBN(String isbnInput) {
		if (isbnInputIsValid(isbnInput)) {
			ISBN = isbnInput;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public String getISBN() {
		return ISBN;
	}
	
	private boolean isbnInputIsValid(String isbnInput) {
		return stringHasOnlyNumbers(isbnInput) && stringHasThirteenChars(isbnInput);
	}
	
	private boolean stringHasOnlyNumbers(String isbnInput) {
		return isbnInput.matches("[0-9]+");
	}
	
	private boolean stringHasThirteenChars(String isbnInput) {
		return isbnInput.length() == ISBN_LENGTH;
	}
}
