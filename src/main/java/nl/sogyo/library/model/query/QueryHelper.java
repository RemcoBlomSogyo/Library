package nl.sogyo.library.model.query;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.helper.InputValidator;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.persistence.HibernateDatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;

public class QueryHelper {
	
	public static List<BookPreview> getBooks(String titleInput, 
			String authorInput, String isbnInput) {
		List<Book> books = null;
		if (isbnInput.isEmpty()) {
			if (titleInput.isEmpty()) {
				if (authorInput.isEmpty()) {
					return new ArrayList<BookPreview>();
				} else if (inputIsSingleName(authorInput)) {
//					return DatabaseHandler.selectBooksOnAuthorSingleName(authorInput);
					books = HibernateDatabaseHandler.selectBooksByAuthorSingleName(authorInput);
				} else {
//					return DatabaseHandler.selectBooksOnAuthorTotalName(authorInput.substring(0, authorInput.indexOf(" ")), 
//							authorInput.substring(authorInput.indexOf(" ") + 1));
					books = HibernateDatabaseHandler.selectBooksByAuthorTotalName(authorInput.substring(0, 
							authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			} else {
				if (authorInput.isEmpty()) {
					System.out.println("TESTTEST");
//					return DatabaseHandler.selectBooksOnTitle(titleInput);
					books = HibernateDatabaseHandler.selectBooksByTitle(titleInput);
				} else if (inputIsSingleName(authorInput)) {
//					return DatabaseHandler.selectBooksOnTitleAndAuthorSingleName(titleInput, authorInput);
					books = HibernateDatabaseHandler.selectBooksByTitleAndAuthorSingleName(titleInput, authorInput);
				} else {
//					return DatabaseHandler.selectBooksOnTitleAndAuthorTotalName(titleInput, authorInput.substring(0, 
//							authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
					books = HibernateDatabaseHandler.selectBooksByTitleAndAuthorTotalName(titleInput, authorInput.substring(0, 
							authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
				}
			}
		} else if (InputValidator.validateIsbn(isbnInput)) {
//			return DatabaseHandler.selectBooksOnISBN(isbnInput);
			books = HibernateDatabaseHandler.selectBooksByIsbn(isbnInput);
		} else {
			return new ArrayList<BookPreview>();
		}
		return parseBooksIntoBookPreviews(books);
	}
	
	public static BookInfo getBookInfo(int id) {
//		BookInfo bookInfo = DatabaseHandler.selectBookOnId(id);
		BookInfo bookInfo = HibernateDatabaseHandler.selectBookById(id);
		System.out.println("copies: " + bookInfo.getCopiesAvailable());
		System.out.println("title: " + bookInfo.getBook().getTitle());
		System.out.println("subtitle: " + bookInfo.getBook().getSubtitle());
		System.out.println("category: " + bookInfo.getBook().getCategory().getName());
		System.out.println("publisher: " + bookInfo.getBook().getPublisher().getName());
		return bookInfo;
	}
	
	private static boolean inputIsSingleName(String authorInput) {
		return !authorInput.contains(" ");
	}
	
	private static List<BookPreview> parseBooksIntoBookPreviews(List<Book> books) {
		List<BookPreview> bookPreviews = new ArrayList<BookPreview>();
		for (Book book : books) {
			bookPreviews.add(new BookPreview(book.getId(), book.getTitle(), book.getAuthors(), 
					book.getCategory().getName(), book.getIsbn()));
		}
		return bookPreviews;
	}
}
