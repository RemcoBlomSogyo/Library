package nl.sogyo.library.model.entity;

public class User {
	
	private int id;
	private String googleUserId;
	private String name;
	private String email;
	
	public User(String googleUserId, String name, String email) {
		this(0, googleUserId, name, email);
	}
	
	public User(int id, String googleUserId, String name, String email) {
		if (isSogyoAccount(email)) {
			this.id = id;
			this.googleUserId = googleUserId;
			this.name = name;
			this.email = email;
		} else {
			throw new IllegalArgumentException("Account is niet van Sogyo");
		}
	}

	private boolean isSogyoAccount(String email) {
		return email.contains("@sogyo.nl");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoogleUserId() {
		return googleUserId;
	}

	public void setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
