package com.project.database_project;

import com.project.database_project.domain.Customer;
import com.project.database_project.domain.Employee;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.project.database_project.DAO.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KiouaneElMehdiController {
	
	
	 private Stage stage;
	 private Scene scene;
	 private Parent root;

	 @FXML
	    private Button AddCustomer;
	 
	 @FXML
	    private TableView<Customer> CustomerTable;

	    @FXML
	    private TableColumn<Customer, Integer> CustomerId;

	    @FXML
	    private TextField CustomerIdtxt;

	    @FXML
	    private Button UpdateCustomer;

	    @FXML
	    private TableColumn<Customer, String> custaddress;

	    @FXML
	    private TextField custaddresstxt;

	    @FXML
	    private TableColumn<Customer, String> custname;

	    @FXML
	    private TextField custnametxt;

	    @FXML
	    private TableColumn<Customer, String> custphone;

	    @FXML
	    private TextField custphonetxt;
    
    private ctrDAO CtrDao=new ctrDAO();

    private List<Customer> customers = new ArrayList<>();

    @FXML
    void onAddCustomer(ActionEvent event) {
    	
    	 int CustomerId=Integer.parseInt(CustomerIdtxt.getText());
    	 String custname=custnametxt.getText();
    	 String custaddress=custaddresstxt.getText();
    	 String custphone=custphonetxt.getText();
    	 try {
			CtrDao.insertCustomer(CustomerId, custname, custaddress, custphone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,538,407);
        stage.setScene(scene);
        stage.show();
}

    @FXML
    void onGetCustomer(ActionEvent event) {
    	Customer cr=CustomerTable.getSelectionModel().getSelectedItem();
    	CustomerIdtxt.setText(String.valueOf(cr.getCustomerId()));
    	custnametxt.setText(cr.getCustname());
    	custaddresstxt.setText(cr.getCustaddress());
    	custphonetxt.setText(cr.getCustphone());

    }

    @FXML
    void onLoadCustomer(ActionEvent event) {
    	CustomerTable.getItems().clear();
    	customers=CtrDao.selectAllCustomers();
    	for(Customer cr: customers)
    	{
    		CustomerTable.getItems().add(cr);
    	}
    }

    @FXML
    void onRemoveCustomer(ActionEvent event) {
    	
    	Customer cr=CustomerTable.getSelectionModel().getSelectedItem();
    	try {
			CtrDao.deleteCustomer(cr.getCustomerId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	CustomerTable.getItems().removeAll(CustomerTable.getSelectionModel().getSelectedItem());

    }

    @FXML
    void onShowquerries(ActionEvent event) {
    	
    	 Stage stage1 = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KiouaneElMehdiQuerries-view.fxml"));
         Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load(), 800, 480);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         stage1.setTitle("Querries");
         stage1.setScene(scene);
         stage1.show();

    }

    @FXML
    void onUpdateCustomer(ActionEvent event) {
    	
    	 int CustomerId=Integer.parseInt(CustomerIdtxt.getText());
    	 String custname=custnametxt.getText();
    	 String custaddress=custaddresstxt.getText();
    	 String custphone=custphonetxt.getText();
    	 
    	 CtrDao.updateCustomer(CustomerId, custname, custaddress, custphone);

    }
    
    @FXML
    void initialize(){
    	CustomerId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("CustomerId"));
    	custname.setCellValueFactory(new PropertyValueFactory<Customer,String>("custname"));
    	custaddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("custaddress"));
    	custphone.setCellValueFactory(new PropertyValueFactory<Customer,String>("custphone"));
        

    }

}

