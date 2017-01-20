package com.zapper.zapperdisplaydata.dbcom;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.zapper.zapperdisplaydata.Person;
import com.zapper.zapperdisplaydata.commons.Database;
import com.zapper.zapperdisplaydata.db.DBPerson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Masilibale on 2015-08-26.
 */
public class DataManager {

    public static void write_person_details_to_database(JSONObject response) throws JSONException {
        /**
         * JSON objects
         */
        JSONObject jsonResponse = response;
        JSONObject json_person = jsonResponse.getJSONObject("person");
        /**
         * DB file
        */
        DBPerson person;

        //------------------------------------------------------------------------------------------------------------------------------------------
        person = new DBPerson(" serverId = " + "'"+json_person.getString("id")+"'");
        person.values.put("serverId", json_person.getInt("id"));
        person.values.put("firstName", json_person.getString("firstName"));
        person.values.put("lastName", json_person.getString("lastName"));
        person.values.put("age", json_person.getInt("age"));
        person.values.put("favouriteColour", json_person.getString("favouriteColour"));
        person.values.put("isDetails", 1);
        person.save();

        //------------------------------------------------------------------------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------------------------------------------------------------------
    public static void write_persons_to_database(JSONArray response) throws JSONException {
        /**
         * JSON objects
         */
        JSONArray jsonResponse = response;
        /**
         * DB file
         */
        DBPerson person;

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject personObject =  jsonResponse.getJSONObject(i);
            person = new DBPerson(" serverId = " + "'"+personObject.getString("id")+"'");
            person.values.put("serverId", personObject.getInt("id"));
            person.values.put("firstName", personObject.getString("firstName"));
            person.values.put("lastName", personObject.getString("lastName"));
            person.values.put("isDetails", 0);
            person.save();
        }
        //------------------------------------------------------------------------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------------------------------------------------------------------
    public static void backupDatabase() throws IOException {
        //Open my local db as the input stream
        String inFileName = "/data/data/com.zapper.zapperdisplaydata/databases/zapper.db";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);
        String outFileName = Environment.getExternalStorageDirectory().getPath()+"/zapper_bak.db";
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
        Log.i("", "--------------------------------------------------------");
        Log.i("", "Done with the backup!!!");
        Log.i("", "--------------------------------------------------------");
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
    public static ArrayList<Person> get_all_persons(){
        ArrayList<HashMap<String,String>> persons;
        ArrayList<Person> all_persons = null;
        persons = Database.select("select * from person");
        for (HashMap<String, String> person: persons) {
            all_persons.add(new Person(
                    Integer.parseInt(person.get("serverId")),
                    person.get("firstName"),
                    person.get("lastName"),
                    Integer.parseInt(person.get("age")),
                    person.get("favouriteColour"),
                    Integer.parseInt(person.get("isDetails"))));
        }
        return all_persons;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
    public static Person get_person_details_by_id(int id){
        HashMap<String,String> person_details;
        person_details = Database.select("select * from person where server_id = " + id).get(0);

        return new Person(
                id,
                person_details.get("firstName"),
                person_details.get("lastName"),
                Integer.parseInt(person_details.get("age")),
                person_details.get("favouriteColour"),
                Integer.parseInt(person_details.get("isDetails")));
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
    public static Person jsonToPerson(JSONObject jsonPerson) throws JSONException {
        return new Person(jsonPerson.getInt("id"),
                jsonPerson.getString("firstName"),
                jsonPerson.getString("lastName"),
                jsonPerson.getInt("age"),
                jsonPerson.getString("favouriteColour"),
                0); // check if all details have been downloaded
    }
    //----------------------------------------------------------------------------------------------
    public static ArrayList<Person> jsonToPersons(JSONArray jsonPersons) throws JSONException {
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < jsonPersons.length(); i++) {
            persons.add(new Person(jsonPersons.getJSONObject(i)));
        }
        return persons;
    }
}
