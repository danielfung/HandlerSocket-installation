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

import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.IndexSession;
import com.google.code.hs4j.exception.HandlerSocketException;
import com.google.code.hs4j.impl.HSClientImpl;

public class handlerSocketTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", null, "Primary", columns);	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments1() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", "test", null, columns);	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments2() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession("test", "test", "Primary", null);	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments3() throws IOException, InterruptedException, TimeoutException, HandlerSocketException{
		String[] columns= {"user_name", "user_email", "user_id", "created"};
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		hsclient.openIndexSession(null, "test", "Primary", columns);	
	}
	
	@Test
	public void testInsertFind() throws IOException, InterruptedException, TimeoutException, HandlerSocketException, SQLException {
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		handlerSocket hs = new handlerSocket();
		
		String db = "test";//database
		String table = "test_user";//table
		String[] columns = {"user_name", "user_email", "user_id", "created"};//columns
		String[] test_values = {"john_doe", "john_doe@test.com", "1234567", "created"};//values to insert
		String[] find_values = {"john_doe"};
		String[] find_unexist_values = {"amy"};
		
 		IndexSession indexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		hs.insertData(indexSession, test_values);//insert
		
		//value exists in mysql database
		ResultSet rs = indexSession.find(find_values);
		assertTrue(rs.next());
		assertEquals("equal", "john_doe", rs.getString("user_name"));
		assertEquals("equal", "john_doe@test.com", rs.getString("user_email"));
		assertEquals("equal", "1234567", rs.getString("user_id"));
		
		//values that don't exist in mysql database
		rs = indexSession.find(find_unexist_values);
		assertFalse(rs.next());
		
		hsclient.shutdown();
		hs.clearHashMap();
	}
}
