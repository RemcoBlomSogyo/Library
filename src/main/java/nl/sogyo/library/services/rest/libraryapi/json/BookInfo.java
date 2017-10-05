package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.sogyo.library.model.command.Book;

public class BookInfo {
	
//	@XmlElement private Book book;
//	@XmlElement private int copiesAvailable;
	
	private Book book;
	private int copiesAvailable;
	
	public BookInfo() {}
	
	public BookInfo(Book book, int copiesAvailable) {
		this.book = book;
		this.copiesAvailable = copiesAvailable;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public int getCopiesAvailable() {
		return copiesAvailable;
	}
	
	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
}
