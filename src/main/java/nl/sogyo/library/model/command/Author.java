package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
	
//	@XmlElement private int id;
//	@XmlElement private String forename;
//	@XmlElement private String surname;
	
	@JsonProperty private int id;
	@JsonProperty private String forename;
	@JsonProperty private String surname;
	
	public Author() {}
	
	public Author(String forename, String surname) {
		if (forename.isEmpty() || surname.isEmpty()) {
			throw new IllegalArgumentException("A name is empty");
		} else {
			this.forename = forename;
			this.surname = surname;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getForename() {
		return forename;
	}
	
	public void setForename(String forename) {
		this.forename = forename;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
