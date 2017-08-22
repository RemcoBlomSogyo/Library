package com.model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlElement;

public class Book {
	
	private int id;
	private String title;
	private String subtitle;
	private Author author;
	private String category;
	private String publisher;
	private int yearFirstPublication;
	private ISBN isbn;
	private short pages;
	private String language;
//	@XmlElement private Image imageCover;
	
	public Book() {}

	public Book(int id, String title) {		
		this(id, title, null, 0, 0, 0, null, (short) 0, null /*, null*/);
	}
	
	public Book(String title, String subtitle, int categoryID, 
			int publisherID, int yearFirstPublication, String isbn,
			short pages, String language, Image imageCover) {
		this(0, title, subtitle, categoryID, publisherID, 
				yearFirstPublication, isbn, pages, language /*, imageCover*/);
	}
	
	public Book(int id, String title, String subtitle, int categoryID, 
			int publisherID, int yearFirstPublication, String isbn,
			short pages, String language /*, Image imageCover*/) {
//		if (inputIsValid(title, categoryID, publisherID, ISBN)) {
			this.id = id;
			this.title = title;
			this.subtitle = subtitle;
			this.category = category;
			this.publisher = publisher;
			this.yearFirstPublication = yearFirstPublication;
			this.isbn = new ISBN(isbn);
			this.pages = pages;
			this.language = language;
//			this.imageCover = imageCover;
//		} else {
//			throw new IllegalArgumentException();
//		}
	}
	
	public Book(int id, String title, String subtitle, Author author, String category, 
			String publisher, short yearFirstPublication, ISBN isbn,
			short pages, String language) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.category = category;
		this.publisher = publisher;
		this.yearFirstPublication = yearFirstPublication;
		this.isbn = isbn;
		this.pages = pages;
		this.language = language;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubtitle() {
		return subtitle;
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

	public ISBN getISBN() {
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
	
	private boolean inputIsValid(String title, int categoryID, int publisherID, String ISBN) {
		if (titleIsNotValid(title)) {
			return false;
		} else if (categoryIdIsNotValid(categoryID)) {
			return false;
		} else if (publisherIdIsNotValid(publisherID)) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean titleIsNotValid(String title) {
		return title.isEmpty();
	}
	
	private boolean categoryIdIsNotValid(int categoryID) {
		return categoryID <= 0;
	}
	
	private boolean publisherIdIsNotValid(int publisherID) {
		return publisherID <= 0;
	}
	
}
