package view.FinanceManagement;


import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import services.*;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import view.EmployeeManagement.EmployeeAllowancePayPopUPController;
import view.EmployeeManagement.EmployeeController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaySheetAdminController implements Initializable {

    @FXML
    private TextField SearchTextBox;

    @FXML
    private Label DateLabel;

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, String> EIDColumn;

    @FXML
    private TableColumn<Employee, String> ENameColumn;

    @FXML
    private TableColumn<Employee, String> ENICColumn;

    @FXML
    private TableColumn<Employee, String> EGenderColumn;

    @FXML
    private TableColumn<Employee, String> ETitleColumn;

    @FXML
    private Label ENameLabel;

    @FXML
    private Label ENICLabel;

    @FXML
    private Label EYearLabel;

    @FXML
    private Label EMonthLabel;

    @FXML
    private Label ETypeLabel;

    @FXML
    private Label EAmountLabel;

    @FXML
    private Label EAllowanceLabel;

    @FXML
    private Label ETotalSalaryLabel;

    @FXML
    private Label EBankBranchLabel;

    @FXML
    private Label EAccountLabel;

    @FXML
    private Label EDateLabel;

    @FXML
    private Label EIDLabel;

    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        searchTable();
    }

    @FXML
    private void LogoutSession(ActionEvent event) throws IOException {


        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AppHome/login.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();

    }
    @FXML
    private void ItemStock(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void SalesCounter(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesCounterAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Employees(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Employee.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    //internal methods
    @FXML
    private void SalesReport(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesReportAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PurchasesReport(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PurchasesReportAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PaySheet(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PaySheetAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PayRoll(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PayRollAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OtherExpenses(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/OtherExpensesAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void IncomeStatement(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/IncomeStatementAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //Clear Fields
    private void clearFields(){
        EIDLabel.setText("");
        ENameLabel.setText("");
        ENICLabel.setText("");
        ETypeLabel.setText("");
        EAmountLabel.setText("");
        EAllowanceLabel.setText("");
        ETotalSalaryLabel.setText("");
        EYearLabel.setText("");
        EMonthLabel.setText("");
        EDateLabel.setText("");
        EBankBranchLabel.setText("");
        EAccountLabel.setText("");
    }
    //load data to View table

    private void loadData() {
        EmployeeServices employeeServices = new EmployeeServices();

        ObservableList<Employee> employeesData;
        employeesData = employeeServices.loadData();

        //Setting cell value factory to table view
        EIDColumn.setCellValueFactory(new PropertyValueFactory<>("eID"));
        ENameColumn.setCellValueFactory(new PropertyValueFactory<>("eName"));
        ENICColumn.setCellValueFactory(new PropertyValueFactory<>("eNIC"));
        EGenderColumn.setCellValueFactory(new PropertyValueFactory<>("eGender"));
        ETitleColumn.setCellValueFactory(new PropertyValueFactory<>("eTitle"));
        EmployeeTable.setItems(null);
        EmployeeTable.setItems(employeesData);
    }
    @FXML
    private void loadSelectedData() throws SQLException {
        LinkedList<Allowance> allowanceLinkedList = new LinkedList<>();
        double totalAllowancesValue = 0;
        double totalNetSalary = 0;

        Employee employee = new Employee();
        try{
            employee = EmployeeTable.getSelectionModel().getSelectedItem();

            SalarySchemeServices salarySchemeServices = new SalarySchemeServices();
            SalaryScheme salaryScheme = new SalaryScheme();
            salaryScheme = salarySchemeServices.loadSpecificData(employee.geteBSSID());

            AllowancePayServices allowancePayServices = new AllowancePayServices();
            ObservableList<Allowance> allowancesList = null;
            allowancesList = allowancePayServices.loadSpecificEmployeeAllowanceData(UtilityMethod.seperateID(employee.geteID()));

            allowanceLinkedList.addAll(allowancesList);

            for(Allowance allowance : allowanceLinkedList){
                if(allowance.getaType().equals("Fixed Value")){
                    totalAllowancesValue += allowance.getaValue();
                }else{
                    totalAllowancesValue += salaryScheme.getbSSAmount() * allowance.getaValue() / 100;
                }
            }
            totalNetSalary = totalAllowancesValue + salaryScheme.getbSSAmount();
            EIDLabel.setText(employee.geteID());
            ENameLabel.setText(employee.geteName());
            ENICLabel.setText(employee.geteNIC());
            ETypeLabel.setText(employee.geteBSSID());
            EAmountLabel.setText(salaryScheme.getbSSAmount()+"0");
            EAllowanceLabel.setText(totalAllowancesValue + "0");
            ETotalSalaryLabel.setText(totalNetSalary+"0");
            EYearLabel.setText(String.valueOf(YearMonth.now().getYear()));
            EMonthLabel.setText(String.valueOf(YearMonth.now().getMonth()));
            EDateLabel.setText(String.valueOf(LocalDate.now()));
            EBankBranchLabel.setText(employee.geteBankName());
            EAccountLabel.setText(String.valueOf(employee.geteAccNo()));
        }catch (NullPointerException ex){
           // AlertPopUp.generalError(ex);
        }
    }
    @FXML
    private void addData(ActionEvent event){
        PaySheet paySheet = new PaySheet();
        if(!(EIDLabel.getText().isEmpty()) ){
            paySheet.setpSEID(EIDLabel.getText());
            paySheet.setpSEName(ENameLabel.getText());
            paySheet.setpSNIC(ENICLabel.getText());
            paySheet.setpSBSSTitle(ETypeLabel.getText());
            paySheet.setpSBSSAmount(Double.parseDouble(EAmountLabel.getText()));
            paySheet.setpSATotalAmount(Double.parseDouble(EAllowanceLabel.getText()));
            paySheet.setpSBankName(EBankBranchLabel.getText());
            paySheet.setpSAccountNo(Long.parseLong(EAccountLabel.getText()));
            paySheet.setpSDate(EDateLabel.getText());
            ObservableList<PaySheet> paySheetObservableList = FXCollections.observableArrayList();
            paySheetObservableList.add(paySheet);
            PaySheetServices paySheetServices = new PaySheetServices();
            Boolean resultVal = paySheetServices.insertData(paySheetObservableList);
            if(resultVal){
                clearFields();
            }
        }else{
            AlertPopUp.emptyInsertionFailed("No Paysheet Record Found");
        }
    }
    @FXML
    private void addAllTableData(ActionEvent event) throws SQLException {
        ObservableList<Employee> employeeObservableList = EmployeeTable.getItems();
        ObservableList<Allowance> allowanceObservableList = FXCollections.observableArrayList();
        ObservableList<PaySheet> paySheetObservableList = FXCollections.observableArrayList();
        PaySheet paySheet = new PaySheet();
        double totalAllowancesValue = 0;
        double totalNetSalary = 0;

        for(Employee employee : employeeObservableList){
            SalarySchemeServices salarySchemeServices = new SalarySchemeServices();
            SalaryScheme salaryScheme = new SalaryScheme();
            salaryScheme = salarySchemeServices.loadSpecificData(employee.geteBSSID());

            AllowancePayServices allowancePayServices = new AllowancePayServices();
            ObservableList<Allowance> allowancesList = null;

            allowancesList = allowancePayServices.loadSpecificEmployeeAllowanceData(UtilityMethod.seperateID(employee.geteID()));

            allowanceObservableList.addAll(allowancesList);

            for(Allowance allowance : allowanceObservableList){
                if(allowance.getaType().equals("Fixed Value")){
                    totalAllowancesValue += allowance.getaValue();
                }else{
                    totalAllowancesValue += salaryScheme.getbSSAmount() * allowance.getaValue() / 100;
                }
            }
            paySheet.setpSEID(employee.geteID());
            paySheet.setpSEName(employee.geteName());
            paySheet.setpSNIC(employee.geteNIC());
            paySheet.setpSBSSTitle(employee.geteBSSID());
            paySheet.setpSBSSAmount(salaryScheme.getbSSAmount());
            paySheet.setpSATotalAmount(totalAllowancesValue);
            paySheet.setpSDate(String.valueOf(LocalDate.now()));
            paySheet.setpSBankName(employee.geteBankName());
            paySheet.setpSAccountNo(employee.geteAccNo());

            paySheetObservableList.add(paySheet);
        }
        PaySheetServices paySheetServices = new PaySheetServices();
        Boolean resultVal = paySheetServices.insertData(paySheetObservableList);
        if(resultVal){
            clearFields();
        }
    }
    private void searchTable(){
        EmployeeServices employeeServices = new EmployeeServices();
        //Retrieving sorted data
        SortedList<Employee> sortedData = employeeServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(EmployeeTable.comparatorProperty());
        //adding sorted and filtered data to the table
        EmployeeTable.setItems(sortedData);

    }
}
