package nl.sogyo.library.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;

public class DatabaseHandler {
	
//	private static final String selectBooksStatement = "Select Books.ID, Title, Subtitle, Forename as AuthorForename, "
//			+ "Surname as AuthorSurname, Categories.name as Category, "
//			+ "Publishers.name as Publisher, YearFirstPublication, ISBN, "
//			+ "Pages, Language from Books "
//			+ "inner join BookAuthors on Books.ID = BooksAuthors.BookID "
//			+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
//			+ "inner join Categories on Books.CategoryID = Categories.ID "
//			+ "inner join Publishers on Books.PublisherID = Publishers.ID ";
	private static final String selectBooksStatement = "Select Books.ID as ID, Title, Forename as AuthorForename, "
			+ "Surname as AuthorSurname, Categories.name as Category, ISBN from Books "
			+ "inner join BooksAuthors on Books.ID = BooksAuthors.BookID "
			+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
			+ "inner join Categories on Books.CategoryID = Categories.ID ";
	private static final String selectIdAuthorForename = "Select ID from Authors where Forename = \'";
	private static final String selectIdCategory = "Select ID from Categories where Name = \'";
	private static final String selectIdPublisher = "Select ID from Publishers where Name = \'";
	
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
			String sqlStatement = 
					"Set xact_abort on "
					+ "begin tran "
					
					+ "Declare @idAuthor int "
					+ "Declare @idCategory int "
					+ "Declare @idPublisher int "
					+ "Declare @idBook int "
					+ "Declare @tableAuthor table (ID int) "
					+ "Declare @tableCategory table (ID int) "
					+ "Declare @tablePublisher table (ID int) "
					+ "Declare @tableBook table (ID int) "
					
					+ "Set @idAuthor = (" + selectIdAuthorForename + book.getAuthor().getForename() + "\' and Surname = \'" + book.getAuthor().getSurname() + "\') "
					+ "if (@idAuthor is null) "
						+ "Begin "
						+ "Insert into Authors output inserted.ID into @tableAuthor values (\'" + book.getAuthor().getForename() + "\', \'" + book.getAuthor().getSurname() + "\') "
						+ "Set @idAuthor = (Select ID from @tableAuthor); "
						+ "End "
		
					+ "Set @idCategory = (" + selectIdCategory + book.getCategory() + "\')"
					+ "if (@idCategory is null) "
						+ "Begin "
						+ "Insert into Categories(Name) output inserted.ID into @tableCategory values (\'" + book.getCategory() + "\') "
						+ "Set @idCategory = (Select ID from @tableCategory); "
						+ "End "
		
					+ "Set @idPublisher = (" + selectIdPublisher + book.getPublisher() + "\')"
					+ "if (@idPublisher is null) "
						+ "Begin "
						+ "Insert into Publishers output inserted.ID into @tablePublisher values (\'" + book.getPublisher() + "\');"
						+ "Set @idPublisher = (Select ID from @tablePublisher); "
						+ "End "
		
					+ "Insert into Books(Title, Subtitle, CategoryID, PublisherID, YearFirstPublication, ISBN, Pages, Language) output inserted.ID into @tableBook values (\'" + book.getTitle() + "\', \'" + book.getSubtitle() + "\', " + "@idCategory, @idPublisher, \'" 
					+ book.getYearFirstPublication() + "\', \'" + book.getISBN() + "\', \'" + book.getPages() + "\', \'" + book.getLanguage() + "\') "
					+ "Set @idBook = (Select ID from @tableBook) "
					+ "Insert into BooksAuthors values (@idBook, @idAuthor) "
					
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
			System.out.println("insert success");
			System.out.println("rows affected: " + rowsAffected);
			return rowsAffected >= 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.out.println("sqlexcep");
			return false;
		}
	}
	
//	public static List<Book> getAllBooks() {
//		String sqlStatement = selectBooksStatement;
//		return selectBooks(sqlStatement);
//	}
	
	public static List<BookPreview> getBooksOnTitle(String titleInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> getBooksOnAuthorSingleName(String authorInput) {
		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorInput + "%\' "
				+ "or Surname like \'%" + authorInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> getBooksOnISBN(String isbnInput) {
		String sqlStatement = selectBooksStatement + "where ISBN = \'" + isbnInput + "\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> getBooksOnAuthorTotalName(String authorFornameInput, String authorSurnameInput) {
		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorFornameInput + "%\' "
				+ "and Surname like \'%" + authorSurnameInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> getBooksOnTitleAndAuthorSingleName(String titleInput, String authorInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
				+ "and (Forename like \'%" + authorInput + "%\' "
				+ "or Surname like \'%" + authorInput + "%\')";
		return selectBooks(sqlStatement);
	}

	public static List<BookPreview> getBooksOnTitleAndAuthorTotalName(String titleInput, String authorForenameInput, String authorSurnameInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
				+ "and Forename like \'%" + authorForenameInput + "%\' "
				+ "and Surname like \'%" + authorSurnameInput + "%\'";
		return selectBooks(sqlStatement);
	}

	private static List<BookPreview> selectBooks(String sqlStatement) {
		ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
		List<BookPreview> bookPreviews = new ArrayList<BookPreview>();
		try {
			while (resultSet.next()) {
//				int id = resultSet.getInt("ID");
//				String title = resultSet.getString("Title");
//				String subtitle = resultSet.getString("Subtitle");
//				String authorForename = resultSet.getString("AuthorForename");
//				String authorSurname = resultSet.getString("AuthorSurname");
//				String category = resultSet.getString("Category");
//				String publisher = resultSet.getString("Publisher");
//				short yearFirstPublication = resultSet.getShort("YearFirstPublication");
//				String isbn = resultSet.getString("ISBN");
//				short pages = resultSet.getShort("Pages");
//				String language = resultSet.getString("Language");
//				
//				books.add(new Book(title, subtitle, authorForename, authorSurname, category, publisher, yearFirstPublication, isbn, pages, language));
				int id = resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("AuthorForename") + " " + resultSet.getString("AuthorSurname");
				String category = resultSet.getString("Category");
				String isbn = resultSet.getString("ISBN");
				bookPreviews.add(new BookPreview(id, title, author, category, isbn));
				
			}
			return bookPreviews;
		} catch (SQLException se) {
			se.printStackTrace();
			return new ArrayList<BookPreview>();
		} finally {
			DatabaseConnector.disconnect();
		}
	}

}

