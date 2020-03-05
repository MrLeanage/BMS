package view.OrderManagement;

import controller.OrderMenuController;
import javafx.collections.FXCollections;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.OrderMenu;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderMenuCashierViewController implements Initializable {


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

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/Orders.fxml"));

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
    private void PendingOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/PendingOrders.fxml"));

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
    private void SalesInfo(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }


    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        OrderMenuController orderMenuController = new OrderMenuController();

        ObservableList<OrderMenu> orderMenuData;
        orderMenuData = orderMenuController.loadData();

        //Setting cell value factory to table view
        OMIIDColumn.setCellValueFactory(new PropertyValueFactory<>("oMIID"));
        OMIImageColumn.setCellValueFactory(new PropertyValueFactory<>("oMIImage"));
        OMINameColumn.setCellValueFactory(new PropertyValueFactory<>("oMIName"));
        OMIDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("oMIDescription"));
        OMIWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("oMIWeight"));
        OMIPriceColumn.setCellValueFactory(new PropertyValueFactory<>("oMIPrice"));
        OMIStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oMIStatus"));
        OMIActionColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<OrderMenu, String>, TableCell<OrderMenu, String>> parentCellFactory
                =
                new Callback<TableColumn<OrderMenu, String>, TableCell<OrderMenu, String>>() {
                    @Override
                    public TableCell call(final TableColumn<OrderMenu, String> param) {
                        final TableCell<OrderMenu, String> cell = new TableCell<OrderMenu, String>() {

                            final Button btn = new Button("Place an Order");

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

                                        FXMLLoader loader = new FXMLLoader();
                                        //loader.setLocation(getClass().getResource("editparentpopup.fxml"));
                                        try{
                                            loader.load();
                                        }catch (IOException ex){
                                           // Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        //EditParentPopUpController ePPC = loader.getController();
                                       // String sID = getID();
                                        //ePPC.setStudentID(sID);

                                        Parent p = loader.getRoot();
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(p));
                                        stage.setResizable(false);

                                        stage.showAndWait();
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

        OrderMenuController orderMenuController = new OrderMenuController();
        //Retrieving sorted data from Main Controller
        SortedList<OrderMenu> sortedData = orderMenuController.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderMenuTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderMenuTable.setItems(sortedData);




    }
}
