package view.OrderManagement;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Allowance;
import model.Employee;
import model.Order;
import model.OrderMenu;
import services.AllowancePayServices;
import services.OrderMenuServices;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import view.EmployeeManagement.EmployeeAllowancePayPopUPController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderMenuPopUPController implements Initializable {

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        try {
            loadData();
            searchTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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
    private TextField SearchTextBox;

    @FXML
    private TextField OMIDTextBox;

    @FXML
    private TextField OMPrice;

    @FXML
    private TextField OMNameTextBox;

    @FXML
    private TextField OMWeightTextBox;

    @FXML
    private Button OrderMenuCloseButton;

    private static Employee employee;
    private void loadData() throws SQLException {

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
        OrderMenuTable.setItems(null);
        OrderMenuTable.setItems(orderMenuData);

    }

    @FXML
    private void loadSelectedData( ){
        OrderMenu orderMenuModel;
        try{

            orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();
            OMIDTextBox.setText(orderMenuModel.getoMIID());
            OMNameTextBox.setText(orderMenuModel.getoMIName());
            OMWeightTextBox.setText(String.valueOf(orderMenuModel.getoMIWeight()));
            OMPrice.setText(String.valueOf(orderMenuModel.getoMIPrice()));


        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    public void searchTable() throws SQLException {
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        //Retrieving sorted data from Main LoginController
        SortedList<OrderMenu> sortedData = orderMenuServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderMenuTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderMenuTable.setItems(sortedData);
    }
    @FXML
    public void returnSelectedMenuItem() throws IOException {
        boolean resultVal = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Orders.fxml"));
            Parent root = (Parent) loader.load();
            OrderController orderController = loader.getController();
            OrderMenu orderMenuModel = new OrderMenu();
            orderMenuModel.setoMIID(OMIDTextBox.getText());
            resultVal = orderController.setOrderMenuItem(orderMenuModel);

        if(resultVal){
            closeStage();
        }
    }
    void closeStage(){
        Stage stage = (Stage) OrderMenuCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
