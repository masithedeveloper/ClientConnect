package com.zapper.zapperdisplaydata.commons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Globals {
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	public static Context context;
	private static dbHelper privHelperDB;
	public static SQLiteDatabase database;

	public static boolean overrideDBOpen = false;
	public static int api_version = Build.VERSION.SDK_INT;

	// Environment variables
	public static String MASTER_ENDPOINT = null;
	public static String DETAIL_ENDPOINT = null;
	//------------------------------------------------------------------------------------
	// Methods
	//------------------------------------------------------------------------------------
	public static dbHelper helperDB()
	{
		if (Globals.privHelperDB!=null)
			return Globals.privHelperDB;

		else
		{
			Globals.privHelperDB = new dbHelper(Globals.context);
			return Globals.privHelperDB;
		}
	}
	//------------------------------------------------------------------------------------
	public static int sdk_version(){
		return api_version;
	}
	//------------------------------------------------------------------------------------
}
