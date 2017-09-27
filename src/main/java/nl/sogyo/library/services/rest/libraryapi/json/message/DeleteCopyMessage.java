package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class DeleteCopyMessage {

	@XmlElement private boolean commandSucceeded;
	@XmlElement private int copiesOfBook;
	@XmlElement private String message;
	
	public DeleteCopyMessage() {}
	
	public DeleteCopyMessage(boolean commandSucceeded, int copiesOfBook) {
		this.commandSucceeded = commandSucceeded;
		this.copiesOfBook = copiesOfBook;
		
		if (commandSucceeded) {
			this.message = "Exemplaar is verwijderd uit de database";
		} else {
			this.message = "Exemplaar verwijderen mislukt";
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
