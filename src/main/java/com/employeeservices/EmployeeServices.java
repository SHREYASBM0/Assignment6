package com.employeeservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeServices {
	
	private static String url = "jdbc:sqlserver://TNS-IT-DESKTOP;instanceName=SQLEXPRESS;databaseName=TrainingJul2026;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

	public static void getAllData() {
			
			String query = "SELECT TOP 10 * FROM EMPLOYEEEE";
			
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
	
	public static void getEmpByDept(String department) {
		String query = "SELECT * FROM EMPLOYEEEE WHERE DEPARTMENT=?";
		
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
	
	

}
