package view.InventoryManagement;

import services.SupplierServices;
import javafx.collections.transformation.SortedList;
import model.Supplier;
import util.playAudio.Audio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.ResourceBundle;

import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

public class SupplierViewController implements Initializable {
    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */
    Audio play = new Audio();
    
    private ObservableList<String> choiceboxList = FXCollections.observableArrayList("Stock Items","Agency");

    //overriding methods and connections to load data on page visit
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        loadData();
        //setting choice box values
        SITypeChoiceBox.setValue("Stock Items");
        SITypeChoiceBox.setItems(choiceboxList);
        //to auto refresh search results
        searchTable();
    }

    @FXML
    private TableView<Supplier> SupplierTable;

    @FXML
    private TableColumn<Supplier, String> SIIDColumn;

    @FXML
    private TableColumn<Supplier, String> SINameColumn;

    @FXML
    private TableColumn<Supplier, String> SIAddressColumn;

    @FXML
    private TableColumn<Supplier, Integer> SIPhone1Column;

    @FXML
    private TableColumn<Supplier, Integer> SIPhone2Column;

    @FXML
    private TableColumn<Supplier, String> SIEmailColumn;

    @FXML
    private TableColumn<Supplier, String> SITypeColumn;

    @FXML
    private TableColumn<Supplier, String> SIBankColumn;

    @FXML
    private TableColumn<Supplier, Long> SIAccNoColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private ChoiceBox<String> SITypeChoiceBox;


    @FXML
    private TextField SINameTextField;

    @FXML
    private TextField SIAddressTextField;

    @FXML
    private TextField SIPhone1TextField;

    @FXML
    private TextField SIPhone2TextField;

    @FXML
    private TextField SIEmailTextField;

    @FXML
    private TextField SIBankTextField;

    @FXML
    private TextField SIAccNoTextField;

    @FXML
    private Label SITypeLabel;

    @FXML
    private Label SIAddressLabel;

    @FXML
    private Label SIPhone2Label;

    @FXML
    private Label SINameLabel;

    @FXML
    private Label SIPhone1Label;

    @FXML
    private Label SIEmailLabel;

    @FXML
    private Label SIBankLabel;

    @FXML
    private Label SIAccNoLabel;

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
    private void playBeep(){
        play.AddItemPlay();
    }
    @FXML
    private void clearFields(){
        try{
            SINameTextField.setText("");
            SIAddressTextField.setText("");
            SIPhone1TextField.setText("");
            SIPhone2TextField.setText("");
            SIEmailTextField.setText("");
            SIBankTextField.setText("");
            SIAccNoTextField.setText("");
        }catch (NullPointerException ex){

        }

    }

    private void clearLabels(){
        try{
            SINameLabel.setText("");
            SIAddressLabel.setText("");
            SIPhone1Label.setText("");
            SIPhone2Label.setText("");
            SIEmailLabel.setText("");
            SIBankLabel.setText("");
            SIAccNoLabel.setText("");
            SITypeLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){
        boolean returnVal = false;
        if(DataValidation.TextFieldNotEmpty(SINameTextField)
                && DataValidation.TextFieldNotEmpty(SIAddressTextField)
                && DataValidation.TextFieldNotEmpty(SIPhone1TextField.getText())
                && DataValidation.TextFieldNotEmpty(SIEmailTextField)
                && DataValidation.TextFieldNotEmpty(SIBankTextField)
                && DataValidation.TextFieldNotEmpty(SIAccNoTextField)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(SINameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(SIAddressTextField.getText(),100)
                && DataValidation.isValidMaximumLength(SIEmailTextField.getText(),80)
                && DataValidation.isValidMaximumLength(SIBankTextField.getText(),80)
                && DataValidation.isValidMaximumLength(SIAccNoTextField.getText(),15)
                //Checking for Specific Data Validation
                && DataValidation.isValidPhoneNo(SIPhone1TextField.getText())
                && DataValidation.isValidPhoneNo(SIPhone2TextField.getText())
                && DataValidation.isValidEmail(SIEmailTextField.getText())
                && DataValidation.isValidNumberFormat(SIAccNoTextField.getText())){
            returnVal = true;
        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(SIPhone1TextField)
                && DataValidation.TextFieldNotEmpty(SIEmailTextField)
                && DataValidation.TextFieldNotEmpty(SIBankTextField)
                && DataValidation.TextFieldNotEmpty(SINameTextField.getText())
                && DataValidation.TextFieldNotEmpty(SIAddressTextField.getText())
                && DataValidation.TextFieldNotEmpty(SIAccNoTextField))) {


            DataValidation.TextFieldNotEmpty(SINameTextField, SINameLabel, "Supplier Name is Required");
            DataValidation.TextFieldNotEmpty(SIAddressTextField, SIAddressLabel, "Supplier Address is Required");
            DataValidation.TextFieldNotEmpty(SIPhone1TextField, SIPhone1Label, "Phone number is Required");
            DataValidation.TextFieldNotEmpty(SIEmailTextField, SIEmailLabel, "Email Address is Required");
            DataValidation.TextFieldNotEmpty(SIBankTextField, SIBankLabel, "Bank name is Required");
            DataValidation.TextFieldNotEmpty(SIAccNoTextField, SIAccNoLabel, "Bank Account No is required");

            //checking for exceeding limit

        }
        if(!DataValidation.isValidMaximumLength(SINameTextField.getText(),20)
                || DataValidation.isValidMaximumLength(SIAddressTextField.getText(),100)
                || DataValidation.isValidMaximumLength(SIEmailTextField.getText(),80)
                || DataValidation.isValidMaximumLength(SIBankTextField.getText(),80)
                || DataValidation.isValidMaximumLength(SIAccNoTextField.getText(),15)){


            DataValidation.isValidMaximumLength(SINameTextField.getText(),20, SINameLabel,"Name is too Long!..");
            DataValidation.isValidMaximumLength(SIAddressTextField.getText(),80, SIAddressLabel,"Address is too Long!..");
            DataValidation.isValidMaximumLength(SIEmailTextField.getText(),80, SIEmailLabel, "Email is too Long!..");
            DataValidation.isValidMaximumLength(SIBankTextField.getText(),80, SIBankLabel, "Bank Name is is too Long!..");
            DataValidation.isValidMaximumLength(SIAccNoTextField.getText(),15, SIAccNoLabel, "Error!..Exceeding limit 15");


        }
        //checking for specific properties
        if(!DataValidation.isValidPhoneNo(SIPhone1TextField.getText())
                || DataValidation.isValidPhoneNo(SIPhone2TextField.getText())
                || DataValidation.isValidEmail(SIEmailTextField.getText())
                || DataValidation.isValidNumberFormat(SIAccNoTextField.getText())){
            //Checking for Specific Data Validation
            DataValidation.isValidPhoneNo(SIPhone1TextField.getText(),SIPhone1Label,"Invalid Phone Number!..ex : '0112345678'");
            DataValidation.isValidPhoneNo(SIPhone2TextField.getText(), SIPhone2Label, "Invalid Phone Number!..ex : '0112345678'");
            DataValidation.isValidNumberFormat(SIPhone1TextField.getText(),SIPhone1Label,"Phone number can contain digits Only!..");
            DataValidation.isValidNumberFormat(SIPhone2TextField.getText(),SIPhone2Label,"Phone number can contain digits Only!..");
            DataValidation.isValidEmail(SIEmailTextField.getText(), SIEmailLabel, "Invalid Email Address!.. ex :'abc@gmail.com'");
            DataValidation.isValidNumberFormat(SIAccNoTextField.getText(), SIAccNoLabel, "Account Number can contain digits Only!..");
        }
    }
    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        SupplierServices supplierServices = new SupplierServices();
        
        ObservableList<Supplier> supplierData;
        supplierData = supplierServices.loadData();
        
        //Setting cell value factory to table view
        SIIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIID"));
        SINameColumn.setCellValueFactory(new PropertyValueFactory<>("sIName"));
        SIAddressColumn.setCellValueFactory(new PropertyValueFactory<>("sIAddress"));
        SIPhone1Column.setCellValueFactory(new PropertyValueFactory<>("sIPhone1"));
        SIPhone2Column.setCellValueFactory(new PropertyValueFactory<>("sIPhone2"));
        SIEmailColumn.setCellValueFactory(new PropertyValueFactory<>("sIEmail"));
        SITypeColumn.setCellValueFactory(new PropertyValueFactory<>("sIType"));
        SIBankColumn.setCellValueFactory(new PropertyValueFactory<>("sIBank"));
        SIAccNoColumn.setCellValueFactory(new PropertyValueFactory<>("sIAccNo"));

        SupplierTable.setItems(null);
        SupplierTable.setItems(supplierData);
    }

    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{
        clearFields();
        clearLabels();
        loadData();
    }
    //Add Supplier
    @FXML
    private void addData(ActionEvent event) throws Exception{
        clearLabels();
        Supplier supplierModel = new Supplier();
        SupplierServices supplierServices = new SupplierServices();

        if(dataValidate()){

            supplierModel.setsIName(SINameTextField.getText());
            supplierModel.setsIAddress(SIAddressTextField.getText());
            supplierModel.setsIPhone1(Integer.parseInt(SIPhone1TextField.getText()));
            supplierModel.setsIPhone2(Integer.parseInt(SIPhone2TextField.getText()));
            supplierModel.setsIEmail(SIEmailTextField.getText());
            supplierModel.setsIBank(SIBankTextField.getText());
            supplierModel.setsIAccNo(Long.parseLong(SIAccNoTextField.getText()));
            supplierModel.setsIType(SITypeChoiceBox.getValue());

            Boolean resultVal = supplierServices.insertData(supplierModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){
        Supplier supplierModel;
        try{

            supplierModel = SupplierTable.getSelectionModel().getSelectedItem();
            SINameTextField.setText(supplierModel.getsIName());
            SIAddressTextField.setText(supplierModel.getsIAddress());
            SIPhone1TextField.setText("0"+ supplierModel.getsIPhone1());
            SIPhone2TextField.setText("0" + supplierModel.getsIPhone2());
            SIEmailTextField.setText(supplierModel.getsIEmail());
            SITypeChoiceBox.setValue(supplierModel.getsIType());
            SITypeChoiceBox.setItems(choiceboxList);
            SIBankTextField.setText(supplierModel.getsIBank());
            SIAccNoTextField.setText((String.valueOf(supplierModel.getsIAccNo())));

            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        PreparedStatement ps = null;
        Supplier supplierModel;
        SupplierServices supplierServices = new SupplierServices();

        if(dataValidate()){
            //getting selected ID
            supplierModel = SupplierTable.getSelectionModel().getSelectedItem();

            supplierModel.setsIName(SINameTextField.getText());
            supplierModel.setsIAddress(SIAddressTextField.getText());
            supplierModel.setsIPhone1(Integer.parseInt(SIPhone1TextField.getText()));
            supplierModel.setsIPhone2(Integer.parseInt(SIPhone2TextField.getText()));
            supplierModel.setsIEmail(SIEmailTextField.getText());
            supplierModel.setsIBank(SIBankTextField.getText());
            supplierModel.setsIAccNo(Long.parseLong(SIAccNoTextField.getText()));
            supplierModel.setsIType(SITypeChoiceBox.getValue());

            boolean resultVal = supplierServices.updateData(supplierModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }

    }
    @FXML
    private void deleteData(){
        int ID;
        Supplier supplierModel;
        SupplierServices supplierServices = new SupplierServices();
        supplierModel = SupplierTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(supplierModel.getsIID());
            if(ID !=0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Supplier Information");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = supplierServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Supplier");
        }
    }
    public void searchTable(){

        SupplierServices supplierServices = new SupplierServices();
        //Retrieving sorted data from Main Controller
        SortedList<Supplier> sortedData = supplierServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(SupplierTable.comparatorProperty());
        //adding sorted and filtered data to the table
        SupplierTable.setItems(sortedData);


    }




}
