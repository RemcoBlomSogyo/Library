package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

import nl.sogyo.library.model.command.Book;

public class BookInfo {
	
	@XmlElement private Book book;
	@XmlElement private int copiesAvailable;
	
	public BookInfo() {}
	
	public BookInfo(Book book, int copiesAvailable) {
		this.book = book;
		this.copiesAvailable = copiesAvailable;
	}
}
