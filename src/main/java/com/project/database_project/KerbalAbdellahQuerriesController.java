package com.project.database_project;

import com.project.database_project.DAO.ctrDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class KerbalAbdellahQuerriesController {

    @FXML
    private TextArea querry1Input;

    @FXML
    private TextArea querry1Result;

    @FXML
    private TextArea querry2Input;

    @FXML
    private TextArea querry2Result;

    @FXML
    private TextArea querry3Input;

    @FXML
    private TextArea querry3Result;

    private ctrDAO CtrDao=new ctrDAO();

    @FXML
    void initialize(){
        querry1Input.setDisable(true);
        querry2Input.setDisable(true);
        querry3Input.setDisable(true);
        querry1Input.setText("SELECT e.Lname \n" +
                "FROM dbi14.employee as e\n" +
                "WHERE NOT EXISTS(\n" +
                "        SELECT *\n" +
                "        FROM employee_customer as ec\n" +
                "        WHERE e.Ssn = ec.Emp_id\n" +
                "        );");
        querry2Input.setText("");
        querry3Input.setText("");
    }

    @FXML
    void onExecutequerry1(ActionEvent event) {
        int i = 1;
        Object result = CtrDao.executeQuerry(i);
        querry1Result.setText(String.valueOf(result));
    }

    @FXML
    void onExecutequerry2(ActionEvent event) {

    }

    @FXML
    void onExecutequerry3(ActionEvent event) {

    }
}
