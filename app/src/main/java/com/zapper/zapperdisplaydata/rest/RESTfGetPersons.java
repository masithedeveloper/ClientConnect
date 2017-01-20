package com.zapper.zapperdisplaydata.rest;

import android.annotation.SuppressLint;
import com.zapper.zapperdisplaydata.commons.RESTClient;
import java.util.HashMap;

public class RESTfGetPersons implements RESTClient.RESTClientResponseListener {

	//------------------------------------------------------------------------------------
	// Properties
	//------------------------------------------------------------------------------------
	private RESTBase _service = null;
	private RESTClient.RESTClientResponseListener _responseListener = null;

	//------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------
	public RESTfGetPersons(RESTBase service, RESTClient.RESTClientResponseListener responseListener)
	{
		this._service = service;
		this._responseListener = responseListener;
	}
	//------------------------------------------------------------------------------------
	// Methods 
	//------------------------------------------------------------------------------------	
	public void call(){
		RESTClient restClient = new RESTClient(this._service);

		restClient.responseListener = this;
		try {
			restClient.callFunction("");
		} catch(Exception ex){
		}
    }
	//------------------------------------------------------------------------------------
	@SuppressLint("NewApi")
	public void HandleResponse(HashMap<String, Object> response) {
		//check for timeout exception, if fallback not on, enable it and run again
		if (!(this._service.checkResponse(response))){
			call();
			return;
		}		
		_responseListener.HandleResponse(response);
	}
	//------------------------------------------------------------------------------------		
}
