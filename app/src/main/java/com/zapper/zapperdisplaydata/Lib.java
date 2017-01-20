package com.zapper.zapperdisplaydata;

import android.content.Context;

import com.zapper.zapperdisplaydata.commons.Globals;
import com.zapper.zapperdisplaydata.commons.dbHelper;
import com.zapper.zapperdisplaydata.rest.RESTBase;
import java.io.IOException;

public class Lib {
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	private static boolean didInit = false;	
	private static RESTBase baseService = null;
	//------------------------------------------------------------------------------------
	public static void Init(String dbPath, String dbName, Context context){
		if (Lib.didInit) return;
		
		Globals.context = context;
		
		if (dbPath!=null)
		{
			dbHelper.DB_PATH = dbPath;
			dbHelper.DB_NAME = dbName;
	        try {
	        	Globals.helperDB().createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
	        	e.printStackTrace();
			}
		}
		// put the service environment thing here
		// environment
		Globals.MASTER_ENDPOINT = "http://demo4012764.mockable.io/person";
		Globals.DETAIL_ENDPOINT = "http://demo4012764.mockable.io/person/";
		Lib.baseService = new RESTBase(Globals.MASTER_ENDPOINT);
		didInit = true;
	}
	//------------------------------------------------------------------------------------
	public static RESTBase getService()
	{
		return Lib.baseService;
	}
	//------------------------------------------------------------------------------------
}