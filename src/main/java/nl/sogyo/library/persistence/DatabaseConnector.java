package nl.sogyo.library.persistence;

import java.sql.*;

public class DatabaseConnector {
	private static Connection connection;
	
	private static void connect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://10.10.2.95:1433;databaseName=Library";
			String sqlLogin = "javaLogin";
			String sqlPassword = "javaLogin";
			connection = DriverManager.getConnection(url, sqlLogin, sqlPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public static int executeNonQuery(String sqlStatement) throws SQLException {
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
		return preparedStatement.executeUpdate();
	}
	
	// for insert and return created id
	public static ResultSet executeInsertForId(String sqlStatement) throws SQLException {
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		return preparedStatement.getGeneratedKeys();
	}
	
	// only for select
	public static ResultSet executeQuery(String sqlStatement) throws SQLException {
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE, 
				  ResultSet.CONCUR_READ_ONLY);
		return preparedStatement.executeQuery();
	}
}
