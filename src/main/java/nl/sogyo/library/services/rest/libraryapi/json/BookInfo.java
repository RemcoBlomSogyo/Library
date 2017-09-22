package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

import nl.sogyo.library.model.command.Book;

public class BookInfo {
	
//	@XmlElement private String title;
//	@XmlElement private String subtitle;
//	@XmlElement private String authorForename;
//	@XmlElement private String authorSurname;
//	@XmlElement private String category;
//	@XmlElement private String publisher;
//	@XmlElement private short yearFirstPublication;
//	@XmlElement private String isbn;
//	@XmlElement private short pages;
//	@XmlElement private String language;
	
	@XmlElement private Book book;
	@XmlElement private int copiesAvailable;
	
	public BookInfo() {}
	
//	public BookInfo(String title, String subtitle, String authorForename, String authorSurname, String category,
//			String publisher, short yearFirstPublication, String isbn, short pages, String language) {
//		this.title = title;
//		this.subtitle = subtitle;
//		this.authorForename = authorForename;
//		this.authorSurname = authorSurname;
//		this.category = category;
//		this.publisher = publisher;
//		this.yearFirstPublication = yearFirstPublication;
//		this.isbn = isbn;
//		this.pages = pages;
//		this.language = language;
//	}
	
	public BookInfo(Book book, int copiesAvailable) {
		this.book = book;
		this.copiesAvailable = copiesAvailable;
	}
}
