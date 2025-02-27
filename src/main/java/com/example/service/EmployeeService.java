package com.example.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.example.model.Skill;

public interface EmployeeService {
    int insertEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException;
    List<Employee> getAllEmployee() throws ClassNotFoundException, SQLException;
    boolean deleteEmployee(int id) throws SQLException, ClassNotFoundException;
    Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException;
}