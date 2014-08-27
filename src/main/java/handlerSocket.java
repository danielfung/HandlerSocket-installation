package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.google.code.hs4j.FindOperator;
import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.exception.HandlerSocketException;

public class handlerSocket {
	
	private Map<String, List<String>> myhashmap = new HashMap<String, List<String>>();
	private List<String> data = new ArrayList<String>();
	
	/*
	 * Insert data into hashmap
	 * 
	 * @param key the item to be added into hashmap as key.
	 * @param array the items you want to be added that is associated with the "key".
	 */
	public void addtoHashMap(String key, List<String> array){
		myhashmap.put(key, array);
	}
	
	/*
	 * Clear the hashmap(myhashmap)
	 */
	public void clearHashMap(){
		myhashmap.clear();
	}
	
	/*
	 * Returns size of current hashmap(myhashmap).
	 * 
	 * @return return myhashmap current size.
	 */
	public int sizeHashMap(){
		return myhashmap.size();
	}
	
	/*
	 * Look in hashmap(myhashmap) for specific data
	 * 
	 * @param key the item that you want to look for in hashmap.
	 * @return based on key returns data if found in hashmap.
	 */
	public List<String> findKey(String key){
		return myhashmap.get(key);
	}
	
	/*
	 * Insert data into arraylist(data)
	 * 
	 * @param datas the data to be inserted into arraylist.
	 */
	public void addToList(String datas){
		data.add(datas);
	}
	
	/*
	 * clear the arraylist(data)
	 */
	public void clearList(){
		data.clear();
	}
	
	/*
	 * Finding data in mysql and place into hashmap
	 * 
	 * @param hsclient
	 * @param indexId
	 * @param fields column names in the table.
	 * @param keys values to be compared with index columns.
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	public void findData(HSClient hsclient, int indexId, String[] fields, String[] keys) throws SQLException, InterruptedException, TimeoutException, HandlerSocketException{	
		for(int i = 0; i<keys.length; i++){//list of items to find
			String key = keys[i];
			String[] test = {key};
			ResultSet rs = hsclient.find(indexId, test);//item to find
			while(rs.next()){
				for(int j = 0; j<fields.length; j++){
					addToList(rs.getString(fields[i]));//columns to display based on item
				}
			addtoHashMap(key, data);
			clearList();//clear list
			}
		}
	}
	
	/*
	 * Inserting data into mysql
	 * 
	 * @param hsclient
	 * @param indexId
	 * @param values String array of values to be inserted into a specific table in mysql.
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	public void insertData(HSClient hsclient, int indexId, String[] values) throws InterruptedException, TimeoutException, HandlerSocketException{
		hsclient.insert(indexId, values);

	}
	
	/*
	 * Deleting data from mysql
	 * 
	 * @param hsclient
	 * @param indexId
	 * @param values items to be deleted.
	 * @param operator specifies the comparison operation(EQ, GE, GT, LE, LT) to use.
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	public void deleteData(HSClient hsclient, int indexId, String[] values, FindOperator operator) throws InterruptedException, TimeoutException, HandlerSocketException{
		hsclient.delete(indexId, values, operator);
	}
	
	/*
	 * Updating data in mysql
	 * 
	 * @param hsclient
	 * @param indexId
	 * @param keys keys that will be compared with index columns.
	 * @param values values that will be modified.
	 * @param operator specifies the comparison operation(EQ, GE, GT, LE, LT) to use.
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws HandlerSocketException
	 */
	public void updateData(HSClient hsclient, int indexId, String[] keys, String[] values, FindOperator operator) throws InterruptedException, TimeoutException, HandlerSocketException{
		hsclient.update(indexId, keys, values, operator);
	}
}