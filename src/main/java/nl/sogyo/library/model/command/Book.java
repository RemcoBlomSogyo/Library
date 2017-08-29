package nl.sogyo.library.model.command;

import java.awt.Image;
import java.time.Year;

import javax.xml.bind.annotation.XmlElement;

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
	
	private static final int ISBN_LENGTH = 13;
	
	public Book(String title, String subtitle, String authorForename, 
			String authorSurname, String category, String publisher, short yearFirstPublication, 
			String isbn, short pages, String language /*, Image imageCover*/) throws IllegalArgumentException {
		if (variablesAreValid(title, authorForename, authorSurname, 
				category, publisher, yearFirstPublication, isbn)) {
			this.title = title;
			this.subtitle = subtitle;
			this.author = new Author(authorForename, authorSurname);
			this.category = category;
			this.publisher = publisher;
			this.yearFirstPublication = yearFirstPublication;
			this.isbn = isbn;
			this.pages = pages;
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
	
	public int getYearFirstPublication() {
		return yearFirstPublication;
	}

	public String getISBN() {
		return isbn;
	}

	public int getPages() {
		return pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
//	public Image getImageCover() {
//		return imageCover;
//	}
	
	private boolean variablesAreValid(String title, String authorForename, String authorSurname,
			String category, String publisher, short yearFirstPublication, String isbn) {
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
		
		if (!yearIsValid(yearFirstPublication)) {
			System.out.println("invalid year");
			throw new IllegalArgumentException("Invalid year");
		}
		
		if (!isbnIsValid(isbn)) {
			System.out.println("invalid isbn");
			throw new IllegalArgumentException("Invalid ISBN");
		}
		
		return true;
	}
	
	private boolean isbnIsValid(String isbnInput) {
		return stringHasOnlyNumbers(isbnInput) && stringHasThirteenChars(isbnInput);
	}
	
	private boolean stringHasOnlyNumbers(String isbnInput) {
		return isbnInput.matches("[0-9]+");
	}
	
	private boolean stringHasThirteenChars(String isbnInput) {
		return isbnInput.length() == ISBN_LENGTH;
	}
	
	private boolean yearIsValid(short yearFirstPublication) {
		return yearFirstPublication <= Year.now().getValue();
	}
}
