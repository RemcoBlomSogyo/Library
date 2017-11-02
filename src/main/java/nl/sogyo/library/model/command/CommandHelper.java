package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.LibraryHelper;
import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.entity.Book;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

public class CommandHelper extends LibraryHelper {
	
	public CommandHelper(String idToken) {
		super(idToken);
	}
	
	public AddBookMessage addBook(BookFormInput bookFormInput) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_ADMIN)) {
			int bookId;
			List<Author> authors = getAuthorsFromBookFormInput(bookFormInput);
			
			System.out.println("test libary: " + bookFormInput.getTitle());
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
			System.out.println("bookId out of try: " + bookId);
			return new AddBookMessage(bookId);
		} else {
			return new AddBookMessage(-3);
		}
	}
	
	public AddCopyMessage addCopy(int bookId) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_ADMIN)) {
			AddCopyMessage addCopyMessage = databaseHandler.insertCopy(bookId);
			System.out.println("Copymessage: " + addCopyMessage.getCommandSucceeded());
			System.out.println("Copymessage: " + addCopyMessage.getCopiesOfBook());
			return addCopyMessage;
		} else {
			return new AddCopyMessage(false, 0);
		}
	}
	
	public EditBookMessage editBook(int id, BookFormInput bookFormInput) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_ADMIN)) {
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
			System.out.println("commandsucceeded: " + commandSucceeded);
			return new EditBookMessage(commandSucceeded);
		} else {
			return new EditBookMessage(false);
		}
	}
	
	public DeleteCopyMessage deleteCopy(int bookId) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_ADMIN)) {
			DeleteCopyMessage deleteCopyMessage = databaseHandler.deleteCopy(bookId);
			return deleteCopyMessage;
		} else {
			return new DeleteCopyMessage(false, 0);
		}
	}
	
	public DeleteBookMessage deleteBook(int id) {
		if (databaseHandler.isUserAuthorized(googleUser.getUserId(), USERTYPE_ADMIN)) {
			boolean commandSucceeded = databaseHandler.deleteBookAndCopies(id);
			DeleteBookMessage deleteBookMessage = new DeleteBookMessage(commandSucceeded);
			return deleteBookMessage;
		} else {
			return new DeleteBookMessage(false);
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
