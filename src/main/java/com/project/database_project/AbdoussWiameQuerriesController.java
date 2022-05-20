package com.project.database_project;

import com.project.database_project.DAO.ctrDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AbdoussWiameQuerriesController {

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
        querry1Input.setText("SELECT DISTINCT ft.nameId\n" +
                "FROM frequents AS ft\n" +
                "WHERE ft.restaurname IN(\n" +
                "       SELECT ft1.restaurname \n" +
                "       FROM frequents AS ft1\n" +
                "       WHERE ft1.nameId = 'Kevin')");
        querry2Input.setText("SELECT person.nameId\n" +
                "FROM person\n" +
                "WHERE (person.age >=22)\n" +
                "       AND person.nameId = ANY (SELECT frequents.nameId\n" +
                "  FROM frequents)");
        querry3Input.setText("SELECT DISTINCT sv.restaurname\n" +
                "FROM serves AS sv\n" +
                "WHERE \n" +
                "  11 <= ANY (SELECT sv1.price\n" +
                "             FROM serves AS sv1 \n" +
                "        WHERE sv.restaurname = sv1.restaurname)");
    }

    @FXML
    void onExecutequerry1(ActionEvent event) {
        int i = 1;
        List<String> result = CtrDao.executeQuerryRestaurant(i);
        querry1Result.setText(String.valueOf(result));
    }

    @FXML
    void onExecutequerry2(ActionEvent event) {
        int i = 2;
        List<String> result1 = CtrDao.executeQuerryRestaurant(i);
        querry2Result.setText(String.valueOf(result1));
    }

    @FXML
    void onExecutequerry3(ActionEvent event) {
        int i = 3;
        List<String> result2 = CtrDao.executeQuerryRestaurant(i);
        querry3Result.setText(String.valueOf(result2));
    }

}
