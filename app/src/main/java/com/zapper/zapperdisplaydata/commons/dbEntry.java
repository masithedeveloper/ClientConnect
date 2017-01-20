package com.zapper.zapperdisplaydata.commons;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbEntry {
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	public String table = "";
	
	public Hashtable<String, Object[]> fields;
	              
	public ContentValues values = new ContentValues();
	
	public enum dataType { DB_INT, DB_STRING, DB_ENUM, DB_DATETIME, DB_REFERENCE, DB_BOOL, DB_FLOAT, DB_SET, DB_TEXT, DB_DATE, DB_TIME };
	
	public String fieldString = "";
	public String keyField = "_id";
	
	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	public dbEntry(){
		
	}
	//------------------------------------------------------------------------------------
	// Methods
	//------------------------------------------------------------------------------------
	public void setKeyField(String _inField)
	{
		this.keyField = _inField;
		keyField = _inField;
	}
	//------------------------------------------------------------------------------------
	public void createDefaults() {
		// init
		Enumeration<String> e = fields.keys();
		int count = 0;
		
		// populate content values
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			Object[] defaults = fields.get(key);
			
			values.put(key, defaults[1].toString());
			
			// create quick string for
			if(count == 0) fieldString += key;
			else fieldString += ","+key;
			
			count++;
		}
	}
	//------------------------------------------------------------------------------------
	public void printValues() {
		// init
		Enumeration<String> e = fields.keys();
		
		// construct string
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			
			//Globals.log(key+" - "+values.getAsString(key));
		}
	}
	//------------------------------------------------------------------------------------
	public boolean getFromDb(int id) {
		// init
		
		SQLiteDatabase db = null;
		if (!Globals.overrideDBOpen) {
			db = Globals.helperDB().getWritableDatabase();
		} else {
			db = Globals.database;
		}	
		// get data
		Cursor cur = db.rawQuery("SELECT "+fieldString+" FROM "+table+" WHERE "+this.keyField+" = "+id, new String[]{});
		cur.moveToFirst();
		
		// check for 0 entries
		if(cur.getCount() == 0){
			Globals.helperDB().close();
			cur.close();
			return false;
		}
		
		// populate content values
		Enumeration<String> e = fields.keys();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			int index = cur.getColumnIndex(key);
			String value = cur.getString(index);
			values.put(key, value);
		}
		
		// close db
		cur.close();
		if (!Globals.overrideDBOpen) {
			Globals.helperDB().close();
		}
		
		return true;
	}
	//------------------------------------------------------------------------------------
		public boolean getFromDb(String whereClause) {
			// init

			SQLiteDatabase db = null;
			if (!Globals.overrideDBOpen) {
				db = Globals.helperDB().getWritableDatabase();
			} else {
				db = Globals.database;
			}

			// get data
			Cursor cur = db.rawQuery("SELECT "+fieldString+" FROM "+table+" WHERE "+whereClause, new String[]{});
			cur.moveToFirst();
			
			// check for 0 entries
			if(cur.getCount() == 0){
				if (!Globals.overrideDBOpen) {
					Globals.helperDB().close();
				}
				cur.close();
				return false;
			}
			
			// populate content values
			Enumeration<String> e = fields.keys();
			while(e.hasMoreElements()){
				String key = e.nextElement().toString();
				int index = cur.getColumnIndex(key);
				String value = cur.getString(index);
				values.put(key, value);
			}
			
			// close db
			cur.close();
			if (!Globals.overrideDBOpen) {
				Globals.helperDB().close();
			}
			
			return true;
		}
	//------------------------------------------------------------------------------------
	public ArrayList<HashMap<String, String>> getAllFromDB(){
		ArrayList<HashMap<String, String>> returnList = new ArrayList<HashMap<String, String>>();
		returnList = Database.select("select * from "+table+" where deleted = "+0);
		return returnList;
	}
	//------------------------------------------------------------------------------------
	public ArrayList<HashMap<String, String>> getAllDirtyFromDB(){
		ArrayList<HashMap<String, String>> returnList = new ArrayList<HashMap<String, String>>();
		returnList = Database.select("select * from "+table+" where dirty = "+1);
		return returnList;
	}
		
	//------------------------------------------------------------------------------------
	public long insert() {
		// init
		SQLiteDatabase db = null;
		if (!Globals.overrideDBOpen) {
			db = Globals.helperDB().getWritableDatabase();
		} else {
			db = Globals.database;
		}

		// unset _id for insert
		values.remove(this.keyField);
		
		// insert
		Long id = db.insert(table, null, values);
		
		// update values with new id
		values.put(this.keyField, id);
		
		// close db
		if (!Globals.overrideDBOpen) {
			Globals.helperDB().close();
		}
		
		// return inserted key
		return id;
	}
	//------------------------------------------------------------------------------------
	public void update() {
		// init
		SQLiteDatabase db = null;
		if (!Globals.overrideDBOpen) {
			db = Globals.helperDB().getWritableDatabase();
		} else {
			db = Globals.database;
		}
		
		// update
		db.update(table, values, this.keyField+" = ?", new String[]{ values.getAsString(this.keyField) });
		
		// close db
		if (!Globals.overrideDBOpen) {
			Globals.helperDB().close();
		}
	}
	//------------------------------------------------------------------------------------
	public long save() {
		// init
		if (this.isValidDBEntry()) {
			this.update();
			return -1;
		} 
		else 
		{
			return this.insert();
		}
	}
	//------------------------------------------------------------------------------------
	public long save(int setDirty) {
		// init
		
		if (this.values.containsKey("dirty")) {
			this.values.put("dirty", setDirty);
		}
		
		return this.save();
	}
	//------------------------------------------------------------------------------------
	public void delete() {
		// init
		SQLiteDatabase db = null;
		if (!Globals.overrideDBOpen) {
			db = Globals.helperDB().getWritableDatabase();
		} else {
			db = Globals.database;
		}
		
		// update
		db.delete(table, this.keyField+" = ?", new String[]{ values.getAsString(this.keyField) });

		// close db
		if (!Globals.overrideDBOpen) {
			Globals.helperDB().close();
		}
	}
	//------------------------------------------------------------------------------------
	
	public void deleteAll(){
		// init
		SQLiteDatabase db = null;
		if (!Globals.overrideDBOpen) {
			db = Globals.helperDB().getWritableDatabase();
		} else {
			db = Globals.database;
		}

		db.execSQL("delete from "+table );
		// close db
		if (!Globals.overrideDBOpen) {
			Globals.helperDB().close();
		}
	} 
	//------------------------------------------------------------------------------------
	public boolean isValidDBEntry(){
	
		if (this.values.containsKey(this.keyField)) {
			if (!this.values.getAsString(this.keyField).equals("null") && !this.values.getAsString(this.keyField).equals("")) {
				return true;
			}
		}
		return false;
			
	}
	//------------------------------------------------------------------------------------
}
