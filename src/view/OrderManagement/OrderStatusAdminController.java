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
import util.authenticate.AdminManagementHandler;
import util.authenticate.OrderSessionHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderStatusAdminController implements Initializable {

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, String> OIDColumn;

    @FXML
    private TableColumn<Order, String> OTypeColumn;

    @FXML
    private TableColumn<Order, String> ODetailsColumn;

    @FXML
    private TableColumn<Order, Integer> OQuantityColumn;

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
    private TableColumn<Order, String> OPaymentStatusColumn;

    @FXML
    private TableColumn<Order, String> OProcessingStatusColumn;

    @FXML
    private TextField SearchTextBox;

    private static Order existingOrderModel;

    private static OrderMenu orderMenu;
    private ObservableList<String> orderTypeChoiceboxList = FXCollections.observableArrayList("Menu Item","Customized");

    @FXML
    public Label UserNameLabel;

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private OrderSessionHandler orderSessionHandler = new OrderSessionHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {;
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
    private void Employees(ActionEvent event){
        adminManagementHandler.loadEmployees(event);
    }
    //internal methods

    @FXML
    private void OrderStatus(ActionEvent event) {
        adminManagementHandler.loadOrderStatus(event);
    }
    @FXML
    private void OrderMenu(ActionEvent event){
        orderSessionHandler.loadOrderMenu(rootpane);
    }
    @FXML
    private void CompletedOrders(ActionEvent event) {
        orderSessionHandler.loadCompletedOrder(rootpane);
    }

    //load data from Main LoginController to View table
    private void loadData() {
        //getting data from main LoginController
        OrderServices orderServices = new OrderServices();

        ObservableList<Order> ordersData;
        ordersData = orderServices.loadData();

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        OTypeColumn.setCellValueFactory(new PropertyValueFactory<>("oType"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oOMName"));
        OQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("oQuantity"));
        ODeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveredDate"));
        ODeliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveredTime"));
        OTakenDateColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenDate"));
        OTakenTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenTime"));
         OTakenByColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenUID"));
        OProcessingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oProcessingStatus"));
        OPaymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oStatus"));

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
