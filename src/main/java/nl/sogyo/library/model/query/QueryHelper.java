package nl.sogyo.library.model.query;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.helper.InputValidator;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;

public class QueryHelper {
	
	public static List<BookPreview> getBooks(String titleInput, 
			String authorInput, String isbnInput) {
		
		if (isbnInput.isEmpty()) {
			if (titleInput.isEmpty()) {
				if (authorInput.isEmpty()) {
					return new ArrayList<BookPreview>();
				} else if (inputIsSingleName(authorInput)) {
					return DatabaseHandler.selectBooksOnAuthorSingleName(authorInput);
				} else {
					return DatabaseHandler.selectBooksOnAuthorTotalName(authorInput.substring(0, authorInput.indexOf(" ")), 
							authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			} else {
				if (authorInput.isEmpty()) {
					return DatabaseHandler.selectBooksOnTitle(titleInput);
				} else if (inputIsSingleName(authorInput)) {
					return DatabaseHandler.selectBooksOnTitleAndAuthorSingleName(titleInput, authorInput);
				} else {
					return DatabaseHandler.selectBooksOnTitleAndAuthorTotalName(titleInput, authorInput.substring(0, 
							authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			}
		} else if (InputValidator.validateIsbn(isbnInput)) {
			return DatabaseHandler.selectBooksOnISBN(isbnInput);
		} else {
			return new ArrayList<BookPreview>();
		}
	}
	
	public static BookInfo getBookInfo(int id) {
		BookInfo bookInfo = DatabaseHandler.selectBookOnId(id);
		return bookInfo;
	}
	
	private static boolean inputIsSingleName(String authorInput) {
		return !authorInput.contains(" ");
	}
}
