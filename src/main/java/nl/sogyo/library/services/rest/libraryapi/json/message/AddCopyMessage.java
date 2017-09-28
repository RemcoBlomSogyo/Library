package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class AddCopyMessage extends Message {

	@XmlElement private int copiesOfBook;
	private static final String messagePositive = "Book is added to the database.";
	private static final String messageNegative = "Something went wrong. No book was added.";
	
	public AddCopyMessage() {}
	
	public AddCopyMessage(boolean commandSucceeded, int copiesOfBook) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.copiesOfBook = copiesOfBook;
		
//		if (commandSucceeded) {
//			this.message = "Exemplaar is toegevoegd aan de database";
//		} else {
//			this.message = "Exemplaar toevoegen mislukt";
//		}
	}
	
	public int getCopiesOfBook() {
		return copiesOfBook;
	}
}
