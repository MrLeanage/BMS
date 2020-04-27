
package view.OrderManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Order;
import model.OrderMenu;
import services.OrderServices;
import util.authenticate.CashierSessionHandler;
import util.authenticate.UserAuthentication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderStatusCashierController implements Initializable {
    @FXML
    AnchorPane rootpane;
    private CashierSessionHandler cashierSessionHandler = new CashierSessionHandler();

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, String> OIDColumn;

    @FXML
    private TableColumn<Order, String> OCustomerNameColumn;

    @FXML
    private TableColumn<Order, String> OTypeColumn;

    @FXML
    private TableColumn<Order, String> ODetailsColumn;

    @FXML
    private TableColumn<Order, String> ODeliveryDateColumn;

    @FXML
    private TableColumn<Order, String> ODeliveryTimeColumn;

    @FXML
    private TableColumn<Order, String> OTakenDateColumn;

    @FXML
    private TableColumn<Order, String> OTakenTimeColumn;

    @FXML
    private TableColumn<Order, Integer> OTakenByColumn;

    @FXML
    private TableColumn<Order, String> OStatusColumn;

    @FXML
    private TableColumn<Order, String> OProcessingStatusColumn;

    @FXML
    private TextField SearchTextBox;

    private static Order existingOrderModel;

    private static OrderMenu orderMenu;
    private ObservableList<String> orderTypeChoiceboxList = FXCollections.observableArrayList("Menu Item","Customized");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       loadData();
        searchTable();
    }

    @FXML
    private void LogoutSession(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.endAuthenticatedSession(event);
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
    private void loadData() {
        //getting data from main LoginController
        OrderServices orderServices = new OrderServices();

        ObservableList<Order> ordersData;
        ordersData = orderServices.loadData();

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        OCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("oCustomerName"));
        OTypeColumn.setCellValueFactory(new PropertyValueFactory<>("oType"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oDetails"));
        ODeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryDate"));
        ODeliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryTime"));
        OTakenDateColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenDate"));
        OTakenTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenTime"));
        OTakenByColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenUID"));
        OProcessingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oProcessingStatus"));
        OStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oStatus"));

        OrderTable.setItems(null);
        OrderTable.setItems(ordersData);

    }

    public void searchTable(){

        OrderServices orderServices = new OrderServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Order> sortedData = orderServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderTable.setItems(sortedData);
    }
}
