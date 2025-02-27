package com.example.model;

import java.util.Date;

public class Employee {
	private int id;
	private String name;
	private String skills;
	int age;
	double salary;
	Date birthDate;
	
	public Employee() {}
	
	public Employee(int id, String name,String skills, int age, double salary, Date birthDate) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.age = age;
        this.salary = salary;
        this.birthDate = birthDate;
    }
	
	 public Employee(int id, String name, int age, double salary, Date birthDate) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	        this.salary = salary;
	        this.birthDate = birthDate;
	 }
	 public String getSkills() {
		    return this.skills;
		}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age=age;
	}
	public double getSalary() {
		return this.salary;
	}
	public void setSalary(double salary) {
		this.salary=salary;
	}
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(Date birthdate) {
		this.birthDate=birthdate;
	}
	@Override
	public String toString() {
	    return "Employee [ID=" + id + ", Name=" + name + ", Skills=" + skills + 
	           ", Age=" + age + ", Salary=" + salary + ", Birth Date=" + birthDate + "]";
	}


}
