package nl.sogyo.library.services.rest.libraryapi.json;

public class BookFormInput {

	private int id;
	private String title;
	private String subtitle;
	private String authorForename1;
	private String authorSurname1;
	private String authorForename2;
	private String authorSurname2;
	private String authorForename3;
	private String authorSurname3;
	private String category;
	private String publisher;
	private String yearFirstPublication;
	private String isbn;
	private String pages;
	private String language;
	
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
	
	public String getAuthorForename1() {
		return authorForename1;
	}
	
	public void setAuthorForename1(String authorForename1) {
		this.authorForename1 = authorForename1;
	}
	
	public String getAuthorSurname1() {
		return authorSurname1;
	}
	
	public void setAuthorSurname1(String authorSurname1) {
		this.authorSurname1 = authorSurname1;
	}
	
	public String getAuthorForename2() {
		return authorForename2;
	}
	
	public void setAuthorForename2(String authorForename2) {
		this.authorForename2 = authorForename2;
	}
	
	public String getAuthorSurname2() {
		return authorSurname2;
	}
	
	public void setAuthorSurname2(String authorSurname2) {
		this.authorSurname2 = authorSurname2;
	}
	
	public String getAuthorForename3() {
		return authorForename3;
	}
	
	public void setAuthorForename3(String authorForename3) {
		this.authorForename3 = authorForename3;
	}
	
	public String getAuthorSurname3() {
		return authorSurname3;
	}
	
	public void setAuthorSurname3(String authorSurname3) {
		this.authorSurname3 = authorSurname3;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getYearFirstPublication() {
		return yearFirstPublication;
	}
	
	public void setYearFirstPublication(String yearFirstPublication) {
		this.yearFirstPublication = yearFirstPublication;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getPages() {
		return pages;
	}
	
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
}
