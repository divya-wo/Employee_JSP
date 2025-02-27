package com.example.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;
import com.example.model.Employee;
import com.example.model.Skill;

public class EmployeeDAO implements EmployeeDAOInterface {

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "root");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public int insertEmployee(Employee emp, Skill skill) throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO employee (name, age, salary, birth_date) VALUES (?, ?, ?, ?)";
		String skillQuery = "INSERT INTO skills (employee_id, name) VALUES (?, ?)";
		Connection con = getConnection();
		PreparedStatement skillPs = null;
		PreparedStatement ps = null;
		ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, emp.getName());
		ps.setInt(2, emp.getAge());
		ps.setDouble(3, emp.getSalary());
		if (emp.getBirthDate() != null) {
			ps.setDate(4, (java.sql.Date) emp.getBirthDate());
		} else {
			ps.setNull(4, java.sql.Types.DATE);
		}
		int rowsaffected = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		int employeeId = 0;
		if (rs.next()) {
			employeeId = rs.getInt(1);
		}
		if (skill.getSkillName() != null && skill.getSkillName().length > 0) {
			String[] skillsArray = skill.getSkillName();
			for (String skillName : skillsArray) {
				skillPs = con.prepareStatement(skillQuery);
				skillPs.setInt(1, employeeId);
				skillPs.setString(2, skillName);
				skillPs.executeUpdate();
			}
		}
		return rowsaffected;
	}

	public List<Employee> getAllEmployee() throws SQLException, ClassNotFoundException {
		List<Employee> employees = new ArrayList<>();
		Connection con = getConnection();
		String query = "SELECT * FROM employee";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int employeeId = rs.getInt("id");
			Skill[] skills = getAllSkillsByEmployeeId(employeeId);
			String[] skillNames = new String[skills.length];
			for (int i = 0; i < skills.length; i++) {
				skillNames[i] = skills[i].getSkillName()[0]; // Extract skill name
			}
			String skillNamesString = String.join(",", skillNames);
			String name = rs.getString("name");
			int age = rs.getInt("age");
			double salary = rs.getDouble("salary");
			Date bdate = rs.getDate("birth_date");
			employees.add(new Employee(employeeId, name, skillNamesString, age, salary, bdate));
		}
		return employees;
	}

	public Skill[] getAllSkillsByEmployeeId(int employee_id) throws SQLException, ClassNotFoundException {
		List<Skill> skillList = new ArrayList<>();
		Connection con = getConnection();
		String query = "SELECT * FROM skills WHERE employee_id=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, employee_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String skillName = rs.getString("name");
			Skill skill = new Skill();
			skill.setSkillName(new String[] { skillName });
			skillList.add(skill);
		}
		return skillList.toArray(new Skill[0]);
	}

	public Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException {
		Employee employee = null;
		Connection con = getConnection();
		String query = "SELECT * FROM employee WHERE id=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String name = rs.getString("name");
			int age = rs.getInt("age");
			double salary = rs.getDouble("salary");
			Date bdate = rs.getDate("birth_date");
			Skill[] skills = getAllSkillsByEmployeeId(id);
			String[] skillNames = new String[skills.length];
			for (int i = 0; i < skills.length; i++) {
				skillNames[i] = skills[i].getSkillName()[0];
			}
			String skillNamesString = String.join(",", skillNames);
			employee = new Employee(id, name, skillNamesString, age, salary, bdate);
		}
		return employee;
	}

	public boolean deleteEmployee(int id) throws SQLException, ClassNotFoundException {
		boolean rowDeleted = false;
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE id = ?");
		ps.setInt(1, id);
		rowDeleted = ps.executeUpdate() > 0;
		PreparedStatement skillps = con.prepareStatement("DELETE FROM skills WHERE employee_id = ?");
		skillps.setInt(1, id);
		skillps.executeUpdate();
		return rowDeleted;
	}

	public boolean updateEmployee(Employee emp) throws SQLException, ClassNotFoundException {
		boolean rowUpdated = false;
		String query_update = "UPDATE employee SET name = ?, age = ?, salary = ?, birth_date = ? WHERE id = ?";
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement(query_update);
		statement.setString(1, emp.getName());
		statement.setInt(2, emp.getAge());
		statement.setDouble(3, emp.getSalary());
		if (emp.getBirthDate() != null) {
			statement.setDate(4, (java.sql.Date) emp.getBirthDate());
		} else {
			statement.setNull(4, java.sql.Types.DATE);
		}
		statement.setInt(5, emp.getId());
		rowUpdated = statement.executeUpdate() > 0;
		return rowUpdated;
	}

	public boolean deleteSkillsByEmployeeId(int employee_id, Skill skill) throws SQLException {
		boolean status = false;
		Connection con = getConnection();
		PreparedStatement ps = null;
		String[] skillArray = skill.getSkillName();
		String query = "DELETE FROM skills WHERE employee_id = ? AND name = ?";
		for (String skillName : skillArray) {
			ps = con.prepareStatement(query);
			ps.setInt(1, employee_id);
			ps.setString(2, skillName);
			status = ps.executeUpdate() > 0;
		}
		return status;
	}

	public int insertSkillByEmployeeId(int employee_id, Skill skill) throws SQLException {
		int response = 0;
		Connection con = getConnection();
		String skillQuery = "INSERT INTO skills (employee_id, name) VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(skillQuery);
		if (skill.getSkillName() != null && skill.getSkillName().length > 0) {
			String[] skillsArray = skill.getSkillName();
			for (String skillName : skillsArray) {
				ps.setInt(1, employee_id);
				ps.setString(2, skillName);
				ps.executeUpdate();
			}
		}
		return response;
	}
}
