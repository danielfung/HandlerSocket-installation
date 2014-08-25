package main.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.exception.HandlerSocketException;
import com.google.code.hs4j.impl.HSClientImpl;
import com.google.code.hs4j.IndexSession;

public class handlerSocketMain {

	public static void main(String [] args) throws IOException, InterruptedException, TimeoutException, HandlerSocketException, SQLException{
		//new client, 9999 is read/write, 9998 read only, 100 connection pools
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		handlerSocket hs = new handlerSocket();
		
		String db = "test";//database
		String table = "test_user";//table
		String[] columns = {"user_name", "user_email", "user_id", "created"};//columns
		IndexSession writeIndexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		IndexSession readIndexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		IndexSession deleteIndexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		IndexSession updateIndexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		
		String[] test_values = {"john_doe", "john_doe@test.com", "1234567", "created"};
		String[] test_valueFind = {"john_doe"};
		hs.insertData(writeIndexSession, test_values);//insert into db-test, test_user-test_user;
		hs.findData(readIndexSession, columns, test_valueFind);
		
		
		hsclient.shutdown();
	}
	
}
