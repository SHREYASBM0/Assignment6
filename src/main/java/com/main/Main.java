package com.main;

import com.employeeservices.EmployeeServices;

public class Main {
	public static void main(String[] args) {
		
		//Get Employee Data by Department name
		EmployeeServices.getEmpByDept("HR");
	}
}
