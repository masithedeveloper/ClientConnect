package com.zapper.zapperdisplaydata.commons;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class RESTClient implements HTTPClient.HTTPClientResponseListener, HTTPClient.HTTPClientErrorListener {
	
	public interface RESTClientResponseListener	{
		public void HandleResponse(HashMap<String, Object> response);
	}
	
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	public String restUrl = ""; 
	public RESTClientResponseListener responseListener = null;	
	public String current_cookie = "";
	private iRESTBase restBase = null;
	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
    public RESTClient (iRESTBase restBase){
    	this.restBase = restBase;
    	restUrl  = this.restBase.getURL();
    	current_cookie  = this.restBase.getCookie();
    }
	//------------------------------------------------------------------------------------
	// Methods
	//------------------------------------------------------------------------------------
	  public void callFunction(String id) throws UnsupportedEncodingException{
		  String urlUsed = Globals.DETAIL_ENDPOINT + id;
          HTTPClient httpclient = new HTTPClient();
          httpclient.responseListener = this;
          httpclient.errorListener = this;
          HashMap<String, String> headers = new HashMap<String, String>();
          if (!this.current_cookie.equals(""))
          {
        	  headers.put("Cookie", this.current_cookie);
          }
          httpclient.setHeaders(headers);          
          httpclient.postData(urlUsed);
	}
	//------------------------------------------------------------------------------------  	  
	public void HandleResponse(HttpResponse r) {
		JSONObject jsonObject = null;
		JSONObject finalResult = null;

		//handle set-cookie: header if returned
		Header[] set_cookie_header_arr = r.getHeaders("Set-Cookie");
		for (Header header : set_cookie_header_arr) {
			String cookie_val = header.getValue();
			this.current_cookie = cookie_val;
			this.restBase.setCookie(this.current_cookie);
		}
		 
		HashMap<String, Object> response = new HashMap<>();
		try {			
			BufferedReader reader = new BufferedReader(new InputStreamReader(r.getEntity().getContent(), "UTF-8"));			
			StringBuilder builder = new StringBuilder();

			String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            } 
			
			JSONTokener tokenizer = new JSONTokener(builder.toString());
			finalResult = new JSONObject(tokenizer);

		} catch (Exception ex) {
			this.HandleError(ex); // Masi added this to have our custom response. See HandleError(Exception);
        }

		try {
			 jsonObject = new JSONObject(finalResult.toString());
			 response.put("code", jsonObject.getString("code"));
	         response.put("message", jsonObject.getString("message"));
	         response.put("meta", jsonObject.getString("meta"));
	         response.put("data", jsonObject.getString("data"));
		}
        		
        catch(JSONException e){
            this.HandleError(e);
        }
        
		if (responseListener!=null) responseListener.HandleResponse(response);
	}
	//------------------------------------------------------------------------------------
	public void HandleError(Exception ex) {
		JSONObject jsonObject = null;
		HashMap<String, Object> response = new HashMap<>();
		
		try {
			jsonObject = new JSONObject(ex.toString());
		} 
		catch (Exception ex2){
		}
		response.put("code", "-1");
        response.put("message", jsonObject);
        response.put("meta", ex.toString());
        response.put("data", ex.toString());
				        
		if (responseListener!=null) responseListener.HandleResponse(response);
	}
}