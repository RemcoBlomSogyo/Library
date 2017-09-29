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
}
