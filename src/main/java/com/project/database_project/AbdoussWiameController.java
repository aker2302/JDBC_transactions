package com.project.database_project;

import com.project.database_project.DAO.ctrDAO;
import com.project.database_project.domain.Employee;
import com.project.database_project.domain.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AbdoussWiameController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField capacityInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField ratingInput;

    @FXML
    private TextField reportsresultsInput;

    @FXML
    private TextField restaurantnameInput;
    @FXML
    private TableView<Restaurant> RestaurantTbl;
    @FXML
    private TableColumn<Restaurant, Integer> capacity;
    @FXML
    private TableColumn<Restaurant, String> city;
    @FXML
    private TableColumn<Restaurant, String> rating;
    @FXML
    private TableColumn<Restaurant, String> reportsresults2;
    @FXML
    private TableColumn<Restaurant, String> restaurantname;

    private ctrDAO CtrDao=new ctrDAO();

    private List<Restaurant> restaurants = new ArrayList<>();

    @FXML
    void initialize(){
        restaurantname.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("restaurname"));
        city.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("city"));
        capacity.setCellValueFactory(new PropertyValueFactory<Restaurant,Integer>("capacity"));
        rating.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("rating"));
        reportsresults2.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("reportsresults2"));
    }

    @FXML
    void onAddRestaurant(ActionEvent event) {
        CtrDao.insertRestaurant(restaurantnameInput.getText(),cityInput.getText(),Integer.parseInt(capacityInput.getText()),ratingInput.getText(),reportsresultsInput.getText());
    }

    @FXML
    void onGetRestaurant(ActionEvent event) {
        Restaurant R = RestaurantTbl.getSelectionModel().getSelectedItem();
        restaurantnameInput.setText(R.getRestaurname());
        cityInput.setText(R.getCity());
        capacityInput.setText(String.valueOf(R.getCapacity()));
        ratingInput.setText(R.getRating());
        reportsresultsInput.setText(R.getReportsresults2());
    }

    @FXML
    void onLoadRestaurant(ActionEvent event) {
        RestaurantTbl.getItems().clear();
        restaurants = CtrDao.selectAllReataurant();
        for (Restaurant R: restaurants){
            RestaurantTbl.getItems().add(R);
        }
    }

    @FXML
    void onRemoveRestaurant(ActionEvent event) throws SQLException {
        Restaurant R = RestaurantTbl.getSelectionModel().getSelectedItem();
        CtrDao.deleteRestaurant(R.getRestaurname());
        RestaurantTbl.getItems().removeAll(RestaurantTbl.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onUpdateRestaurant(ActionEvent event) {
        Restaurant R = RestaurantTbl.getSelectionModel().getSelectedItem();
        CtrDao.updateRestaurant(restaurantnameInput.getText(),cityInput.getText(),Integer.parseInt(capacityInput.getText()),ratingInput.getText(),reportsresultsInput.getText(),R.getRestaurname());
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
    void onShowQuerries(ActionEvent event) throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AbdoussWiameQuerries-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage1.setTitle("Querries");
        stage1.setScene(scene);
        stage1.show();
    }
}
