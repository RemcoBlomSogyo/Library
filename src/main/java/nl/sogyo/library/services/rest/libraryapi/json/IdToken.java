package nl.sogyo.library.services.rest.libraryapi.json;

public class IdToken {

	private String idTokenString;
	
	public IdToken() {}
	
	public IdToken(String idTokenString) {
		this.idTokenString = idTokenString;
	}
	
	public String getIdTokenString() {
		return idTokenString;
	}
	
	public void setIdTokenString(String idTokenString) {
		this.idTokenString = idTokenString;
	}
}
