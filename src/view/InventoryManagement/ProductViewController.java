package view.InventoryManagement;

import controller.BakeryProductController;
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
import model.BakeryProduct;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {
    @FXML
    private TableView<BakeryProduct> BakeryProductsTable;

    @FXML
    private TableColumn<BakeryProduct, String> BPIDColumn;

    @FXML
    private TableColumn<BakeryProduct, String> BPNameColumn;

    @FXML
    private TableColumn<BakeryProduct, String> BPTypeColumn;

    @FXML
    private TableColumn<BakeryProduct, Float> BPWeightPerUnitColumn;

    @FXML
    private TableColumn<BakeryProduct, String> BPDescriptionColumn;

    @FXML
    private TableColumn<BakeryProduct, Float> BPPriceColumn;

    @FXML
    private TableColumn<BakeryProduct, String> BPStatusColumn;

    @FXML
    private TextField SearchTextBox;



    private static BakeryProduct existingBakeryProductModel;

    private ObservableList<String> typeChoiceboxList = FXCollections.observableArrayList("Vegi","Egg","Fish","Chicken", "Beef","Pork","Mutton");

    private ObservableList<String> statusChoiceboxList = FXCollections.observableArrayList("Available","Not Available");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        //to auto refresh search results
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
    private void Employees(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Employee.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //internal methods
    @FXML
    private void FoodProducts(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/BakeryProducts.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void AgencyProduct(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/AgencyProduct.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Supplier(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/Supplier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Billing(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Products(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/Products.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderMenu(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuCashier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/Trash/OrderStatus.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void SalesInfo(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void playBeep(){
        //play.AddItemPlay();
    }


    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        BakeryProductController bakeryProductController = new BakeryProductController();

        ObservableList<BakeryProduct> bakeryProductsData;
        bakeryProductsData = bakeryProductController.loadData();

        //Setting cell value factory to table view
        BPIDColumn.setCellValueFactory(new PropertyValueFactory<>("bPID"));
        BPNameColumn.setCellValueFactory(new PropertyValueFactory<>("bPName"));
        BPTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bPType"));
        BPWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("bPWeight"));
        BPDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("bPDescription"));
        BPPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bPPrice"));
        BPStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bPStatus"));

        BakeryProductsTable.setItems(null);
        BakeryProductsTable.setItems(bakeryProductsData);

    }

    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{
        loadData();
    }

    public void searchTable(){

        BakeryProductController bakeryProductController = new BakeryProductController();
        //Retrieving sorted data from Main Controller
        SortedList<BakeryProduct> sortedData = bakeryProductController.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(BakeryProductsTable.comparatorProperty());
        //adding sorted and filtered data to the table
        BakeryProductsTable.setItems(sortedData);




    }

}
