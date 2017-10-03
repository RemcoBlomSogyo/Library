package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

public class Category {

	@XmlElement private int id;
	@XmlElement private String name;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
