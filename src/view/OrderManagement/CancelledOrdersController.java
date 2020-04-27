
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
import javafx.util.Callback;
import model.Order;
import model.OrderMenu;
import services.OrderServices;
import util.authenticate.SupervisorSessionHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CancelledOrdersController implements Initializable {

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
    SupervisorSessionHandler supervisorSessionHandler = new SupervisorSessionHandler();
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
        supervisorSessionHandler.loadItemWithdraw(event);
    }
    @FXML
    private void WithdrawedItems(ActionEvent event) {
        supervisorSessionHandler.loadWithdrawedItems(rootpane);
    }
    @FXML
    private void PendingOrders(ActionEvent event){
        supervisorSessionHandler.loadPendingOrders(rootpane);
    }
    @FXML
    private void OnGoingOrders(ActionEvent event) {
        supervisorSessionHandler.loadOnGoingOrders(rootpane);
    }
    @FXML
    private void CompletedOrders(ActionEvent event) {
        supervisorSessionHandler.loadCompletedOrders(rootpane);
    }
    @FXML
    private void CancelledOrders(ActionEvent event) {
        supervisorSessionHandler.loadCancelledOrders(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event) {
        supervisorSessionHandler.loadOrderMenu(rootpane);
    }
    //load data from Main LoginController to View table
    private void loadData() throws SQLException {
        //getting data
        OrderServices orderServices = new OrderServices();

        ObservableList<Order> ordersData;
        ordersData = orderServices.loadData("Cancelled");

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        OMNameColumn.setCellValueFactory(new PropertyValueFactory<>("oOMName"));
        OTypeColumn.setCellValueFactory(new PropertyValueFactory<>("oType"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oDetails"));
        ODeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryDate"));
        ODeliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oDeliveryTime"));
        OTakenDateColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenDate"));
        OTakenTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oTakenTime"));
        OActionColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        OProcessingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oProcessingStatus"));
        Callback<TableColumn<Order, String>, TableCell<Order, String>> parentCellFactory
                =
                new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Order, String> param) {
                        final TableCell<Order, String> cell = new TableCell<Order, String>() {

                            final Button btn = new Button("Process Order");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnMouseClicked(event -> {
                                        // student = StudentTable.getSelectionModel().getSelectedItem();
                                        //String sID = student.getsID();
                                    });
                                    btn.setOnAction(event -> {
                                        //pdf generate and status Update method here
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        OActionColumn.setCellFactory(parentCellFactory);
        OrderTable.setItems(null);
        OrderTable.setItems(ordersData);

    }

    public void searchTable() throws SQLException {

        OrderServices orderServices = new OrderServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Order> sortedData = orderServices.searchTable(SearchTextBox, "Cancelled");

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderTable.setItems(sortedData);
    }
}
