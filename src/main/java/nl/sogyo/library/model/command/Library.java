package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

//import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.EditBookMessage;

public class Library {
	
//	public static AddBookMessage addBook(BookFormInput bookFormInput) {
//		int bookId;
//		try {
//			Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
//					bookFormInput.getSubtitle(), bookFormInput.getAuthorForname(),
//					bookFormInput.getAuthorSurname(), bookFormInput.getCategory(),
//					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
//					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
//			bookId = DatabaseHandler.insertBook(book);
//		} catch (IllegalArgumentException e) {
//			bookId = -2;
//		}
//		return new AddBookMessage(bookId);
//	}
	
	public static AddBookMessage addBook(BookFormInput bookFormInput) {
		System.out.println("libary....");
		System.out.println("bookform " + bookFormInput.getTitle() );
		int bookId;
		List<Author> authors = getAuthorsFromBookFormInput(bookFormInput);
		
		System.out.println("test libary: " + bookFormInput.getTitle());
		try {
			Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
					bookFormInput.getSubtitle(), authors, bookFormInput.getCategory(),
					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
//			bookId = DatabaseHandler.insertBook(book);
			System.out.println("in try before insert");
			bookId = DatabaseHandler.insertBook(book);
			System.out.println("bookId in try after insert: " + bookId);
		} catch (IllegalArgumentException e) {
			bookId = -2;
		}
		System.out.println("bookId out of try: " + bookId);
		return new AddBookMessage(bookId);
	}
	
	public static AddCopyMessage addCopy(int bookId) {
//		AddCopyMessage addCopyMessage = DatabaseHandler.insertCopy(bookId);
		AddCopyMessage addCopyMessage = DatabaseHandler.insertCopy(bookId);
		return addCopyMessage;
	}
	
//	public static EditBookMessage editBook(BookFormInput bookFormInput) {
//		boolean commandSucceeded;
//		try {
//			Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
//					bookFormInput.getSubtitle(), bookFormInput.getAuthorForname(),
//					bookFormInput.getAuthorSurname(), bookFormInput.getCategory(),
//					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
//					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
//			commandSucceeded = DatabaseHandler.updateBook(book);
//		} catch (IllegalArgumentException e) {
//			commandSucceeded = false;
//		}
//		return new EditBookMessage(commandSucceeded);
//	}
	
	public static EditBookMessage editBook(int id, BookFormInput bookFormInput) {
		boolean commandSucceeded;
		List<Author> authors = getAuthorsFromBookFormInput(bookFormInput);
		try {
			Book book = new Book(id, bookFormInput.getTitle(),
					bookFormInput.getSubtitle(), authors, bookFormInput.getCategory(),
					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
			commandSucceeded = DatabaseHandler.updateBook(book);
		} catch (IllegalArgumentException e) {
			commandSucceeded = false;
		}
		System.out.println("commandsucceeded: " + commandSucceeded);
		return new EditBookMessage(commandSucceeded);
	}
	
	public static DeleteCopyMessage deleteCopy(int bookId) {
//		DeleteCopyMessage deleteCopyMessage = DatabaseHandler.deleteCopy(bookId);
		DeleteCopyMessage deleteCopyMessage = DatabaseHandler.deleteCopy(bookId);
		return deleteCopyMessage;
	}
	
	public static DeleteBookMessage deleteBook(int id) {
//		boolean commandSucceeded = DatabaseHandler.deleteBookAndCopies(id);
		boolean commandSucceeded = DatabaseHandler.deleteBookAndCopies(id);
		DeleteBookMessage deleteBookMessage = new DeleteBookMessage(commandSucceeded);
		return deleteBookMessage;
	}
	
	private static List<Author> getAuthorsFromBookFormInput(BookFormInput bookFormInput) {
		List<Author> authors = new ArrayList<Author>();
		
		if (!bookFormInput.getAuthorForename1().isEmpty() && !bookFormInput.getAuthorSurname1().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename1(), bookFormInput.getAuthorSurname1()));
			System.out.println("auteur test");
		}
		System.out.println("auteur test3");
		if (!bookFormInput.getAuthorForename2().isEmpty() && !bookFormInput.getAuthorSurname2().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename2(), bookFormInput.getAuthorSurname2()));
		}
		System.out.println("auteur test4");
		if (!bookFormInput.getAuthorForename3().isEmpty() && !bookFormInput.getAuthorSurname3().isEmpty()) {
			authors.add(new Author(bookFormInput.getAuthorForename3(), bookFormInput.getAuthorSurname3()));
		}
		System.out.println("auteur test2");
		return authors;
	}
}
