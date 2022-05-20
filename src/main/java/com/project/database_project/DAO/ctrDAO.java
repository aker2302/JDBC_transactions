package com.project.database_project.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.database_project.domain.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ctrDAO {

	private String jdbcURL = "jdbc:mysql://dif-mysql.ehu.es/dbi14";
	private String jdbcUsername = "DBI14";
	private String jdbcPassword = "DBI14";


    /////////////////////////////////////////// Kiouane El Mehdi ///////////////////////////////////////
	private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer(CustomerId,custname, custaddress, custphone) VALUES (?,?, ?, ?);";
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer SET CustomerId=?,custname=?, custaddress=?, custphone=? WHERE CustomerId=?";
	private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customer WHERE CustomerId=?;";
	private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer;";
	/*TRAVEL_QUERY1 = List of the Tourguide_id and the name of the tour guides who speaks Japanese or Spanish
	and have accompanied a trip that offers hotels in London.*/
	public static final String TRAVEL_QUERY1 = "SELECT DISTINCT TG.GuideId, TG.guidename "
			+ "FROM TOURGUIDE AS TG, LANGUAGES AS L, TRIP AS T "
			+ "WHERE  TG.GuideId = L.GuideId AND T.GuideId = TG.GuideId AND (L.Lang=\"Japanese\" OR L.Lang=\"Spanish\") "
			+ "AND EXISTS (SELECT *\r\n"
			+ "FROM HOTEL_TRIP AS HT, HOTEL AS H "
			+ "WHERE H.hotelcity = \"London\" AND H.HotelId=HT.HotelId AND "
			+ "HT.TripTo = T.TripTo AND HT.DepartureDate = T.DepartureDate) ;";
	/*TRAVEL_QUERY2 = List of tour guides who speak other language besides English and
	have gone more than 1 time in trips to Poland  */
	public static final String TRAVEL_QUERY2 = "SELECT tg.GuideId,tg.guidename "
			+ "FROM (tourguide tg INNER JOIN languages lan ON tg.GuideId=lan.GuideId) "
			+ "INNER JOIN trip t ON t.GuideId=tg.GuideId "
			+ "WHERE lan.Lang=\"English\" AND tg.GuideId=lan.GuideId "
			+ "AND tg.GuideId IN (SELECT tg.GuideId "
			+ "FROM tourguide as tg, languages as lan "
			+ "WHERE lan.Lang<>\"English\" AND tg.GuideId=lan.GuideId) "
			+ "AND t.TripTo='Poland' "
			+ "GROUP BY tg.GuideId "
			+ "HAVING COUNT(*)>=1; ";

	/*TRAVEL_QUERY3 =  name of the city with most offered hotels for trips to Laayoune. */
	public static final String TRAVEL_QUERY3 = "SELECT hotel.hotelcity, COUNT(*) "
			+ "from hotel inner join hotel_trip on hotel.HotelId=hotel_trip.HotelId "
			+ "where hotel_trip.TripTo='Laayoune' "
			+ "group by hotel.HotelId "
			+ "having COUNT(hotel.HotelId) >= ALL(SELECT COUNT(HotelId) "
			+ "FROM hotel_trip "
			+ "where hotel_trip.TripTo='Laayoune' "
			+ "GROUP BY HotelId);";



	/////////////////////////////////////////// Kerbal Abdellah //////////////////////////////////////////////
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employee(Fname,Minit,Lname,Ssn,Bdate,Address,Sex,Salary,Super_ssn,Dno) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE Ssn=?;";
	private static final String SELECT_ALL_EMPLOYEE = "SELECT * FROM employee;";
	private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET  Fname=? ,Minit=? ,Lname=? , Ssn=? , Bdate=?, Address=?, Sex=?,Salary=?,Super_ssn=?,Dno=? WHERE Ssn=?";
	private static final String QUERRY1_EMPLOYEE ="SELECT e.Lname FROM dbi14.employee as e WHERE NOT EXISTS(SELECT * FROM employee_customer as ec WHERE e.Ssn = ec.Emp_id );";
	private static final String QUERRY2_EMPLOYEE ="SELECT e.Fname ,e.Lname FROM dbi14.employee AS e JOIN works_on AS w ON e.Super_ssn = w.Essn WHERE Pno=2;";
	private static final String QUERRY3_EMPLOYEE ="SELECT p.Pname AS Name ,p.Plocation AS Location , d.Dname AS departement ,sum(Hours) AS Hours FROM dbi14.works_on AS w JOIN dbi14.project AS p ON w.Pno=p.Pnumber JOIN dbi14.department AS d ON p.Dnum=d.Dnumber GROUP BY Pno;";
	/////////////////////////////////////////// Abdouss Wiame ///////////////////////////////////////////////////
	private static final String INSERT_RESTAURENT_SQL = "INSERT INTO restaurant(restaurname,city,capacity,rating,reportsresults2) VALUES (?,?,?,?,?);";
	private static final String DELETE_RESTAURENT_SQL = "DELETE FROM restaurant WHERE restaurname=?;";
	private static final String SELECT_ALL_RESTAURENT = "SELECT * FROM restaurant;";
	private static final String UPDATE_RESTAURENT_SQL = "UPDATE restaurant SET  restaurname=? ,city=? ,capacity=? , rating=? , reportsresults2=? WHERE restaurname=?";
	private static final String QUERRY1_RESTAURANT = "SELECT DISTINCT ft.nameId FROM dbi14.frequents AS ft WHERE ft.restaurname IN(SELECT ft1.restaurname FROM frequents AS ft1 WHERE ft1.nameId='Kevin');";
	private static final String QUERRY2_RESTAURANT = "SELECT person.nameId FROM dbi14.person WHERE (person.age >=22) AND person.nameId = ANY(SELECT frequents.nameId FROM frequents);";
	private static final String QUERRY3_RESTAURANT = "SELECT DISTINCT sv.restaurname FROM serves AS sv WHERE 11<= ANY(SELECT sv1.price FROM serves AS sv1 WHERE sv.restaurname=sv1.restaurname);";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	public List<String> ParseData(ResultSet R) throws SQLException {
		List<String> MyList = new ArrayList<>();
		List<String> columnNames = new ArrayList<>();
		ResultSetMetaData rsmd = R.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnNames.add(rsmd.getColumnLabel(i));
		}
		int rowIndex = 0;
		while (R.next()) {
			rowIndex++;
			// collect row data as objects in a List
			List<Object> rowData = new ArrayList<>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				rowData.add(R.getObject(i));
			}
			// for test purposes, dump contents to check our results
			// (the real code would pass the "rowData" List to some other routine)
			System.out.printf("Row %d%n", rowIndex);
			for (int colIndex = 0; colIndex < rsmd.getColumnCount(); colIndex++) {
				String objType = "null";
				String objString = "";
				Object columnObject = rowData.get(colIndex);
				if (columnObject != null) {
					objString = columnObject.toString() + " ";
					objType = columnObject.getClass().getName();
					String obj = "Attribut:"+columnNames.get(colIndex) + ";Value:" + objString + "\n";
					MyList.add(obj);
				}
				System.out.printf("  %s: %s(%s)%n",
						columnNames.get(colIndex), objString, objType);
			}
		}
		return MyList;
	}
	public List<String> executeQuerry(int number){
		Connection connexion =null;

		try {
			connexion=getConnection();
			if(number == 1){
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY1_EMPLOYEE);
				// collect column names
				List<String> MyList1 = new ArrayList<>();
				MyList1 = ParseData(rs);
				return MyList1;
			} else if (number == 2) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY2_EMPLOYEE);
				List<String> MyList2 = new ArrayList<>();
				MyList2 = ParseData(rs);
				return MyList2;
			} else if (number == 3) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY3_EMPLOYEE);
				List<String> MyList3 = new ArrayList<>();
				MyList3 = ParseData(rs);
				return MyList3;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void updateEmployee(String Fname, String Minit, String Lname,int New_Ssn, LocalDate Bdate, String Address, String Sex, Double Salary, int Super_ssn, int Dno,int Old_Ssn){
		Connection connexion =null;
		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_EMPLOYEE_SQL);
			preparedStatement.setString(1,Fname);
			preparedStatement.setString(2,Minit);
			preparedStatement.setString(3,Lname);
			preparedStatement.setInt(4,New_Ssn);
			preparedStatement.setDate(5, Date.valueOf(Bdate));
			preparedStatement.setString(6,Address);
			preparedStatement.setString(7,Sex);
			preparedStatement.setDouble(8,Salary);
			preparedStatement.setInt(9,Super_ssn);
			preparedStatement.setInt(10,Dno);
			preparedStatement.setInt(11,Old_Ssn);
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
	////////////////////////////////////////// Abdouss Wiame functions/////////////////////////////////////////////////////////
	public void updateRestaurant(String New_restaurname, String city, int capacity, String rating, String reportsresults2,String Old_restaurname){
		Connection connexion =null;
		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_RESTAURENT_SQL);
			preparedStatement.setString(1,New_restaurname);
			preparedStatement.setString(2,city);
			preparedStatement.setInt(3,capacity);
			preparedStatement.setString(4,rating);
			preparedStatement.setString(5, reportsresults2);
			preparedStatement.setString(6,Old_restaurname);
			preparedStatement.executeUpdate();
			System.out.println("\nRestaurant Updated to DB  SUCCESSFULLY");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<Restaurant> selectAllReataurant(){
		Connection connexion =null;
		List<Restaurant> restaurants = new ArrayList<>();
		try {
			connexion=getConnection();
			Statement selectStmt = connexion.createStatement();
			ResultSet rs = selectStmt.executeQuery(SELECT_ALL_RESTAURENT);
			while(rs.next()){
				Restaurant R = new Restaurant(rs.getString(1),rs.getString(2),Integer.parseInt(rs.getString(3)),rs.getString(4),rs.getString(5));
				restaurants.add(R);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return restaurants;
	}
	public void deleteRestaurant(String restaurantname) throws SQLException {
		Connection connexion =null;

		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESTAURENT_SQL);
			preparedStatement.setString(1,restaurantname);
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insertRestaurant(String restaurname, String city, int capacity, String rating, String reportsresults2){
		Connection connexion =null;
		try
		{
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(INSERT_RESTAURENT_SQL);
			preparedStatement.setString(1,restaurname);
			preparedStatement.setString(2,city);
			preparedStatement.setInt(3,capacity);
			preparedStatement.setString(4,rating);
			preparedStatement.setString(5,reportsresults2);
			//executeUpdate() retourne le nombre de lignes affectées
			preparedStatement.executeUpdate();
			System.out.println("Restaurant Added to DB  SUCCESSFULLY");
		}
		catch (Exception e)
		{
			//Il vous dit ce qui s'est passé et où dans le code cela s'est produit.
			e.printStackTrace();
		}
	}
	public List<String> executeQuerryRestaurant(int number){
		Connection connexion =null;

		try {
			connexion=getConnection();
			if(number == 1){
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY1_RESTAURANT);
				// collect column names
				List<String> MyList1 = new ArrayList<>();
				MyList1 = ParseData(rs);
				return MyList1;
			} else if (number == 2) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY2_RESTAURANT);
				List<String> MyList2 = new ArrayList<>();
				MyList2 = ParseData(rs);
				return MyList2;
			} else if (number == 3) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(QUERRY3_RESTAURANT);
				List<String> MyList3 = new ArrayList<>();
				MyList3 = ParseData(rs);
				return MyList3;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////// KIOUANE El Mehdi ////////////////////////////////////////

	public void insertCustomer(int CustomerId,String custname,String custaddress,String custphone) throws SQLException
	{
		if(countRowsCustomer(CustomerId)>0)
		{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Confirmation Dialog");
	        alert.setHeaderText("Error Primary key exists");
	        Optional<ButtonType> decision = alert.showAndWait();

		}
		else
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

	public void updateCustomer(int CustomerId_new,String custname,String custaddress,String custphone,int CustomerId)
	{
		Connection connexion =null;
		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_CUSTOMER_SQL);
			preparedStatement.setInt(1,CustomerId_new);
			preparedStatement.setString(2,custname);
			preparedStatement.setString(3,custaddress);
			preparedStatement.setString(4,custphone);
			preparedStatement.setInt(5,CustomerId);
			preparedStatement.executeUpdate();
			System.out.println("Customer Updated in DB  SUCCESSFULLY");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<Customer> selectAllCustomers(){
		Connection connexion =null;
		List<Customer> customers = new ArrayList<>();
		try {
			connexion=getConnection();
			Statement selectStmt = connexion.createStatement();
			ResultSet rs = selectStmt.executeQuery(SELECT_ALL_CUSTOMERS);
			while(rs.next()){
				Customer cr = new Customer(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3),rs.getString(4));
				customers.add(cr);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return customers;
	}

	public void deleteCustomer(int CustomerId) throws SQLException {
		Connection connexion =null;

		try {
			connexion=getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CUSTOMER_SQL);
			preparedStatement.setInt(1,CustomerId);
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public List<String> executeQuerryTravel(int number){
		Connection connexion =null;

		try {
			connexion=getConnection();
			if(number == 1){
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(TRAVEL_QUERY1);
				// collect column names
				List<String> MyList1 = new ArrayList<>();
				MyList1 = ParseData(rs);
				return MyList1;
			} else if (number == 2) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(TRAVEL_QUERY2);
				List<String> MyList2 = new ArrayList<>();
				MyList2 = ParseData(rs);
				return MyList2;
			} else if (number == 3) {
				Statement selectStmt = connexion.createStatement();
				ResultSet rs = selectStmt.executeQuery(TRAVEL_QUERY3);
				List<String> MyList3 = new ArrayList<>();
				MyList3 = ParseData(rs);
				return MyList3;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int countRowsCustomer(int Id) throws SQLException {
		Connection connexion =null;
		 // select the number of rows in the table
	    Statement stmt = null;
	    ResultSet rs = null;
	    int rowCount = -1;
	    
		try {
			connexion=getConnection();
			 stmt = connexion.createStatement();
		      rs = stmt.executeQuery("SELECT COUNT(*) FROM customer WHERE CustomerId= "+Id);
		      // get the number of rows from the result set
		      rs.next();
		      rowCount = rs.getInt(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
		      rs.close();
		      stmt.close();
		    }
	    
	    return rowCount;
	  }

}