package com.project.database_project;

import java.sql.SQLException;

import com.project.database_project.DAO.ctrDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    private ctrDAO CtrDao=new ctrDAO();

    @FXML
    private Label QuerryState;

    @FXML
    private TextField CellPhone;

    @FXML
    private TextField CustName;

    @FXML
    private TextField CustomerId;

    @FXML
    private TextField CutAddress;

    @FXML
    void onAdd(ActionEvent event) {
        int CustID = Integer.valueOf(CustomerId.getText());
        String Name = CustName.getText();
        String CustPhone = CellPhone.getText();
        String Address = CutAddress.getText();
        try {
            CtrDao.insertCustomer(CustID,Name, Address,CustPhone);
            QuerryState.setText("Customer has been added to the database");
            QuerryState.setVisible(true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        QuerryState.setVisible(false);
    }
}