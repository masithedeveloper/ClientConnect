package com.clientconnect.model;

import entities.Users;

public class User {

    private Long id;
    private String name;
    private String address;
    private String email;
    //----------------------------------------------------------------------------------------------------------

    public User() {
    }
    
    //----------------------------------------------------------------------------------------------------------
    public User(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.address = users.getAddress();
    }
    //----------------------------------------------------------------------------------------------------------    
    public Long getId() {
	return id;
    }
    //----------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }
    //----------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    //----------------------------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    //----------------------------------------------------------------------------------------------------------
    public void setAddress(String address) {
        this.address = address;
    }
    //----------------------------------------------------------------------------------------------------------
    public String getEmail() {
        return email;
    }
    //----------------------------------------------------------------------------------------------------------
    public void setEmail(String email) {
        this.email = email;
    }
    //----------------------------------------------------------------------------------------------------------
}
