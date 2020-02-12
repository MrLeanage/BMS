package controller.InventoryManagement;

import javafx.collections.transformation.FilteredList;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import util.dbConnect.DBConnection;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

public class SupplierController implements Initializable {


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    //creating Connection
    private DBConnection dbcon;
    private PreparedStatement ps;


    Audio play = new Audio();

    Supplier supplierModel = new Supplier();
    private ObservableList<Supplier> supplierData;
    private ObservableList<String> choiceboxList = FXCollections.observableArrayList("Stock Items","Agency");

    //overriding methods and connections to load data on page visit
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dbcon = new DBConnection();
        loadData();
        //setting choice box values
        SITypeChoiceBox.setValue("Stock Items");
        SITypeChoiceBox.setItems(choiceboxList);
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


        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/AppHome/login.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();

    }
    @FXML
    private void ItemStock(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/InventoryManagement/ItemStock.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/OrderManagement/Orders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Employees(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/EmployeeManagement/Employee.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //internal methods
    @FXML
    private void FoodProducts(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/InventoryManagement/FoodProducts.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Supplier(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/controller/InventoryManagement/Supplier.fxml"));

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
    public void clearFields(){
        UtilityMethod.clearField(SINameTextField);
        UtilityMethod.clearField(SIAddressTextField);
        UtilityMethod.clearField(SIPhone1TextField);
        UtilityMethod.clearField(SIPhone2TextField);
        UtilityMethod.clearField(SIEmailTextField);
        UtilityMethod.clearField(SIBankTextField);
        UtilityMethod.clearField(SIAccNoTextField);
    }

    public void clearLabels(){
        UtilityMethod.clearLabel(SINameLabel);
        UtilityMethod.clearLabel(SIAddressLabel);
        UtilityMethod.clearLabel(SIPhone1Label);
        UtilityMethod.clearLabel(SIPhone2Label);
        UtilityMethod.clearLabel(SIEmailLabel);
        UtilityMethod.clearLabel(SIBankLabel);
        UtilityMethod.clearLabel(SIAccNoLabel);
        UtilityMethod.clearLabel(SITypeLabel);
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
        if(!DataValidation.TextFieldNotEmpty(SINameTextField)
                || DataValidation.TextFieldNotEmpty(SIAddressTextField)
                || DataValidation.TextFieldNotEmpty(Integer.parseInt(SIPhone1TextField.getText()))
                || DataValidation.TextFieldNotEmpty(SIEmailTextField)
                || DataValidation.TextFieldNotEmpty(SIBankTextField)
                || DataValidation.TextFieldNotEmpty(SIAccNoTextField)){

            DataValidation.TextFieldNotEmpty(SINameTextField, SINameLabel,"Supplier Name is Required");
            DataValidation.TextFieldNotEmpty(SIAddressTextField,SIAddressLabel,"Supplier Address is Required");
            DataValidation.TextFieldNotEmpty(SIPhone1TextField.getText(), SIPhone1Label, "Phone number is Required");
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
    //load data from db to table
    private void loadData() {

        try {
            Connection conn = dbcon.Connect();
            supplierData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT  SIID, SIName, SIAddress, SIPhone1, SIPhone2, SIEmail, SIType, SIBankName, SIAccNo FROM supplierinfo");

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));
                //System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
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
    private void refreshTable(){
        supplierData.clear();
        clearFields();
        clearLabels();
        loadData();
    }
    //Add Supplier
    @FXML
    private void addData(ActionEvent event) throws Exception{
        clearLabels();
        if(dataValidate()){
            String sIName = SINameTextField.getText();
            String sIAddress = SIAddressTextField.getText();
            int sIPhone1 = Integer.parseInt(SIPhone1TextField.getText());
            int sIPhone2 = Integer.parseInt(SIPhone2TextField.getText());
            String sIEmail = SIEmailTextField.getText();
            String sIType = SITypeChoiceBox.getValue();
            String sIBank = SIBankTextField.getText();
            Long sIAccNo = Long.parseLong(SIAccNoTextField.getText());

            String query = "INSERT INTO supplierinfo (SIName, SIAddress, SIPhone1, SIPhone2, SIEmail, SIType, SIBankName, SIAccNo) VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = null;
            try {
                Connection conn = dbcon.Connect();
                ps = conn.prepareStatement(query);
                ps.setString(1,sIName );
                ps.setString(2,sIAddress );
                ps.setInt(3, sIPhone1);
                ps.setInt(4, sIPhone2);
                ps.setString(5, sIEmail);
                ps.setString(6, sIType);
                ps.setString(7, sIBank);
                ps.setLong(8, sIAccNo);

                ps.execute();
                AlertPopUp.insertSuccesfully("Supplier Information");
                refreshTable();

            } catch (SQLException ex) {
                AlertPopUp.insertionFailed(ex, "Supplier Information");
            }
            finally{

                ps.close();

            }
        }else{
            dataValidateMessage();
        }



    }
    @FXML
    private void loadSelectedData( ){
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
            //System.out.println(taID);
        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    @FXML
    private void updateData(ActionEvent event)throws Exception{
        clearLabels();
        if(dataValidate()){
            //getting selected ID
            supplierModel = SupplierTable.getSelectionModel().getSelectedItem();
            int ID = UtilityMethod.seperateID(supplierModel.getsIID());

            String sIName = SINameTextField.getText();
            String sIAddress = SIAddressTextField.getText();
            int sIPhone1 = Integer.parseInt(SIPhone1TextField.getText());
            int sIPhone2 = Integer.parseInt(SIPhone2TextField.getText());
            String sIEmail = SIEmailTextField.getText();
            String sIType = SITypeChoiceBox.getValue();
            String sIBank = SIBankTextField.getText();
            Long sIAccNo = Long.parseLong(SIAccNoTextField.getText());

            String query = "UPDATE supplierinfo SET SIName = ?, SIAddress = ?, SIPhone1 = ? , SIPhone2 = ?, SIEmail = ?, SIType = ?, SIBankName = ?, SIAccNo = ? WHERE SIID = '" + ID + "'";
            ps = null;



            try {
                Connection conn = dbcon.Connect();
                ps = conn.prepareStatement(query);
                ps.setString(1, sIName);
                ps.setString(2, sIAddress);
                ps.setInt(3, sIPhone1);
                ps.setInt(4, sIPhone2);
                ps.setString(5, sIEmail);
                ps.setString(6, sIType);
                ps.setString(7, sIBank);
                ps.setLong(8, sIAccNo);

                ps.execute();
                AlertPopUp.updateSuccesfully("Supplier Information");
                refreshTable();

            } catch (SQLException ex) {
                AlertPopUp.updateFailed(ex, "Supplier Information");
            } finally {

                ps.close();
            }
        }else{
            dataValidateMessage();
        }

    }
    @FXML
    private void deleteData(){
        int ID = 0;
        supplierModel = SupplierTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(supplierModel.getsIID());
            if(ID !=0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Supplier Information");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    ps = null;
                    Connection conn = dbcon.Connect();
                    try{
                        ps = conn.prepareStatement("DELETE FROM supplierinfo WHERE SIID = ? ");
                        ps.setInt(1, ID);

                        ps.executeUpdate();
                        AlertPopUp.deleteSuccesfull("Supplier Information");
                        refreshTable();

                    }catch (Exception ex) {
                        AlertPopUp.deleteFailed(ex, "Supplier Information");
                    }finally{
                        ps.close();
                    }
                    refreshTable();
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Supplier");
        }
    }
    public void searchTable(){
        //Retreiving all data from database
        try {
            Connection conn = dbcon.Connect();
            supplierData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT  SIID, SIName, SIAddress, SIPhone1, SIPhone2, SIEmail, SIType, SIBankName, SIAccNo FROM supplierinfo");

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));
                //System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Supplier> filteredData = new FilteredList<>(supplierData, b -> true);

        SearchTextBox.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(supplier -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(supplier.getsIID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIAddress().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone1()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone2()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIEmail().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIType().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Supplier> sortedData = new SortedList<>(filteredData);
        //bninding the SortedList to TableView
        sortedData.comparatorProperty().bind(SupplierTable.comparatorProperty());
        //adding sorted and filtered data to the table
        SupplierTable.setItems(sortedData);


    }




}
