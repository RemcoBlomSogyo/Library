package com.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Book;
import com.model.ISBN;
import com.model.Author;

public class DatabaseHandler {
	
	private static final String selectBooksStatement = "Select Books.ID, Title, Subtitle, Forename as AuthorForename, "
			+ "Surname as AuthorSurname, Categories.name as Category, "
			+ "Publishers.name as Publisher, YearFirstPublication, ISBN, "
			+ "Pages, Language from Books "
			+ "inner join BookAuthors on Books.ID = BooksAuthors.BookID "
			+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
			+ "inner join Categories on Books.CategoryID = Categories.ID "
			+ "inner join Publishers on Books.PublisherID = Publishers.ID ";
	
	public static List<Book> getAllBooksTest() {
		String sqlStatement = "Select * from Test;";
		ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
		
		List<Book> books = new ArrayList<Book>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("name");
				books.add(new Book(id, title));
			}
			return books;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		} finally {
			DatabaseConnector.disconnect();
		}
	}
	
//	public static boolean addBook(Book book) {
//		String sqlStatement = "Insert into Books values (" 
//				+ book.getTitle() + ", "
//				+ book.getSubtitle() + ", "
//				+ book.getCategoryID() + ", "
//				+ book.getPublisherID() + ", "
//				+ book.getYearFirstPublication() + ", "
//				+ book.getISBN() + ", "
//				+ book.getPages() + ", "
//				+ book.getLanguage() + ", "
//				/*+ book.getImageCover()*/ + ");";
//		int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
//		return rowsAffected >= 1;
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

