package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class BookId {

	@XmlElement private int bookId;
	
	public BookId() {}
	
	public int getBookId() {
		return bookId;
	}
	
}
