package nl.sogyo.library.services.rest.libraryapi.json;

import nl.sogyo.library.model.entity.Book;

public class BookInfo {
	
	private Book book;
	private long copiesAvailable;
	private long copiesBorrowed;
	
	public BookInfo() {}
	
	public BookInfo(Book book, long copiesAvailable, long copiesBorrowed) {
		this.book = book;
		this.copiesAvailable = copiesAvailable;
		this.copiesBorrowed = copiesBorrowed;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public long getCopiesAvailable() {
		return copiesAvailable;
	}
	
	public void setCopiesAvailable(long copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
	
	public long getCopiesBorrowed() {
		return copiesBorrowed;
	}
	
	public void setCopiesBorrowed(long copiesBorrowed) {
		this.copiesBorrowed = copiesBorrowed;
	}
}
