package com.persistence;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

public class DatabaseConnector {
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=Library;user=javaLogin;password=javaLogin;integratedSecurity=true;");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	public static void disconnect() {
		try {
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// for insert, update and delete
	public static void executeNonQuery(PreparedStatement preparedStatement) {
		try {
			preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// only for select
	public static ResultSet executeQuery(PreparedStatement preparedStatement) {
		try {
			return preparedStatement.executeQuery();
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}
	
	// for insert, update and delete
	public static void executeNonQuery(String sqlStatement) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// only for select
	public static ResultSet executeQuery(String sqlStatement) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			return preparedStatement.executeQuery();
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}
	
//	public static void main (String[] args) throws SQLException {
//		DbConnection.connect();
//		String query = "Select * from Book";
//		PreparedStatement preparedStatement = connection.prepareStatement(query);
//		ResultSet resultSet = executeQuery(preparedStatement);
//		
//		while (resultSet.next()) {
//			int id = resultSet.getInt("id");
//			String name = resultSet.getString("name");
//			System.out.println(id + " - " + name);
//		}
//	}
}
