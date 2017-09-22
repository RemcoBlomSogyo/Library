package nl.sogyo.library.model.query;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.helper.InputValidator;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
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
			//throw new Exception("Invalid isbn");
			return new ArrayList<BookPreview>();
		}
	}
	
	public static BookInfo getBookInfo(int id) {
//		Book book = DatabaseHandler.selectBookOnId(id);
//		return new BookInfo(book.getTitle(), book.getSubtitle(), book.getAuthorForename(), book.getAuthorSurname(),
//				book.getCategory(), book.getPublisher(), book.getYearFirstPublication(), book.getISBN(),
//				book.getPages(), book.getLanguage());
		System.out.println("Queryhelper.getbookinfo");
		BookInfo bookInfo = DatabaseHandler.selectBookOnId(id);
		return bookInfo;
	}
	
	private static boolean inputIsSingleName(String authorInput) {
		return !authorInput.contains(" ");
	}
}
