package com.model;

import javax.xml.bind.annotation.XmlElement;

public class Book {
	
	@XmlElement private int id;
	@XmlElement private String title;
	
	public Book() {}

	public Book(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
}
