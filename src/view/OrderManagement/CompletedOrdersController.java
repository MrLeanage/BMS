
package view.OrderManagement;


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
import model.Order;
import model.OrderMenu;
import services.OrderServices;
import util.authenticate.SupervisorHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CompletedOrdersController implements Initializable {

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, String> OIDColumn;

    @FXML
    private TableColumn<Order, String> OMNameColumn;

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
    private TableColumn<Order, String> OProcessingStatusColumn;

    @FXML
    private TableColumn<Order, String> OActionColumn;

    @FXML
    private TextField SearchTextBox;

    private static Order existingOrderModel;

    private static OrderMenu orderMenu;
    private ObservableList<String> orderTypeChoiceboxList = FXCollections.observableArrayList("Menu Item","Customized");
    @FXML
    AnchorPane rootpane;
    SupervisorHandler supervisorHandler = new SupervisorHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // OTypeChoiceBox.setValue("Menu Item");
        // OTypeChoiceBox.setItems(orderTypeChoiceboxList);

        try {
            loadData();
            searchTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ItemWithdraw(ActionEvent event) {
        supervisorHandler.loadItemWithdraw(event);
    }
    @FXML
    private void WithdrawedItems(ActionEvent event) {
        supervisorHandler.loadWithdrawedItems(rootpane);
    }
    @FXML
    private void PendingOrders(ActionEvent event){
        supervisorHandler.loadPendingOrders(rootpane);
    }
    @FXML
    private void OnGoingOrders(ActionEvent event) {
        supervisorHandler.loadOnGoingOrders(rootpane);
    }
    @FXML
    private void CompletedOrders(ActionEvent event) {
        supervisorHandler.loadCompletedOrders(rootpane);
    }
    @FXML
    private void CancelledOrders(ActionEvent event) {
        supervisorHandler.loadCancelledOrders(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event) {
        supervisorHandler.loadOrderMenu(rootpane);
    }
    //load data from Main LoginController to View table
    private void loadData() throws SQLException {
        //getting data from main LoginController
        OrderServices orderServices = new OrderServices();

        ObservableList<Order> ordersData;
        ordersData = orderServices.loadData("Completed");

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        OMNameColumn.setCellValueFactory(new PropertyValueFactory<>("oOMName"));
        OTypeColumn.setCellValueFactory(new PropertyValueFactory<>("oType"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oDetails"));
        ODeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryDate"));
        ODeliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryTime"));
        OTakenDateColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenDate"));
        OTakenTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenTime"));
        OProcessingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oProcessingStatus"));

        OrderTable.setItems(null);
        OrderTable.setItems(ordersData);

    }

    public void searchTable() throws SQLException {

        OrderServices orderServices = new OrderServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Order> sortedData = orderServices.searchTable(SearchTextBox, "Completed");

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderTable.setItems(sortedData);
    }
}
