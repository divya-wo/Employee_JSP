package com.example.controller;

import com.example.service.EmployeeService;
import com.example.service.EmployeeServiceImplementation;
import com.example.model.Employee;
import com.example.model.Skill;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet {

	private EmployeeService employeeService = new EmployeeServiceImplementation();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String[] skillsArray = request.getParameterValues("skills");
		int age = Integer.parseInt(request.getParameter("age"));
		double salary = Double.parseDouble(request.getParameter("salary"));
		String birthDateString = request.getParameter("birthdate");
		java.sql.Date birthDate = null;
		if (birthDateString != null && !birthDateString.isEmpty()) {
			try {
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsedDate = sd.parse(birthDateString); // Parse string to java.util.Date
				java.util.Date currentDate = new java.util.Date();
				if (!parsedDate.before(currentDate)) {
					request.setAttribute("errorMessage", "Birthdate must be before the current date.");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
					dispatcher.forward(request, response);
					return;
				}
				birthDate = new java.sql.Date(parsedDate.getTime()); // Convert java.util.Date to java.sql.Date
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (Integer.parseInt(request.getParameter("id")) > 0) {
			int id = Integer.parseInt(request.getParameter("id"));
			Employee emp = new Employee(id, name, age, salary, birthDate);
			Skill skill = new Skill(0, id, skillsArray);
			EmployeeService employeeService = new EmployeeServiceImplementation();
			try {
				boolean res = employeeService.updateEmployee(emp, skill);
				if (res) {
					getEmployeeData(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp?message=updatefailed");
					dispatcher.forward(request, response);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			Employee newEmployee = new Employee(0, name, age, salary, birthDate);
			Skill skill = new Skill(0, 0, skillsArray);
			int resCode = 0;
			try {
				resCode = employeeService.insertEmployee(newEmployee, skill);

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (resCode == 1) {
				getEmployeeData(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {

		case "/viewEmployees":
			getEmployeeData(request, response);
			break;
		case "delete":
			deleteEmployee(request, response);
			break;
		case "edit":
			try {
				editEmployee(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			getEmployeeData(request, response);
			break;
		}

	}

	public void getEmployeeData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Employee> employees = employeeService.getAllEmployee();
			request.setAttribute("employees", employees);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			boolean isDeleted = employeeService.deleteEmployee(id);
			if (isDeleted) {
				getEmployeeData(request, response);
			} else {
				getEmployeeData(request, response);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void editEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingEmployee = employeeService.getEmployeeById(id);
		request.setAttribute("emp", existingEmployee);
//		System.out.print(existingEmployee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
		dispatcher.forward(request, response);
	}
}
