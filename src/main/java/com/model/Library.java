package com.model;

import java.util.List;

import com.persistence.DatabaseHandler;

public class Library {
	
	public static List<Book> getAllBooks() {
		return DatabaseHandler.getAllBooks();
	}
}
