package com.zapper.zapperdisplaydata.commons;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	//--------------------------------------------------------------
	// Properties
	//--------------------------------------------------------------
	
	//--------------------------------------------------------------
	// Methods
	//--------------------------------------------------------------
	public static ArrayList<HashMap<String, String>> select(String query){
		// init
		Globals.helperDB().openDataBase();
		ArrayList<HashMap<String, String>> returnList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> arr;
		
		// run query
		Cursor cursor = Globals.database.rawQuery(query, new String[0]);
		
		// check if not 0
		if(cursor.getCount() != 0){ 
			// do first entry
			cursor.moveToFirst();
			String[] cols = cursor.getColumnNames();
			arr = new HashMap<String, String>(); 
			for(int i = 0; i < cols.length; i++){
				arr.put(cols[i], cursor.getString(i));
			}
			returnList.add(arr);
			
			// do the rest
			while(cursor.moveToNext()){
				arr = new HashMap<String, String>(); 
				for(int i = 0; i < cols.length; i++){
					arr.put(cols[i], cursor.getString(i));
				}
				returnList.add(arr);
			}
		}
		
		// close db
		cursor.close();
		Globals.helperDB().close();
		return returnList;
	}
	//--------------------------------------------------------------
	public static HashMap<Long, String> selectList(String query, String id, String name){
		// init
		Globals.helperDB().openDataBase();
		//Globals.helperDB().ope
		HashMap<Long, String> returnList= new HashMap<Long, String>();
		int idIndex;
		int nameIndex;
		
		// run query
		Cursor cursor = Globals.database.rawQuery(query, null);
		
		// check if not 0
		if(cursor.getCount() != 0){
			// do first entry
			cursor.moveToFirst();
			idIndex = cursor.getColumnIndex(id);
			nameIndex = cursor.getColumnIndex(name);
			returnList.put(cursor.getLong(idIndex), cursor.getString(nameIndex));
			
			// do rest
			while(cursor.moveToNext()){
				idIndex = cursor.getColumnIndex(id);
				nameIndex = cursor.getColumnIndex(name);
				returnList.put(cursor.getLong(idIndex), cursor.getString(nameIndex));
			}
		}
		
		// close db
		cursor.close();
		Globals.helperDB().close();
		return returnList;
	}
	//--------------------------------------------------------------
	public static ContentValues selectCVList(String query, String id, String name){
		
		return Database.selectCVList(query, id, name, false);
	}
	//--------------------------------------------------------------
	public static ContentValues selectCVList(String query, String id, String name, boolean noNulls){
		// init
		Globals.helperDB().openDataBase();		
		ContentValues returnList= new ContentValues();
		int idIndex;
		int nameIndex;
		
		// run query
		Cursor cursor = Globals.database.rawQuery(query, null);
		
		// check if not 0
		if(cursor.getCount() != 0){
			// do first entry
			cursor.moveToFirst();
			idIndex = cursor.getColumnIndex(id);
			nameIndex = cursor.getColumnIndex(name);
			String nameValue = cursor.getString(nameIndex);			
			if (noNulls) if ((nameValue == null) || nameValue.toLowerCase().contentEquals("null")) nameValue = "";	
			
			returnList.put(cursor.getString(idIndex), nameValue);
			
			// do rest
			while(cursor.moveToNext()){
				idIndex = cursor.getColumnIndex(id);
				nameIndex = cursor.getColumnIndex(name);
				nameValue = cursor.getString(nameIndex);			
				if (noNulls) if ((nameValue == null) || nameValue.toLowerCase().contentEquals("null")) nameValue = "";
				returnList.put(cursor.getString(idIndex), nameValue);
			}
		}
		
		// close db
		cursor.close();
		Globals.helperDB().close();
		return returnList;
	}
	//--------------------------------------------------------------
	public static int[] selectIds(String query, String id){
		// init
		Globals.helperDB().openDataBase();
		int idIndex;
		int count = 0;
		
		// run query
		Cursor cursor = Globals.database.rawQuery(query, new String[0]);
		int[] returnList = new int[cursor.getCount()];
		
		// check if not 0
		if(cursor.getCount() != 0){
			// do first entry
			cursor.moveToFirst();
			idIndex = cursor.getColumnIndex(id);
			returnList[count] = cursor.getInt(idIndex);
			count++;
			
			// do the rest
			while(cursor.moveToNext()){
				idIndex = cursor.getColumnIndex(id);
				returnList[count] = cursor.getInt(idIndex);
				count++;
			}
		}
		
		// close db
		cursor.close();
		Globals.helperDB().close();
		return returnList;
	}
	//--------------------------------------------------------------
	public static Object selectSingle(String query){
		// init
		Globals.helperDB().openDataBase();	
		Object retval = null;
		
		Cursor cursor = null;
		// run query
		//try
		{
			cursor = Globals.database.rawQuery(query, null);
		}
		//catch (Exception ee) { return ""; }
		
		// check if not 0
		if(cursor.getCount() != 0){
			// do first entry
			cursor.moveToFirst();			
			retval = cursor.getString(0);			
		}
		else
			retval = null;
		
		// close db
		cursor.close();
		Globals.helperDB().close();
		return retval;
	}
	//--------------------------------------------------------------
}
