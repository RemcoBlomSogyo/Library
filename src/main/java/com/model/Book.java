package com.model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlElement;

public class Book {
	
	@XmlElement private int id;
	@XmlElement private String title;
	@XmlElement private String subtitle;
	@XmlElement private int categoryID;
	@XmlElement private int publisherID;
	@XmlElement private int yearFirstPublication;
	@XmlElement private String ISBN;
	@XmlElement private int pages;
	@XmlElement private String language;
	@XmlElement private Image imageCover;
	
	public Book() {}

	public Book(int id, String title) {		
		this(id, title, null, 0, 0, 0, null, 0, null, null);
	}
	
	public Book(String title, String subtitle, int categoryID, 
			int publisherID, int yearFirstPublication, String ISBN,
			int pages, String language, Image imageCover) {
		this(0, title, subtitle, categoryID, publisherID, 
				yearFirstPublication, ISBN, pages, language, imageCover);
	}
	
	public Book(int id, String title, String subtitle, int categoryID, 
			int publisherID, int yearFirstPublication, String ISBN,
			int pages, String language, Image imageCover) {
//		if (inputIsValid(title, categoryID, publisherID, ISBN)) {
			this.id = id;
			this.title = title;
			this.subtitle = subtitle;
			this.categoryID = categoryID;
			this.publisherID = publisherID;
			this.yearFirstPublication = yearFirstPublication;
			this.ISBN = ISBN;
			this.pages = pages;
			this.language = language;
			this.imageCover = imageCover;
//		} else {
//			throw new IllegalArgumentException();
//		}
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
	
	public int getCategoryID() {
		return categoryID;
	}
	
	public int getPublisherID() {
		return publisherID;
	}
	
	public int getYearFirstPublication() {
		return yearFirstPublication;
	}

	public String getISBN() {
		return ISBN;
	}

	public int getPages() {
		return pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public Image getImageCover() {
		return imageCover;
	}
	
	private boolean inputIsValid(String title, int categoryID, int publisherID, String ISBN) {
		if (titleIsNotValid(title)) {
			return false;
		} else if (categoryIdIsNotValid(categoryID)) {
			return false;
		} else if (publisherIdIsNotValid(publisherID)) {
			return false;
		} else if (isbnIsNotValid(ISBN)) {
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
	
	private boolean isbnIsNotValid(String ISBN) {
		return ISBN.length() != 13;
	}
}
