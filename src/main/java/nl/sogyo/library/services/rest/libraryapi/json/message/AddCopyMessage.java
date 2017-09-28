package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class AddCopyMessage extends Message {

	@XmlElement private int copiesOfBook;
	private static final String messagePositive = "Exemplaar is toegevoegd aan de database";
	private static final String messageNegative = "Exemplaar toevoegen mislukt";
	
	public AddCopyMessage() {}
	
	public AddCopyMessage(boolean commandSucceeded, int copiesOfBook) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.copiesOfBook = copiesOfBook;
	}
	
	public int getCopiesOfBook() {
		return copiesOfBook;
	}
}
