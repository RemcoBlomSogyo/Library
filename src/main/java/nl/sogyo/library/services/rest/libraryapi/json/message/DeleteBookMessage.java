package nl.sogyo.library.services.rest.libraryapi.json.message;

import javax.xml.bind.annotation.XmlElement;

public class DeleteBookMessage extends Message {
	
	private static final String messagePositive = "Boek en exemplaren zijn verwijderd uit de database";
	private static final String messageNegative = "Boek en exemplaren verwijderen mislukt";
	
	public DeleteBookMessage() {}
	
	public DeleteBookMessage(boolean commandSucceeded) {
		super(commandSucceeded, messagePositive, messageNegative);
	}
}
