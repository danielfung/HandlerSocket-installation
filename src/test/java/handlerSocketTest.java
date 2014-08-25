package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testInsert() throws IOException, InterruptedException, TimeoutException, HandlerSocketException {
		HSClient hsclient = new HSClientImpl(new InetSocketAddress(9999),100);
		handlerSocket hs = new handlerSocket();
		
		String db = "test";//database
		String table = "test_user";//table
		String[] columns = {"user_name", "user_email", "user_id", "created"};//columns
		String[] test_values = {"john_doe", "john_doe@test.com", "1234567", "created"};//values to insert
		IndexSession writeIndexSession = hsclient.openIndexSession(db, table, "Primary", columns);
		hs.insertData(writeIndexSession, test_values);
		hsclient.shutdown();
		assertEquals("equal", "", "");
	}
}
