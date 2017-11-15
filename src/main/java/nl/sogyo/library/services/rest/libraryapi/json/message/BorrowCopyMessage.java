package nl.sogyo.library.services.rest.libraryapi.json.message;

public class BorrowCopyMessage extends Message {
	
	private long copiesAvailable;
	private long copiesBorrowed;
	private static final String messagePositive = "Lenen boek geslaagd";
	private static final String messageNegative = "Lenen boek mislukt";
	
	public BorrowCopyMessage() {}
	
	public BorrowCopyMessage(boolean commandSucceeded, long copiesAvailable, long copiesBorrowed) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.copiesAvailable = copiesAvailable;
		this.copiesBorrowed = copiesBorrowed;
	}

	public long getCopiesAvailable() {
		return copiesAvailable;
	}

	public void setCopiesAvailable(long copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}

	public long getCopiesBorrowed() {
		return copiesBorrowed;
	}

	public void setCopiesBorrowed(long copiesBorrowed) {
		this.copiesBorrowed = copiesBorrowed;
	}
}
