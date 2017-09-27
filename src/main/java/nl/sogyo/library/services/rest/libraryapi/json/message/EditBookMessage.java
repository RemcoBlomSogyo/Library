package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class EditBookMessage {

	@XmlElement private boolean commandSucceeded;
	@XmlElement private String message;
	
	public EditBookMessage() {}
	
	public EditBookMessage(boolean commandSucceeded) {
		this.commandSucceeded = commandSucceeded;

		if (commandSucceeded) {
			this.message = "Boek is succesvol aangepast";
		} else {
			this.message = "Aanpassen boek is mislukt";
		}
	}
	
	public boolean getCommandSucceeded() {
		return commandSucceeded;
	}
	
	public String getMessage() {
		return message;
	}
}
