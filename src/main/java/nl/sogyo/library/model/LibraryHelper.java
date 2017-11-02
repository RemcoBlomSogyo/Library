package nl.sogyo.library.model;

import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.library.model.helper.TokenParser;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.oauth.javagooglesignin.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.GoogleUser;
import nl.sogyo.oauth.javagooglesignin.InvalidTokenException;

public abstract class LibraryHelper {

	protected GoogleUser googleUser;
	protected DatabaseHandler databaseHandler;
	protected static final int USERTYPE_USER = 1;
	protected static final int USERTYPE_ADMIN = 2;
	
	public LibraryHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		googleUser = TokenParser.getGoogleUserFromToken(idToken);
		databaseHandler = new DatabaseHandler();
	}
}
