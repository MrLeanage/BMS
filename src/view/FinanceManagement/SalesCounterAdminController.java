package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Order;
import model.OrderMenu;
import model.SalesItem;
import model.User;
import services.BillingServices;
import services.OrderServices;
import services.UserServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceSessionHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SalesCounterAdminController implements Initializable {

    @FXML
    private Label TotalAmountLabel;

    @FXML
    private TableView<SalesItem> BillingTable;

    @FXML
    private TableColumn<SalesItem, String> BIDColumn;

    @FXML
    private TableColumn<SalesItem, String> BNameColumn;

    @FXML
    private TableColumn<SalesItem, String> BWeightColumn;

    @FXML
    private TableColumn<SalesItem, Float> BPriceColumn;

    @FXML
    private TableColumn<SalesItem, Integer> BQuantityColumn;

    @FXML
    private TableColumn<SalesItem, Double> BTotalColumn;

    @FXML
    private TableView<User> UserTable;

    @FXML
    private TableColumn<User, String> UIDColumn;

    @FXML
    private TableColumn<User, String> UNameColumn;

    @FXML
    private TableColumn<User, String> UStatusColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField UNameTextbox;

    @FXML
    private TextField UIDTextbox;

    @FXML
    private Label TotalItemLabel;

    @FXML
    private Label DateLabel;

    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();

    @FXML
    public Label UserNameLabel;

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceSessionHandler financeSessionHandler = new FinanceSessionHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        searchTable();
        UserNameLabel.setText(UserAuthentication.getAuthenticatedSession().getuName());
    }

    @FXML
    private void LogoutSession(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.endAuthenticatedSession(event);
    }
    @FXML
    private void ItemStock(ActionEvent event){
        adminManagementHandler.loadItemStock(event);
    }
    @FXML
    private void SalesCounter(ActionEvent event){
        adminManagementHandler.loadSalesCounter(event);
    }
    @FXML
    private void OrderStatus(ActionEvent event) {
        adminManagementHandler.loadOrderStatus(event);
    }
    @FXML
    private void Employees(ActionEvent event){
        adminManagementHandler.loadEmployees(event);
    }
    //internal methods
    @FXML
    private void SalesReport(ActionEvent event){
        financeSessionHandler.loadSalesReport(rootpane);
    }
    @FXML
    private void PurchasesReport(ActionEvent event){
        financeSessionHandler.loadPurchasesReport(rootpane);
    }
    @FXML
    private void PaySheet(ActionEvent event){
        financeSessionHandler.loadPaySheet(rootpane);
    }
    @FXML
    private void PayRoll(ActionEvent event) {
        financeSessionHandler.loadPayRoll(rootpane);
    }
    @FXML
    private void OtherExpenses(ActionEvent event){
        financeSessionHandler.loadOtherExpenses(rootpane);
    }
    @FXML
    private void IncomeStatement(ActionEvent event) {
        financeSessionHandler.loadIncomeStatement(rootpane);
    }
    @FXML
    private void clearField(){
        UIDTextbox.setText("");
        UNameTextbox.setText("");
    }
    @FXML
    private void clearSalesList(){
        salesItemLinkedList.clear();
        loadSalesDataToTable();
    }
    //load data from Main LoginController to View table
    private void loadData() {

        //getting data from Services
        UserServices userServices = new UserServices();

        ObservableList<User> usersData;
        usersData = userServices.loadData();
        //Setting cell value factory to table view
        UIDColumn.setCellValueFactory(new PropertyValueFactory<>("uID"));
        UNameColumn.setCellValueFactory(new PropertyValueFactory<>("uName"));
        UStatusColumn.setCellValueFactory(new PropertyValueFactory<>("uStatus"));

        UserTable.setItems(null);
        UserTable.setItems(usersData);
        DateLabel.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    private void loadSalesData() {
        String id = UIDTextbox.getText();
        //getting data from Services
        if(!id.isEmpty()){
            BillingServices billingServices = new BillingServices();

            ObservableList<SalesItem> salesItemsData;
            salesItemsData = billingServices.loadData(id, "Pending");
            salesItemLinkedList.addAll(salesItemsData);
            if(salesItemLinkedList.size() == 0){
                AlertPopUp.noRecordFound("User Records");
                clearField();
            }
            loadSalesDataToTable();

        }else{
            AlertPopUp.selectRow("row to Retrieve User Sales");
        }
    }
    private void loadSalesDataToTable(){
        ObservableList<SalesItem> salesItemsData = FXCollections.observableArrayList(salesItemLinkedList);
        counter();
        //Setting cell value factory to table view
        BIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIPID"));
        BNameColumn.setCellValueFactory(new PropertyValueFactory<>("sIPName"));
        BWeightColumn.setCellValueFactory(new PropertyValueFactory<>("sIPWeight"));
        BPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sIUnitPrice"));
        BQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sIPQuantity"));
        BTotalColumn.setCellValueFactory(new PropertyValueFactory<>("sITotalAmount"));

        BillingTable.setItems(null);
        BillingTable.setItems(salesItemsData);
    }
    @FXML
    private void loadSelectedData(){
        User user;
        try{
            user = UserTable.getSelectionModel().getSelectedItem();
            UIDTextbox.setText(user.getuID());
            UNameTextbox.setText(user.getuName());
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }
    private void counter(){
        TotalItemLabel.setText(String.valueOf(salesItemLinkedList.size()));
        double total = 0;
        for(SalesItem salesItem : salesItemLinkedList){
            total += salesItem.getsITotalAmount();
        }
        TotalAmountLabel.setText("Rs : "+total+"0");
    }
    @FXML
    private void claimSales(){
        if(salesItemLinkedList.size() != 0){
            BillingServices billingServices = new BillingServices();
            boolean resultVal = billingServices.updateClearance(salesItemLinkedList);
            if(resultVal){
                salesItemLinkedList.clear();
                loadSalesDataToTable();
            }
        }else{
            AlertPopUp.noRecordFound(" User Sales");
        }
    }
    private void searchTable(){
        UserServices userServices = new UserServices();
        //Retrieving sorted data from Main LoginController
        SortedList<User> sortedData = userServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(UserTable.comparatorProperty());
        //adding sorted and filtered data to the table
        UserTable.setItems(sortedData);

    }
}
