package nl.sogyo.library.model.command;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.BookFormInput;
import nl.sogyo.library.services.rest.libraryapi.json.BookId;
import nl.sogyo.library.services.rest.libraryapi.json.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.SuccessMessage;

public class Library {
	
	public static SuccessMessage addBook(BookFormInput bookFormInput) {
		Book book;
		boolean commandSucceeded;
		try {
			book = new Book(bookFormInput.getTitle(), bookFormInput.getSubtitle(),
					bookFormInput.getAuthorForname(), bookFormInput.getAuthorSurname(),
					bookFormInput.getCategory(), bookFormInput.getPublisher(),
					bookFormInput.getYearFirstPublication(), bookFormInput.getIsbn(),
					bookFormInput.getPages(), bookFormInput.getLanguage());
			commandSucceeded = DatabaseHandler.insertBook(book);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.out.println("library illargexcp");
			commandSucceeded = false;
		}
		return new SuccessMessage(commandSucceeded);
	}
	
	public static AddCopyMessage addCopy(BookId addCopyCommand) {
		AddCopyMessage addCopyMessage = DatabaseHandler.insertCopy(addCopyCommand.getBookId());
		return addCopyMessage;
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
