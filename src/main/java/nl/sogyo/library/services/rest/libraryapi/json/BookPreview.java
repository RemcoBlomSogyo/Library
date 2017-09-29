package nl.sogyo.library.services.rest.libraryapi.json;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import nl.sogyo.library.model.command.Author;

public class BookPreview {
	
	@XmlElement private int id;
	@XmlElement private String title;
//	@XmlElement private String author;
	@XmlElement private List<Author> authors;
	@XmlElement private String category;
	@XmlElement private String isbn;
	
	public BookPreview() {}
	
//	public BookPreview(int id, String title, String author, String category, String isbn) {
//		this.id = id;
//		this.title = title;
//		this.author = author;
//		this.category = category;
//		this.isbn = isbn;
//	}
	
	public BookPreview(int id, String title, List<Author> authors, String category, String isbn) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.category = category;
		this.isbn = isbn;
	}
	
	public int getId() {
		return id;
	}
	
	public List<Author> getAuthors() {
		return authors;
	}
}
