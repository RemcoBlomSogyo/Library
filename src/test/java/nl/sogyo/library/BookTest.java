package nl.sogyo.library;

import java.time.Year;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.command.Book;

public class BookTest {
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyTitleGivesException() {
		new Book("", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyAuthorForenameGivesException() {
		new Book("title", "subtitle", "", "surname", "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyAuthorSurnameGivesException() {
		new Book("title", "subtitle", "forename", "", "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyCategoryGivesException() {
		new Book("title", "subtitle", "forename", "surname", "",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyPublisherGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"", "2000", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptySubtitleGivesNoException() {
		try {
			Book book = new Book("title", "", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "1", "language");
			Assert.assertTrue(book.getSubtitle().equals(""));
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty subtitle gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearInFutureGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", Integer.toString(Year.now().getValue() + 1), "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyYearFirstPublicationGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "", "9781484226643", "1", "language");
			Assert.assertTrue(book.getYearFirstPublication() == 0);
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty yearFirstPublication gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativeYearGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "-1", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearIsNotOnlyDigitsGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "e2r3l9q", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyPagesGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "", "language");
			Assert.assertTrue(book.getPages() == 0);
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty pages gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativePagesGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "-1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void pagesIsNotOnlyDigitsGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "1f2l8r", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void NotExistingIsbnGivesException() {
		new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226642", "1", "language");
	}
	
	@Test
	public void emptyLanguageGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", "forename", "surname", "category",
				"publisher", "2000", "9781484226643", "1", "");
			Assert.assertTrue(book.getLanguage().isEmpty());
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty language gives illegalargumentexception");
		}
	}
}
