package nl.sogyo.library.services.rest.libraryapi.json;

import nl.sogyo.library.model.entity.Book;

public class BookInfo {
	
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
