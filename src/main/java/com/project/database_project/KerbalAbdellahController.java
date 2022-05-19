package com.project.database_project;

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

public class KerbalAbdellahController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button AddEmployee;

    @FXML
    private TextField Address1;

    @FXML
    private DatePicker Bdate1;

    @FXML
    private TextField Dno1;

    @FXML
    private TextField Fname1;

    @FXML
    private TextField Lname1;

    @FXML
    private TextField Minit1;

    @FXML
    private TextField Salary1;

    @FXML
    private TextField Sex1;

    @FXML
    private TextField Ssn1;

    @FXML
    private TextField Super_ssn1;

    @FXML
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableColumn<Employee, String> Address;

    @FXML
    private TableColumn<Employee, Integer> Dno;

    @FXML
    private TableColumn<Employee, String> Fname;

    @FXML
    private TableColumn<Employee, String> Lname;

    @FXML
    private TableColumn<Employee, String> Minit;

    @FXML
    private TableColumn<Employee, Double> Salary;

    @FXML
    private TableColumn<Employee, String> Sex;

    @FXML
    private TableColumn<Employee, Integer> Ssn;

    @FXML
    private TableColumn<Employee, Integer> Super_ssn;

    @FXML
    private TableColumn<Employee, LocalDate> Bdate;

    private ctrDAO CtrDao=new ctrDAO();

    private List<Employee> employees = new ArrayList<>();

    @FXML
    void onAddEmployee(ActionEvent event) {
        int Ssnint = Integer.parseInt(Ssn1.getText());
        Double SalaryDouble = Double.parseDouble(Salary1.getText());
        int SuperSsnint = Integer.parseInt(Super_ssn1.getText());
        int Dnoint = Integer.parseInt(Dno1.getText());
        LocalDate Bdate2 = Bdate1.getValue();
        CtrDao.insertEmployee(Fname1.getText(),Minit1.getText(),Lname1.getText(),Ssnint,Bdate2,Address1.getText(),Sex1.getText(),SalaryDouble,SuperSsnint,Dnoint);
    }

    @FXML
    void onUpdateEmployee(ActionEvent event) {
        int Ssnint = Integer.parseInt(Ssn1.getText());
        Double SalaryDouble = Double.parseDouble(Salary1.getText());
        int SuperSsnint = Integer.parseInt(Super_ssn1.getText());
        int Dnoint = Integer.parseInt(Dno1.getText());
        LocalDate Bdate2 = Bdate1.getValue();
        CtrDao.updateEmployee(Fname1.getText(),Minit1.getText(),Lname1.getText(),Ssnint,Bdate2,Address1.getText(),Sex1.getText(),SalaryDouble,SuperSsnint,Dnoint);
    }
    @FXML
    void onRemoveEmployee(ActionEvent event) throws SQLException {
        Employee em = EmployeeTbl.getSelectionModel().getSelectedItem();
        CtrDao.deleteEmployee(em.getSsn());
        EmployeeTbl.getItems().removeAll(EmployeeTbl.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onLoadEmployee(ActionEvent event) {
        EmployeeTbl.getItems().clear();
        employees = CtrDao.selectAllEmployee();
        for (Employee em: employees){
            EmployeeTbl.getItems().add(em);
        }
    }

    @FXML
    void onGetEmployee(ActionEvent event) {
        Employee em = EmployeeTbl.getSelectionModel().getSelectedItem();
        Fname1.setText(em.getFname());
        Minit1.setText(em.getMinit());
        Lname1.setText(em.getLname());
        Ssn1.setText(String.valueOf(em.getSsn()));
        Bdate1.setValue(em.getBdate());
        Address1.setText(em.getAddress());
        Sex1.setText(em.getSex());
        Salary1.setText(String.valueOf(em.getSalary()));
        Super_ssn1.setText(String.valueOf(em.getSuper_ssn()));
        Dno1.setText(String.valueOf(em.getDno()));
    }

    @FXML
    void initialize(){
        Fname.setCellValueFactory(new PropertyValueFactory<Employee,String>("Fname"));
        Minit.setCellValueFactory(new PropertyValueFactory<Employee,String>("Minit"));
        Lname.setCellValueFactory(new PropertyValueFactory<Employee,String>("Lname"));
        Ssn.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Ssn"));
        Bdate.setCellValueFactory(new PropertyValueFactory<Employee,LocalDate>("Bdate"));
        Address.setCellValueFactory(new PropertyValueFactory<Employee,String>("Address"));
        Sex.setCellValueFactory(new PropertyValueFactory<Employee,String>("Sex"));
        Salary.setCellValueFactory(new PropertyValueFactory<Employee,Double>("Salary"));
        Super_ssn.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Super_ssn"));
        Dno.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Dno"));
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
    void onShowquerries(ActionEvent event) throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KerbalAbdellahQuerries-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage1.setTitle("Querries");
        stage1.setScene(scene);
        stage1.show();
    }
}
