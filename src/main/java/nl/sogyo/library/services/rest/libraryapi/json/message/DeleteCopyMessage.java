package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class DeleteCopyMessage extends Message {

	@XmlElement private int copiesOfBook;
	private static final String messagePositive = "Exemplaar is verwijderd uit de database";
	private static final String messageNegative = "Exemplaar verwijderen mislukt";
	
	public DeleteCopyMessage() {}
	
	public DeleteCopyMessage(boolean commandSucceeded, int copiesOfBook) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.copiesOfBook = copiesOfBook;
	}

	public int getCopiesOfBook() {
		return copiesOfBook;
	}
}
