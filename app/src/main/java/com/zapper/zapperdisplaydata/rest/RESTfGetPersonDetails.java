package com.zapper.zapperdisplaydata.rest;

import android.annotation.SuppressLint;

import com.zapper.zapperdisplaydata.commons.RESTClient;

import java.util.HashMap;

public class RESTfGetPersonDetails implements RESTClient.RESTClientResponseListener {

	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	private RESTBase _service = null;
	private RESTClient.RESTClientResponseListener _responseListener = null;

	private String _server_id = "";

	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	public RESTfGetPersonDetails(RESTBase service, RESTClient.RESTClientResponseListener responseListener)
	{
		this._service = service;
		this._responseListener = responseListener;
	}
	//------------------------------------------------------------------------------------
	// Methods 
	//------------------------------------------------------------------------------------	
	public void call(String server_id){
		this._server_id = server_id;
		RESTClient restClient = new RESTClient(this._service);
		HashMap<String, Object> dataTable = new HashMap <String, Object>();
		dataTable.put("person", this._server_id);

		restClient.responseListener = this;
		try {
			restClient.callFunction(this._server_id);
		} catch(Exception ex){
		}
    }
	//------------------------------------------------------------------------------------
	@SuppressLint("NewApi")
	public void HandleResponse(HashMap<String, Object> response) {
		//check for timeout exception, if fallback not on, enable it and run again
		if (!(this._service.checkResponse(response))){
			call(this._server_id);
			return;
		}		
		_responseListener.HandleResponse(response);
	}
	//------------------------------------------------------------------------------------		
}
