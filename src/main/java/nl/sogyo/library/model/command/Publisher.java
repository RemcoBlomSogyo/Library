package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

public class Publisher {

	@XmlElement private String name;
	
	public String getName() {
		return name;
	}
}
