package nl.sogyo.library.model.query;

import java.util.List;

import nl.sogyo.library.persistence.DatabaseHandler;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;

public class QueryHelper {
	
	public static List<TestBook> getAllBooks() {
		return DatabaseHandler.getAllBooksTest();
	}
}
