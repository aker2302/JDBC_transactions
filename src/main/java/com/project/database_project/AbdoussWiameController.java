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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        if(restaurantnameInput.getText().length()>0 && cityInput.getText().length()>0 && capacityInput.getText().length()>0 )
        {
            CtrDao.insertRestaurant(restaurantnameInput.getText(),cityInput.getText(),Integer.parseInt(capacityInput.getText()),Double.parseDouble(ratingInput.getText()),reportsresultsInput.getText());
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
    void onGetRestaurant(ActionEvent event) {
        Restaurant R = RestaurantTbl.getSelectionModel().getSelectedItem();
        if(R!=null){
            restaurantnameInput.setText(R.getRestaurname());
            cityInput.setText(R.getCity());
            capacityInput.setText(String.valueOf(R.getCapacity()));
            ratingInput.setText(String.valueOf(R.getRating()));
            reportsresultsInput.setText(R.getReportsresults2());
        }else{
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation Dialog");
            alert2.setHeaderText("Error You should select a Restaurant ");
            Optional<ButtonType> decision2 = alert2.showAndWait();
        }

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
        if(R!=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete?");
            Optional<ButtonType> decision = alert.showAndWait();

            if (decision.get() == ButtonType.OK){
                try {
                    CtrDao.deleteRestaurant(R.getRestaurname());
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                RestaurantTbl.getItems().removeAll(RestaurantTbl.getSelectionModel().getSelectedItem());

                restaurantnameInput.setText("");
                cityInput.setText("");
                capacityInput.setText("");
                ratingInput.setText("");
                reportsresultsInput.setText("");
            }
        }
        else
        {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation Dialog");
            alert2.setHeaderText("Error You should select a Employee ");
            Optional<ButtonType> decision2 = alert2.showAndWait();
        }
    }

    @FXML
    void onUpdateRestaurant(ActionEvent event) {
        if(restaurantnameInput.getText().length()>0 && cityInput.getText().length()>0 && capacityInput.getText().length()>0)
        {
            Restaurant R = RestaurantTbl.getSelectionModel().getSelectedItem();
            CtrDao.updateRestaurant(restaurantnameInput.getText(),cityInput.getText(),Integer.parseInt(capacityInput.getText()),Double.parseDouble(ratingInput.getText()),reportsresultsInput.getText(),R.getRestaurname());
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

    @FXML
    void onShowQuerries(ActionEvent event) throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AbdoussWiameQuerries-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 847, 480);
        stage1.setTitle("Querries");
        stage1.setScene(scene);
        stage1.show();
    }
}
