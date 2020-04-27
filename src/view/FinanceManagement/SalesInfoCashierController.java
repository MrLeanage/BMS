
package view.FinanceManagement;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.SalesItem;
import services.BillingServices;
import util.authenticate.CashierSessionHandler;
import util.authenticate.UserAuthentication;
import util.utility.UtilityMethod;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SalesInfoCashierController implements Initializable {
    @FXML
    AnchorPane rootpane;
    private CashierSessionHandler cashierSessionHandler = new CashierSessionHandler();

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
    private TextField UIDTextBox;

    @FXML
    private TextField UNameTextBox;

    @FXML
    private TextField TotalItemsSoldTextBox;
    @FXML
    private Label DateLabel;

    @FXML
    private Label TotalAmountLabel;

    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();
    private String uID = UserAuthentication.getAuthenticatedSession().getuID();
    private String uName = UserAuthentication.getAuthenticatedSession().getuName();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }



    @FXML
    private void Billing(ActionEvent event){
        cashierSessionHandler.loadBilling(event);
    }
    @FXML
    private void Products(ActionEvent event){
        cashierSessionHandler.loadProducts(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event){
        cashierSessionHandler.loadOrderMenu(rootpane);
    }
    @FXML
    private void Order(ActionEvent event) {
        cashierSessionHandler.loadOrder(rootpane);
    }
    @FXML
    private void OrderStatus(ActionEvent event){
        cashierSessionHandler.loadOrderStatus(rootpane);
    }
    @FXML
    private void SalesInfo(ActionEvent event) {
        cashierSessionHandler.loadSalesInfo(rootpane);
    }
    //load data from Main LoginController to View table
    private void loadData(){
        ObservableList<SalesItem> salesItemsData;

        //getting data from Services
        BillingServices billingServices = new BillingServices();
        salesItemsData = billingServices.loadData(uID, "Pending");
        salesItemLinkedList.addAll(salesItemsData);
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
    private void counter(){
        DateLabel.setText(String.valueOf(LocalDate.now()));
        TotalItemsSoldTextBox.setText(String.valueOf(salesItemLinkedList.size()));
        UIDTextBox.setText(uID);
        UNameTextBox.setText(uName);
        double total = 0;
        for(SalesItem salesItem : salesItemLinkedList){
            total += salesItem.getsITotalAmount();
        }
        TotalAmountLabel.setText("Rs : "+ UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(total));
    }
}
