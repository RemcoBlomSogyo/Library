package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.SuccessMessage;

public class Library {
	
	public static SuccessMessage addBook(BookFormInput bookFormInput) {
		Book book;
		boolean commandSucceeded;
		try {
			book = new Book(bookFormInput.getTitle(), bookFormInput.getSubtitle(),
					bookFormInput.getAuthorForname(), bookFormInput.getAuthorSurname(),
					bookFormInput.getCategory(), bookFormInput.getPublisher(),
					Short.parseShort(bookFormInput.getYearFirstPublication()), bookFormInput.getIsbn(),
					Short.parseShort(bookFormInput.getPages()), bookFormInput.getLanguage());
			commandSucceeded = DatabaseHandler.addBook(book);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.out.println("library illargexcp");
			commandSucceeded = false;
		}
		return new SuccessMessage(commandSucceeded);
	}
	
	public static List<BookPreview> getBooks(String titleInput, 
			String authorInput, String isbnInput) {

		
		if 
		return DatabaseHandler.getBooks(titleInput, authorInput, isbn);
	}
	
}
