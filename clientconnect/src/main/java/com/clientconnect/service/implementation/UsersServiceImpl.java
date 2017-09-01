/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientconnect.service.implementation;

import com.clientconnect.repository.UsersRepository;
import com.clientconnect.service.UsersService;
import entities.Users;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author masi
 */
@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;
    //----------------------------------------------------------------------------------------------------    
    @Override
    public Users save(Users entity) {
        return usersRepository.save(entity);
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public Users getById(Serializable id) {
        return usersRepository.getOne((Long) id);
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public List<Users> getAll() {
        return usersRepository.findAll();
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public void delete(Serializable id) {
        usersRepository.delete((Long) id);
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public Users getUserByUsername(String username) {
        return new Users();
    }
    //----------------------------------------------------------------------------------------------------    
    @Override
    public Users getUserByEmail(String email) {
       return new Users();
    }
    //----------------------------------------------------------------------------------------------------
}
