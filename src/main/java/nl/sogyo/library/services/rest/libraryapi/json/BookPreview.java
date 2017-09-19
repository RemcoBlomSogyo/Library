package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class BookPreview {
	
	@XmlElement private int id;
	@XmlElement private String title;
	@XmlElement private String author;
	@XmlElement private String category;
	@XmlElement private String isbn;
	
	public BookPreview() {}
	
	public BookPreview(int id, String title, String author, String category, String isbn) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.isbn = isbn;
	}
}
