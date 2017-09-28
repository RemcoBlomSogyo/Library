package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class EditBookMessage extends Message {

	private static final String messagePositive = "Exemplaar is verwijderd uit de database";
	private static final String messageNegative = "Exemplaar verwijderen mislukt";
	
	public EditBookMessage() {}
	
	public EditBookMessage(boolean commandSucceeded) {
		super(commandSucceeded, messagePositive, messageNegative);
	}
}
