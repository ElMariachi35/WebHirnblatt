package login;

import java.sql.*;
import java.util.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class ConnectionManager {
	static Connection conn;
	static String url;


	public static Connection getConnection() {
		
		
		String url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com:3306/";
		String dbName = "db_hirnblatt";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "elm35";
		String password = "dbhirnblatt";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName,
					userName, password);
		
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
