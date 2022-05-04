package com.project.database_project;

import java.sql.SQLException;

import com.project.database_project.DAO.ctrDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    
    private ctrDAO CtrDao=new ctrDAO();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    @FXML
    void initialize() {
    	try {
			CtrDao.insertCustomer(66,"hassan", "dcheira agadir 3334","0635859478");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}