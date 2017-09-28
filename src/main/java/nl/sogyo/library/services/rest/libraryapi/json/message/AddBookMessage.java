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
	}
	
	public int getBookId() {
		return bookId;
	}
}