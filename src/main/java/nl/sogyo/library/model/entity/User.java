package nl.sogyo.library.model.entity;

public class User {
	
	private int id;
	private long googleUserId;
	private String email;
	private String name;
	
	public User(long googleUserId, String email, String name) {
		this(0, googleUserId, email, name);
	}
	
	public User(int id, long googleUserId, String email, String name) {
		if (isSogyoAccount(email)) {
			this.id = id;
			this.googleUserId = googleUserId;
			this.email = email;
			this.name = name;
		} else {
			throw new IllegalArgumentException("Account is niet van Sogyo");
		}
	}

	private boolean isSogyoAccount(String email) {
		return email.contains("@sogyo.nl");
	}
}
