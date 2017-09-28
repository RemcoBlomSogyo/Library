package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class AddBookMessage extends Message {
	
	@XmlElement private int bookId;
	private static final String messagePositive = "Boek is toegevoegd";
	private static final String messageNegative = "Toevoegen boek mislukt";
	
	public AddBookMessage() {}
	
	public AddBookMessage(int bookId) {
		super(bookId > 0, messagePositive, messageNegative);
		this.bookId = bookId;
//		this.bookId = bookId;
//		if (bookId > 0) {
//			commandSucceeded = true;
//			message = "Book is added to the database.";
//		} else {
//			commandSucceeded = false;
//			message = "Something went wrong. No book was added.";
//		}
	}
	
	public int getBookId() {
		return bookId;
	}
}