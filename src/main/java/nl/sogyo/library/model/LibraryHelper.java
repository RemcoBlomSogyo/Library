package nl.sogyo.library.model;

import java.io.IOException;

import nl.sogyo.library.model.helper.TokenParser;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;

public abstract class LibraryHelper {

	protected GoogleUser googleUser;
	protected DatabaseHandler databaseHandler;
	protected static final int USERTYPE_USER = 1;
	protected static final int USERTYPE_ADMIN = 2;
	
	public LibraryHelper(String idToken) {
		try { 
			googleUser = TokenParser.getGoogleUserFromToken(idToken);
		} catch (IOException e) {
			// Could not verify token at Google
			System.out.println("Could not verify token at Google");
		} catch (EmailNotVerifiedException e) {
			// Email is not verified by Google
			System.out.println("Email is not verified by Google");
		} catch (Exception e) {
			// ID token is invalid
			System.out.println("ID token is invalid");
			System.out.println(e.getMessage());
		}
		databaseHandler = new DatabaseHandler();
	}
}
