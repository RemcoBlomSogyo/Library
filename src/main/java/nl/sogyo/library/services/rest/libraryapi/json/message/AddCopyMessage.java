package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCopyMessage extends Message {

//	@XmlElement private int copiesOfBook;
	
	@JsonProperty private int copiesOfBook;
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
