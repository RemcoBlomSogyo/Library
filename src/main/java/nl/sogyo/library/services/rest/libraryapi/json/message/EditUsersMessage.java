package nl.sogyo.library.services.rest.libraryapi.json.message;

public class EditUsersMessage extends Message {
	
	private static final String messagePositive = "Gebruikers zijn aangepast";
	private static final String messageNegative = "Aanpassen gebruikers is mislukt";
	
	public EditUsersMessage() {}
	
	public EditUsersMessage(boolean commandSucceeded) {
		super(commandSucceeded, messagePositive, messageNegative);
	}
}
