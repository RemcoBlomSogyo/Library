package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class TestBook {

	@XmlElement private int id;
	@XmlElement private String title;
	
	public TestBook() {}
	
	public TestBook(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
}
