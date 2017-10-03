package nl.sogyo.library.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;

public class BookTest {
	
	private List<Author> authors = new ArrayList<Author>();
	
	public BookTest() {
		authors.add(new Author("forname", "surname"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyTitleGivesException() {
		new Book("", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyCategoryGivesException() {
		new Book("title", "subtitle", authors, "",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyPublisherGivesException() {
		new Book("title", "subtitle", authors, "category",
				"", "2000", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptySubtitleGivesNoException() {
		try {
			Book book = new Book("title", "", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
			Assert.assertTrue(book.getSubtitle().equals(""));
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty subtitle gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearInFutureGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", Integer.toString(Year.now().getValue() + 1), "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyYearFirstPublicationGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", authors, "category",
				"publisher", "", "9781484226643", "1", "language");
			Assert.assertTrue(book.getYearFirstPublication() == 0);
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty yearFirstPublication gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativeYearGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", "-1", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearIsNotOnlyDigitsGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", "e2r3l9q", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyPagesGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "", "language");
			System.out.println("testPages:" + book.getPages());
			Assert.assertTrue(book.getPages() == 0);
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty pages gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativePagesGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "-1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void pagesIsNotOnlyDigitsGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1f2l8r", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void NotExistingIsbnGivesException() {
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226642", "1", "language");
	}
	
	@Test
	public void emptyLanguageGivesNoException() {
		try {
			Book book = new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "");
			Assert.assertTrue(book.getLanguage().isEmpty());
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty language gives illegalargumentexception");
		}
	}
}
