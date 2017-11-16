package nl.sogyo.library.model.logic;

import java.io.IOException;
import java.security.GeneralSecurityException;

import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.model.logic.helper.TokenParser;
import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.oauth.javagooglesignin.exception.EmailNotVerifiedException;
import nl.sogyo.oauth.javagooglesignin.exception.InvalidTokenException;

public abstract class LibraryHelper {

	protected User user;
	protected DatabaseHandler databaseHandler;
	protected static final byte USERTYPE_USER = 1;
	protected static final byte USERTYPE_ADMIN = 2;
	
	public LibraryHelper(String idToken) throws IOException, GeneralSecurityException, EmailNotVerifiedException, InvalidTokenException {
		user = TokenParser.getUserFromToken(idToken);
		databaseHandler = new DatabaseHandler();
	}
}
