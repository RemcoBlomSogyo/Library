package nl.sogyo.library.model.query;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.LibraryHelper;
import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.entity.Book;
import nl.sogyo.library.model.helper.InputValidator;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;

public class QueryHelper extends LibraryHelper {
	
	public QueryHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		super(idToken);
	}
	
	public List<BookPreview> getBooks(String titleInput, String authorInput, String isbnInput) {
		List<Book> books = null;
		System.out.println(databaseHandler);
		System.out.println(googleUser);
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_USER)) {
			if (isbnInput.isEmpty()) {
				if (titleInput.isEmpty()) {
					if (authorInput.isEmpty()) {
						return new ArrayList<BookPreview>();
					} else if (inputIsSingleName(authorInput)) {
						books = databaseHandler.selectBooksByAuthorSingleName(authorInput);
					} else {
						books = databaseHandler.selectBooksByAuthorTotalName(authorInput.substring(0, 
								authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
					}
				} else {
					if (authorInput.isEmpty()) {
						books = databaseHandler.selectBooksByTitle(titleInput);
					} else if (inputIsSingleName(authorInput)) {
						books = databaseHandler.selectBooksByTitleAndAuthorSingleName(titleInput, authorInput);
					} else {
						books = databaseHandler.selectBooksByTitleAndAuthorTotalName(titleInput, authorInput.substring(0, 
								authorInput.indexOf(" ")), authorInput.substring(authorInput.indexOf(" ") + 1));
					}
				}
			} else if (InputValidator.validateIsbn(isbnInput)) {
				books = databaseHandler.selectBooksByIsbn(isbnInput);
			} else {
				return new ArrayList<BookPreview>();
			}
			return parseBooksIntoBookPreviews(books);
		} else {
			return new ArrayList<BookPreview>();
		}
	}
	
	public BookInfo getBookInfo(int id) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_USER)) {
			BookInfo bookInfo = databaseHandler.selectBookById(id);
			System.out.println("copies: " + bookInfo.getCopiesAvailable());
			System.out.println("title: " + bookInfo.getBook().getTitle());
			System.out.println("subtitle: " + bookInfo.getBook().getSubtitle());
			System.out.println("category: " + bookInfo.getBook().getCategory().getName());
			System.out.println("publisher: " + bookInfo.getBook().getPublisher().getName());
			return bookInfo;
		} else {
			return new BookInfo();
		}
	}
	
	public List<Author> getAllAuthors() {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_USER)) {
			List<Author> authors = databaseHandler.selectAllAuthors();
			return authors;
		} else {
			return new ArrayList<Author>();
		}
	}
	
	private boolean inputIsSingleName(String authorInput) {
		return !authorInput.contains(" ");
	}
	
	private List<BookPreview> parseBooksIntoBookPreviews(List<Book> books) {
		List<BookPreview> bookPreviews = new ArrayList<BookPreview>();
		for (Book book : books) {
			bookPreviews.add(new BookPreview(book.getId(), book.getTitle(), book.getAuthors(), 
					book.getCategory().getName(), book.getIsbn()));
		}
		return bookPreviews;
	}
}
