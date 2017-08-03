package com.persistence;

import java.sql.*;

public class DatabaseHandler {
	
	public static void getAllBooks() {
		String query = "Select * from Book";
		Connection connection = DatabaseConnector.connection;
		PreparedStatement preparedStatement = DatabaseConnector.connection.connect();
		
	}
}
