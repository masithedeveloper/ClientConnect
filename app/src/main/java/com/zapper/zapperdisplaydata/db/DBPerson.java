package com.zapper.zapperdisplaydata.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zapper.zapperdisplaydata.commons.Globals;
import com.zapper.zapperdisplaydata.commons.dbEntry;

import java.util.Enumeration;
import java.util.Hashtable;

public class DBPerson extends dbEntry {

	public String keyField = "id";

	public DBPerson() {
		this(0);
	}

	public DBPerson(int id){

		// table name
		table = "person";
		super.setKeyField(this.keyField);

		// declare fields 
		fields = new Hashtable<String, Object[]>();
		// 			field			=>		 name		default	  datatype		
		fields.put(this.keyField, 				new Object[]{this.keyField		, "null", dbEntry.dataType.DB_INT});
		fields.put("firstName", 				new Object[]{"firstName"		, ""	, dataType.DB_STRING});
		fields.put("lastName", 				new Object[]{"lastName"		, ""	, dataType.DB_STRING});
		fields.put("age", 				new Object[]{"age"		, "null", dataType.DB_INT});
		fields.put("serverId", 				new Object[]{"serverId"		, "null", dataType.DB_INT});
		fields.put("favouriteColour", 				new Object[]{"favouriteColour"		, "null", dataType.DB_STRING});
		fields.put("isDetails", 				new Object[]{"isDetails"		, 0, dataType.DB_INT});
		// setup content values
		createDefaults();

		if(id != 0){
			getFromDb(id);
		}
	}

	public DBPerson(String whereClause){

		// table name
		table = "person";
		super.setKeyField(this.keyField);

		// declare fields 
		fields = new Hashtable<String, Object[]>();
		// 			field			=>		 name		default	  datatype		
		fields.put(this.keyField, 				new Object[]{this.keyField		, "null", dbEntry.dataType.DB_INT});
		fields.put("firstName", 				new Object[]{"firstName"		, ""	, dataType.DB_STRING});
		fields.put("lastName", 				new Object[]{"lastName"		, ""	, dataType.DB_STRING});
		fields.put("age", 				new Object[]{"age"		, "null", dataType.DB_INT});
		fields.put("serverId", 				new Object[]{"serverId"		, "null", dataType.DB_INT});
		fields.put("favouriteColour", 				new Object[]{"favouriteColour"		, "null", dataType.DB_STRING});
		fields.put("isDetails", 				new Object[]{"isDetails"		, 0, dataType.DB_INT});
		// setup content values
		createDefaults();

		if(!(whereClause.equals(""))){
			getFromDb(whereClause);
		}
	}

	public boolean getExtFromDb(int id) {
		// init

		SQLiteDatabase db = Globals.helperDB().getWritableDatabase();
		// get data
		Cursor cur = db.rawQuery("SELECT "+fieldString+" FROM "+table+" WHERE serverId = " + id, new String[]{});
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
		Globals.helperDB().close();

		return true;
	}

	//------------------------------------------------------------------------------------
	// Methods

}
