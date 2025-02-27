package com.example.dao;

import java.sql.SQLException;
import java.util.List;
import com.example.model.Employee;
import com.example.model.Skill;

public interface EmployeeDAOInterface {
    int insertEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException;
    List<Employee> getAllEmployee() throws SQLException, ClassNotFoundException;
    Skill[] getAllSkillsByEmployeeId(int employee_id) throws SQLException, ClassNotFoundException;
    Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(int id) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(Employee emp) throws SQLException, ClassNotFoundException;
    boolean deleteSkillsByEmployeeId(int employee_id, Skill skill) throws SQLException;
    int insertSkillByEmployeeId(int employee_id, Skill skill) throws SQLException;
}
