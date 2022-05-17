package com.project.database_project;

import java.io.IOException;
import java.sql.SQLException;

import com.project.database_project.DAO.ctrDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void onAbdoussWiame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AbdoussWiame-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,745,444);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onKerbalAbdellah(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("KerbalAbdellah-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,745,444);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onKiouaneElMehdi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("KiouaneElMehdi-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,745,444);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
    }
}