package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	Connection com = null;
	public static Connection dbConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection com = DriverManager.getConnection("jdbc:mysql://localhost:3306/quincaillerie","root","");
			return com;
			
		}catch(Exception e) {
			
			
			e.printStackTrace();
			
		}
		
		return null;
		
		
		
	}
	
	

}
