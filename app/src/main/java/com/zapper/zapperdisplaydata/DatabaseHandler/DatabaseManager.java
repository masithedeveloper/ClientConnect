package com.zapper.zapperdisplaydata.DatabaseHandler;

import com.zapper.zapperdisplaydata.Person;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Masi on 12/10/2016.
 */
public class DatabaseManager {

    ArrayList<Person> persons = new ArrayList<>();
    //----------------------------------------------------------------------------------------------
    public DatabaseManager() {
    }
    //----------------------------------------------------------------------------------------------
    public void savePersonDetailsLocally(JSONArray jsonArrayPersonsDetails) throws JSONException {
        for (int i = 0; i < jsonArrayPersonsDetails.length(); i++) {
            savePersonRow(new Person(jsonArrayPersonsDetails.getJSONObject(i)));
        }
    }
    //----------------------------------------------------------------------------------------------
    public Person getPersonById(int id){
        // interface to db
        return new Person();
    }
    //----------------------------------------------------------------------------------------------
    public ArrayList<Person> getAllPersons(){
        // interface to db
        return new ArrayList<>();
    }
    //----------------------------------------------------------------------------------------------
    public int savePersonRow(Person personRow){
        // save row to sqlite db
        if (getPersonById(personRow.getId()) != null)
            return -1;
        else return 1;
    }
    //----------------------------------------------------------------------------------------------
}