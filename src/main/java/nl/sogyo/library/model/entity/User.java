package nl.sogyo.library.model.entity;

import nl.sogyo.oauth.javagooglesignin.GoogleUser;

public class User {
	
	private int id;
	private String googleUserId;
	private String givenName;
	private String familyName;
	private String email;
	private byte userType;
	
	public User() {}
	
	public User(String googleUserId, String givenName, String familyName, String email, byte userType) {
		this(0, googleUserId, givenName, familyName, email, userType);
	}
	
	public User(int id, String googleUserId, String givenName, String familyName, String email, byte userType) {
		if (isSogyoAccount(email)) {
			this.id = id;
			this.googleUserId = googleUserId;
			this.givenName = givenName;
			this.familyName = familyName;
			this.email = email;
			this.userType = userType;
		} else {
			throw new IllegalArgumentException("Account is niet van Sogyo");
		}
	}
	
	public User(GoogleUser googleUser) {
		this(0, googleUser.getUserId(), googleUser.getGivenName(), googleUser.getFamilyName(), 
				googleUser.getEmail(), (byte) 1);
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

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}
}
