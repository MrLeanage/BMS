package view.InventoryManagement;

import services.ItemStockServices;
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
import model.ItemStock;
import model.Supplier;
import util.authenticate.AdminManagementHandler;
import util.authenticate.InventorySessionHandler;
import util.authenticate.UserAuthentication;
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

public class ItemStockController implements Initializable {

    Audio play = new Audio();
    private static Supplier supplier;
    private static ItemStock existingItemStockModel;

    @FXML
    private TableView<ItemStock> ItemStockTable;

    @FXML
    private TableColumn<ItemStock, String> IIDColumn;

    @FXML
    private TableColumn<ItemStock, String> INameColumn;

    @FXML
    private TableColumn<ItemStock, String> ISIIDColumn;

    @FXML
    private TableColumn<ItemStock, String> ISISupplierNameColumn;

    @FXML
    private TableColumn<ItemStock, Integer> IUnitsPerBlockColumn;

    @FXML
    private TableColumn<ItemStock, Integer> IBlocksColumn;

    @FXML
    private TableColumn<ItemStock, Float> IWeightPerUnitColumn;

    @FXML
    private TableColumn<ItemStock, Float> IBuyingPriceColumn;

    @FXML
    private TableColumn<ItemStock, String> IExpireDateColumn;

    @FXML
    private TableColumn<ItemStock, String> IAddedDateColumn;

    @FXML
    private TableColumn<ItemStock, Integer> IMinQuantityColumn;

    @FXML
    private TableColumn<ItemStock, Integer> IAvailableQuantityColumn;

    @FXML
    private TableColumn<ItemStock, String> IStatusColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField INameTextField;

    @FXML
    private TextField IUnitsPerBlockTextField;

    @FXML
    private TextField IBlocksTextField;

    @FXML
    private TextField IWeightPerUnitTextField;

    @FXML
    private TextField IBuyingPriceTextField;

    @FXML
    private DatePicker IExpireDateDatePicker;

    @FXML
    private TextField ISupplierNameTextField;

    @FXML
    private Button ISelectSupplierButton;

    @FXML
    private Label INameLabel;

    @FXML
    private Label IBlocksLabel;

    @FXML
    private Label IUnitsPerBlockLabel;

    @FXML
    private Label IWeightPerUnitLabel;

    @FXML
    private Label IExpireDateLabel;

    @FXML
    private Label ISupplierNameLabel;

    @FXML
    private Label IBuyingPriceLabel;

    @FXML
    private TextField ISupplierIDTextField;

    @FXML
    private TextField IMinQuantityLimitTextField;

    @FXML
    private Label IMinQuantityLimitLabel;

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */
    @FXML
    public Label UserNameLabel;

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private InventorySessionHandler inventorySessionHandler = new InventorySessionHandler();
    //overriding methods and connections to load data on page visit
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        //to auto refresh search results
        searchTable();
        dateValidation();
        UserNameLabel.setText(UserAuthentication.getAuthenticatedSession().getuName());

    }
    @FXML
    private void LogoutSession(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.endAuthenticatedSession(event);
    }
    @FXML
    private void ItemStock(ActionEvent event){
        adminManagementHandler.loadItemStock(event);
    }
    @FXML
    private void SalesCounter(ActionEvent event){
        adminManagementHandler.loadSalesCounter(event);
    }
    @FXML
    private void OrderStatus(ActionEvent event) {
        adminManagementHandler.loadOrderStatus(event);
    }
    @FXML
    private void Employees(ActionEvent event){
        adminManagementHandler.loadEmployees(event);
    }
    //internal methods
    @FXML
    private void FoodProducts(ActionEvent event){
        inventorySessionHandler.loadBakeryProducts(rootpane);
    }
    @FXML
    private void AgencyProduct(ActionEvent event){
        inventorySessionHandler.loadAgencyProduct(rootpane);
    }
    @FXML
    private void Supplier(ActionEvent event) {
        inventorySessionHandler.loadSupplier(rootpane);
    }
    @FXML
    private void playBeep(){
        play.AddItemPlay();
    }
    @FXML
    private void clearFields(){
        try{
            INameTextField.setText("");
            IUnitsPerBlockTextField.setText("");
            IBlocksTextField.setText("");
            IWeightPerUnitTextField.setText("");
            IBuyingPriceTextField.setText("");
            IMinQuantityLimitTextField.setText("");
            IExpireDateDatePicker.setValue(null);
            ISupplierIDTextField.setText("");
            ISupplierNameTextField.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }

    }

    private void clearLabels(){
        try{
            INameLabel.setText("");
            IUnitsPerBlockLabel.setText("");
            IBlocksLabel.setText("");
            IWeightPerUnitLabel.setText("");
            IBuyingPriceLabel.setText("");
            IMinQuantityLimitLabel.setText("");
            IExpireDateLabel.setText("");
            ISupplierNameLabel.setText("");

        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(INameTextField)
                && DataValidation.TextFieldNotEmpty(IUnitsPerBlockTextField)
                && DataValidation.TextFieldNotEmpty(IBlocksTextField)
                && DataValidation.TextFieldNotEmpty(IWeightPerUnitTextField)
                && DataValidation.TextFieldNotEmpty(IBuyingPriceTextField)
                && DataValidation.TextFieldNotEmpty(IMinQuantityLimitTextField)
                && DataValidation.DatePickerNotEmpty(IExpireDateDatePicker)
                && DataValidation.TextFieldNotEmpty(ISupplierIDTextField)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(INameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(IUnitsPerBlockTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IBlocksTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IWeightPerUnitTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IBuyingPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IMinQuantityLimitTextField.getText(),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(IUnitsPerBlockTextField.getText())
                && DataValidation.isValidNumberFormat(IBlocksTextField.getText())
                && DataValidation.isValidNumberFormat(IWeightPerUnitTextField.getText())
                && DataValidation.isValidNumberFormat(IBuyingPriceTextField.getText())
                && DataValidation.isValidNumberFormat(IMinQuantityLimitTextField.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(INameTextField)
                && DataValidation.TextFieldNotEmpty(IUnitsPerBlockTextField)
                && DataValidation.TextFieldNotEmpty(IBlocksTextField)
                && DataValidation.TextFieldNotEmpty(IWeightPerUnitTextField)
                && DataValidation.TextFieldNotEmpty(IBuyingPriceTextField)
                && DataValidation.TextFieldNotEmpty(IMinQuantityLimitTextField)
                && DataValidation.DatePickerNotEmpty(IExpireDateDatePicker)
                && DataValidation.TextFieldNotEmpty(ISupplierIDTextField))){


            DataValidation.TextFieldNotEmpty(INameTextField, INameLabel,"Stock Item Name is Required");
            DataValidation.TextFieldNotEmpty(IUnitsPerBlockTextField, IUnitsPerBlockLabel,"Units per Block is Required");
            DataValidation.TextFieldNotEmpty(IBlocksTextField, IBlocksLabel,"Total no of Blocks is Required");
            DataValidation.TextFieldNotEmpty(IWeightPerUnitTextField, IWeightPerUnitLabel, "Weight of a Unit is Required(KG/L)");
            DataValidation.TextFieldNotEmpty(IBuyingPriceTextField, IBuyingPriceLabel, "Purchasing Price per Unit is Required");
            DataValidation.TextFieldNotEmpty(IMinQuantityLimitTextField, IMinQuantityLimitLabel, "Minimum Stock Quantity Limit is Required");
            DataValidation.DatePickerNotEmpty(IExpireDateDatePicker, IExpireDateLabel, "Expiration Date is required");
            DataValidation.TextFieldNotEmpty(ISupplierIDTextField, ISupplierNameLabel, " Choose a Stock Item Supplier");
            //checking for exceeding limit

        }
        if(!( DataValidation.isValidMaximumLength(INameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(IUnitsPerBlockTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IBlocksTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IWeightPerUnitTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IBuyingPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(IMinQuantityLimitTextField.getText(),10))){


            DataValidation.isValidMaximumLength(INameTextField.getText(),80, INameLabel,"Stock Item Name is too Long!..");
            DataValidation.isValidMaximumLength(IUnitsPerBlockTextField.getText(),10, IUnitsPerBlockLabel,"Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(IBlocksTextField.getText(),10, IBlocksLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(IWeightPerUnitTextField.getText(),10, IWeightPerUnitLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(IBuyingPriceTextField.getText(),10, IBuyingPriceLabel, "Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(IMinQuantityLimitTextField.getText(),10, IMinQuantityLimitLabel, "Error!..Exceeding Character limit 10");


        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(IUnitsPerBlockTextField.getText())
                && DataValidation.isValidNumberFormat(IBlocksTextField.getText())
                && DataValidation.isValidNumberFormat(IWeightPerUnitTextField.getText())
                && DataValidation.isValidNumberFormat(IBuyingPriceTextField.getText())
                && DataValidation.isValidNumberFormat(IMinQuantityLimitTextField.getText()))){
            //Checking for Specific Data Validation

            DataValidation.isValidNumberFormat(IUnitsPerBlockTextField.getText(), IUnitsPerBlockLabel,"No of Unit can contain only Digits");
            DataValidation.isValidNumberFormat(IBlocksTextField.getText(), IBlocksLabel, "No of Blocks can contain only Digits");
            DataValidation.isValidNumberFormat(IWeightPerUnitTextField.getText(), IWeightPerUnitLabel,"Stock Item Weight/Volume can contain only Digits");
            DataValidation.isValidNumberFormat(IBuyingPriceTextField.getText(), IBuyingPriceLabel,"Purchasing Price per Unit can contain only Digits");
            DataValidation.isValidNumberFormat(IMinQuantityLimitTextField.getText(), IMinQuantityLimitLabel, "Min Stock Unit Qty Limit can contain only Digits");
        }


    }
    private void dateValidation(){
        //hiding Old Dates on date picker
        Callback hideOldCallback = DataValidation.hideOldDates();
        IExpireDateDatePicker.setDayCellFactory(hideOldCallback);
    }
    @FXML
    private void selectSupplier(ActionEvent event){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ItemSupplierPopUP.fxml"));

        try{
            loader.load();

        }catch (IOException ex){
            Logger.getLogger(ItemSupplierPopUPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemSupplierPopUPController itemSupplierPopUPController = loader.getController();


        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.showAndWait();

        try{
            ISupplierIDTextField.setText(supplier.getsIID());
            ISupplierNameTextField.setText(supplier.getsIName());
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
    //load data from Main LoginController to View table
    private void loadData() {
        //getting data from main LoginController
        ItemStockServices itemStockServices = new ItemStockServices();
        
        ObservableList<ItemStock> itemStocktData;
        itemStocktData = itemStockServices.loadData();
        
        //Setting cell value factory to table view
        IIDColumn.setCellValueFactory(new PropertyValueFactory<>("iID"));
        INameColumn.setCellValueFactory(new PropertyValueFactory<>("iName"));
        ISIIDColumn.setCellValueFactory(new PropertyValueFactory<>("iSIID"));
        ISISupplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("iSISupplierName"));
        IUnitsPerBlockColumn.setCellValueFactory(new PropertyValueFactory<>("iUnitsPerBlock"));
        IBlocksColumn.setCellValueFactory(new PropertyValueFactory<>("iBlocks"));
        IWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("iWeightPerBlock"));
        IBuyingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("iBuyingPrice"));
        IExpireDateColumn.setCellValueFactory(new PropertyValueFactory<>("iExpireDate"));
        IAddedDateColumn.setCellValueFactory(new PropertyValueFactory<>("iAddedDate"));
        IMinQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("iMinQuantityLimit"));
        IAvailableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("iAvailableQuantity"));
        IStatusColumn.setCellValueFactory(new PropertyValueFactory<>("iStatus"));

        ItemStockTable.setItems(null);
        ItemStockTable.setItems(itemStocktData);
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
        ItemStock itemStockModel = new ItemStock();
        ItemStockServices itemStockServices = new ItemStockServices();

        if(dataValidate()){

            itemStockModel.setiName(INameTextField.getText());
            itemStockModel.setiUnitsPerBlock(Integer.parseInt(IUnitsPerBlockTextField.getText()));
            itemStockModel.setiBlocks(Integer.parseInt(IBlocksTextField.getText()));
            itemStockModel.setiWeightPerBlock(Float.parseFloat(IWeightPerUnitTextField.getText()));
            itemStockModel.setiBuyingPrice(Float.parseFloat(IBuyingPriceTextField.getText()));
            itemStockModel.setiMinQuantityLimit(Integer.parseInt(IMinQuantityLimitTextField.getText()));
            itemStockModel.setiExpireDate(String.valueOf(IExpireDateDatePicker.getValue()));
            itemStockModel.setiSIID(ISupplierIDTextField.getText());
            itemStockModel.setiAddedDate(String.valueOf(LocalDate.now()));

            Boolean resultVal = itemStockServices.insertData(itemStockModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }


    }
    @FXML
    private void loadSelectedData( ){

        ItemStock itemStockModel;
        try{
            itemStockModel = ItemStockTable.getSelectionModel().getSelectedItem();
            INameTextField.setText(itemStockModel.getiName());
            IUnitsPerBlockTextField.setText(String.valueOf(itemStockModel.getiUnitsPerBlock()));
            IBlocksTextField.setText(String.valueOf(itemStockModel.getiBlocks()));
            IWeightPerUnitTextField.setText(String.valueOf(itemStockModel.getiWeightPerBlock()));
            IBuyingPriceTextField.setText(String.valueOf(itemStockModel.getiBuyingPrice()));
            IMinQuantityLimitTextField.setText(String.valueOf(itemStockModel.getiMinQuantityLimit()));
            IExpireDateDatePicker.setValue(LocalDate.parse(itemStockModel.getiExpireDate()));
            ISupplierIDTextField.setText(itemStockModel.getiSIID());
            ISupplierNameTextField.setText(itemStockModel.getiSISupplierName());

            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        ItemStock itemStockModel;
        try{
            itemStockModel = ItemStockTable.getSelectionModel().getSelectedItem();
            existingItemStockModel = itemStockModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }
    public ItemStock getExistingItemStockModel(){
        return existingItemStockModel;
    }
    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        ItemStock itemStockModel = new ItemStock();
        ItemStockServices itemStockServices = new ItemStockServices();

        try{
            if(!(existingItemStockModel.getiID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    itemStockModel.setiID(existingItemStockModel.getiID());
                    itemStockModel.setiName(INameTextField.getText());
                    itemStockModel.setiSIID(ISupplierIDTextField.getText());
                    itemStockModel.setiSISupplierName(ISupplierNameTextField.getText());
                    itemStockModel.setiUnitsPerBlock(Integer.parseInt(IUnitsPerBlockTextField.getText()));
                    itemStockModel.setiBlocks(Integer.parseInt(IBlocksTextField.getText()));
                    itemStockModel.setiWeightPerBlock(Float.parseFloat(IWeightPerUnitTextField.getText()));
                    itemStockModel.setiBuyingPrice(Float.parseFloat(IBuyingPriceTextField.getText()));
                    itemStockModel.setiMinQuantityLimit(Integer.parseInt(IMinQuantityLimitTextField.getText()));
                    itemStockModel.setiExpireDate(String.valueOf(IExpireDateDatePicker.getValue()));
                    boolean resultVal = itemStockServices.updateData(existingItemStockModel, itemStockModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Item");
        }

    }
    @FXML
    private void deleteData(){

        int ID;
        ItemStock itemStockModel;
        ItemStockServices itemStockServices = new ItemStockServices();
        itemStockModel = ItemStockTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(itemStockModel.getiID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Item Stock");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = itemStockServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Item Stock");
        }


    }
    public void searchTable(){

        ItemStockServices itemStockServices = new ItemStockServices();
        //Retrieving sorted data from Main LoginController
        SortedList<ItemStock> sortedData = itemStockServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(ItemStockTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ItemStockTable.setItems(sortedData);


    }
}
