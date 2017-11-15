package nl.sogyo.library.services.rest.libraryapi.json.message;

public class DeleteCopyMessage extends Message {
	
	private long copiesOfBook;
	private static final String messagePositive = "Exemplaar is verwijderd uit de database";
	private static final String messageNegative = "Exemplaar verwijderen mislukt";
	
	public DeleteCopyMessage() {}
	
	public DeleteCopyMessage(boolean commandSucceeded, long copiesOfBook) {
		super(commandSucceeded, messagePositive, messageNegative);
		this.copiesOfBook = copiesOfBook;
	}

	public long getCopiesOfBook() {
		return copiesOfBook;
	}
	
	public void setCopiesOfBook(long copiesOfBook) {
		this.copiesOfBook = copiesOfBook;
	}
}
