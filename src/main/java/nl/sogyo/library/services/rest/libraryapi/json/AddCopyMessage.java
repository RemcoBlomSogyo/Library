package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class AddCopyMessage {

	@XmlElement private boolean commandSucceeded;
	@XmlElement private int copiesOfBook;
	@XmlElement private String message;
	
	public AddCopyMessage() {}
	
	public AddCopyMessage(boolean commandSucceeded, int copiesOfBook) {
		this.commandSucceeded = commandSucceeded;
		this.copiesOfBook = copiesOfBook;
		
		if (commandSucceeded) {
			this.message = "Exemplaar is toegevoegd aan de database";
		} else {
			this.message = "Exemplaar toevoegen mislukt";
		}
	}
	
	public boolean getCommandSucceeded() {
		return commandSucceeded;
	}
	
	public int getCopiesOfBook() {
		return copiesOfBook;
	}
	
	public String getMessage() {
		return message;
	}
}
