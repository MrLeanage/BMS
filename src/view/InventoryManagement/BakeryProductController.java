package view.InventoryManagement;

import services.BakeryProductServices;
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
import util.authenticate.AdminManagementHandler;
import util.authenticate.InventorySessionHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BakeryProductController implements Initializable {
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

    @FXML
    private TextField BPNameTextField;

    @FXML
    private TextField BPWeightTextField;

    @FXML
    private Label BPNameLabel;

    @FXML
    private Label BPWeightLabel;

    @FXML
    private Label BPTypeLabel;

    @FXML
    private TextField BPPriceTextField;

    @FXML
    private Label BPPriceLabel;

    @FXML
    private TextArea BPDescriptionTextArea;

    @FXML
    private ChoiceBox<String> BPTypeChoiceBox;

    @FXML
    private ChoiceBox<String> BPStatusChoiceBox;

    @FXML
    private Label BPDescriptionLabel;

    @FXML
    private Label BPStatusLabel;

    private static BakeryProduct existingBakeryProductModel;

    private ObservableList<String> typeChoiceboxList = FXCollections.observableArrayList("Vegi","Egg","Fish","Chicken", "Beef","Pork","Mutton");

    private ObservableList<String> statusChoiceboxList = FXCollections.observableArrayList("Available","Not Available");

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private InventorySessionHandler inventorySessionHandler = new InventorySessionHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BPTypeChoiceBox.setValue("Vegi");
        BPTypeChoiceBox.setItems(typeChoiceboxList);

        BPStatusChoiceBox.setValue("Available");
        BPStatusChoiceBox.setItems(statusChoiceboxList);
        loadData();
        //to auto refresh search results
        searchTable();

    }
    //internal methods
    @FXML
    private void ItemStock(ActionEvent event){
        adminManagementHandler.loadItemStock(event);
    }
    @FXML
    private void FoodProducts(ActionEvent event) {
        inventorySessionHandler.loadBakeryProducts(rootpane);
    }
    @FXML
    private void AgencyProduct(ActionEvent event)  {
        inventorySessionHandler.loadAgencyProduct(rootpane);
    }
    @FXML
    private void Supplier(ActionEvent event) {
        inventorySessionHandler.loadSupplier(rootpane);
    }
    @FXML
    private void playBeep(){
        //play.AddItemPlay();
    }
    @FXML
    private void clearFields(){

        try{
            BPNameTextField.setText("");
            BPWeightTextField.setText("");
            BPPriceTextField.setText("");
            BPDescriptionTextArea.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            BPNameLabel.setText("");
            BPTypeLabel.setText("");
            BPWeightLabel.setText("");
            BPDescriptionLabel.setText("");
            BPPriceLabel.setText("");

        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(BPNameTextField)
                && DataValidation.TextFieldNotEmpty(BPWeightTextField)
                && DataValidation.TextAreaNotEmpty(BPDescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(BPPriceTextField)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(BPNameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(BPWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(BPDescriptionTextArea.getText(),250)
                && DataValidation.isValidMaximumLength(BPPriceTextField.getText(),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(BPWeightTextField.getText())
                && DataValidation.isValidNumberFormat(BPPriceTextField.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(BPNameTextField)
                && DataValidation.TextFieldNotEmpty(BPWeightTextField)
                && DataValidation.TextAreaNotEmpty(BPDescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(BPPriceTextField))){


            DataValidation.TextFieldNotEmpty(BPNameTextField, BPNameLabel,"Name for Bakery SalesItem is Required");
            DataValidation.TextFieldNotEmpty(BPWeightTextField, BPWeightLabel,"Weight of SalesItem is Required(KG/L)");
            DataValidation.TextFieldNotEmpty(BPDescriptionTextArea, BPDescriptionLabel,"Description about the SalesItem is Required");
            DataValidation.TextFieldNotEmpty(BPPriceTextField, BPPriceLabel, "Price of the SalesItem is Required");
            //checking for exceeding limit

        }
        if(!( DataValidation.isValidMaximumLength(BPNameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(BPWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(BPDescriptionTextArea.getText(),250)
                && DataValidation.isValidMaximumLength(BPPriceTextField.getText(),10))){


            DataValidation.isValidMaximumLength(BPNameTextField.getText(),80, BPNameLabel,"Bakery SalesItem Name is too Long!..");
            DataValidation.isValidMaximumLength(BPWeightTextField.getText(),10, BPWeightLabel,"Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(BPDescriptionTextArea.getText(),250, BPDescriptionLabel, "Error!..Exceeding Character limit 250 Characters");
            DataValidation.isValidMaximumLength(BPPriceTextField.getText(),10, BPPriceLabel, "Error!..Exceeding Character limit 10");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(BPWeightTextField.getText())
                && DataValidation.isValidNumberFormat(BPPriceTextField.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(BPWeightTextField.getText(), BPWeightLabel,"SalesItem Weight can contain only Digits");
            DataValidation.isValidNumberFormat(BPPriceTextField.getText(), BPPriceLabel, "SalesItem Price can contain only Digits");
        }
    }

    //load data from Main LoginController to View table
    private void loadData() {
        //getting data from main LoginController
        BakeryProductServices bakeryProductServices = new BakeryProductServices();

        ObservableList<BakeryProduct> bakeryProductsData;
        bakeryProductsData = bakeryProductServices.loadData();

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
        clearFields();
        loadData();
        clearLabels();
    }
    //Add Supplier
    @FXML
    private void addData(ActionEvent event) throws Exception{

        clearLabels();
        BakeryProduct bakeryProductModel = new BakeryProduct();
        BakeryProductServices bakeryProductServices = new BakeryProductServices();

        if(dataValidate()){

            bakeryProductModel.setbPName(BPNameTextField.getText());
            bakeryProductModel.setbPType(BPTypeChoiceBox.getValue());
            bakeryProductModel.setbPWeight(Float.parseFloat(BPWeightTextField.getText()));
            bakeryProductModel.setbPDescription(BPDescriptionTextArea.getText());
            bakeryProductModel.setbPPrice(Float.parseFloat(BPPriceTextField.getText()));
            bakeryProductModel.setbPStatus(BPStatusChoiceBox.getValue());

            Boolean resultVal = bakeryProductServices.insertData(bakeryProductModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        BakeryProduct bakeryProductModel;

        try{
            bakeryProductModel = BakeryProductsTable.getSelectionModel().getSelectedItem();
            BPNameTextField.setText(bakeryProductModel.getbPName());
            BPTypeChoiceBox.setValue(bakeryProductModel.getbPType());
            BPWeightTextField.setText(String.valueOf(bakeryProductModel.getbPWeight()));
            BPDescriptionTextArea.setText(bakeryProductModel.getbPDescription());
            BPPriceTextField.setText(String.valueOf(bakeryProductModel.getbPPrice()));
            BPStatusChoiceBox.setValue(bakeryProductModel.getbPStatus());
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        BakeryProduct bakeryProductModel;
        try{
            bakeryProductModel = BakeryProductsTable.getSelectionModel().getSelectedItem();
            existingBakeryProductModel = bakeryProductModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        BakeryProduct bakeryProductModel = new BakeryProduct();
        BakeryProductServices bakeryProductServices = new BakeryProductServices();

        try{
            if(!(existingBakeryProductModel.getbPID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    bakeryProductModel.setbPID(existingBakeryProductModel.getbPID());
                    bakeryProductModel.setbPName(BPNameTextField.getText());
                    bakeryProductModel.setbPType(BPTypeChoiceBox.getValue());
                    bakeryProductModel.setbPWeight(Float.parseFloat(BPWeightTextField.getText()));
                    bakeryProductModel.setbPDescription(BPDescriptionTextArea.getText());
                    bakeryProductModel.setbPPrice(Float.parseFloat(BPPriceTextField.getText()));
                    bakeryProductModel.setbPStatus(BPStatusChoiceBox.getValue());
                    boolean resultVal = bakeryProductServices.updateData(bakeryProductModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Bakery SalesItem");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        BakeryProduct bakeryProductModel;
        BakeryProductServices bakeryProductServices = new BakeryProductServices();
        bakeryProductModel = BakeryProductsTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(bakeryProductModel.getbPID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Bakery SalesItem");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = bakeryProductServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Bakery SalesItem");
        }
    }
    public void searchTable(){

        BakeryProductServices bakeryProductServices = new BakeryProductServices();
        //Retrieving sorted data from Main LoginController
        SortedList<BakeryProduct> sortedData = bakeryProductServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(BakeryProductsTable.comparatorProperty());
        //adding sorted and filtered data to the table
        BakeryProductsTable.setItems(sortedData);




    }

}
