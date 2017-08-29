package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class SuccessMessage {
	
	@XmlElement private boolean commandSucceeded;
	@XmlElement private String message;
	
	public SuccessMessage() {}
	
	public SuccessMessage(boolean commandSucceeded) {
		this.commandSucceeded = commandSucceeded;
		if (commandSucceeded) {
			message = "Book is added to the database.";
		} else {
			message = "Something went wrong. No book was added.";
		}
	}
}