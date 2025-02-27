package com.example.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.example.model.Skill;

public class EmployeeServiceImplementation implements EmployeeService {
	private EmployeeDAO employeeDAO = new EmployeeDAO();

	public int insertEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException {
		return employeeDAO.insertEmployee(emp, skill);
	}

	public List<Employee> getAllEmployee() throws ClassNotFoundException, SQLException {
		return employeeDAO.getAllEmployee();
	}

	public boolean deleteEmployee(int id) throws SQLException, ClassNotFoundException {
		return employeeDAO.deleteEmployee(id);
	}

	public Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException {
		return employeeDAO.getEmployeeById(id);
	}

	public boolean updateEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException {
		int id = emp.getId();
		boolean response = employeeDAO.updateEmployee(emp);

		Skill[] dbSkills = employeeDAO.getAllSkillsByEmployeeId(id);
		Set<String> dbSkillSet = new HashSet<>();

		if (dbSkills != null) {
			for (Skill dbSkill : dbSkills) {
				if (dbSkill.getSkillName() != null && dbSkill.getSkillName().length > 0) {
					dbSkillSet.add(dbSkill.getSkillName()[0]);
				}
			}
		}

		Set<String> newSkillSet = new HashSet<>();
		if (skill != null && skill.getSkillName() != null) {
			newSkillSet.addAll(Arrays.asList(skill.getSkillName()));
		}

		Set<String> skillsToInsert = new HashSet<>(newSkillSet);
		skillsToInsert.removeAll(dbSkillSet);

		Set<String> skillsToDelete = new HashSet<>(dbSkillSet);
		skillsToDelete.removeAll(newSkillSet);

		if (!skillsToInsert.isEmpty()) {
			Skill skillToInsert = new Skill(0, id, skillsToInsert.toArray(new String[0]));
			employeeDAO.insertSkillByEmployeeId(id, skillToInsert);
		}

		if (!skillsToDelete.isEmpty()) {
			Skill skillToDelete = new Skill(0, id, skillsToDelete.toArray(new String[0]));
			employeeDAO.deleteSkillsByEmployeeId(id, skillToDelete);
		}

		return response;
	}
}
