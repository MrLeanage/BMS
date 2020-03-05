package view.OrderManagement;

import controller.BakeryProductController;
import controller.OrderMenuController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.BakeryProduct;
import model.OrderMenu;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderMenuViewController implements Initializable {


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

    private ObservableList<String> choiceboxList = FXCollections.observableArrayList("Available","Not Available");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OMIStatusChoiceBox.setValue("Available");
        OMIStatusChoiceBox.setItems(choiceboxList);
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
    private void Employees(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Employee.fxml"));

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
    private void OnGoingOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OnGoingOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void CompletedOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/CompletedOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void CancelledOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/CancelledOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderMenu(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuSupervisor.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }



    @FXML
    private void imageChooser(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            System.out.println(file.getAbsolutePath());
            //OMIImageView.setImage();
            staticFile = file;
            Image image = new Image(file.toURI().toString());
            OMIImageView.setImage(image);
        }
    }
    @FXML
    private void clearFields(){

        try{
            OMINameTextField.setText("");
            OMIWeightTextField.setText("");
            OMIPriceTextField.setText("");
            OMIDescriptionTextArea.setText("");
            OMIImageView.setImage(null);

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            OMINameLabel.setText("");
            OMIWeightLabel.setText("");
            OMIPriceLabel.setText("");
            OMIDescriptionLabel.setText("");
            OMIImageViewLabel.setText("");

        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(OMINameTextField)
                && DataValidation.TextFieldNotEmpty(OMIWeightTextField)
                && DataValidation.TextFieldNotEmpty(OMIPriceTextField)
                && DataValidation.TextAreaNotEmpty(OMIDescriptionTextArea)
                && DataValidation.ImageFieldNotEmpty(OMIImageView)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(OMINameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(OMIWeightTextField.getText())
                && DataValidation.isValidNumberFormat(OMIPriceTextField.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(OMINameTextField)
                && DataValidation.TextFieldNotEmpty(OMIWeightTextField)
                && DataValidation.TextFieldNotEmpty(OMIPriceTextField)
                && DataValidation.TextAreaNotEmpty(OMIDescriptionTextArea)
                && DataValidation.ImageFieldNotEmpty(OMIImageView))){


            DataValidation.TextFieldNotEmpty(OMINameTextField, OMINameLabel,"Name for Bakery Product is Required");
            DataValidation.TextFieldNotEmpty(OMIWeightTextField, OMIWeightLabel,"Weight of Product is Required(KG/L)");
            DataValidation.TextFieldNotEmpty(OMIPriceTextField, OMIPriceLabel,"Price of the Product is Required");
            DataValidation.TextFieldNotEmpty(OMIDescriptionTextArea, OMIDescriptionLabel, "Description about the Product is Required");
            DataValidation.ImageFieldNotEmpty(OMIImageView, OMIImageViewLabel, "Please Select a Image of the Product");
            //checking for exceeding limit

        }
        if(!( DataValidation.isValidMaximumLength(OMINameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250))){


            DataValidation.isValidMaximumLength(OMINameTextField.getText(),80, OMINameLabel,"Order Menu Product Name is too Long!..");
            DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10, OMIWeightLabel,"Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250, OMIDescriptionLabel, "Error!..Exceeding Character limit 250 Characters");
            DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10, OMIPriceLabel, "Error!..Exceeding Character limit 10");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(OMIWeightTextField.getText())
                && DataValidation.isValidNumberFormat(OMIPriceTextField.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(OMIWeightTextField.getText(), OMIWeightLabel,"Product Weight can contain only Digits");
            DataValidation.isValidNumberFormat(OMIPriceTextField.getText(), OMIPriceLabel, "Product Price can contain only Digits");
        }
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

        OrderMenuTable.setItems(null);
        OrderMenuTable.setItems(orderMenuData);

    }

    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{
        clearFields();
        loadData();
        clearLabels();
    }
    //Add Supplier
    @FXML
    private void addData(ActionEvent event) throws Exception{

        clearLabels();
        OrderMenu orderMenuModel = new OrderMenu();
        OrderMenuController orderMenuController = new OrderMenuController();

        if(dataValidate()){

            orderMenuModel.setoMIName(OMINameTextField.getText());
            orderMenuModel.setoMIImage(OMIImageView);
            orderMenuModel.setoMIDescription(OMIDescriptionTextArea.getText());
            orderMenuModel.setoMIWeight(Float.parseFloat(OMIWeightTextField.getText()));
            orderMenuModel.setoMIPrice(Float.parseFloat(OMIPriceTextField.getText()));
            orderMenuModel.setoMIStatus(OMIStatusChoiceBox.getValue());

            Boolean resultVal = orderMenuController.insertData(orderMenuModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        OrderMenu orderMenuModel;

        try{
            orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();
            OMINameTextField.setText(orderMenuModel.getoMIName());
            OMIImageView.setImage(orderMenuModel.getoMIImage().getImage());
            OMIWeightTextField.setText(String.valueOf(orderMenuModel.getoMIWeight()));
            OMIDescriptionTextArea.setText(orderMenuModel.getoMIDescription());
            OMIPriceTextField.setText(String.valueOf(orderMenuModel.getoMIPrice()));
            OMIStatusChoiceBox.setValue(orderMenuModel.getoMIStatus());
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

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


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        OrderMenu orderMenuModel = new OrderMenu();
        OrderMenuController orderMenuController = new OrderMenuController();

        try{
            if(!(existingOrderMenuProductModel.getoMIID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    orderMenuModel.setoMIID(existingOrderMenuProductModel.getoMIID());
                    orderMenuModel.setoMIImage(OMIImageView);
                    orderMenuModel.setoMIName(OMINameTextField.getText());
                    orderMenuModel.setoMIDescription(OMIDescriptionTextArea.getText());
                    orderMenuModel.setoMIWeight(Float.parseFloat(OMIWeightTextField.getText()));
                    orderMenuModel.setoMIPrice(Float.parseFloat(OMIPriceTextField.getText()));
                    orderMenuModel.setoMIStatus(OMIStatusChoiceBox.getValue());
                    boolean resultVal = orderMenuController.updateData(orderMenuModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Order Menu Item");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        OrderMenu orderMenuModel;
        OrderMenuController orderMenuController = new OrderMenuController();
        orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(orderMenuModel.getoMIID());

            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Order Menu Item");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = orderMenuController.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Order Menu Item");
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
