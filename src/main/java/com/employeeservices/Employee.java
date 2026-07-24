package com.employeeservices;

public class Employee {
	private String empName;
	private String department;
	private Double salary;
	private String city;
	
	public Employee(String empName, String department, Double salary, String city) {
		super();
		this.empName = empName;
		this.department = department;
		this.salary = salary;
		this.city = city;
	}

	public String getEmpName() {
		return empName;
	}

	public String getDepartment() {
		return department;
	}

	public Double getSalary() {
		return salary;
	}

	public String getCity() {
		return city;
	}
	
	
	
	
}
