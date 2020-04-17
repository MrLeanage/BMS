package view.FinanceManagement;


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
import services.AllowancePayServices;
import services.EmployeeServices;
import services.PaySheetServices;
import services.SalarySchemeServices;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PayRollAdminController implements Initializable {

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TableView<PaySheet> PayRollTable;

    @FXML
    private TableColumn<PaySheet, String> PayIDColumn;

    @FXML
    private TableColumn<PaySheet, String> PayEIDColumn;

    @FXML
    private TableColumn<PaySheet, String> PayENameColumn;

    @FXML
    private TableColumn<PaySheet, String> PayENICColumn;

    @FXML
    private TableColumn<PaySheet, String> PayBasicSalaryTitle;

    @FXML
    private TableColumn<PaySheet, Double> PayBasicSalaryColumn;

    @FXML
    private TableColumn<PaySheet, Double> PayTotalAllowancesColumn;

    @FXML
    private TableColumn<PaySheet, Double> PayNetSalaryColumn;

    @FXML
    private TableColumn<PaySheet, String> PayDateColumn;

    @FXML
    private TableColumn<PaySheet, String> PayBankColumn;

    @FXML
    private TableColumn<PaySheet, String> PayAccNoColumn;

    @FXML
    private TableColumn<PaySheet, String> PayActionColumn;

    @FXML
    private ComboBox<Integer> YearChoiceBox;

    @FXML
    private ComboBox<String> MonthChoiceBox;

    @FXML
    private Label TotalPaidLabel;

    @FXML
    private Label PayRollPeriodLabel;
    private LinkedList<PaySheet> paySheetLinkedList = new LinkedList<>();
    private Integer year = UtilityMethod.getYear(String.valueOf(LocalDate.now()));
    private String month = UtilityMethod.getMonth(String.valueOf(LocalDate.now()));

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
    //load sales dates to choiceboxes and Chart
    private void loadChoiceBoxes(){
        ObservableList<String> sortedMonths;
        ObservableList<Integer> sortedYears;

        ObservableList<Integer> unSortedYears = FXCollections.observableArrayList();
        ObservableList<String> unSortedMonths = FXCollections.observableArrayList();

        ObservableList<Integer> choiceBoxYears = FXCollections.observableArrayList();
        ObservableList<String> choiceBoxMonths = FXCollections.observableArrayList();

        PaySheetServices paySheetServices = new PaySheetServices();
        //getting all Sales Items with billing Date
        ObservableList<PaySheet> dateObservableList;
        dateObservableList = paySheetServices.loadData();

        for(PaySheet paySheet : dateObservableList){
            //Adding dates to observable list
            unSortedYears.add(UtilityMethod.getYear(paySheet.getpSDate()));
            unSortedMonths.add(UtilityMethod.getMonth(paySheet.getpSDate()));
        }
        //setting sorted data for Table sorting choice boxes
        sortedYears = UtilityMethod.removeIntegerDuplicates(unSortedYears);
        sortedMonths = UtilityMethod.removeStringDuplicates(unSortedMonths);

        choiceBoxMonths.addAll(sortedMonths);
        choiceBoxYears.addAll(sortedYears);

        //default value
        MonthChoiceBox.setValue(UtilityMethod.getMonth(String.valueOf(LocalDate.now())));
        choiceBoxMonths.add("All Months");
        MonthChoiceBox.setItems(choiceBoxMonths);

        //default value
        YearChoiceBox.setValue(UtilityMethod.getYear(String.valueOf(LocalDate.now())));
        YearChoiceBox.setItems(choiceBoxYears);

        PayRollPeriodLabel.setText(month + "-"+ year);

    }
    //load data to View table
    private void loadData() {
        PaySheetServices paySheetServices = new PaySheetServices();

        ObservableList<PaySheet> paySheetsData;
        paySheetsData = paySheetServices.loadData(year, month);
        paySheetLinkedList.addAll(paySheetsData);
        //Setting cell value factory to table view
        PayIDColumn.setCellValueFactory(new PropertyValueFactory<>("pSID"));
        PayEIDColumn.setCellValueFactory(new PropertyValueFactory<>("pSEID"));
        PayENameColumn.setCellValueFactory(new PropertyValueFactory<>("pSEName"));
        PayENICColumn.setCellValueFactory(new PropertyValueFactory<>("pSNIC"));
        PayBasicSalaryTitle.setCellValueFactory(new PropertyValueFactory<>("pSBSSTitle"));
        PayBasicSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("pSBSSAmount"));
        PayTotalAllowancesColumn.setCellValueFactory(new PropertyValueFactory<>("pSATotalAmount"));
        PayNetSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("pSNetSalary"));
        PayDateColumn.setCellValueFactory(new PropertyValueFactory<>("pSDate"));
        PayBankColumn.setCellValueFactory(new PropertyValueFactory<>("pSBankName"));
        PayAccNoColumn.setCellValueFactory(new PropertyValueFactory<>("pSAccountNo"));
        PayActionColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        Callback<TableColumn<PaySheet, String>, TableCell<PaySheet, String>> parentCellFactory
                =
                new Callback<TableColumn<PaySheet, String>, TableCell<PaySheet, String>>() {
                    @Override
                    public TableCell call(final TableColumn<PaySheet, String> param) {
                        final TableCell<PaySheet, String> cell = new TableCell<PaySheet, String>() {

                            final Button btn = new Button("Print PaySheet");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        PayActionColumn.setCellFactory(parentCellFactory);

        PayRollTable.setItems(null);
        PayRollTable.setItems(paySheetsData);
        calculatePayRoll();
    }
    @FXML
    private  void loadFilteredData(ActionEvent event){
        year = YearChoiceBox.getValue();

        if(MonthChoiceBox.getValue().equals("All Months")){
            month = "None";
        }else{
            month = MonthChoiceBox.getValue();
        }
        PayRollPeriodLabel.setText(month + "-"+ year);
        loadData();
        searchTable();

    }
    private void calculatePayRoll(){
        paySheetLinkedList.clear();
        paySheetLinkedList.addAll(PayRollTable.getItems());

        double totalSales = 0;
        for(PaySheet paySheet : paySheetLinkedList){
            totalSales += paySheet.getpSNetSalary();
        }
        TotalPaidLabel.setText("Rs : "+ UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalSales));
    }
    private void calculateSearchPayRoll(){
        SearchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
            calculatePayRoll();
        });
    }
    private void searchTable(){
        PaySheetServices paySheetServices = new PaySheetServices();
        //Retrieving sorted data
        SortedList<PaySheet> sortedData = paySheetServices.searchTable(SearchTextBox, year, month);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(PayRollTable.comparatorProperty());
        //adding sorted and filtered data to the table
        PayRollTable.setItems(sortedData);
        calculateSearchPayRoll();
    }
}
