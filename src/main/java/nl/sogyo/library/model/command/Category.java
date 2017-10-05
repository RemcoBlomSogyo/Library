package nl.sogyo.library.model.command;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

//	@XmlElement private int id;
//	@XmlElement private String name;
	
	private int id;
	private String name;
	
	public Category() {}
	
	public Category(String name) {
		this(0, name);
	}
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
