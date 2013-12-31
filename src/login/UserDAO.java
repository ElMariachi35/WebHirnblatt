package login;

import java.text.*;
import java.util.*;
import java.sql.*;
import login.ConnectionManager;

public class UserDAO {

	static Connection currentCon = null;
	static ResultSet rs = null;
		
	public static UserBean getUserById(String id){
		UserBean u = new UserBean();
		Statement stmt = null;
		String searchQuery = "select * from logintable where id='" + id+"'";
		System.out.println(">> Searching for userId '"+id +" in database...");
		
		try {
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			// if user does not exist set the isValid variable to false
			if (more) {
				System.out.println(">> User '"+id+"' with username '"+rs.getString("username")+"' was found in database");
				u.setValid(true);
				u.setId(id);
				u.setUserName(rs.getString("username"));
			}else{
				System.out.println(">> User '"+id+"' was not found in database");
				u.setValid(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}
	
		return u;
	}
	
	
	public static UserBean register(UserBean bean){
		Statement stmt=null;
		String username = bean.getUsername();
		String password = bean.getPassword();
		String email = bean.getEmail();
		String id = bean.getId();
		
		String searchQuery = "select * from logintable where username='"+username+"'";


		try {
			// connect to DB
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			// if user does not exist set the isValid variable to false
			if (more) {
				System.out
						.println(">> Registration failed, the username '"+username + "' exists already");
				bean.setValid(false);
			}
			// if user exists set the isValid variable to true
			else if (!more) {
				String insertStmt="INSERT INTO logintable (username, password, email, id) VALUES ('"+username+"','"+password+"','"+email+"','"+id+"');";
				stmt.execute(insertStmt);
				System.out.println(">> Registration successful,  the user '"+username+"' can now log in");
				bean.setValid(true);
			}
		} catch (Exception ex) {
			System.out.println(">> Registration failed: An Exception has occurred! "
					+ ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}

		return bean;

		
	}
	
	public static UserBean login(UserBean bean) {

		// preparing some objects for connection
		Statement stmt = null;
		String username = bean.getUsername();
		String password = bean.getPassword();
		String searchQuery = "select * from logintable where username='" + username
				+ "' AND password='" + password + "'";

		// "System.out.println" prints in the console; Normally used to trace
		// the process


		try {
			// connect to DB
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out
						.println(">> Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			}
			// if user exists set the isValid variable to true
			else if (more) {
				String email = rs.getString("email");
				String id = rs.getString("id");
				bean.setEmail(email);
				bean.setId(id);
				bean.setValid(true);
			}
		} catch (Exception ex) {
			System.out.println(">> Log In failed: An Exception has occurred! "
					+ ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}

		return bean;
	}
}