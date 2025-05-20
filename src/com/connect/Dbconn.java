package com.connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	static Connection con;
	public static Connection getConectionn()

	{
		
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tomato_pomace", "root", "root");
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return con;
	}

}
