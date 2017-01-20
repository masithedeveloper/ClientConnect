package com.zapper.zapperdisplaydata.commons;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class HTTPClient extends AsyncTask<String, Integer, Double>{
	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------	
	public HTTPClientResponseListener responseListener;
	public HTTPClientErrorListener errorListener;
	private HashMap<String, String> headers;
	private int HTTPTimeout = 20000; // timeout set to 20 seconds
	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	@Override
	protected Double doInBackground(String... params) {
		// TODO Auto-generated method stub
		/*try {
			postData(params[0], params[1]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String url = params[0];
		String dataToSent = params[1];

		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams= client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, HTTPTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, HTTPTimeout);
		HttpPost httppost = new HttpPost(url);

        try {
            StringEntity se = new StringEntity(dataToSent.toString(), HTTP.UTF_8);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                String header = entry.getKey();
                String header_value = entry.getValue();
                
                httppost.addHeader(header, header_value);
            }            
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date dateobj = new Date();
			System.out.println("Request Start time: "+df.format(dateobj));
            HttpResponse response = client.execute(httppost);
            if (responseListener!=null) responseListener.HandleResponse(response);
            dateobj = new Date();
			System.out.println("Request End time: "+ df.format(dateobj));
            
        } catch (Exception ex) {
        	//e.printStackTrace();
        	if (errorListener!=null) errorListener.HandleError(ex);
        } 
		//
		return null;		
	}

	protected HttpResponse onPostExecute(HttpPost httppost){
		return null;
		
	}
	protected void onProgressUpdate(Integer... progress){
		
	}
	
	public interface HTTPClientResponseListener
	{
		public void HandleResponse(HttpResponse r);
	}

	public interface HTTPClientErrorListener
	{
		public void HandleError(Exception ex);
	}
	
	public HTTPClient(){
		this.headers = new HashMap<String, String>();
	}
	//------------------------------------------------------------------------------------
	// Methods
	//------------------------------------------------------------------------------------
	public void postData(String url){
		// Create a new HttpClient and Post Header
		Log.i("url",url.toString());
		this.execute(url);
	}

	public void setHeaders(HashMap<String, String> headers) {
		// TODO Auto-generated method stub
		this.headers = headers;
	} 

	//------------------------------------------------------------------------------------
	
}
