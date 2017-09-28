package nl.sogyo.library.model.command;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import nl.sogyo.library.model.helper.InputValidator;

// code for Hibernate

//@Entity
//@Table(name = "Books")
//@SecondaryTables({
//    @SecondaryTable(name="Categories", pkJoinColumns={
//        @PrimaryKeyJoinColumn(name="ID", referencedColumnName="CategoriesID") }),
//    @SecondaryTable(name="Publishers", pkJoinColumns={
//            @PrimaryKeyJoinColumn(name="ID", referencedColumnName="PublisherID") }),
//})
public class Book {
	
	@XmlElement private int id;
	@XmlElement private String title;
	@XmlElement private String subtitle;
	@XmlElement private String authorForename;
	@XmlElement private String authorSurname;
	@XmlElement private String category;
	@XmlElement private String publisher;
	@XmlElement private short yearFirstPublication;
	@XmlElement private String isbn;
	@XmlElement private short pages;
	@XmlElement private String language;
	
	public Book() {}
	
	public Book(String title, String subtitle, String authorForename, 
			String authorSurname, String category, String publisher, String yearFirstPublication, 
			String isbn, String pages, String language /*, Image imageCover*/) throws IllegalArgumentException {
		this(0, title, subtitle, authorForename, authorSurname, category, publisher, 
				yearFirstPublication, isbn, pages, language);
	}
	
	public Book(int id, String title, String subtitle, String authorForename, 
			String authorSurname, String category, String publisher, short yearFirstPublication, 
			String isbn, short pages, String language /*, Image imageCover*/) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.authorForename = authorForename;
		this.authorSurname = authorSurname;
		this.category = category;
		this.publisher = publisher;
		this.yearFirstPublication = yearFirstPublication;
		this.isbn = isbn;
		this.pages = pages;
		this.language = language;
	}
	
	public Book(int id, String title, String subtitle, String authorForename, 
			String authorSurname, String category, String publisher, String yearFirstPublication, 
			String isbn, String pages, String language /*, Image imageCover*/) throws IllegalArgumentException {
		if (variablesAreValid(title, authorForename, authorSurname, 
				category, publisher, yearFirstPublication, isbn, pages)) {
			this.id = id;
			this.title = title;
			this.subtitle = subtitle;
			this.authorForename = authorForename;
			this.authorSurname = authorSurname;
			this.category = category;
			this.publisher = publisher;
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
	
	public String getTitle() {
		return title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public String getAuthorForename() {
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
	
	public short getYearFirstPublication() {
		return yearFirstPublication;
	}

	public String getIsbn() {
		return isbn;
	}

	public short getPages() {
		return pages;
	}
	
	public String getLanguage() {
		return language;
	}
	
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
