package com.model;

import java.util.ArrayList;
import java.util.List;

import com.persistence.DatabaseHandler;

public class Library {
	
	public static List<Book> getAllBooks() {
		return DatabaseHandler.getAllBooks();
	}
	
	public static boolean addBook(Book book) {
		return DatabaseHandler.addBook(book);
	}
	
//	public static List<Book> getBooks(String titleInput, 
//			String authorInput, String isbnInput) {
//		ISBN isbn;
//		try {
//			isbn = new ISBN(isbnInput);
//		} catch (IllegalArgumentException e) {
//			return new ArrayList<Book>();
//		}
//		return DatabaseHandler.getBooks(titleInput, authorInput, isbn);
//	}
//	
//	private boolean isbnIsValid(String isbnInput) {
//		return stringHasOnlyNumbers(isbnInput) && stringHasThirteenChars(isbnInput);
//	}
//	
//	private boolean stringHasOnlyNumbers(String isbnInput) {
//		return
//	}
}
