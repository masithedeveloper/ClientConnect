package com.zapper.zapperdisplaydata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Masi on 12/10/2016.
 */

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private int age;
    private String favouriteColour;
    private int isDetails;

    //----------------------------------------------------------------------------------------------
    public Person() {

    }
    //----------------------------------------------------------------------------------------------
    public Person(int id, String firstName, String lastName, int age, String favouriteColour, int isDetails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.age = age;
        this.favouriteColour = favouriteColour;
        this.isDetails = isDetails;
    }
    //----------------------------------------------------------------------------------------------
    public Person(JSONObject jsonPerson) throws JSONException{
        this.id = jsonPerson.getInt("id");
        this.firstName = jsonPerson.getString("firstName");
        this.lastName = jsonPerson.getString("lastName");
        this.age = jsonPerson.getInt("age");
        this.favouriteColour = jsonPerson.getString("favouriteColour");
    }
    //----------------------------------------------------------------------------------------------
    public int getId() {
        return id;
    }
    //----------------------------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    //----------------------------------------------------------------------------------------------
    public String getFirstName() {
        return firstName;
    }
    //----------------------------------------------------------------------------------------------
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    //----------------------------------------------------------------------------------------------
    public String getLastName() {
        return lastName;
    }
    //----------------------------------------------------------------------------------------------
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    //----------------------------------------------------------------------------------------------
    public int getAge() {
        return age;
    }
    //----------------------------------------------------------------------------------------------
    public void setAge(int age) {
        this.age = age;
    }
    //----------------------------------------------------------------------------------------------
    public String getFavouriteColour() {
        return favouriteColour;
    }
    //----------------------------------------------------------------------------------------------
    public void setFavouriteColour(String favouriteColour) {
        this.favouriteColour = favouriteColour;
    }
    //----------------------------------------------------------------------------------------------

    public int getIsDetails() {
        return isDetails;
    }

    public void setIsDetails(int isDetails) {
        this.isDetails = isDetails;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName() {
        this.fullName = getFirstName() + " " + getLastName();
    }
    //----------------------------------------------------------------------------------------------
}