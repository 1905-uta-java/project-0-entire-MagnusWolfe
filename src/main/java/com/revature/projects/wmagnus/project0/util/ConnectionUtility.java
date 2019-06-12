package com.revature.projects.wmagnus.project0.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtility {

	private static Connection connection;
	
	public static Connection getConectionFromFile() throws SQLException, IOException {
		Properties properties = new Properties();
		InputStream in = new FileInputStream("connection.properties");
		properties.load(in);
		
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		if (connection == null || connection.isClosed()) connection = DriverManager.getConnection(url, username, password);
		return connection;
		
	}
	
}
