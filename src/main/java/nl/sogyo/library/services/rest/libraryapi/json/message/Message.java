package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Message {
	
//	@XmlElement private boolean commandSucceeded;
//	@XmlElement private String message;
	
	private boolean commandSucceeded;
	private String message;
	
	public Message() {}
	
	public Message(boolean commandSucceeded, String messagePositive, String messageNegative) {
		this.commandSucceeded = commandSucceeded;

		if (commandSucceeded) {
			this.message = messagePositive;
		} else {
			this.message = messageNegative;
		}
	}
	
	public boolean getCommandSucceeded() {
		return commandSucceeded;
	}
	
	public void setCommandSucceeded(boolean commandSucceeded) {
		this.commandSucceeded = commandSucceeded;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
