/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientconnect.model;

/**
 *
 * @author masi
 */

public class LoginInfo {
    
    private String username;
    private String password;
    private String email;
    private String token;
    
    //-------------------------------------------------------------------------------------------------------------------------
    public LoginInfo() {
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public String getUsername() {
        return username;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public void setUsername(String username) {
        this.username = username;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public String getPassword() {
        return password;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public void setPassword(String password) {
        this.password = password;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public String getEmail() {
        return email;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public void setEmail(String email) {
        this.email = email;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public String getToken() {
        return token;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    public void setToken(String token) {
        this.token = token;
    }
    //-------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "LoginInfo{" + "username=" + username + ", password=" + password + ", email=" + email + ", token=" + token + '}';
    }
    //-------------------------------------------------------------------------------------------------------------------------
}
