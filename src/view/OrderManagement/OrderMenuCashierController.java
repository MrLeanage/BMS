package view.OrderManagement;

import services.OrderMenuServices;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.OrderMenu;
import util.userAlerts.AlertPopUp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderMenuCashierController implements Initializable {


    @FXML
    private TableView<OrderMenu> OrderMenuTable;

    @FXML
    private TableColumn<OrderMenu, String> OMIIDColumn;

    @FXML
    private TableColumn<OrderMenu, ImageView> OMIImageColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMINameColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMIDescriptionColumn;

    @FXML
    private TableColumn<OrderMenu, Float> OMIWeightPerUnitColumn;

    @FXML
    private TableColumn<OrderMenu, Float> OMIPriceColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMIStatusColumn;

    @FXML
    private  TableColumn<OrderMenu, String> OMIActionColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField OMINameTextField;

    @FXML
    private TextField OMIWeightTextField;

    @FXML
    private Label OMINameLabel;

    @FXML
    private Label OMIWeightLabel;

    @FXML
    private TextField OMIPriceTextField;

    @FXML
    private Label OMIPriceLabel;

    @FXML
    private TextArea OMIDescriptionTextArea;

    @FXML
    private Label OMIDescriptionLabel;

    @FXML
    private ChoiceBox<String> OMIStatusChoiceBox;

    @FXML
    private Label OMIStatusLabel;

    @FXML
    private Button OMIBrowse;

    @FXML
    private Label OMIImageViewLabel;

    @FXML
    private ImageView OMIImageView;

    private static OrderMenu  existingOrderMenuProductModel;

    private static File staticFile;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadData();
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
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusCashier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void ItemWithdraw(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void WithdrawedItems(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/WithdrawedItems.fxml"));

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
    private void Order(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/Orders.fxml"));

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


    //load data from Main LoginController to View table
    private void loadData() {
        //getting data from main LoginController
        OrderMenuServices orderMenuServices = new OrderMenuServices();

        ObservableList<OrderMenu> orderMenuData;
        orderMenuData = orderMenuServices.loadData();

        //Setting cell value factory to table view
        OMIIDColumn.setCellValueFactory(new PropertyValueFactory<>("oMIID"));
        OMINameColumn.setCellValueFactory(new PropertyValueFactory<>("oMIName"));
        OMIDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("oMIDescription"));
        OMIWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("oMIWeight"));
        OMIPriceColumn.setCellValueFactory(new PropertyValueFactory<>("oMIPrice"));
        OMIStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oMIStatus"));
        OMIActionColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        OMIImageColumn.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(80);

            //Set up the Table
            TableCell<OrderMenu, ImageView> cell = new TableCell<OrderMenu, ImageView>() {
                public void updateItem(ImageView item, boolean empty) {
                    if(item != null){
                        if(!empty){
                            imageview.setImage(item.getImage());
                        }
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(null);
            cell.setGraphic(imageview);
            return cell;
        });
        OMIImageColumn.setCellValueFactory(new PropertyValueFactory<>("oMIImage"));

        Callback<TableColumn<OrderMenu, String>, TableCell<OrderMenu, String>> parentCellFactory
                =
                new Callback<TableColumn<OrderMenu, String>, TableCell<OrderMenu, String>>() {
                    @Override
                    public TableCell call(final TableColumn<OrderMenu, String> param) {
                        final TableCell<OrderMenu, String> cell = new TableCell<OrderMenu, String>() {

                            final Button btn = new Button("Place an Order");
                            OrderMenu orderMenuModel = new OrderMenu();

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
                                        AnchorPane home_page = null;
                                        try{
                                            home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/Orders.fxml"));

                                        }catch (IOException ex){
                                           Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        Scene scene = new Scene(home_page);
                                        Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();
                                        app.setScene(scene);
                                        app.show();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        OMIActionColumn.setCellFactory(parentCellFactory);

        OrderMenuTable.setItems(null);
        OrderMenuTable.setItems(orderMenuData);

    }

    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{

        loadData();
    }
    //Add Supplier


    @FXML
    public void loadSelectedModelData(){
        OrderMenu orderMenuModel;
        try{
            orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();
            existingOrderMenuProductModel = orderMenuModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }



    public void searchTable(){

        OrderMenuServices orderMenuServices = new OrderMenuServices();
        //Retrieving sorted data from Main LoginController
        SortedList<OrderMenu> sortedData = orderMenuServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderMenuTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderMenuTable.setItems(sortedData);




    }
}
