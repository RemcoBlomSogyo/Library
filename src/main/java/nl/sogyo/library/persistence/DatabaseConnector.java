package nl.sogyo.library.persistence;

import java.sql.*;
//import com.microsoft.sqlserver.jdbc.*;
//import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DatabaseConnector {
	private static Connection connection;
	
	private static void connect() {
		try {
//			Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=Library;user=javaLogin;password=javaLogin;integratedSecurity=true;");
			String url = "jdbc:sqlserver://10.10.2.95:1433;databaseName=Library";
			String sqlLogin = "javaLogin";
			String sqlPassword = "javaLogin";
			connection = DriverManager.getConnection(url, sqlLogin, sqlPassword);
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
	public static int executeNonQuery(String sqlStatement) {
		connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			return preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			return -1;
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
