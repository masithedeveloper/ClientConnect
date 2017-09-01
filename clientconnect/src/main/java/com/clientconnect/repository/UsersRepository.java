package com.clientconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Users;
/**
 * 
 * @author Masi Stoto
 *
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}
