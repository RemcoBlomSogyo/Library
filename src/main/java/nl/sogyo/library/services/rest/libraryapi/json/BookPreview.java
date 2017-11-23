package nl.sogyo.library.services.rest.libraryapi.json;

import java.util.List;

import nl.sogyo.library.model.entity.Author;

public class BookPreview {
	
	private int id;
	private String title;
	private List<Author> authors;
	private String category;
	private String isbn;
	
	public BookPreview() {}
	
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Author> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
