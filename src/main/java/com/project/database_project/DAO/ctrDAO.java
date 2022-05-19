package com.project.database_project.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.project.database_project.domain.*;

public class ctrDAO {

	private String jdbcURL = "jdbc:mysql://dif-mysql.ehu.es/dbi14";
	private String jdbcUsername = "DBI14";
	private String jdbcPassword = "DBI14";


    /////////////////////////////////////////// Kiouane El Mehdi ///////////////////////////////////////
	private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer(CustomerId,custname, custaddress, custphone) VALUES (?,?, ?, ?);";
	private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer(CustomerId,custname, custaddress, custphone) WHERE CustomerId= ?";
	private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer(CustomerId,custname, custaddress, custphone)";
	private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customer(CustomerId,custname, custaddress, custphone);";
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer(CustomerId,custname, custaddress, custphone) ";

	/////////////////////////////////////////// Kerbal Abdellah //////////////////////////////////////////////
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employee(Fname,Minit,Lname,Ssn,Bdate,Address,Sex,Salary,Super_ssn,Dno) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE Ssn=?;";
	private static final String SELECT_ALL_EMPLOYEE = "SELECT * FROM employee;";
	private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET  Fname=? ,Minit=? ,Lname=? , Bdate=?, Address=?, Sex=?,Salary=?,Super_ssn=?,Dno=? WHERE Ssn=?";
	private static final String QUERRY1_EMPLOYEE ="SELECT e.Lname FROM dbi14.employee as e WHERE NOT EXISTS(SELECT * FROM employee_customer as ec WHERE e.Ssn = ec.Emp_id );";
	private static final String QUERRY2_EMPLOYEE ="";
	private static final String QUERRY3_EMPLOYEE ="";
	/////////////////////////////////////////// Abdouss Wiame /////////////////////////////////////////////////

	//fonction pour  etablir la connexion avec la bd
	protected Connection getConnection()
	{
		Connection connexion=null;
		// Chargement du driver java connector
		//s'il n'st pas trouver il cree une exception
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("JDBC Connector ERROR");
		}

		try {
			//la connexion a la bdd- la methode:jdbc - bdd - serveur/db -user-pass
			connexion = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			System.out.print("DB Connection SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB Connection ERROR");
		}
		return connexion;

	}
    ////////////////////////////////////////// Kerbal Abdellah functions ////////////////////////////////////
	public Object executeQuerry(int number){
		Connection connexion =null;

		try {
			connexion=getConnection();
			if(number == 1){
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY1_EMPLOYEE);
				return rs;
			} else if (number == 2) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY2_EMPLOYEE);
				return rs;
			} else if (number == 3) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY3_EMPLOYEE);
				Object myquerry = rs;
				return myquerry.toString();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void updateEmployee(String Fname, String Minit, String Lname, int Ssn, LocalDate Bdate, String Address, String Sex, Double Salary, int Super_ssn, int Dno){
		Connection connexion =null;
		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_EMPLOYEE_SQL);
			preparedStatement.setString(1,Fname);
			preparedStatement.setString(2,Minit);
			preparedStatement.setString(3,Lname);
			preparedStatement.setDate(4, Date.valueOf(Bdate));
			preparedStatement.setString(5,Address);
			preparedStatement.setString(6,Sex);
			preparedStatement.setDouble(7,Salary);
			preparedStatement.setInt(8,Super_ssn);
			preparedStatement.setInt(9,Dno);
			preparedStatement.setInt(10,Ssn);
			preparedStatement.executeUpdate();
			System.out.println("Employee Updated to DB  SUCCESSFULLY");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void deleteEmployee(int Ssn) throws SQLException {
		Connection connexion =null;

		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_EMPLOYEE_SQL);
			preparedStatement.setInt(1,Ssn);
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<Employee> selectAllEmployee(){
		Connection connexion =null;
		List<Employee> employee = new ArrayList<>();
		try {
			connexion=getConnection();
			Statement selectStmt = connexion.createStatement();
			ResultSet rs = selectStmt.executeQuery(SELECT_ALL_EMPLOYEE);
			while(rs.next()){
				Employee em = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),Integer.parseInt(rs.getString(4)),LocalDate.parse(rs.getString(5)),rs.getString(6),rs.getString(7),Double.parseDouble(rs.getString(8)),Integer.parseInt(rs.getString(9)),Integer.parseInt(rs.getString(10)));
				employee.add(em);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return employee;
	}
	public void insertEmployee(String Fname, String Minit, String Lname, int Ssn, LocalDate Bdate, String Address, String Sex, Double Salary, int Super_ssn, int Dno){
		Connection connexion =null;
		try
		{
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(INSERT_EMPLOYEE_SQL);
			preparedStatement.setString(1,Fname);
			preparedStatement.setString(2,Minit);
			preparedStatement.setString(3,Lname);
			preparedStatement.setInt(4,Ssn);
			preparedStatement.setDate(5, Date.valueOf(Bdate));
			preparedStatement.setString(6,Address);
			preparedStatement.setString(7,Sex);
			preparedStatement.setDouble(8,Salary);
			preparedStatement.setInt(9,Super_ssn);
			preparedStatement.setInt(10,Dno);
			//executeUpdate() retourne le nombre de lignes affectées
			preparedStatement.executeUpdate();
			System.out.println("Employee Added to DB  SUCCESSFULLY");
		}
		catch (Exception e)
		{
			//Il vous dit ce qui s'est passé et où dans le code cela s'est produit.
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void insertCustomer(int CustomerId,String custname,String custaddress,String custphone) throws SQLException
	{
		Connection connexion =null;
		try
		{
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(INSERT_CUSTOMER_SQL);
			preparedStatement.setInt(1,CustomerId);
			preparedStatement.setString(2,custname);
			preparedStatement.setString(3,custaddress);
			preparedStatement.setString(4,custphone);
			//executeUpdate() retourne le nombre de lignes affectées
			preparedStatement.executeUpdate();
			System.out.println("Customer Added to DB  SUCCESSFULLY");
		}
		catch (Exception e)
		{
			//Il vous dit ce qui s'est passé et où dans le code cela s'est produit.
			e.printStackTrace();
		}
	}


}