package com.main;

import com.employeeservices.Employee;
import com.employeeservices.EmployeeServices;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String menu = """
		        --------------- Employee Services ----------------
		        1. Get Employee Data By Department Name
		        2. Add Employee and get generated key
		        3. Update Employee Details
		        4. Delete Employee By ID
		        5. Batch Insert
		        6. Exit
		           Enter Your Choice
		        """;
		
		while(true) {
			
			System.out.println(menu);
			
			int choice = sc.nextInt();
			
			switch(choice) {
			
			//Get Employee Data by Department name
			case 1: {
				EmployeeServices.getEmpByDepartment("HR");
				break;
			}
			case 2: {
				EmployeeServices.addEmployees(sc);
				break;
			}
			case 3: {
				EmployeeServices.updateEmployeeDetails(sc);
				break;
			}
			case 4: {
				EmployeeServices.deleteEmployee(sc);
				break;
				
			}
			case 5: {
				EmployeeServices.batchInsert(sc);
				break;
			}
			case 6: {
				sc.close();
				System.exit(0);
			}
			default: System.out.println("INVALID INPUT");
			}
		}
		
		


	}
}
