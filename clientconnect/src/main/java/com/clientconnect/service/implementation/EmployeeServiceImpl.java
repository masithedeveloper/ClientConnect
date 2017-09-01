package com.clientconnect.service.implementation;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Employee;
import com.clientconnect.repository.EmployeeRepository;
import com.clientconnect.service.EmployeeService;

/**
 * 
 * @author Masi Stoto
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    //----------------------------------------------------------------------------------------------------        
    @Autowired
    private EmployeeRepository employeeRepository;
    //----------------------------------------------------------------------------------------------------
    @Override
    public Employee save(Employee entity) {
            return employeeRepository.save(entity);
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public Employee getById(Serializable id) {
            return employeeRepository.findOne((Long) id);
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public List<Employee> getAll() {
            return employeeRepository.findAll();
    }
    //----------------------------------------------------------------------------------------------------
    @Override
    public void delete(Serializable id) {
            employeeRepository.delete((Long) id);
    }
    //----------------------------------------------------------------------------------------------------
}
