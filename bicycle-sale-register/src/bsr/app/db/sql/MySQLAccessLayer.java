package bsr.app.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccessLayer {
	private Connection con = null;
	private CallableStatement cStmt = null;
	
	public MySQLAccessLayer() throws ClassNotFoundException, SQLException {
		super();
		initiateDbConnection();
	}
	
	public void initiateDbConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/newspaper_delivery_system?useSSL=no";
		
		con = DriverManager.getConnection(url, "root", "<a password>");
	}
}
