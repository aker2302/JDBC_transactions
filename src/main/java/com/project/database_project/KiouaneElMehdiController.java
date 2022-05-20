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
import java.util.Optional;

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
		 if(CustomerIdtxt.getText().length()>0 && custnametxt.getText().length()>0 &&  custaddresstxt.getText().length()>0 && custphonetxt.getText().length()>0)
	   	 {
	    	 int CustomerId=Integer.parseInt(CustomerIdtxt.getText());
	    	 String custname=custnametxt.getText();
	    	 String custaddress=custaddresstxt.getText();
	    	 String custphone=custphonetxt.getText();
	    	 try {
				CtrDao.insertCustomer(CustomerId, custname, custaddress, custphone);
				CustomerTable.getItems().clear();
		    	customers=CtrDao.selectAllCustomers();
		    	for(Customer cr: customers)
		    	{
		    		CustomerTable.getItems().add(cr);
		    	}
		    	
		    	CustomerIdtxt.setText("");
	        	custnametxt.setText("");
	        	custaddresstxt.setText("");
	        	custphonetxt.setText("");
	        	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	 }
		 else
		 {
			 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	         alert.setTitle("Confirmation Dialog");
	         alert.setHeaderText("ERROR You should Fill in all the information");
	         Optional<ButtonType> decision = alert.showAndWait();
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

    /*@FXML
    void onGetCustomer(ActionEvent event) {
    	Customer cr=CustomerTable.getSelectionModel().getSelectedItem();
    	CustomerIdtxt.setText(String.valueOf(cr.getCustomerId()));
    	custnametxt.setText(cr.getCustname());
    	custaddresstxt.setText(cr.getCustaddress());
    	custphonetxt.setText(cr.getCustphone());

    }*/

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
    	
        Customer cr=null;
        cr=CustomerTable.getSelectionModel().getSelectedItem();
        if(cr!=null)
        {
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete?");
            Optional<ButtonType> decision = alert.showAndWait();
            
	        if (decision.get() == ButtonType.OK){
		    	try {
					CtrDao.deleteCustomer(cr.getCustomerId());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	CustomerTable.getItems().removeAll(CustomerTable.getSelectionModel().getSelectedItem());
		   
	        	CustomerIdtxt.setText("");
	        	custnametxt.setText("");
	        	custaddresstxt.setText("");
	        	custphonetxt.setText("");
	        }
        }
        else
        {
        	Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation Dialog");
            alert2.setHeaderText("Error You should select a customer ");
            Optional<ButtonType> decision2 = alert2.showAndWait();
        }
    }

    @FXML
    void onShowquerries(ActionEvent event) {
    	
    	 Stage stage1 = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KiouaneElMehdiQuerries-view.fxml"));
         Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load(), 1300, 600);
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
    if(CustomerIdtxt.getText().length()>0 && custnametxt.getText().length()>0 &&  custaddresstxt.getText().length()>0 && custphonetxt.getText().length()>0)
   	 {
    	 int CustomerId=Integer.parseInt(CustomerIdtxt.getText());
    	 String custname=custnametxt.getText();
    	 String custaddress=custaddresstxt.getText();
    	 String custphone=custphonetxt.getText();
    	 Customer cr = CustomerTable.getSelectionModel().getSelectedItem();
    	 
    	 if(cr!=null)
    	 {
    		 CtrDao.updateCustomer(CustomerId, custname, custaddress, custphone,cr.getCustomerId());
    		 
    	 }
    	 else
    	 {
    		 Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
             alert2.setTitle("Confirmation Dialog");
             alert2.setHeaderText("ERROR You should also select the customer you want to update in the table");
             Optional<ButtonType> decision2 = alert2.showAndWait();
    	 }
    		 
	 }
	 else
	 {
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Confirmation Dialog");
         alert.setHeaderText("ERROR You should Fill in all the information");
         Optional<ButtonType> decision = alert.showAndWait();
	 }
    		 

    }
    
    @FXML
    void initialize(){
    	setupCustomerSelection();
    	CustomerId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("CustomerId"));
    	custname.setCellValueFactory(new PropertyValueFactory<Customer,String>("custname"));
    	custaddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("custaddress"));
    	custphone.setCellValueFactory(new PropertyValueFactory<Customer,String>("custphone"));
        

    }
    
    private void setupCustomerSelection() {
    	CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    		Customer cr=CustomerTable.getSelectionModel().getSelectedItem();
    		if(cr!=null)
    		{
        	CustomerIdtxt.setText(String.valueOf(cr.getCustomerId()));
        	custnametxt.setText(cr.getCustname());
        	custaddresstxt.setText(cr.getCustaddress());
        	custphonetxt.setText(cr.getCustphone());
    		}
        });
      }

}

