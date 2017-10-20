package nl.sogyo.library.services.rest.libraryapi.json;

public class SignInCode {

	private String code;
	
	public SignInCode() {}
	
	public SignInCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
