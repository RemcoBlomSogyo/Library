package nl.sogyo.library.services.rest.libraryapi.json.message;

public class AddCopyMessage extends Message {
	
	private long copiesOfBook;
	private static final String messagePositive = "Exemplaar is toegevoegd aan de database";
	private static final String messageNegative = "Exemplaar toevoegen mislukt";
	
	public AddCopyMessage() {}
	
	public AddCopyMessage(boolean commandSucceeded, long copiesOfBook) {
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
