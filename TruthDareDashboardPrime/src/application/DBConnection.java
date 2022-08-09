package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DBConnection {
	
	private static String HOST= "127.0.0.1";
	private static int PORT = 3306; 
	private static String DB_NAME = "truthordaredb";
	private static String USERNAME = "root";
	private static String PASSWORD = "";
	private static Connection conn;
	
	public DBConnection(String Host) {
		this.HOST = HOST;
	}

	/*
	 * public static void main(String[] args) { getConnection(); }
	 */
	public static Connection getConnection()  {
		try {
			conn = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME),USERNAME,PASSWORD);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}
}
