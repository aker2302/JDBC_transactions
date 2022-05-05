package com.project.database_project.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ctrDAO {

	private String jdbcURL = "jdbc:mysql://dif-mysql.ehu.es/dbi14";
	private String jdbcUsername = "DBI14";
	private String jdbcPassword = "DBI14";

	private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer(CustomerId,custname, custaddress, custphone) VALUES (?,?, ?, ?);";
	private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer(CustomerId,custname, custaddress, custphone) WHERE CustomerId= ?";
	private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer(CustomerId,custname, custaddress, custphone)";
	private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customer(CustomerId,custname, custaddress, custphone);";
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer(CustomerId,custname, custaddress, custphone) ";


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