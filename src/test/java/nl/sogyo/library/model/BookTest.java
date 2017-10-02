package nl.sogyo.library.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;

public class BookTest {
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyTitleGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyAuthorForenameGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyAuthorSurnameGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", ""));
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyCategoryGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "",
				"publisher", "2000", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyPublisherGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"", "2000", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptySubtitleGivesNoException() {
		try {
			List<Author> authors = new ArrayList<Author>();
			authors.add(new Author("forname", "surname"));
			Book book = new Book("title", "", authors, "category",
				"publisher", "2000", "9781484226643", "1", "language");
			Assert.assertTrue(book.getSubtitle().equals(""));
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty subtitle gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearInFutureGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", Integer.toString(Year.now().getValue() + 1), "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyYearFirstPublicationGivesNoException() {
		try {
			List<Author> authors = new ArrayList<Author>();
			authors.add(new Author("forname", "surname"));
			Book book = new Book("title", "subtitle", authors, "category",
				"publisher", "", "9781484226643", "1", "language");
			Assert.assertTrue(book.getYearFirstPublication() == 0);
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty yearFirstPublication gives illegalargumentexception");
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void negativeYearGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "-1", "9781484226643", "1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void yearIsNotOnlyDigitsGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "e2r3l9q", "9781484226643", "1", "language");
	}
	
	@Test
	public void emptyPagesGivesNoException() {
		try {
			List<Author> authors = new ArrayList<Author>();
			authors.add(new Author("forname", "surname"));
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
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "-1", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void pagesIsNotOnlyDigitsGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1f2l8r", "language");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void NotExistingIsbnGivesException() {
		List<Author> authors = new ArrayList<Author>();
		authors.add(new Author("forname", "surname"));
		new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226642", "1", "language");
	}
	
	@Test
	public void emptyLanguageGivesNoException() {
		try {
			List<Author> authors = new ArrayList<Author>();
			authors.add(new Author("forname", "surname"));
			Book book = new Book("title", "subtitle", authors, "category",
				"publisher", "2000", "9781484226643", "1", "");
			Assert.assertTrue(book.getLanguage().isEmpty());
		} catch (IllegalArgumentException e) {
			Assert.fail("Empty language gives illegalargumentexception");
		}
	}
}
