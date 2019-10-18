package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicModel {
	protected Connection connection = null;
	protected PreparedStatement prepStatment = null;
	protected ResultSet results = null;
	
	protected boolean initDB() 
	{
		// attempt to get the Driver
		
		try {
			System.out.println("looking for mySQL database");
			Class.forName("com.mysql.cj.jdbc.Driver");
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("failed to find mySQL database");
			closeDB();
			return false;
		}
	} // END
	
	protected boolean startConnection()
	{
		String url = "jdbc:mysql://localhost:3306/BlogDB";
		String user = "root";
		String password = "mysql";
		
		try { // attempt to connect
			System.out.println("attempting to connect to mySQL database");
			connection = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("failed connect to mySQL database");
			e.printStackTrace();
			closeDB();
			return false;
		}
	} // END
	
	protected void closeDB()
	{
		try 
		{
			if (results != null)
			results.close();
			
			if (prepStatment != null)
				prepStatment.close();
			
			if (connection != null)
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // END
}
