package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

public class Author {
	
	@XmlElement private String forename;
	@XmlElement private String surname;
	
	public Author() {}
	
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
}
