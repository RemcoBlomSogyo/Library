package nl.sogyo.library.model.command;

public class Publisher {
	
	private int id;
	private String name;
	
	public Publisher() {}
	
	public Publisher(String name) {
		this(0, name);
	}
	
	public Publisher(int id, String name) {
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
