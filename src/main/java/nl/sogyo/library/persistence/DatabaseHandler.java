package nl.sogyo.library.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.BookPreview;
import nl.sogyo.library.services.rest.libraryapi.json.TestBook;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteBookMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;

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
	private static final String selectCopiesOnId = "Select count(*) as CopiesOfBook from Copies where BookID = ";
	
	public static List<TestBook> getAllBooksTest() {
		String sqlStatement = "Select * from Test;";
		List<TestBook> testBooks = new ArrayList<TestBook>();
		
		try {
			ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
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
	
	public static int insertBook(Book book) {
		
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
				
				+ "Set @idAuthor = (" + selectIdAuthorForename + book.getAuthorForename() + "\' and Surname = \'" + book.getAuthorSurname() + "\') "
				+ "if (@idAuthor is null) "
					+ "Begin "
					+ "Insert into Authors output inserted.ID into @tableAuthor values (\'" + book.getAuthorForename() + "\', \'" + book.getAuthorSurname() + "\') "
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
	
				+ "Insert into Books(Title, Subtitle, CategoryID, PublisherID, YearFirstPublication, ISBN, Pages, Language) output inserted.ID into @tableBook "
				+ "values (\'" + book.getTitle() + "\', \'" + book.getSubtitle() + "\', " + "@idCategory, @idPublisher, \'" 
				+ book.getYearFirstPublication() + "\', \'" + book.getIsbn() + "\', \'" + book.getPages() + "\', \'" + book.getLanguage() + "\') "
				+ "Set @idBook = (Select ID from @tableBook) "
				+ "Insert into BooksAuthors values (@idBook, @idAuthor) "
				+ "Select ID from @tableBook "
				
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
		try {
//			int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
//			System.out.println("insert success");
//			System.out.println("rows affected: " + rowsAffected);
//			//return rowsAffected >= 1;
			
//			ResultSet resultSet = DatabaseConnector.executeInsertForId(sqlStatement);
			
			ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
			if (resultSet.next()) {
				int id = resultSet.getInt("ID");
				System.out.println("ID = " + id);
				return id;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.out.println("sqlexcep");
			return -1;
		}
	}
	
//	public static List<Book> getAllBooks() {
//		String sqlStatement = selectBooksStatement;
//		return selectBooks(sqlStatement);
//	}
	
	public static List<BookPreview> selectBooksOnTitle(String titleInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> selectBooksOnAuthorSingleName(String authorInput) {
		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorInput + "%\' "
				+ "or Surname like \'%" + authorInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> selectBooksOnISBN(String isbnInput) {
		String sqlStatement = selectBooksStatement + "where ISBN = \'" + isbnInput + "\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> selectBooksOnAuthorTotalName(String authorFornameInput, String authorSurnameInput) {
		String sqlStatement = selectBooksStatement + "where Forename like \'%" + authorFornameInput + "%\' "
				+ "and Surname like \'%" + authorSurnameInput + "%\'";
		return selectBooks(sqlStatement);
	}
	
	public static List<BookPreview> selectBooksOnTitleAndAuthorSingleName(String titleInput, String authorInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
				+ "and (Forename like \'%" + authorInput + "%\' "
				+ "or Surname like \'%" + authorInput + "%\')";
		return selectBooks(sqlStatement);
	}

	public static List<BookPreview> selectBooksOnTitleAndAuthorTotalName(String titleInput, String authorForenameInput, String authorSurnameInput) {
		String sqlStatement = selectBooksStatement + "where Title like \'%" + titleInput + "%\' "
				+ "and Forename like \'%" + authorForenameInput + "%\' "
				+ "and Surname like \'%" + authorSurnameInput + "%\'";
		return selectBooks(sqlStatement);
	}

	private static List<BookPreview> selectBooks(String sqlStatement) {
		List<BookPreview> bookPreviews = new ArrayList<BookPreview>();
		try {
			ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
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

	public static BookInfo selectBookOnId(int id) {
//		String bookSqlStatement = 
////				"Set xact_abort on "
////				+ "begin tran "
//				"Select Title, Subtitle, Forename as AuthorForename, "
//				+ "Surname as AuthorSurname, Categories.name as Category, Publishers.name as Publisher, YearFirstPublication, "
//				+ "ISBN, Pages, Language, count(*) as CopiesAvailable from Books "
//				+ "inner join BooksAuthors on Books.ID = BooksAuthors.BookID "
//				+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
//				+ "inner join Categories on Books.CategoryID = Categories.ID "
//				+ "inner join Publishers on Books.PublisherID = Publishers.ID "
//				+ "where BookID = " + id;
//		
//		String copiesSqlStatement = "Select count(*) as CopiesAvailable from Copies where BookID = " + id;
				
//				+ "Select count(*) as CopiesAvailable from Copies where BookID = " + id + " "
//				+ "commit tran";
		
		String bookSqlStatement = 
			"Set xact_abort on "
			+ "begin tran "
					
			+ "Declare @copies int "
			+ "Set @copies = (Select count(*) from Copies where BookID = " + id + ") "
			
			+ "if (@copies = 0) "
				+ "Begin "
				+ "Select Title, Subtitle, Forename as AuthorForename, "
				+ "Surname as AuthorSurname, Categories.name as Category, Publishers.name as Publisher, YearFirstPublication, "
				+ "ISBN, Pages, Language from Books "
				+ "inner join BooksAuthors on Books.ID = BooksAuthors.BookID "
				+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
				+ "inner join Categories on Books.CategoryID = Categories.ID "
				+ "inner join Publishers on Books.PublisherID = Publishers.ID "
				+ "where Books.ID = " + id + " "
				+ "End "
			+ "else "
				+ "Begin "
				+ "Select Title, Subtitle, Forename as AuthorForename, "
				+ "Surname as AuthorSurname, Categories.name as Category, Publishers.name as Publisher, YearFirstPublication, "
				+ "ISBN, Pages, Language, count(*) as CopiesAvailable from Books "
				+ "inner join BooksAuthors on Books.ID = BooksAuthors.BookID "
				+ "inner join Authors on BooksAuthors.AuthorID = Authors.ID "
				+ "inner join Categories on Books.CategoryID = Categories.ID "
				+ "inner join Publishers on Books.PublisherID = Publishers.ID "
				+ "inner join Copies on Books.ID = Copies.BookID "
				+ "where Books.ID = " + id + " "
				+ "group by Title, Subtitle, Forename, Surname, Categories.name, "
				+ "Publishers.name, YearFirstPublication, ISBN, Pages, Language "
				+ "End "
			+ "commit tran";
		
		Book book = new Book(id, "", "", "", "", "", "", (short) 0, "", (short) 0, "");
		int copiesAvailable = 0;
		try {
			ResultSet resultSet = DatabaseConnector.executeQuery(bookSqlStatement);
			while (resultSet.next()) {
				System.out.println("title: " + resultSet.getString("Title"));
				String title = resultSet.getString("Title");
				String subtitle = resultSet.getString("Subtitle");
				String authorForename = resultSet.getString("AuthorForename");
				String authorSurname = resultSet.getString("AuthorSurname");
				String category = resultSet.getString("Category");
				String publisher = resultSet.getString("Publisher");
				short yearFirstPublication = resultSet.getShort("YearFirstPublication");
				String isbn = resultSet.getString("ISBN");
				short pages = resultSet.getShort("Pages");
				String language = resultSet.getString("Language");
				
				book = new Book(id, title, subtitle, authorForename, authorSurname, category, publisher,
						yearFirstPublication, isbn, pages, language);
				
				copiesAvailable = resultSet.getInt("CopiesAvailable");
			}
			
//			resultSet = DatabaseConnector.executeQuery(copiesSqlStatement);
//			while(resultSet.next()) {
//				copiesAvailable = resultSet.getInt("CopiesAvailable");
//			}
			
//			return new BookInfo(book, copiesAvailable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		
		return new BookInfo(book, copiesAvailable);
	}
	
	public static boolean updateBook(Book book) {
		String sqlStatement = 
				"Set xact_abort on "
				+ "begin tran "
				
				+ "Declare @idAuthor int "
				+ "Declare @idCategory int "
				+ "Declare @idPublisher int "
				+ "Declare @tableAuthor table (ID int) "
				+ "Declare @tableCategory table (ID int) "
				+ "Declare @tablePublisher table (ID int) "
				
				+ "Set @idAuthor = (" + selectIdAuthorForename + book.getAuthorForename() + "\' and Surname = \'" + book.getAuthorSurname() + "\') "
				+ "if (@idAuthor is null) "
					+ "Begin "
					+ "Insert into Authors output inserted.ID into @tableAuthor values (\'" + book.getAuthorForename() + "\', \'" + book.getAuthorSurname() + "\') "
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
				
				+ "Update Books Set Title = \'" + book.getTitle() + "\', Subtitle = \'" + book.getSubtitle() + "\', CategoryID = @idCategory, PublisherID = @idPublisher"
				+ ", YearFirstPublication = " + book.getYearFirstPublication() + ", ISBN = \'" + book.getIsbn() + "\', Pages = " + book.getPages() + ", Language = \'" 
				+ book.getLanguage() + "\' where ID = " + book.getId() + " "
				
				+ "Update BooksAuthors Set AuthorID = @idAuthor where BookID = " + book.getId() + " "
				
				+ "commit tran";
		
		try {
			System.out.println(sqlStatement);
			int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
			System.out.println("update success");
			System.out.println("rows affected: " + rowsAffected);
			return rowsAffected >= 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.out.println("sqlexcep");
			return false;
		}
	}
	
	public static AddCopyMessage insertCopy(int bookId) {
//		String insertStatement = "Insert into Copies(BookID) values (" + bookId + ")";
//		String selectStatement = "Select count(*) as CopiesOfBook from Copies where BookID = " + bookId;
		
		String sqlStatement = 
				"Set xact_abort on "
				+ "begin tran "
				+ "Insert into Copies(BookID) values (" + bookId + ") "
				+ "if (@@ROWCOUNT = 1) "
					+ "Begin "
					+ selectCopiesOnId + bookId + " "
					+ "commit tran "
					+ "End "
				+ "else "
					+ "rollback tran";
		int copiesOfBook = 0;
		
		try {
			System.out.println("insertCopy try");
			ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
			while (resultSet.next()) {
				copiesOfBook = resultSet.getInt("CopiesOfBook");
			}
			return new AddCopyMessage(true, copiesOfBook);
		} catch(SQLException e) {
			System.out.println("insertCopy catch");
			System.err.println(e.getMessage());
			return new AddCopyMessage(false, 0);
		}
		
//		try {
//			if (DatabaseConnector.executeNonQuery(insertStatement) == 1) {
//				ResultSet resultSet = DatabaseConnector.executeQuery(selectStatement);
//				while (resultSet.next()) {
//					copiesOfBook = resultSet.getInt("CopiesOfBook");
//				}
//				return new AddCopyMessage(true, copiesOfBook);
//			} else {
//				return new AddCopyMessage(false, 0);
//			}
//		} catch(SQLException e) {
//			return new AddCopyMessage(false, 0);
//		}

	}
	
	public static DeleteCopyMessage deleteCopy(int bookId) {
		
		String sqlStatement = 
				"Set xact_abort on "
				+ "begin tran "
				+ "Delete Top (1) from Copies where BookID = " + bookId + " "
				+ "if (@@ROWCOUNT = 1) "
					+ "Begin "
					+ selectCopiesOnId + bookId + " "
					+ "commit tran "
					+ "End "
				+ "else "
					+ "rollback tran";
		int copiesOfBook = 0;
		
		try {
			System.out.println("deleteCopy try");
			ResultSet resultSet = DatabaseConnector.executeQuery(sqlStatement);
			while (resultSet.next()) {
				copiesOfBook = resultSet.getInt("CopiesOfBook");
			}
			return new DeleteCopyMessage(true, copiesOfBook);
		} catch(SQLException e) {
			System.out.println("deleteCopy catch");
			System.err.println(e.getMessage());
			return new DeleteCopyMessage(false, 0);
		}
	}
	
	public static boolean deleteBookAndCopies(int bookId) {
		String sqlStatement = 
				"Set xact_abort on "
				+ "begin tran "
				+ "Delete from Copies where BookID = " + bookId + " "
				+ "Delete from BooksAuthors where BookID = " + bookId + " "
				+ "Delete from Books where ID = " + bookId + " "
				+ "commit tran";
		
		try {
			System.out.println("try deleteBook");
			int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
			return rowsAffected >= 1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
				
	}
}

