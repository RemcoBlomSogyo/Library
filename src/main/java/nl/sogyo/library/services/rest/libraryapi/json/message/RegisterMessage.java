package nl.sogyo.library.services.rest.libraryapi.json.message;

public class RegisterMessage extends Message {

	private boolean userInTable;
	private String errorDescription;
	private int userId;
	private String userGivenName;
	private String userFamilyName;
	private byte userType;
	private static final String messagePositive = "Gebruiker is aan de database toegevoegd";
	private static final String messageNegative = "Gebruiker is niet aan de database toegevoegd";
	
	public RegisterMessage() {}
	
	public RegisterMessage(boolean commandSucceeded, boolean userInTable, String errorDescription,
			int userId, String userGivenName, String userFamilyName, byte userType) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.userInTable = userInTable;
		this.errorDescription = errorDescription;
		this.userId = userId;
		this.userGivenName = userGivenName;
		this.userFamilyName = userFamilyName;
		this.userType = userType;
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
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserGivenName() {
		return userGivenName;
	}

	public void setUserGivenName(String userGivenName) {
		this.userGivenName = userGivenName;
	}

	public String getUserFamilyName() {
		return userFamilyName;
	}

	public void setUserFamilyName(String userFamilyName) {
		this.userFamilyName = userFamilyName;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}
}
