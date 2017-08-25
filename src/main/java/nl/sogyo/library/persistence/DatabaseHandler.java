package nl.sogyo.library.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;

public class DatabaseHandler {
	
	private static final String selectBooksStatement = "Select Books.ID, Title, Subtitle, Forename as AuthorForename, "
			+ "Surname as AuthorSurname, Categories.name as Category, "
			+ "Publishers.name as Publisher, YearFirstPublication, ISBN, "
			+ "Pages, Language from Books "
			+ "inner join BookAuthors on Books.ID = BooksAuthors.BookID "
			+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
			+ "inner join Categories on Books.CategoryID = Categories.ID "
			+ "inner join Publishers on Books.PublisherID = Publishers.ID ";
	private static final String selectIdAuthorForename = "Select ID from Authors where Forename = ";
	private static final String selectIdCategory = "Select ID from Categories where Name = ";
	private static final String selectIdPublisher = "Select ID from Publishers where Name = ";
	
	public static List<TestBook> getAllBooksTest() {
		String sqlStatement = "Select * from Test;";
		ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
		
		List<TestBook> testBooks = new ArrayList<TestBook>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("name");
				testBooks.add(new TestBook(id, title));
			}
			return testBooks;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		} finally {
			DatabaseConnector.disconnect();
		}
	}
	
	public static boolean addBook(Book book) {
		try {
			String sqlStatement = "begin tran"
			+ "if ((" + selectIdAuthorForename + book.getAuthor().getForename() + " and Surname = " + book.getAuthor().getSurname() + ") = null)"
				+ "Insert into Authors values (" + book.getAuthor().getForename() + ", " + book.getAuthor().getSurname() + ");"

			+ "if ((" + selectIdCategory + book.getCategory() + ") = null)"
				+ "Insert into Categories values (" + book.getCategory() + ");"

			+ "if ((" + selectIdPublisher + book.getPublisher() + ") = null)"
				+ "Insert into Publishers values (" + book.getPublisher() + ");"

			+ "Insert into Books values (" + book.getTitle() + ", " + book.getSubtitle() + ", " + book.getCategory() + ")"
			+ "commit tran";
			
//			String sqlStatement = "Insert into Books values (" 
//					+ book.getTitle() + ", "
//					+ book.getSubtitle() + ", ("
//					+ selectIdAuthorForename + book.getAuthor().getForename() 
//					+ " and Surname = " + book.getAuthor().getSurname() + "), ("
//					+ selectIdCategory + book.getCategory() + "), ("
//					+ selectIdPublisher + book.getPublisher() + "), "
//					+ book.getYearFirstPublication() + ", "
//					+ book.getISBN() + ", "
//					+ book.getPages() + ", "
//					+ book.getLanguage() + ", "
//					/*+ book.getImageCover()*/ + ");";
			int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
			return rowsAffected >= 1;
		} catch (SQLException e) {
			//return addMissingRows(book);
		}
	}
	
//	private static boolean addMissingRows(Book book) {
//		if (DatabaseConnector.executeQuery(selectIdAuthorForename + book.getAuthor().getForename() 
//				+ " and Surname = " + book.getAuthor().getSurname()) == null) {
//			try {
//				DatabaseConnector.executeNonQuery("Insert into Authors values (" 
//					+ book.getAuthor().getForename() + ", " + book.getAuthor().getSurname() + ")");
//			} catch (SQLException e) {
//				// send error to user
//			}
//		}
//		
//		if (DatabaseConnector.executeQuery(selectIdCategory + book.getCategory()) == null) {
//			try {
//				DatabaseConnector.executeNonQuery("Insert into Categories values (" 
//					+ book.getCategory() + ")");
//			} catch (SQLException e) {
//				// send error
//			}
//		}
//		
//		if (DatabaseConnector.executeQuery(selectIdPublisher + book.getPublisher()) == null) {
//			try {
//				DatabaseConnector.executeNonQuery("Insert into Publishers values (" 
//					+ book.getPublisher() + ")");
//			} catch (SQLException e) {
//				// send error
//			}
//		}
//	}
	
//	public static List<Book> getAllBooks() {
//		String sqlStatement = selectBooksStatement;
//		return selectBooks(sqlStatement);
//	}
//	
//	public static List<Book> getBooksOnTitle(String titleInput) {
//		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\'";
//		return selectBooks(sqlStatement);
//	}
//	
//	public static List<Book> getBooksOnAuthorSingleName(String authorInput) {
//		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorInput + "%\' "
//				+ "or Surname like \'%" + authorInput + "%\'";
//		return selectBooks(sqlStatement);
//	}
//	
//	public static List<Book> getBooksOnISBN(ISBN isbn) {
//		String sqlStatement = selectBooksStatement + "where ISBN = \'" + isbn.getISBN() + "\'";
//		return selectBooks(sqlStatement);
//	}
//	
//	public static List<Book> getBooksOnAuthorTotalName(String authorFornameInput, String authorSurnameInput) {
//		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorFornameInput + "%\' "
//				+ "and Surname like \'%" + authorSurnameInput + "%\'";
//		return selectBooks(sqlStatement);
//	}
//	
//	public static List<Book> getBooksOnTitleAndAuthorSingleName(String titleInput, String authorInput) {
//		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
//				+ "and (Forename like \'%" + authorInput + "%\' "
//				+ "or Surname like \'%" + authorInput + "%\')";
//		return selectBooks(sqlStatement);
//	}
//
//	public static List<Book> getBooksOnTitleAndAuthorTotalName(String titleInput, String authorForenameInput, String authorSurnameInput) {
//		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
//				+ "and Forename like \'%" + authorForenameInput + "%\' "
//				+ "and Surname like \'%" + authorSurnameInput + "%\'";
//		return selectBooks(sqlStatement);
//	}
//
//	private static List<Book> selectBooks(String sqlStatement) {
//		ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
//		List<Book> books = new ArrayList<Book>();
//		try {
//			while (resultSet.next()) {
//				int id = resultSet.getInt("ID");
//				String title = resultSet.getString("Title");
//				String subtitle = resultSet.getString("Subtitle");
//				Author author = new Author(resultSet.getString("AuthorForename"), resultSet.getString("AuthorSurname"));
//				String category = resultSet.getString("Category");
//				String publisher = resultSet.getString("Publisher");
//				short yearFirstPublication = resultSet.getShort("YearFirstPublication");
//				ISBN isbn = new ISBN(resultSet.getString("ISBN"));
//				short pages = resultSet.getShort("Pages");
//				String language = resultSet.getString("Language");
//				
//				books.add(new Book(id, title, subtitle, author, category, publisher, yearFirstPublication, isbn, pages, language));
//			}
//			return books;
//		} catch (SQLException se) {
//			se.printStackTrace();
//			return new ArrayList<Book>();
//		} finally {
//			DatabaseConnector.disconnect();
//		}
//	}

}

