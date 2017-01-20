package com.zapper.zapperdisplaydata;

/**
 * Created by developer on 12/13/2016.
 */

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.zapper.zapperdisplaydata.commons.RESTClient;
import com.zapper.zapperdisplaydata.db.DBPerson;
import com.zapper.zapperdisplaydata.dbcom.DataManager;
import com.zapper.zapperdisplaydata.rest.RESTfGetPersons;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;

public class RefreshService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    static final String TAG = "RefreshService";
    //-------------------------------------------------------------------------------------------------------------
    public RefreshService() { //
        super(TAG);
    }
    //-------------------------------------------------------------------------------------------------------------
    @Override
    public void onCreate() { //
        super.onCreate();
        Log.d(TAG, "onCreated");
    }
    //-------------------------------------------------------------------------------------------------------------
    // Executes on a worker thread
    @Override
    protected void onHandleIntent(Intent intent) { //
        Log.d(TAG, "onStarted");
        // async task for downloading data from end-point
        RESTfGetPersons resTfGetPersonDetails = new RESTfGetPersons(Lib.getService(), new RESTClient.RESTClientResponseListener() {
            @SuppressLint("NewApi")
            public void HandleResponse(final HashMap<String, Object> response) {
                final HashMap<String, Object> fresponse = response;
                    String response_data = fresponse.toString();
                    // update person record from the DB
                    try {
                        ArrayList<Person> persons = DataManager.jsonToPersons(new JSONArray(response_data));
                        DBPerson dbPerson;
                        for (Person person : persons) {
                            dbPerson = new DBPerson("serverId = " + person.getId());
                            dbPerson.values.put("firstName", person.getAge());
                            dbPerson.values.put("lastName", person.getFavouriteColour());
                            dbPerson.values.put("isDetails", 0);
                            dbPerson.save();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        });
        resTfGetPersonDetails.call();
    }
    //-------------------------------------------------------------------------------------------------------------
    @Override
    public void onDestroy() { //
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }
    //-------------------------------------------------------------------------------------------------------------
}
