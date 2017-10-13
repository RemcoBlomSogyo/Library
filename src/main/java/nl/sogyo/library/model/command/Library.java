package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

public class Library {
	
	private DatabaseHandler databaseHandler;
	
	public Library() {
		databaseHandler = new DatabaseHandler();
	}
	
	public AddBookMessage addBook(BookFormInput bookFormInput) {
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
			bookId = -2;
		}
		System.out.println("bookId out of try: " + bookId);
		return new AddBookMessage(bookId);
	}
	
	public AddCopyMessage addCopy(int bookId) {
		AddCopyMessage addCopyMessage = databaseHandler.insertCopy(bookId);
		System.out.println("Copymessage: " + addCopyMessage.getCommandSucceeded());
		System.out.println("Copymessage: " + addCopyMessage.getCopiesOfBook());
		return addCopyMessage;
	}
	
	public EditBookMessage editBook(int id, BookFormInput bookFormInput) {
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
	}
	
	public DeleteCopyMessage deleteCopy(int bookId) {
		DeleteCopyMessage deleteCopyMessage = databaseHandler.deleteCopy(bookId);
		return deleteCopyMessage;
	}
	
	public DeleteBookMessage deleteBook(int id) {
		boolean commandSucceeded = databaseHandler.deleteBookAndCopies(id);
		DeleteBookMessage deleteBookMessage = new DeleteBookMessage(commandSucceeded);
		return deleteBookMessage;
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
