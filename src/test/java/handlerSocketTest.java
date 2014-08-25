package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import main.java.handlerSocket;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.hs4j.FindOperator;
import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.IndexSession;
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
		String[] columns= {"user_name", "user_email", "user_id", "created"};
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
	 * Insert,Delete,Update,Find data in mysql table.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 * @throws SQLException
	 */
	/*
	@Test
	public void testInsertFindDelete() throws IOException, InterruptedException, TimeoutException, HandlerSocketException, SQLException {
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		handlerSocket hs = new handlerSocket();
		
		String db = "test";//database
		String table = "test_user";//table
		String[] columns = {"user_name", "user_email", "user_id", "created"};//columns
		String[] test_valueInsert = {"john_doe", "john_doe@test.com", "1234567", "created"};//values to insert
		String[] test_valueUpdate = {"john_doe", "john_doe@updateTest.com", "1234567", "created"};//values to update
		String[] find_values = {"john_doe"};//values to find
		String[] find_unexist_values = {"amy"};//value(does not exist) to find
		String[] test_valueDelete = {"john_doe"};//value to delete from mysql
		
 		IndexSession indexSession = hsclient.openIndexSession(db, table, "Primary", columns);
 		
 		//CRUD operations:	
 		//insert
		hs.insertData(indexSession, test_valueInsert);		
		//check if value exists in mysql database(read)
		ResultSet rs = indexSession.find(find_values);
		assertTrue(rs.next());
		assertEquals("equal", "john_doe", rs.getString("user_name"));
		assertEquals("equal", "john_doe@test.com", rs.getString("user_email"));
		assertEquals("equal", 1234567, rs.getInt("user_id"));
		
		//update value(john_doe) with the new values in table(test)
		hs.updateData(indexSession, find_values, test_valueUpdate, FindOperator.EQ);
		
		rs = indexSession.find(find_values);
		assertTrue(rs.next());
		assertEquals("equal", "john_doe", rs.getString("user_name"));
		assertEquals("equal", "john_doe@updateTest.com", rs.getString("user_email"));
		assertEquals("equal", 1234567, rs.getInt("user_id"));
		
		//values that don't exist in mysql table(test)
		rs = indexSession.find(find_unexist_values);
		assertFalse(rs.next());
		
		//delete the test_valueDelete from table(test)
		hs.deleteData(indexSession, test_valueDelete, FindOperator.EQ);	
		rs = indexSession.find(test_valueDelete);
		assertFalse(rs.next());
		
		hsclient.shutdown();
		hs.clearHashMap();
		
	}
	*/
}
