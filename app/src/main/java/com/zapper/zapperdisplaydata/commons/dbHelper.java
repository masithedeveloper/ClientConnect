package com.zapper.zapperdisplaydata.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHelper extends SQLiteOpenHelper{
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	public static String DB_PATH = "";
	public static String DB_NAME = "";
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	
	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	/**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public dbHelper(Context context) {
    	// init
    	super(context, DB_NAME, null, 1);
    	this.myContext = context;
    }
    //------------------------------------------------------------------------------------
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException{
    	// init
    	boolean dbExist = checkDataBase();
    	
    	if(dbExist){
    		// TODO - remove override
    		/*this.getReadableDatabase();

    		try{
    			copyDataBase();
    		}catch (IOException e){
    			throw new Error("Error copying database");
    		}*/
    	}else{    		
    		try{
    			this.getReadableDatabase();
    			copyDataBase();    			
    		}catch (Exception e){
    			throw new Error("Error copying database");
    		}
    	}
    }
    // Masi's code remove
    private boolean checkDataBase(){
	    File dbFile = myContext.getDatabasePath(DB_NAME);
	    return dbFile.exists();
    }
    
    private boolean checkDataBase2(){
	    File dbFile = new File("/storage/emulated/zapper_bak.db");
	    return dbFile.exists();
    }
    
    public void createDataBase2() throws IOException{
    	// init
    	boolean dbExist = checkDataBase2();
    	
    	if(dbExist){
    		// TODO - remove override
    		/*this.getReadableDatabase();

    		try{
    			copyDataBase();
    		}catch (IOException e){
    			throw new Error("Error copying database");
    		}*/
    	}else{    		
    		try{
    			this.getReadableDatabase();
    			Log.i("before copy","before copy");
    			copyDataBase2();    			
    			Log.i("after copy","after copy");
    		}catch (Exception e){
    			throw new Error("Error copying database");
    		}
    	}
    }
    
    private void copyDataBase2() throws IOException{
    	//Open your local db as the input stream
    	Log.i("before access assets","before access assets");
    	InputStream myInput = myContext.getAssets().open("zapper_bk.db");
    	Log.i("after access assets","after access assets");
    	// Path to the just created empty db
    	String outFileName = "/storage/emulated/zapper_bk.db";
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
    //------------------------------------------------------------------------------------
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    
    /*
    private boolean checkDataBase(){
    	// init
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
    	
    	return checkDB != null ? true : false;
    }*\
    //------------------------------------------------------------------------------------
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException{
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
    //------------------------------------------------------------------------------------
    public byte[] readDataBase() throws IOException{
    	
    	String inFileName = DB_PATH + DB_NAME;
    	InputStream myInput = new FileInputStream(inFileName);
 
    	//Open the empty db as the output stream
    	//myOutput = new FileOutputStream(outFileName);
    	ByteArrayOutputStream myOutput = new ByteArrayOutputStream();
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	
    	return myOutput.toByteArray();
    }
    //------------------------------------------------------------------------------------
    public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        //myDataBase = SQLiteDatabase.        
    	Globals.database = myDataBase;
    }
    //------------------------------------------------------------------------------------
    @Override
	public synchronized void close() {
    	// init
    	if(myDataBase != null) myDataBase.close();
    	super.close();
	}
    //------------------------------------------------------------------------------------
	@Override
	public void onCreate(SQLiteDatabase db) {
		// init
	}
	//------------------------------------------------------------------------------------
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// init
	}
	//------------------------------------------------------------------------------------
}