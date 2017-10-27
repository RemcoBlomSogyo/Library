package nl.sogyo.library.services.rest.libraryapi.json.message;

public class RegisterMessage extends Message {

	private boolean userInTable;
	private String errorDescription;
	private static final String messagePositive = "Gebruiker is aan de database toegevoegd";
	private static final String messageNegative = "Gebruiker is niet aan de database toegevoegd";
	
	public RegisterMessage() {}
	
	public RegisterMessage(boolean commandSucceeded, boolean userInTable, String errorDescription) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.userInTable = userInTable;
		this.errorDescription = errorDescription;
	}

	public boolean isUserInTable() {
		return userInTable;
	}
	
	public void setUserInTable(boolean userInTable) {
		this.userInTable = userInTable;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
