package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class DeleteBookMessage {

	@XmlElement private boolean commandSucceeded;
	@XmlElement private String message;
	
	public DeleteBookMessage() {}
	
	public DeleteBookMessage(boolean commandSucceeded) {
		this.commandSucceeded = commandSucceeded;

		if (commandSucceeded) {
			this.message = "Boek en exemplaren zijn verwijderd uit de database";
		} else {
			this.message = "Boek en exemplaren verwijderen mislukt";
		}
	}
	
	public boolean getCommandSucceeded() {
		return commandSucceeded;
	}
	
	public String getMessage() {
		return message;
	}
}
