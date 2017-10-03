package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class BookFormInput {

	@XmlElement private int id;
	@XmlElement private String title;
	@XmlElement private String subtitle;
	@XmlElement private String authorForename1;
	@XmlElement private String authorSurname1;
	@XmlElement private String authorForename2;
	@XmlElement private String authorSurname2;
	@XmlElement private String authorForename3;
	@XmlElement private String authorSurname3;
	@XmlElement private String category;
	@XmlElement private String publisher;
	@XmlElement private String yearFirstPublication;
	@XmlElement private String isbn;
	@XmlElement private String pages;
	@XmlElement private String language;
	
	public BookFormInput() {}
	
	public BookFormInput(String title, String subtitle, String authorForename1, 
			String authorSurname1, String authorForename2, String authorSurname2,
			String authorForename3, String authorSurname3, String category, String publisher, 
			String yearFirstPublication, String isbn, String pages, String language) {
		this.title = title;
		this.subtitle = subtitle;
		this.authorForename1 = authorForename1;
		this.authorSurname1 = authorSurname1;
		this.authorForename2 = authorForename2;
		this.authorSurname2 = authorSurname2;
		this.authorForename3 = authorForename3;
		this.authorSurname3 = authorSurname3;
		this.category = category;
		this.publisher = publisher;
		this.yearFirstPublication = yearFirstPublication;
		this.isbn = isbn;
		this.pages = pages;
		this.language = language;
	}
	
	public BookFormInput(String title, String authorForename1, String authorSurname1, 
			String category, String publisher, String isbn) {
		this.title = title;
		this.subtitle = "";
		this.authorForename1 = authorForename1;
		this.authorSurname1 = authorSurname1;
		this.authorForename2 = "";
		this.authorSurname2 = "";
		this.authorForename3 = "";
		this.authorSurname3 = "";
		this.category = category;
		this.publisher = publisher;
		this.yearFirstPublication = "";
		this.isbn = isbn;
		this.pages = "";
		this.language = "";
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
	
	public String getAuthorForename1() {
		return authorForename1;
	}
	
	public String getAuthorSurname1() {
		return authorSurname1;
	}
	
	public String getAuthorForename2() {
		return authorForename2;
	}
	
	public String getAuthorSurname2() {
		return authorSurname2;
	}
	
	public String getAuthorForename3() {
		return authorForename3;
	}
	
	public String getAuthorSurname3() {
		return authorSurname3;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public String getYearFirstPublication() {
		return yearFirstPublication;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public String getPages() {
		return pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public void setAuthorForename1(String authorForename1) {
		this.authorForename1 = authorForename1;
	}
	
	public void setAuthorSurname1(String authorSurname1) {
		this.authorSurname1 = authorSurname1;
	}
	
	public void setAuthorForename2(String authorForename2) {
		this.authorForename2 = authorForename2;
	}
	
	public void setAuthorSurname2(String authorSurname2) {
		this.authorSurname2 = authorSurname2;
	}
	
	public void setAuthorForename3(String authorForename3) {
		this.authorForename3 = authorForename3;
	}
	
	public void setAuthorSurname3(String authorSurname3) {
		this.authorSurname3 = authorSurname3;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public void setYearFirstPublication(String yearFirstPublication) {
		this.yearFirstPublication = yearFirstPublication;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
}
