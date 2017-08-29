package nl.sogyo.library.services.rest.libraryapi.json;

import javax.xml.bind.annotation.XmlElement;

public class BookPreview {
	
	@XmlElement private String title;
	@XmlElement private String author;
	@XmlElement private String category;
}
