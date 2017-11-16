package nl.sogyo.library.model.unittest;

import org.junit.Test;

import nl.sogyo.library.model.entity.Author;

public class AuthorTest {
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyForenameGivesException() {
		new Author("", "surname");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptySurnameGivesException() {
		new Author("forname", "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyNamesGivesException() {
		new Author("", "");
	}
}
