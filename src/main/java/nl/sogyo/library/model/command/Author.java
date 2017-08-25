package nl.sogyo.library.model.command;

public class Author {

	private String forename;
	private String surname;
	
	public Author(String authorInput) {
		if (inputContainsWhiteSpace(authorInput)) {
			parseInputIntoTwoNames(authorInput);
		} else {
			forename = authorInput;
		}
	}
	
	public Author(String forename, String surname) {
		this.forename = forename;
		this.surname = surname;
	}
	
	public String getForename() {
		return forename;
	}
	
	public String getSurname() {
		return surname;
	}
	
	private boolean inputContainsWhiteSpace(String authorInput) {
		return authorInput.contains(" ");
	}
	
	private void parseInputIntoTwoNames(String authorInput) {
		forename = authorInput.substring(0, authorInput.indexOf(' '));
		String secondPartName = authorInput.substring(authorInput.indexOf(' ') + 1);
		surname = secondPartName.trim();
	}
}
