package com.zapper.zapperdisplaydata.rest;

import com.zapper.zapperdisplaydata.commons.iRESTBase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RESTBase implements iRESTBase {
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	private String baseURL = null;
	private String current_cookie = "";
	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	public RESTBase(String baseURL){
		this.baseURL = baseURL;
	}
	//------------------------------------------------------------------------------------
	// Methods
	//------------------------------------------------------------------------------------
	public String getRESTUrl(){
		return this.baseURL;
	}
	//------------------------------------------------------------------------------------	
	public boolean ping(String url, int timeout) {
	    url = url.replaceFirst("https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.	   
	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (200 <= responseCode && responseCode <= 399);
	    } catch (IOException exception) {
	        return false;
	    }
	}
	//------------------------------------------------------------------------------------	
	public boolean checkResponse(HashMap<String, Object> response) {			
		//check if timeout or connection issue
		if (response.get("code").toString().equals("-1")){
			if ((response.get("meta").toString().contains("timeout")) ||
			   (response.get("meta").toString().contains("UnknownHostException"))){
				//could not be reached .. time out ... switch to and try with fallback
				return false;
			}
		}
		return true;
	}
	//------------------------------------------------------------------------------------	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return this.getRESTUrl();
	}
	@Override
	public String getCookie() {
		// TODO Auto-generated method stub
		return current_cookie;
	}
	@Override
	public void setCookie(String cookie) {
		// TODO Auto-generated method stub
		this.current_cookie = cookie;
	}
}
