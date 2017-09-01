package com.clientconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Employee;
/**
 * 
 * @author Masi Stoto
 *
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
