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
		return subtitle;
	}
	
	public String getAuthorSurname() {
		return subtitle;
	}
	
	public String getCategory() {
		return subtitle;
	}
	
	public String getPublisher() {
		return subtitle;
	}
	
	public String getYearFirstPublication() {
		return subtitle;
	}
	
	public String getIsbn() {
		return subtitle;
	}
	
	public String getPages() {
		return subtitle;
	}
	
	public String getLanguage() {
		return language;
	}
}
