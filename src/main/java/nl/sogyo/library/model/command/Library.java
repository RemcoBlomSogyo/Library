package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;

public class Library {
	
	public static boolean addBook(BookFormInput bookFormInput) {
		Book book;
		try {
			book = new Book(bookFormInput.getTitle(), bookFormInput.getSubtitle(),
					bookFormInput.getAuthorForname(), bookFormInput.getAuthorSurname(),
					bookFormInput.getCategory(), bookFormInput.getPublisher(),
					Short.parseShort(bookFormInput.getYearFirstPublication()), bookFormInput.getIsbn(),
					Short.parseShort(bookFormInput.getPages()), bookFormInput.getLanguage());
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.out.println("library illargexcp");
			return false;
		}
		System.out.println("go to databasehandler");
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
//		
//		if 
//		return DatabaseHandler.getBooks(titleInput, authorInput, isbn);
//	}
	
}
