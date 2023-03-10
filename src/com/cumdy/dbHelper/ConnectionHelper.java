package com.cumdy.dbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
	private static ConnectionHelper instance;
	private static Connection conn;
	private String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private String directory = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
	private String dbName = "/business.accdb;";

	// The class cannot be object build by new Keyword because access modifier
	// is 'private'
	private ConnectionHelper() {
		instance = this;
		try {
			Class.forName(driver).newInstance();// Load Database Driver
			System.out.println("Driver load successful");
			conn = getConnection();// method call to 'getConnection()' inside in
									// class
			System.out.println("Database Connection successful");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ConnectionHelper getInstance() {
		if (instance == null) {
			new ConnectionHelper();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		String currentPath = System.getProperty("user.dir");
		// currentPath = currentPath.substring(0,currentPath.lastIndexOf("\\"));
		// JOptionPane.showMessageDialog(null, currentPath + dbName);
		System.out.println(currentPath);
		if (conn == null) {
			conn = DriverManager
					.getConnection(directory + currentPath + dbName);
			return conn;
		}
		return conn;
	}

	public void closeConnection() throws SQLException {
		if (conn != null)
			conn.close();
	}
}
