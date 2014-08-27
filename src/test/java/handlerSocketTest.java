package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import main.java.handlerSocket;

import org.junit.Test;

import com.google.code.hs4j.FindOperator;
import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.exception.HandlerSocketException;
import com.google.code.hs4j.impl.HSClientImpl;

public class handlerSocketTest {
	
	/*
	 * Testing for illegal argument exception, if one of the parameters(table = null) in openIndexSession is null.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", null, "Primary", columns);	
	}
	
	/*
	 * Testing for illegal argument exception, if one of the parameters(indexName = null) in openIndexSession is null.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments1() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", "test", null, columns);	
	}
	
	/*
	 * Testing for illegal argument exception, if one of the parameters(columnName = null) in openIndexSession is null.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments2() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		//String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", "test", "Primary", null);	
	}
	
	/*
	 * Testing for illegal argument exception, if one of the parameters(database = null) in openIndexSession is null.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments3() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession(null, "test", "Primary", columns);	
	}
	
	/*
	 * CRUD Operations using handlersocket.
	 * Insert,Find,Update,Delete data in mysql table.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 * @throws SQLException
	 */
	@Test
	public void testInsertFindUpdateDelete() throws IOException, InterruptedException, TimeoutException, HandlerSocketException, SQLException {
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		handlerSocket hs = new handlerSocket();
		
		int indexId = 0;
		String db = "test";//database
		String table = "test_user";//table
		String[] columns = { "user_name", "user_email", "user_id"};//columns
		
		String[] test_valueInsert = { "john_doe", "john_doe@test.com", "1234567" };//values to insert
		String[] test_valueUpdate = { "john_doe", "john_doe@updateTest.com", "1234567" };//values to update
		String[] find_valuesPrimKey = {"1234567"};//values to find
		String[] find_unexist_values = {"amy"};//value(does not exist) to find
		String[] test_valueDeletePrimKey = {"1234567"};//value to delete from mysql
		
		assertTrue(hsclient.openIndex(indexId, db, table, "PRIMARY", columns));
		
 		//CRUD operations:	
 		//insert
		hs.insertData(hsclient, indexId, test_valueInsert);
		//check if value exists in mysql database(read)
		ResultSet rs = hsclient.find(indexId, find_valuesPrimKey);	
		//ResultSet rs = hsclient.find(indexId, find_values, FindOperator.EQ, 1, 0);
		while(rs.next()){
			assertEquals("equal", "john_doe", rs.getString("user_name"));
			assertEquals("equal", "john_doe@test.com", rs.getString("user_email"));
			assertEquals("equal", 1234567, rs.getInt("user_id"));
		}
		//update value(1234567) with the new values in table(test)
		hs.updateData(hsclient, indexId, find_valuesPrimKey, test_valueUpdate, FindOperator.EQ);
	
		rs = hsclient.find(indexId, find_valuesPrimKey);
		while(rs.next()){
			assertEquals("equal", "john_doe", rs.getString("user_name"));
			assertEquals("equal", "john_doe@updateTest.com", rs.getString("user_email"));
			assertEquals("equal", 1234567, rs.getInt("user_id"));
		}
		//values that don't exist in mysql table(test)
		rs = hsclient.find(indexId, find_unexist_values);
		assertFalse(rs.next());
		
		//delete the test_valueDelete(1234567) from table(test)
		hs.deleteData(hsclient, indexId, test_valueDeletePrimKey, FindOperator.EQ);	
		rs = hsclient.find(indexId, test_valueDeletePrimKey);
		assertFalse(rs.next());
		
		hsclient.shutdown();
		hs.clearHashMap();	
	}
}
