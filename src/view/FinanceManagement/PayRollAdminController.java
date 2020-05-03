package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.*;
import services.PaySheetServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceHandler;
import util.utility.PrintReport;
import util.utility.UtilityMethod;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

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

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceHandler financeHandler = new FinanceHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChoiceBoxes();
        loadData();
        searchTable();
    }

    @FXML
    private void SalesCounter(ActionEvent event){
        adminManagementHandler.loadSalesCounter(event);
    }
    @FXML
    private void SalesReport(ActionEvent event){
        financeHandler.loadSalesReport(rootpane);
    }
    @FXML
    private void PurchasesReport(ActionEvent event){
        financeHandler.loadPurchasesReport(rootpane);
    }
    @FXML
    private void PaySheet(ActionEvent event){
        financeHandler.loadPaySheet(rootpane);
    }
    @FXML
    private void PayRoll(ActionEvent event) {
        financeHandler.loadPayRoll(rootpane);
    }
    @FXML
    private void OtherExpenses(ActionEvent event){
        financeHandler.loadOtherExpenses(rootpane);
    }
    @FXML
    private void IncomeStatement(ActionEvent event) {
        financeHandler.loadIncomeStatement(rootpane);
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

                            final Button btn = new Button("Print Slip");
                            PaySheet paySheet = new PaySheet();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        //RequestedResources resource = getTableView().getItems().get(getIndex());

                                        paySheet = getTableView().getItems().get(getIndex());
                                        System.out.println("ID :" + paySheet);
                                        ObservableList<PaySheet> paySheetObservableList = FXCollections.observableArrayList();
                                        paySheetObservableList.add(paySheet);
                                        PaySheetServices paySheetServices = new PaySheetServices();
                                        Integer resultVal = paySheetServices.insertData(paySheetObservableList);
                                        if(resultVal != 0){
                                            paySheet.setpSID(String.valueOf(resultVal));
                                            PrintReport printReport =  new PrintReport();
                                            printReport.printPaySheet(paySheet);

                                        }
                                    });
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
        PayRollPeriodLabel.setText(MonthChoiceBox.getValue() + "-"+ year);
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
