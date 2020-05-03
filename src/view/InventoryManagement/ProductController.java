package view.InventoryManagement;

import model.SalesItem;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.ProductServices;
import util.authenticate.CashierHandler;
import util.authenticate.UserAuthentication;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private TableView<SalesItem> ProductsTable;

    @FXML
    private TableColumn<SalesItem, String> PIDColumn;

    @FXML
    private TableColumn<SalesItem, String> PNameColumn;

    @FXML
    private TableColumn<SalesItem, String> PTypeColumn;

    @FXML
    private TableColumn<SalesItem, Float> PWeightPerUnitColumn;

    @FXML
    private TableColumn<SalesItem, Float> PPriceColumn;

    @FXML
    private TableColumn<SalesItem, String> PStatusColumn;

    @FXML
    private TextField SearchTextBox;


    @FXML
    AnchorPane rootpane;

    private CashierHandler cashierHandler = new CashierHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        //to auto refresh search results
        searchTable();
    }
    @FXML
    private void Billing(ActionEvent event){
        cashierHandler.loadBilling(event);
    }
    @FXML
    private void Products(ActionEvent event){
        cashierHandler.loadProducts(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event){
        cashierHandler.loadOrderMenu(rootpane);
    }
    @FXML
    private void Order(ActionEvent event) {
        cashierHandler.loadOrder(rootpane);
    }
    @FXML
    private void OrderStatus(ActionEvent event){
        cashierHandler.loadOrderStatus(rootpane);
    }
    @FXML
    private void SalesInfo(ActionEvent event) {
        cashierHandler.loadSalesInfo(rootpane);
    }
    @FXML
    private void playBeep(){
        //play.AddItemPlay();
    }


    //load data to View table
    private void loadData() {
        //getting data
        ProductServices productServices = new ProductServices();

        //ObservableList<SalesItem> productsData = FXCollections.observableArrayList(productServices.loadData());
        ObservableList<SalesItem> productsData = productServices.loadData();
        //Setting cell value factory to table view
        PIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIPID"));
        PNameColumn.setCellValueFactory(new PropertyValueFactory<>("sIPName"));
        PTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sIType"));
        PWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("sIPWeight"));
        PPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sIUnitPrice"));
        PStatusColumn.setCellValueFactory(new PropertyValueFactory<>("sIStatus"));

        ProductsTable.setItems(null);
        ProductsTable.setItems(productsData);

    }

    @FXML
    public void refreshTable()throws Exception{
        loadData();
    }

    public void searchTable(){
        ProductServices productServices = new ProductServices();
        //Retrieving sorted data
        SortedList<SalesItem> sortedData = productServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(ProductsTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ProductsTable.setItems(sortedData);
    }

}
