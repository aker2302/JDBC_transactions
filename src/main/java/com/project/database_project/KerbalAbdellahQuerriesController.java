package com.project.database_project;

import com.project.database_project.DAO.ctrDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

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
        querry2Input.setText("SELECT e.Essn , e.Salary/(count(e.Essn)+1)\n"+
                " FROM employee AS e \n"+
                "JOIN dependent AS d ON e.Ssn=d.Essn\n" +
                " GROUP BY e.Essn");
        querry3Input.setText("SELECT p.Pname AS Name ,p.Plocation AS Location\n" +
                ", d.Dname AS departement ,sum(Hours) AS Hours\n" +
                "FROM dbi14.works_on AS w\n" +
                "JOIN dbi14.project AS p ON w.Pno=p.Pnumber\n" +
                "JOIN dbi14.department AS d ON p.Dnum=d.Dnumber\n" +
                "GROUP BY Pno;");
    }

    @FXML
    void onExecutequerry1(ActionEvent event) {
        int i = 1;
        List<String> result = CtrDao.executeQuerry(i);
        querry1Result.setText(String.valueOf(result));
    }

    @FXML
    void onExecutequerry2(ActionEvent event) {
        int i = 2;
        Object result1 = CtrDao.executeQuerry(i);
        querry2Result.setText(String.valueOf(result1.toString()));
    }

    @FXML
    void onExecutequerry3(ActionEvent event) {
        int i = 3;
        Object result2 = CtrDao.executeQuerry(i);
        querry3Result.setText(String.valueOf(result2.toString()));
    }
}
