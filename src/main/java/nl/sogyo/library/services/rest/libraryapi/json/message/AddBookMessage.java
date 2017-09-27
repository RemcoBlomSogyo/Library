package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class AddBookMessage {
	
	@XmlElement private boolean commandSucceeded;
	@XmlElement private int bookId;
	@XmlElement private String message;
	
	public AddBookMessage() {}
	
	public AddBookMessage(int bookId) {
		this.bookId = bookId;
		if (bookId > 0) {
			commandSucceeded = true;
			message = "Book is added to the database.";
		} else {
			commandSucceeded = false;
			message = "Something went wrong. No book was added.";
		}
	}
}