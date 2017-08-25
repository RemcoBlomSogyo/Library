package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class BookFormInput {

	@XmlElement private String title;
	@XmlElement private String subtitle;
	@XmlElement private String authorForename;
	@XmlElement private String authorSurname;
	@XmlElement private String category;
	@XmlElement private String publisher;
	@XmlElement private String yearFirstPublication;
	@XmlElement private String isbn;
	@XmlElement private String pages;
	@XmlElement private String language;
	
	public BookFormInput() {}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public String getAuthorForname() {
		return authorForename;
	}
	
	public String getAuthorSurname() {
		return authorSurname;
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
