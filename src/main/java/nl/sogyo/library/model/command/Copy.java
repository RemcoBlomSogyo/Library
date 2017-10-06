package nl.sogyo.library.model.command;

public class Copy {

	private int id;
	private Book book;
	
	public Copy() {}
	
	public Copy(int id, Book book) {
		this.id = id;
		this.book = book;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
}
