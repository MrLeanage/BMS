package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Purchase;
import services.PurchaseServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceHandler;
import util.userAlerts.AlertPopUp;
import util.utility.PrintReport;
import util.utility.UtilityMethod;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchasesReportAdminController implements Initializable {

    @FXML
    private TableView<Purchase> PurchaseTable;

    @FXML
    private TableColumn<Purchase, String> PIDColumn;

    @FXML
    private TableColumn<Purchase, String> PTypeColumn;

    @FXML
    private TableColumn<Purchase, String> IIDColumn;

    @FXML
    private TableColumn<Purchase, String> INameColumn;

    @FXML
    private TableColumn<Purchase, String> SIDColumn;

    @FXML
    private TableColumn<Purchase, String> SNameColumn;

    @FXML
    private TableColumn<Purchase, String> PDateColumn;

    @FXML
    private TableColumn<Purchase, Float> PPricePerUnitColumn;

    @FXML
    private TableColumn<Purchase, Integer> IQuantityColumn;

    @FXML
    private TableColumn<Purchase, Double> ITotalColumn;

    @FXML
    private TableColumn<Purchase, String> PStatusColumn;

    @FXML TableColumn<Purchase, String> PBankingInfoColumn;

    @FXML
    private ComboBox<String> CategoryChoiceBox;

    @FXML
    private ComboBox<Integer> YearChoiceBox;

    @FXML
    private ComboBox<String> MonthChoiceBox;

    @FXML
    private ComboBox<String> PaymentStatusChoiceBox;

    @FXML
    private Label TotalUnitsLabel;

    @FXML
    private Label TotalPurchasesLabel;

    @FXML
    private Label PurchasePeriodLabel;


    @FXML
    private TextField SearchTextBox;

    @FXML
    private Label PayForSupplierLabel;


    private LinkedList<Purchase> purchaseItemLinkedList = new LinkedList<>();
    private ObservableList<String> sortedMonths;
    private ObservableList<Integer> sortedYears;
    //defining "None" as default value to load all data, -> respectively "Agency Items", "Stock Items"
    private String category = "None";
    private String status = "Pending";
    private Integer year = UtilityMethod.getYear(String.valueOf(LocalDate.now()));
    private String month = UtilityMethod.getMonth(String.valueOf(LocalDate.now()));

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceHandler financeHandler = new FinanceHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        loadChoiceBoxes();
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
    //load purchase dates to choiceboxes and Chart
    private void loadChoiceBoxes(){

        try{
            ObservableList<Integer> unSortedYears = FXCollections.observableArrayList();
            ObservableList<String> unSortedMonths = FXCollections.observableArrayList();

            ObservableList<Integer> choiceBoxYears = FXCollections.observableArrayList();
            ObservableList<String> choiceBoxMonths = FXCollections.observableArrayList();

            ObservableList<String> categoryList = FXCollections.observableArrayList("All Items","Agency Items", "Stock Items");
            PurchaseServices purchaseServices = new PurchaseServices();
            //getting all purchases
            ObservableList<Purchase> dateObservableList;
            dateObservableList = purchaseServices.loadData();

            for(Purchase purchase : dateObservableList){
                //Adding dates to observable list
                unSortedYears.add(UtilityMethod.getYear(purchase.getpPurchaseDate()));
                unSortedMonths.add(UtilityMethod.getMonth(purchase.getpPurchaseDate()));
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

            //default value
            CategoryChoiceBox.setValue("All Purchases");
            CategoryChoiceBox.setItems(categoryList);

            PurchasePeriodLabel.setText(month + " "+ year + " - "+"Payment Pending(All Purchases)");

            //Adding Values to Payment Status ChoiceBox
            ObservableList<String> paymentStatusList = FXCollections.observableArrayList("Pending Payment", "Processing Payment","Paid Payment" );
            PaymentStatusChoiceBox.setValue("Pending Payment");
            PaymentStatusChoiceBox.setItems(paymentStatusList);
        }catch(NullPointerException ex){

        }

    }

    //load data to View table
    private void loadData() {
        //getting data
        PurchaseServices purchaseServices = new PurchaseServices();
        ObservableList<Purchase> purchasesData;
        purchasesData = purchaseServices.loadData(status, year, month, category);
        purchaseItemLinkedList.clear();
        purchaseItemLinkedList.addAll(purchasesData);

        //Setting cell value factory to table view
        PIDColumn.setCellValueFactory(new PropertyValueFactory<>("pID"));
        PTypeColumn.setCellValueFactory(new PropertyValueFactory<>("pType"));
        IIDColumn.setCellValueFactory(new PropertyValueFactory<>("pItemID"));
        INameColumn.setCellValueFactory(new PropertyValueFactory<>("pItemName"));
        SIDColumn.setCellValueFactory(new PropertyValueFactory<>("pSupplierID"));
        SNameColumn.setCellValueFactory(new PropertyValueFactory<>("pSupplierName"));
        PDateColumn.setCellValueFactory(new PropertyValueFactory<>("pPurchaseDate"));
        PPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("pPricePerUnit"));
        IQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("pItemQuantity"));
        ITotalColumn.setCellValueFactory(new PropertyValueFactory<>("pItemTotal"));
        PStatusColumn.setCellValueFactory(new PropertyValueFactory<>("pStatus"));
        Callback<TableColumn<Purchase, String>, TableCell<Purchase, String>> parentCellFactory
                =
                new Callback<TableColumn<Purchase, String>, TableCell<Purchase, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Purchase, String> param) {
                        final TableCell<Purchase, String> cell = new TableCell<Purchase, String>() {

                            final Button btn = new Button("Update Banking Info");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Purchase purchase = getTableView().getItems().get(getIndex());

                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("PurchaseReportBankingInfoPopUP.fxml"));
                                        try{
                                            loader.load();
                                        }catch (IOException ex){
                                           Logger.getLogger(PurchaseReportBankingInfoPayPopUPController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        PurchaseReportBankingInfoPayPopUPController purchaseReportBankingInfoPayPopUPController = loader.getController();
                                        purchaseReportBankingInfoPayPopUPController.setPurchaseInfo(purchase);


                                        Parent p = loader.getRoot();
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(p));
                                        stage.setResizable(false);

                                        stage.showAndWait();
                                        loadData();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        PBankingInfoColumn.setCellFactory(parentCellFactory);
        PurchaseTable.setItems(null);
        PurchaseTable.setItems(purchasesData);
        calculateSales();
    }
    @FXML
    private  void loadFilteredData(ActionEvent event){
        year = YearChoiceBox.getValue();

        if(MonthChoiceBox.getValue().equals("All Months")){
            month = "None";
        }else{
            month = MonthChoiceBox.getValue();
        }

        if(CategoryChoiceBox.getValue().equals("Agency Items")){
            category = "Agency";
        }else if(CategoryChoiceBox.getValue().equals("Stock Items")){
            category = "Stock";
        }else{
            category = "None";
        }
        if(PaymentStatusChoiceBox.getValue().equals("Pending Payment")){
            status = "Pending";
        }else if(PaymentStatusChoiceBox.getValue().equals("Processing Payment")){
            status = "Processing";
        }else{
            status = "Paid";
        }
        PurchasePeriodLabel.setText(MonthChoiceBox.getValue() + " "+ year + " - "+status + "(" +CategoryChoiceBox.getValue() + ")");

        loadData();
        searchTable();

    }
    @FXML
    private void resetFilters(){
        year = UtilityMethod.getYear(String.valueOf(LocalDate.now()));
        month = UtilityMethod.getMonth(String.valueOf(LocalDate.now()));
        category = "None";
        //status = "Pending";
        YearChoiceBox.setValue(year);
        MonthChoiceBox.setValue(month);
        CategoryChoiceBox.setValue("All Purchases");
        PaymentStatusChoiceBox.setValue("Pending Payment");

        loadData();
        PurchasePeriodLabel.setText(MonthChoiceBox.getValue() + " "+ year + " - "+status + "(" +CategoryChoiceBox.getValue() + ")");

    }
    private void calculateSales(){
        purchaseItemLinkedList.clear();
        purchaseItemLinkedList.addAll(PurchaseTable.getItems());

        double totalSales = 0;
        int totalItems = 0;
        for(Purchase purchase : purchaseItemLinkedList){
            totalItems ++;
            totalSales += purchase.getpItemTotal();
        }
        TotalPurchasesLabel.setText("Rs : "+ UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalSales));
        TotalUnitsLabel.setText(totalItems +" Items");
    }
    private void calculateSearchSales(){
        SearchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
            purchaseItemLinkedList.clear();
            purchaseItemLinkedList.addAll(PurchaseTable.getItems());
            double totalSales = 0;
            int totalItems = 0;
            for(Purchase purchase : purchaseItemLinkedList){
                totalItems ++;
                totalSales += purchase.getpItemTotal();
            }
            TotalPurchasesLabel.setText("Rs : "+ UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalSales));
            TotalUnitsLabel.setText(totalItems +" Items");
        });
    }
    @FXML
    private void generateReportForSortedData(){
        ObservableList<String> idStringList = FXCollections.observableArrayList();
        ObservableList<String> sortedIDStringList = FXCollections.observableArrayList();
        ObservableList<Purchase> sortedPurchaseList = FXCollections.observableArrayList();

        Purchase sortedPurchaseData = new Purchase();
        ObservableList<Purchase> purchaseObservableList = PurchaseTable.getItems();
        if(purchaseObservableList.size() > 0){
            for(Purchase purchase : purchaseItemLinkedList){
                idStringList.add(purchase.getpSupplierID());
            }
            sortedIDStringList = UtilityMethod.removeStringDuplicates(idStringList);

            for(String id : sortedIDStringList){
                float totalAmount = 0;
                for(Purchase purchase : purchaseItemLinkedList){
                    if(id.equals(purchase.getpSupplierID())){
                        totalAmount += purchase.getpItemTotal();
                        sortedPurchaseData = purchase;
                        sortedPurchaseData.setpItemTotal(totalAmount);
                    }
                }
                sortedPurchaseList.add(sortedPurchaseData);
            }
            if(category.equals("Pending")){
                PurchaseServices purchaseServices = new PurchaseServices();
                Boolean resultVal = purchaseServices.updatePurchaseStatus(purchaseItemLinkedList);
                if(resultVal){

                    loadData();
                    searchTable();
                }
            }
            PrintReport printReport = new PrintReport();
            printReport.printPurchaseReport(year,MonthChoiceBox.getValue(), CategoryChoiceBox.getValue(), status, sortedPurchaseList);

        }else{
            AlertPopUp.noRecordFound("No records found to generate Purchase Report");
        }




    }

    public void searchTable(){

        PurchaseServices purchaseServices = new PurchaseServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Purchase> sortedData = purchaseServices.searchTable(SearchTextBox, status, year, month, category);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(PurchaseTable.comparatorProperty());
        //adding sorted and filtered data to the table
        PurchaseTable.setItems(sortedData);
        calculateSearchSales();
    }


}
