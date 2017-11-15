package nl.sogyo.library.model.entity;

public class Copy {

	private int id;
	private Book book;
	private User user;
	
	public Copy() {}
	
	public Copy(Book book) {
		this.id = 0;
		this.book = book;
	}
	
	public Copy(int id, Book book) {
		this.id = id;
		this.book = book;
	}
	
	public Copy(int id, Book book, User user) {
		this.id = id;
		this.book = book;
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
