package com.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Book;

public class DatabaseHandler {
	
	public static List<Book> getAllBooks() {
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
	
	public static boolean addBook(Book book) {
		String sqlStatement = "Insert into Books values (" 
				+ book.getTitle() + ", "
				+ book.getSubtitle() + ", "
				+ book.getCategoryID() + ", "
				+ book.getPublisherID() + ", "
				+ book.getYearFirstPublication() + ", "
				+ book.getISBN() + ", "
				+ book.getPages() + ", "
				+ book.getLanguage() + ", "
				+ book.getImageCover() + ");";
		int rowsAffected = DatabaseConnector.executeNonQuery(sqlStatement);
		return rowsAffected >= 1;
	}
}

