package com.model;

import java.util.ArrayList;
import java.util.List;

import com.rest.json.AddBookInput;
import com.persistence.DatabaseHandler;

public class Library {
	
	public static List<Book> getAllBooks() {
		return DatabaseHandler.getAllBooksTest();
	}
	
//	public static boolean addBook(AddBookInput addBookInput) {
//		Book book;
//		try {
//			book = new Book(/* arguments */);
//		} catch (IllegalArgumentException e) {
//			return false;
//		}
//		return DatabaseHandler.addBook(book);
//	}
	
//	public static List<Book> getBooks(String titleInput, 
//			String authorInput, String isbnInput) {
//		ISBN isbn;
//		try {
//			isbn = new ISBN(isbnInput);
//		} catch (IllegalArgumentException e) {
//			return new ArrayList<Book>();
//		}
//		
//		if 
//		return DatabaseHandler.getBooks(titleInput, authorInput, isbn);
//	}
	
}
