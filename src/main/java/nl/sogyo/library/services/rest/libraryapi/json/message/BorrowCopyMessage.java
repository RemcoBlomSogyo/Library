package nl.sogyo.library.services.rest.libraryapi.json.message;

public class BorrowCopyMessage extends Message {
	
	private static final String messagePositive = "Lenen boek geslaagd";
	private static final String messageNegative = "Lenen boek mislukt";
	
	public BorrowCopyMessage() {}
	
	public BorrowCopyMessage(boolean commandSucceeded) {
		super(commandSucceeded, messagePositive, messageNegative);
	}
}
