package com.employeeservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class EmployeeServices {
	
	private static String url = "jdbc:sqlserver://TNS-IT-DESKTOP;instanceName=SQLEXPRESS;databaseName=TrainingJul2026;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
	
	public static void getAllData() {
			
			String query = "SELECT TOP 10 * FROM EMPLOYEEEEE";
			
		try (
				Connection connection = DriverManager.getConnection(url);
				Statement statement = connection.createStatement();
				) {
	
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				System.out.println("id: "+result.getInt("EmpId")+",EmpName: "+result.getString("EmpName")+",Department: "+result.getString("Department")+",Salary: "+result.getInt("Salary")+",City: "+result.getString("City"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		}
	
	public static void getEmpByDepartment(String department) {
		String query = "SELECT * FROM EMPLOYEEEEE WHERE DEPARTMENT=?";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				) {
			statement.setString(1,department);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				System.out.println("id: "+result.getInt("EmpId")+",EmpName: "+result.getString("EmpName")+",Department: "+result.getString("Department")+",Salary: "+result.getInt("Salary")+",City: "+result.getString("City"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void addEmployees(Scanner sc) {
		
		Employee employee = EmployeeServices.askData(sc);
		
		if(isAlreadyThere(employee)) {
			System.out.println("Duplicate Data Insertion Not Allowed");
			return;
		}
		
		String query = "INSERT INTO EMPLOYEEEEE(EMPNAME,DEPARTMENT,SALARY,CITY) VALUES (?,?,?,?)";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				) {
			statement.setString(1,employee.getEmpName());
			statement.setString(2,employee.getDepartment());
			statement.setDouble(3,employee.getSalary());
			statement.setString(4,employee.getCity());
			Integer rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
			    ResultSet  generatedKeys = statement.getGeneratedKeys();
			    
			    if (generatedKeys.next()) {
			        int generatedId = generatedKeys.getInt(1);
			        System.out.println("Employee created with ID: " + generatedId);
			    } else {
			        System.out.println("No generated key returned.");
			    }
			   
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateEmployeeDetails(Scanner sc) {
		
		System.out.print("Enter The EmployeeId: ");
		int empId = sc.nextInt();
		System.out.println();
		

		String query = "UPDATE EMPLOYEEEEE SET EMPNAME=?,DEPARTMENT=?,SALARY=?,CITY=? WHERE EMPID=?";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				) {
			Employee empData = getEmpData(empId);
			if(empData == null){
				System.out.println("Record Not Exist");
			    return;
			}
			
			String choice;
			//For Name
			System.out.println("Name Change");
			System.out.println("---Enter N to change Name and O to Not Change---");
			System.out.println("OldName is: "+empData.getEmpName());
			choice = sc.next();
			
			if(choice.equalsIgnoreCase("N")) {
				System.out.print("NewName: ");
				String newName = sc.next();
				statement.setString(1,newName);
				System.out.println();
			} else {
				statement.setString(1,empData.getEmpName());
			}
			
			//For Department
			
			System.out.println("Department Change");
			System.out.println("---Enter N to change Dept and O to Not Change---");
			System.out.println("Old Department is: "+empData.getDepartment());
			choice = sc.next();
			
			
			if(choice.equalsIgnoreCase("N")) {
				System.out.print("New Department Name: ");
				String newName = sc.next();
				statement.setString(2,newName);
				System.out.println();
			} else {
				statement.setString(2,empData.getDepartment());
			}
			
			//For Salary
			
			System.out.println("Salary Change");
			System.out.println("---Enter N to change Salary and O to Not Change---");
			System.out.println("Old Salary is: "+empData.getSalary());
			choice = sc.next();
			
			
			if(choice.equalsIgnoreCase("N")) {
				System.out.print("New salary: ");
				Double newSalary = sc.nextDouble();
				statement.setDouble(3,newSalary);
				System.out.println();
			} else {
				statement.setDouble(3,empData.getSalary());
			}
			
			//For City
			
			System.out.println("City Change");
			System.out.println("---Enter N to change City and O to Not Change---");
			System.out.println("Old City Name is: "+empData.getCity());
			choice = sc.next();
			
			
			if(choice.equalsIgnoreCase("N")) {
				System.out.print("New City name: ");
				String newName = sc.next();
				statement.setString(4,newName);
				System.out.println();
			} else {
				statement.setString(4,empData.getCity());
			}
			
			statement.setInt(5,empId);
			
			Integer rowsAffected = statement.executeUpdate();
			
			System.out.println("Rows Affected: "+rowsAffected);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteEmployee(Scanner sc) {
		
		System.out.print("Enter The EmployeeId: ");
		int empId = sc.nextInt();
		System.out.println();
	
		if(getEmpData(empId)==null) {
			System.out.println("Record Not Exist");
			return;
		} 
		String query = "DELETE FROM EMPLOYEEEEE WHERE EMPID=?";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				) {
			statement.setInt(1,empId);
			Integer rowsAffected = statement.executeUpdate();
			
			System.out.println("Rows Affected: "+rowsAffected);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void batchInsert(Scanner sc) {
		
		String query = "INSERT INTO EMPLOYEEEEE(EMPNAME,DEPARTMENT,SALARY,CITY) VALUES (?,?,?,?)";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				) {
			System.out.print("Enter How Many Records You Want To add: ");
			int numberOfRecords = sc.nextInt();
			for(int i = 1;i <= numberOfRecords;i++) {
				
				System.out.println("--------Enter new Employee Data---------");
				
				Employee employee = askData(sc);
				
				statement.setString(1,employee.getEmpName());
				statement.setString(2,employee.getDepartment());
				statement.setDouble(3,employee.getSalary());
				statement.setString(4,employee.getCity());
				
				statement.addBatch();
				
			}
			int [] result = statement.executeBatch();
			System.out.println(result.length+" Rows are Inserted");
			
			}
		
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public static boolean isAlreadyThere(Employee employee) {
		String query = "SELECT * FROM EMPLOYEEEEE WHERE EMPNAME=? AND DEPARTMENT=? AND SALARY=? AND CITY=?";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				) {
			statement.setString(1,employee.getEmpName());
			statement.setString(2,employee.getDepartment());
			statement.setDouble(3,employee.getSalary());
			statement.setString(4,employee.getCity());
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return true;
			} 
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Employee getEmpData(int empId) {
		String query = "SELECT * FROM EMPLOYEEEEE WHERE EMPID=?";
		
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement statement = connection.prepareStatement(query);
				
				){
				
				 
			statement.setInt(1,empId);
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return new Employee(
					    result.getString("EmpName"),
					    result.getString("Department"),
					    result.getDouble("Salary"),
					    result.getString("City")
					);

			} else {
				System.out.println("Record not found");
				return null;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static Employee askData(Scanner sc) {
		
		System.out.print("Enter The Employee Name:" );
		String name = sc.next();
		System.out.println();
		sc.nextLine();
		System.out.print("Enter The Employee Department Name:" );
		String department = sc.nextLine();
		System.out.println();
		
		System.out.print("Enter The Employee Salary:" );
		Double salary = sc.nextDouble();
		System.out.println();
		sc.nextLine();
		System.out.print("Enter The Employee City:" );
		String city = sc.nextLine();
		System.out.println();
		
		return new Employee(name, department, salary, city);
		
	}
	
	

}
