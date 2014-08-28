package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlQuery {
	protected static String hostName;
	protected static String userName;
	protected static String password;
	protected static String databaseName;	
	/*
	 * Take a query string and execute it if the database the user wants to create a table in exists.
	 * 
	 * @params query the query string to pass to mysql.
	 * @params databaseExist boolean value to see if database exists.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static void createTable(String query, boolean databaseExist) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(query != null && databaseExist != false){//db exists - create table
			String url = hostName+"/"+databaseName;
			Connection conn = getConnection(url, userName, password);//create connection to mysql
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);//execute query
			stmt.close();//close statement
			conn.close();//close connection
		}
		else if(query != null && databaseExist == false){//db does not exist
			System.out.println("Database: "+databaseName+" does not exist");
		}
	}
	
	/*
	 * Delete all data in a table(tableName).
	 * 
	 * @params tableName the name of a table in mysql.
	 * @throws Exception
	 * @throws SQLException
	 */
	private static void truncateTable(String tableName) throws Exception, SQLException {
		String url = hostName+"/"+databaseName;
		Connection conn = getConnection(url, userName, password);//create connection to mysql
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("truncate table "+tableName);//execute query
		stmt.close();//close statement
		conn.close();//close connection
	}
	
	/*
	 * Perform a query to check if database exists in mysql.
	 * 
	 * @params hostName the mysql hostname.
	 * @params dbName the database you want to use in mysql.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @returns a boolean value, true if database exists and false if database doesn't exist.
	 */
	private static boolean queryCheckDatabase(String hostName, String dbName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		boolean exists = false;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostName, userName, dbName);
		ResultSet resultSet = conn.getMetaData().getCatalogs();//getting the database names
		while(resultSet.next()){
			String databaseName = resultSet.getString(1);
			if(databaseName.equals(dbName)){
				exists = true;
			}
		}
		resultSet.close();
		conn.close();
		return exists;
	}
	
	/*
	 * Establish a connection with mysql using hostName, user, password.
	 * 
	 * @params url the hostName\databaseName.
	 * @params user your username to log into mysql.
	 * @params password your password assoicate with your username.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @returns a connection is establish with mysql, if hostName/user/password are correct. 
	 */
	protected static Connection getConnection(String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+url, user, password);
		return conn;
	}
	
	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		userName = args[0];//username to login mysql.
		password = args[1];//password associate with username to login to mysql.
		hostName = args[2];//mysql hostname.
		databaseName = args[3];//database you wish to access.
		String query = args[4];//the query string you want to execute.
		
		boolean dbExist = queryCheckDatabase(hostName, databaseName);
		
		//example query string:
		//CREATE TABLE user (user_id INT UNSIGNED PRIMARY KEY, 
		//					 user_name VARCHAR(50), 
		//                   user_email VARCHAR(255), 
		//                   created DATETIME) ENGINE=InnoDB;
		
		createTable(query, dbExist);
	}
}
