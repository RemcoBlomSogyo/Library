package com.persistence;

import java.sql.*;
//import com.microsoft.sqlserver.jdbc.*;
//import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DatabaseConnector {
	private static Connection connection;
	
	private static void connect() {
		try {
//			Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=Library;user=javaLogin;password=javaLogin;integratedSecurity=true;");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
//		} catch (IllegalAccessException ie) {
//			ie.printStackTrace();
//		} catch (InstantiationException je) {
//			je.printStackTrace();
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
	public static void executeNonQuery(String sqlStatement) {
		connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// only for select
	public static ResultSet executeQuery(String sqlStatement) {
		DatabaseConnector.connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			return preparedStatement.executeQuery();
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}
}
