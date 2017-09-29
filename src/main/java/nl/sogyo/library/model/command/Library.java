package nl.sogyo.library.model.command;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookId;
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
		int bookId;
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
		
		try {
			Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
					bookFormInput.getSubtitle(), authors, bookFormInput.getCategory(),
					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
			bookId = DatabaseHandler.insertBook(book);
		} catch (IllegalArgumentException e) {
			bookId = -2;
		}
		return new AddBookMessage(bookId);
	}
	
	public static AddCopyMessage addCopy(BookId addCopyCommand) {
		AddCopyMessage addCopyMessage = DatabaseHandler.insertCopy(addCopyCommand.getBookId());
		return addCopyMessage;
	}
	
	public static EditBookMessage editBook(BookFormInput bookFormInput) {
		boolean commandSucceeded;
		try {
			Book book = new Book(bookFormInput.getId(), bookFormInput.getTitle(),
					bookFormInput.getSubtitle(), bookFormInput.getAuthorForname(),
					bookFormInput.getAuthorSurname(), bookFormInput.getCategory(),
					bookFormInput.getPublisher(), bookFormInput.getYearFirstPublication(),
					bookFormInput.getIsbn(), bookFormInput.getPages(), bookFormInput.getLanguage());
			commandSucceeded = DatabaseHandler.updateBook(book);
		} catch (IllegalArgumentException e) {
			commandSucceeded = false;
		}
		return new EditBookMessage(commandSucceeded);
	}
	
	public static DeleteCopyMessage deleteCopy(BookId deleteCopyCommand) {
		DeleteCopyMessage deleteCopyMessage = DatabaseHandler.deleteCopy(deleteCopyCommand.getBookId());
		return deleteCopyMessage;
	}
	
	public static DeleteBookMessage deleteBook(BookId deleteBookCommand) {
		boolean commandSucceeded = DatabaseHandler.deleteBookAndCopies(deleteBookCommand.getBookId());
		DeleteBookMessage deleteBookMessage = new DeleteBookMessage(commandSucceeded);
		return deleteBookMessage;
	}
}
