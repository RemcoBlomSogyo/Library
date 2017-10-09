package nl.sogyo.library.services.rest.libraryapi.json.message;

public class EditBookMessage extends Message {

	private static final String messagePositive = "Boek is aangepast";
	private static final String messageNegative = "Aanpassen boek is mislukt";
	
	public EditBookMessage() {}
	
	public EditBookMessage(boolean commandSucceeded) {
		super(commandSucceeded, messagePositive, messageNegative);
	}
}
