package nl.sogyo.library.model.entity;

import java.util.List;

import nl.sogyo.library.model.helper.InputValidator;

public class Book {
	
	private int id;
	private String title;
	private String subtitle;
	private List<Author> authors;
	private Category category;
	private Publisher publisher;
	private short yearFirstPublication;
	private String isbn;
	private short pages;
	private String language;
	
	public Book() {}
	
	public Book(String title, String subtitle, List<Author> authors, String category, String publisher, String yearFirstPublication, 
			String isbn, String pages, String language) throws IllegalArgumentException {
		this(0, title, subtitle, authors, category, publisher, 
				yearFirstPublication, isbn, pages, language);
	}

	public Book(int id, String title, String subtitle, List<Author> authors, String category, 
			String publisher, short yearFirstPublication, String isbn, short pages, String language) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.authors = authors;
		this.category = new Category(category);
		this.publisher = new Publisher(publisher);
		this.yearFirstPublication = yearFirstPublication;
		this.isbn = isbn;
		this.pages = pages;
		this.language = language;
	}
	
	public Book(int id, String title, String subtitle, List<Author> authors, String category, String publisher, String yearFirstPublication, 
			String isbn, String pages, String language /*, Image imageCover*/) throws IllegalArgumentException {
		if (variablesAreValid(title, authors, 
				category, publisher, yearFirstPublication, isbn, pages)) {
			this.id = id;
			this.title = title;
			this.subtitle = subtitle;
			this.authors = authors;
			this.category = new Category(category);
			this.publisher = new Publisher(publisher);
			this.yearFirstPublication = Short.parseShort(emptyToZero(yearFirstPublication));
			this.isbn = isbn;
			this.pages = Short.parseShort(emptyToZero(pages));
			this.language = language;
		} else {
			throw new IllegalArgumentException("A variable is invalid");
		}
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
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<Author> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	public short getYearFirstPublication() {
		return yearFirstPublication;
	}
	
	public void setYearFirstPublication(short yearFirstPublication) {
		this.yearFirstPublication = yearFirstPublication;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public short getPages() {
		return pages;
	}
	
	public void setPages(short pages) {
		this.pages = pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	private boolean variablesAreValid(String title, List<Author> authors,
			String category, String publisher, String yearFirstPublication, String isbn, String pages) {
		if (title.isEmpty()) {
			System.out.println("invalid title");
			throw new IllegalArgumentException("Title is empty");
		}
		
		if (authors.isEmpty()) {
			System.out.println("invalid authors");
			throw new IllegalArgumentException("Authors is empty");
		}
		
		if (category.isEmpty()) {
			System.out.println("invalid category");
			throw new IllegalArgumentException("Category is empty");
		}
		
		if (publisher.isEmpty()) {
			System.out.println("invalid publisher");
			throw new IllegalArgumentException("Publisher is empty");
		}
		
		if (!InputValidator.validateYear(yearFirstPublication)) {
			System.out.println("invalid year");
			throw new IllegalArgumentException("Invalid year");
		}
		
		if (!InputValidator.validateIsbn(isbn)) {
			System.out.println("invalid isbn");
			throw new IllegalArgumentException("Invalid ISBN");
		}
		
		if (!InputValidator.validatePages(pages)) {
			System.out.println("invalid pages");
			throw new IllegalArgumentException("Invalid pages");
		}
		
		return true;
	}
	
	private String emptyToZero(String numberInput) {
		if (numberInput.isEmpty()) {
			numberInput = "0";
		}
		System.out.println("test: " + numberInput);
		return numberInput;
	}
}
