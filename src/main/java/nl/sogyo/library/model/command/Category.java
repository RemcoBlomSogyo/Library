package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

public class Category {

	@XmlElement private String name;
	
	public String getName() {
		return name;
	}
}
