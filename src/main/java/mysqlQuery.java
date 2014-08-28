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
	
	private static void createTable(String query, boolean databaseExist) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(query != null && databaseExist != false){//db exists - create table
			String url = hostName+"/"+databaseName;
			Connection conn = getConnection(url, userName, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		}
		else if(query != null && databaseExist == false){//db does not exist
			System.out.println("Database: "+databaseName+" does not exist");
		}
	}
	
	private static void truncateTable(String dbName) throws Exception, SQLException {
		String url = hostName+"/"+databaseName;
		Connection conn = getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("truncate table "+dbName);
		stmt.close();
		conn.close();
	}
	
	private static boolean queryCheckDatabase(String hostName, String dbName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		boolean exists = false;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostName, userName, dbName);
		ResultSet resultSet = conn.getMetaData().getCatalogs();
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
	
	protected static Connection getConnection(String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+url, user, password);
		return conn;
	}
	
	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		userName = args[0];
		password = args[1];
		hostName = args[2];
		databaseName = args[3];
		String query = args[4];
		
		boolean dbExist = queryCheckDatabase(hostName, databaseName);
		createTable(query, dbExist);
	}
}
