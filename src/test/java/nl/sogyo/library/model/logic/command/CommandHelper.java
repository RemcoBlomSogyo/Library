package nl.sogyo.library.model.logic.command;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.entity.Book;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.logic.LibraryHelper;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.BorrowCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditUsersMessage;
import nl.sogyo.oauth.javagooglesignin.exception.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;

public class CommandHelper extends LibraryHelper {
	
	public CommandHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		super(idToken);
	}
	
	public AddBookMessage addBook(BookFormInput bookFormInput) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			int bookId;
			List<Author> authors = getAuthorsFromBookFormInput(bookFormInput);

			try {
				Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
						bookFormInput.getSubtitle(), authors, bookFormInput.getCategory(),
						bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
						bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
				bookId = databaseHandler.insertBook(book);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				bookId = -2;
			}
			return new AddBookMessage(bookId);
		} else {
			return new AddBookMessage(-3);
		}
	}
	
	public AddCopyMessage addCopy(int bookId) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			AddCopyMessage addCopyMessage = databaseHandler.insertCopy(bookId);
			return addCopyMessage;
		} else {
			return new AddCopyMessage(false, 0);
		}
	}
	
	public EditBookMessage editBook(int id, BookFormInput bookFormInput) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			boolean commandSucceeded;
			List<Author> authors = getAuthorsFromBookFormInput(bookFormInput);
			try {
				Book book = new Book(id, bookFormInput.getTitle(),
						bookFormInput.getSubtitle(), authors, bookFormInput.getCategory(),
						bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
						bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
				commandSucceeded = databaseHandler.updateBook(book);
			} catch (IllegalArgumentException e) {
				commandSucceeded = false;
			}
			return new EditBookMessage(commandSucceeded);
		} else {
			return new EditBookMessage(false);
		}
	}
	
	public DeleteCopyMessage deleteCopy(int bookId) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			DeleteCopyMessage deleteCopyMessage = databaseHandler.deleteCopy(bookId);
			return deleteCopyMessage;
		} else {
			return new DeleteCopyMessage(false, 0);
		}
	}
	
	public DeleteBookMessage deleteBook(int id) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			boolean commandSucceeded = databaseHandler.deleteBookAndCopies(id);
			DeleteBookMessage deleteBookMessage = new DeleteBookMessage(commandSucceeded);
			return deleteBookMessage;
		} else {
			return new DeleteBookMessage(false);
		}
	}
	
	public EditUsersMessage editUsers(List<User> users) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), USERTYPE_ADMIN)) {
			boolean commandSucceeded = databaseHandler.updateUsers(users);
			EditUsersMessage editUsersMessage = new EditUsersMessage(commandSucceeded);
			return editUsersMessage;
		} else {
			return new EditUsersMessage(false);
		}
	}
	
	public BorrowCopyMessage borrowCopy(int userId, int bookId) {
		if (databaseHandler.isUserAuthorized(user.getGoogleUserId(), userId)) {
			System.out.println("borrow commandhelper if1");
			BorrowCopyMessage borrowCopyMessage = databaseHandler.borrowCopy(userId, bookId);
			System.out.println("borrow commandhelper if2");
//			BorrowCopyMessage borrowCopyMessage = new BorrowCopyMessage(commandSucceeded);
			System.out.println("borrow commandhelper if3");
			return borrowCopyMessage;
		} else {
			System.out.println("borrow commandhelper else");
			return new BorrowCopyMessage(false, 0, 0);
		}
	}
	
	private static List<Author> getAuthorsFromBookFormInput(BookFormInput bookFormInput) {
		List<Author> authors = new ArrayList<Author>();
		
		if (!bookFormInput.getAuthorForename1().isEmpty() && !bookFormInput.getAuthorSurname1().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename1(), bookFormInput.getAuthorSurname1()));
		}

		if (!bookFormInput.getAuthorForename2().isEmpty() && !bookFormInput.getAuthorSurname2().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename2(), bookFormInput.getAuthorSurname2()));
		}

		if (!bookFormInput.getAuthorForename3().isEmpty() && !bookFormInput.getAuthorSurname3().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename3(), bookFormInput.getAuthorSurname3()));
		}
		
		return authors;
	}
}
