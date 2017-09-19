package nl.sogyo.library.model.query;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.helper.InputValidator;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;

public class QueryHelper {
	
	public static List<TestBook> getAllBooks() {
		return DatabaseHandler.getAllBooksTest();
	}
	
	public static List<BookPreview> getBooks(String titleInput, 
			String authorInput, String isbnInput) {
		
		if (isbnInput.isEmpty()) {
			if (titleInput.isEmpty()) {
				if (authorInput.isEmpty()) {
					//throw new Exception("No input");
					return new ArrayList<BookPreview>();
				} else if (inputIsSingleName(authorInput)) {
					return DatabaseHandler.getBooksOnAuthorSingleName(authorInput);
				} else {
					return DatabaseHandler.getBooksOnAuthorTotalName(authorInput.substring(0, authorInput.indexOf(" ")), 
							authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			} else {
				if (authorInput.isEmpty()) {
					return DatabaseHandler.getBooksOnTitle(titleInput);
				} else if (inputIsSingleName(authorInput)) {
					return DatabaseHandler.getBooksOnTitleAndAuthorSingleName(titleInput, authorInput);
				} else {
					return DatabaseHandler.getBooksOnTitleAndAuthorTotalName(titleInput, authorInput.substring(0, 
							authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			}
		} else if (InputValidator.validateIsbn(isbnInput)) {
			return DatabaseHandler.getBooksOnISBN(isbnInput);
		} else {
			//throw new Exception("Invalid isbn");
			return new ArrayList<BookPreview>();
		}
	}
	
	private static boolean inputIsSingleName(String authorInput) {
		return !authorInput.contains(" ");
	}
}
