package com.clientconnect.service;

import entities.Users;

/**
 * 
 * @author Masi Stoto
 *
 */
public interface UsersService extends CRUDService<Users> {
    //----------------------------------------------------------------------------------------------------
    public Users getUserByUsername(String username);
    //----------------------------------------------------------------------------------------------------
    public Users getUserByEmail(String email);
    //----------------------------------------------------------------------------------------------------
}
