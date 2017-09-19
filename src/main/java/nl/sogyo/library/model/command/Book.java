package nl.sogyo.library.model.command;

import java.awt.Image;

import nl.sogyo.library.model.helper.InputValidator;

public class Book {
	
	private String title;
	private String subtitle;
	private Author author;
	private String category;
	private String publisher;
	private short yearFirstPublication;
	private String isbn;
	private short pages;
	private String language;
//	private Image imageCover;
	
	public Book(String title, String subtitle, String authorForename, 
			String authorSurname, String category, String publisher, String yearFirstPublication, 
			String isbn, String pages, String language /*, Image imageCover*/) throws IllegalArgumentException {
		if (variablesAreValid(title, authorForename, authorSurname, 
				category, publisher, yearFirstPublication, isbn, pages)) {
			this.title = title;
			this.subtitle = subtitle;
			this.author = new Author(authorForename, authorSurname);
			this.category = category;
			this.publisher = publisher;
			this.yearFirstPublication = Short.parseShort(emptyToZero(yearFirstPublication));
			this.isbn = isbn;
			this.pages = Short.parseShort(emptyToZero(pages));
			this.language = language;
//			this.imageCover = imageCover;
		} else {
			throw new IllegalArgumentException("A variable is invalid");
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public short getYearFirstPublication() {
		return yearFirstPublication;
	}

	public String getISBN() {
		return isbn;
	}

	public short getPages() {
		return pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
//	public Image getImageCover() {
//		return imageCover;
//	}
	
	private boolean variablesAreValid(String title, String authorForename, String authorSurname,
			String category, String publisher, String yearFirstPublication, String isbn, String pages) {
		if (title.isEmpty()) {
			System.out.println("invalid title");
			throw new IllegalArgumentException("Title is empty");
		}
		
		if (authorForename.isEmpty() && !authorSurname.isEmpty()) {
			System.out.println("invalid author");
			throw new IllegalArgumentException("Forename author is empty");
		}
		
		if (authorSurname.isEmpty()) {
			System.out.println("invalid author");
			throw new IllegalArgumentException("Surname author is empty");
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
