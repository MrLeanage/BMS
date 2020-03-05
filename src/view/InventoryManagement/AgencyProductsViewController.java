package view.InventoryManagement;

import services.AgencyProductServices;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AgencyProduct;
import model.Supplier;
import util.playAudio.Audio;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgencyProductsViewController implements Initializable {


    @FXML
    private TableView<AgencyProduct> AgencyProductTable;

    @FXML
    private TableColumn<AgencyProduct, String> APIDColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APNameColumn;

    @FXML
    private TableColumn<AgencyProduct, Integer> APSIDColumn;

    @FXML
    private TableColumn<AgencyProduct, Float> APSupplierNameColumn;

    @FXML
    private TableColumn<AgencyProduct, Float> APUnitsColumn;

    @FXML
    private TableColumn<AgencyProduct, Float> APWeightColumn;

    @FXML
    private TableColumn<AgencyProduct, Float> APBuyingPriceColumn;

    @FXML
    private TableColumn<AgencyProduct, Float> APMarketPriceColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APSellingPriceColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APManufactureDateColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APExpireDateColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APAddedDateColumn;

    @FXML
    private TableColumn<AgencyProduct, String> APDiscontinueAlertDateColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField APProductNameTextBox;

    @FXML
    private TextField APUnitsTextBox;

    @FXML
    private TextField APWeightTextBox;

    @FXML
    private TextField APBuyingPriceTextBox;

    @FXML
    private TextField APMarketPriceTextBox;

    @FXML
    private TextField APSellingPriceTextBox;

    @FXML
    private DatePicker APManufactureDateDatePicker;

    @FXML
    private DatePicker APExpDateDatePicker;

    @FXML
    private DatePicker APDiscontinueAlertDatePicker;
    // Factory to create Cell of DatePicker


    @FXML
    private   TextField APSupplierIDTextBox;
    private  static String staticAPSupplierIDTextBox = null;

    @FXML
    private  TextField APSupplierNameTextBox;
    private  String staticAPSupplierNameTextBox = null;
    @FXML
    private Button APSelectSupplierButton;

    @FXML
    private Label APProductNameLabel;

    @FXML
    private Label APWeightLabel;

    @FXML
    private Label APUnitsLabel;

    @FXML
    private Label APBuyingPriceLabel;

    @FXML
    private Label APManufactureDateLabel;

    @FXML
    private Label APSupplierNameLabel;

    @FXML
    private Label APExpDateLabel;

    @FXML
    private Label APMarketPriceLabel;

    @FXML
    private Label APSellingPriceLabel;

    @FXML
    private Label APDiscontinueDateLabel;


    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */
    Audio play = new Audio();
    private static Supplier supplier;
    //overriding methods and connections to load data on page visit
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        //to auto refresh search results
        searchTable();
        dateValidation();

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
            APProductNameTextBox.setText("");
            APUnitsTextBox.setText("");
            APWeightTextBox.setText("");
            APBuyingPriceTextBox.setText("");
            APMarketPriceTextBox.setText("");
            APSellingPriceTextBox.setText("");
            APManufactureDateDatePicker.setValue(null);
            APExpDateDatePicker.setValue(null);
            APDiscontinueAlertDatePicker.setValue(null);
            APSupplierIDTextBox.setText("");
            APSupplierNameTextBox.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }

    }

    private void clearLabels(){
        try{
            APProductNameLabel.setText("");
            APUnitsLabel.setText("");
            APWeightLabel.setText("");
            APBuyingPriceLabel.setText("");
            APMarketPriceLabel.setText("");
            APSellingPriceLabel.setText("");
            APManufactureDateLabel.setText("");
            APExpDateLabel.setText("");
            APDiscontinueDateLabel.setText("");
            APSupplierNameLabel.setText("");

        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(APProductNameTextBox)
                && DataValidation.TextFieldNotEmpty(APSupplierIDTextBox)
                && DataValidation.TextFieldNotEmpty(APSupplierNameTextBox)
                && DataValidation.TextFieldNotEmpty(APUnitsTextBox)
                && DataValidation.TextFieldNotEmpty(APWeightTextBox.getText())
                && DataValidation.TextFieldNotEmpty(APBuyingPriceTextBox)
                && DataValidation.TextFieldNotEmpty(APMarketPriceTextBox)
                && DataValidation.TextFieldNotEmpty(APSellingPriceTextBox)
                && DataValidation.DatePickerNotEmpty(APManufactureDateDatePicker)
                && DataValidation.DatePickerNotEmpty(APExpDateDatePicker)
                && DataValidation.DatePickerNotEmpty(APDiscontinueAlertDatePicker)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(APProductNameTextBox.getText(),80)
                && DataValidation.isValidMaximumLength(APUnitsTextBox.getText(),4)
                && DataValidation.isValidMaximumLength(APWeightTextBox.getText(),10)
                && DataValidation.isValidMaximumLength(APBuyingPriceTextBox.getText(),10)
                && DataValidation.isValidMaximumLength(APMarketPriceTextBox.getText(),10)
                && DataValidation.isValidMaximumLength(APSellingPriceTextBox.getText(),10)
                && DataValidation.isValidMaximumLength(String.valueOf(APManufactureDateDatePicker.getValue()),10)
                && DataValidation.isValidMaximumLength(String.valueOf(APExpDateDatePicker.getValue()),10)
                && DataValidation.isValidMaximumLength(String.valueOf(APDiscontinueAlertDatePicker.getValue()),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(APUnitsTextBox.getText())
                && DataValidation.isValidNumberFormat(APWeightTextBox.getText())
                && DataValidation.isValidNumberFormat(APBuyingPriceTextBox.getText())
                && DataValidation.isValidNumberFormat(APMarketPriceTextBox.getText())
                && DataValidation.isValidNumberFormat(APSellingPriceTextBox.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(APProductNameTextBox.getText())
                && DataValidation.TextFieldNotEmpty(APSupplierIDTextBox)
                && DataValidation.TextFieldNotEmpty(APSupplierNameTextBox)
                && DataValidation.TextFieldNotEmpty(APUnitsTextBox)
                && DataValidation.TextFieldNotEmpty(APWeightTextBox.getText())
                && DataValidation.TextFieldNotEmpty(APBuyingPriceTextBox)
                && DataValidation.TextFieldNotEmpty(APMarketPriceTextBox)
                && DataValidation.TextFieldNotEmpty(APSellingPriceTextBox)
                && DataValidation.DatePickerNotEmpty(APManufactureDateDatePicker)
                && DataValidation.DatePickerNotEmpty(APExpDateDatePicker)
                && DataValidation.DatePickerNotEmpty(APDiscontinueAlertDatePicker))){


            DataValidation.TextFieldNotEmpty(APProductNameTextBox, APProductNameLabel,"Agency Product Name is Required");
            DataValidation.TextFieldNotEmpty(APSupplierNameTextBox, APSupplierNameLabel,"Please Select a Agency Product Supplier");
            DataValidation.TextFieldNotEmpty(APUnitsTextBox,APUnitsLabel,"Total no of Units is Required");
            DataValidation.TextFieldNotEmpty(APWeightTextBox, APWeightLabel, "Weight of a Unit is Required(KG/L)");
            DataValidation.TextFieldNotEmpty(APBuyingPriceTextBox, APBuyingPriceLabel, "Purchasing Price is Required");
            DataValidation.TextFieldNotEmpty(APMarketPriceTextBox, APMarketPriceLabel, "Market Price is Required");
            DataValidation.TextFieldNotEmpty(APSellingPriceTextBox, APSellingPriceLabel, "Selling Price is required");
            DataValidation.DatePickerNotEmpty(APManufactureDateDatePicker, APManufactureDateLabel, "Manufacture Date is required");
            DataValidation.DatePickerNotEmpty(APExpDateDatePicker, APExpDateLabel, "Expiration Date is required");
            DataValidation.DatePickerNotEmpty(APDiscontinueAlertDatePicker, APDiscontinueDateLabel, "Discontinuing Alert Date  is required");

            //checking for exceeding limit

        }
        if(!DataValidation.isValidMaximumLength(APProductNameTextBox.getText(),80)
                || DataValidation.isValidMaximumLength(APUnitsTextBox.getText(),4)
                || DataValidation.isValidMaximumLength(APWeightTextBox.getText(),10)
                || DataValidation.isValidMaximumLength(APBuyingPriceTextBox.getText(),10)
                || DataValidation.isValidMaximumLength(APMarketPriceTextBox.getText(),10)
                || DataValidation.isValidMaximumLength(APSellingPriceTextBox.getText(),10)){


            DataValidation.isValidMaximumLength(APProductNameTextBox.getText(),80, APProductNameLabel,"Product Name is too Long!..");
            DataValidation.isValidMaximumLength(APUnitsTextBox.getText(),4, APUnitsLabel,"Error!..Exceeding Character limit 4");
            DataValidation.isValidMaximumLength(APWeightTextBox.getText(),10, APWeightLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(APBuyingPriceTextBox.getText(),10, APBuyingPriceLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(APMarketPriceTextBox.getText(),10, APMarketPriceLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(APSellingPriceTextBox.getText(),10, APSellingPriceLabel, "Error!..Exceeding Character limit 10");


        }
        //checking for specific properties
        if(!DataValidation.isValidNumberFormat(APUnitsTextBox.getText())
                || DataValidation.isValidNumberFormat(APWeightTextBox.getText())
                || DataValidation.isValidNumberFormat(APBuyingPriceTextBox.getText())
                || DataValidation.isValidNumberFormat(APMarketPriceTextBox.getText())
                || DataValidation.isValidNumberFormat(APSellingPriceTextBox.getText())){
            //Checking for Specific Data Validation
            System.out.println(APUnitsTextBox.getText());
            DataValidation.isValidNumberFormat(APUnitsTextBox.getText(), APUnitsLabel,"No of Unit can contain only Digits");
            DataValidation.isValidNumberFormat(APWeightTextBox.getText(), APWeightLabel, "Product Weight can contain only Digits");
            DataValidation.isValidNumberFormat(APBuyingPriceTextBox.getText(), APBuyingPriceLabel,"Buying Price can contain only Digits");
            DataValidation.isValidNumberFormat(APMarketPriceTextBox.getText(), APMarketPriceLabel,"Market Price can contain only Digits");
            DataValidation.isValidNumberFormat(APSellingPriceTextBox.getText(), APSellingPriceLabel, "Selling Price can contain only Digits");
        }


    }
    private void dateValidation(){
        //hiding Old Dates on date picker
        Callback hideOldCallback = DataValidation.hideOldDates();
        APDiscontinueAlertDatePicker.setDayCellFactory(hideOldCallback);
        APExpDateDatePicker.setDayCellFactory(hideOldCallback);

        //hiding Future Dates on date picker
        Callback hideFutureCallback = DataValidation.hideFutureDates();
        APManufactureDateDatePicker.setDayCellFactory(hideFutureCallback);
    }
    @FXML
    private void selectSupplier(ActionEvent event){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmployeeSalarySchemePopUP.fxml"));

        try{
            loader.load();

        }catch (IOException ex){
            Logger.getLogger(AgencySupplierPopUPViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgencySupplierPopUPViewController agencySupplierPopUPViewController = loader.getController();


        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.showAndWait();

        try{
            APSupplierIDTextBox.setText(supplier.getsIID());
            APSupplierNameTextBox.setText(supplier.getsIName());
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }


    }
    //getting selected supplier from popup
    @FXML
    public boolean setSupplier(Supplier supplierInfo){

        boolean resultval = false;

        try{
            // setting supplierInfo data to a static variable for later use and returning true to close stage
            this.supplier = supplierInfo;
            resultval = true;
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
       return resultval;
    }
    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        
        ObservableList<AgencyProduct> agencyProductData;
        agencyProductData = agencyProductServices.loadData();
        
        //Setting cell value factory to table view

        APIDColumn.setCellValueFactory(new PropertyValueFactory<>("aPID"));
        APNameColumn.setCellValueFactory(new PropertyValueFactory<>("aPName"));
        APSIDColumn.setCellValueFactory(new PropertyValueFactory<>("aPSupplierID"));
        APSupplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("aPSupplierName"));
        APUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("aPTotalUnits"));
        APWeightColumn.setCellValueFactory(new PropertyValueFactory<>("aPWeightOfUnit"));
        APBuyingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("aPBuyingPricePerUnit"));
        APMarketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("aPMarketPricePerUnit"));
        APSellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("aPSellingPricePerUnit"));
        APManufactureDateColumn.setCellValueFactory(new PropertyValueFactory<>("aPMDate"));
        APExpireDateColumn.setCellValueFactory(new PropertyValueFactory<>("aPEDate"));
        APAddedDateColumn.setCellValueFactory(new PropertyValueFactory<>("aPADate"));
        APDiscontinueAlertDateColumn.setCellValueFactory(new PropertyValueFactory<>("aPDADate"));

        AgencyProductTable.setItems(null);
        AgencyProductTable.setItems(agencyProductData);
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
        AgencyProduct agencyProductModel = new AgencyProduct();
        AgencyProductServices agencyProductServices = new AgencyProductServices();

        if(dataValidate()){

            agencyProductModel.setaPName(APProductNameTextBox.getText());
            agencyProductModel.setaPSupplierID(APSupplierIDTextBox.getText());
            agencyProductModel.setaPSupplierName(APSupplierNameTextBox.getText());
            agencyProductModel.setaPTotalUnits(Integer.parseInt(APUnitsTextBox.getText()));
            agencyProductModel.setaPWeightOfUnit(Float.parseFloat(APWeightTextBox.getText()));
            agencyProductModel.setaPBuyingPricePerUnit(Float.parseFloat(APBuyingPriceTextBox.getText()));
            agencyProductModel.setaPMarketPricePerUnit(Float.parseFloat(APMarketPriceTextBox.getText()));
            agencyProductModel.setaPSellingPricePerUnit(Float.parseFloat(APSellingPriceTextBox.getText()));
            agencyProductModel.setaPMDate(String.valueOf(APManufactureDateDatePicker.getValue()));
            agencyProductModel.setaPEDate(String.valueOf(APExpDateDatePicker.getValue()));
            agencyProductModel.setaPADate(String.valueOf(LocalDate.now()));
            agencyProductModel.setaPDADate(String.valueOf(APDiscontinueAlertDatePicker.getValue()));

            Boolean resultVal = agencyProductServices.insertData(agencyProductModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }


    }
    @FXML
    private void loadSelectedData( ){

        AgencyProduct agencyProductModel;

        try{

            agencyProductModel = AgencyProductTable.getSelectionModel().getSelectedItem();
            APProductNameTextBox.setText(agencyProductModel.getaPName());
            APSupplierIDTextBox.setText(agencyProductModel.getaPSupplierID());
            APSupplierNameTextBox.setText(agencyProductModel.getaPSupplierName());
            APUnitsTextBox.setText(String.valueOf(agencyProductModel.getaPTotalUnits()));
            APWeightTextBox.setText(String.valueOf(agencyProductModel.getaPWeightOfUnit()));
            APBuyingPriceTextBox.setText(String.valueOf(agencyProductModel.getaPBuyingPricePerUnit()));
            APMarketPriceTextBox.setText(String.valueOf(agencyProductModel.getaPMarketPricePerUnit()));
            APSellingPriceTextBox.setText(String.valueOf(agencyProductModel.getaPSellingPricePerUnit()));
            APManufactureDateDatePicker.setValue(LocalDate.parse(agencyProductModel.getaPMDate()));
            APExpDateDatePicker.setValue(LocalDate.parse(agencyProductModel.getaPEDate()));
            APDiscontinueAlertDatePicker.setValue(LocalDate.parse(agencyProductModel.getaPDADate()));

            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }


    }
    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        AgencyProduct agencyProductModel;
        AgencyProductServices agencyProductServices = new AgencyProductServices();

        if(dataValidate()){
            //getting selected ID
            agencyProductModel = AgencyProductTable.getSelectionModel().getSelectedItem();
            //Overriding existing values retreiving from table
            agencyProductModel.setaPName(APProductNameTextBox.getText());
            agencyProductModel.setaPSupplierID(APSupplierIDTextBox.getText());
            agencyProductModel.setaPSupplierName(APSupplierNameTextBox.getText());
            agencyProductModel.setaPTotalUnits(Integer.parseInt(APUnitsTextBox.getText()));
            agencyProductModel.setaPWeightOfUnit(Float.parseFloat(APWeightTextBox.getText()));
            agencyProductModel.setaPBuyingPricePerUnit(Float.parseFloat(APBuyingPriceTextBox.getText()));
            agencyProductModel.setaPMarketPricePerUnit(Float.parseFloat(APMarketPriceTextBox.getText()));
            agencyProductModel.setaPSellingPricePerUnit(Float.parseFloat(APSellingPriceTextBox.getText()));
            agencyProductModel.setaPMDate(String.valueOf(APManufactureDateDatePicker.getValue()));
            agencyProductModel.setaPEDate(String.valueOf(APExpDateDatePicker.getValue()));
            agencyProductModel.setaPDADate(String.valueOf(APDiscontinueAlertDatePicker.getValue()));

            boolean resultVal = agencyProductServices.updateData(agencyProductModel);
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
        AgencyProduct agencyProductModel;
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        agencyProductModel = AgencyProductTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(agencyProductModel.getaPID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Bakery Product");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = agencyProductServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Agency Product");
        }


    }
    public void searchTable(){

        AgencyProductServices agencyProductServices = new AgencyProductServices();
        //Retrieving sorted data from Main Controller
        SortedList<AgencyProduct> sortedData = agencyProductServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(AgencyProductTable.comparatorProperty());
        //adding sorted and filtered data to the table
        AgencyProductTable.setItems(sortedData);


    }
}
