package com.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Book;

public class DatabaseHandler {
	
	public static List<Book> getAllBooks() {
		String query = "Select * from Book";
		ResultSet resultSet = DatabaseConnector.executeQuery(query);
		
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
}
