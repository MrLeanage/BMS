package view.EmployeeManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.User;
import services.UserServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.EmployeeHandler;
import util.userAlerts.AlertPopUp;
import util.utility.DataEncryption;
import util.validation.DataValidation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SystemUsersController implements Initializable {
    @FXML
    private TableView<User> UserTable;

    @FXML
    private TableColumn<User, String> UIDColumn;

    @FXML
    private TableColumn<User, String> UNameColumn;

    @FXML
    private TableColumn<User, String> UTypeColumn;

    @FXML
    private TableColumn<User, String> UPasswordColumn;

    @FXML
    private TableColumn<User, String> UStatusColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField UIDTextField;

    @FXML
    private PasswordField UConfirmPasswordField;

    @FXML
    private PasswordField UPasswordField;

    @FXML
    private ChoiceBox<String> UTypeChoiceBox;

    @FXML
    private ChoiceBox<String> UStatusChoiceBox;

    @FXML
    private TextField UNameTextField;

    @FXML
    private Label UTypeLabel;

    @FXML
    private Label UIDLabel;

    @FXML
    private Label UNameLabel;

    @FXML
    private Label UPasswordLabel;

    @FXML
    private Label UConfirmPasswordLabel;

    @FXML
    private Label UStatusLabel;

    private static User existingUserModel = null;

    private ObservableList<String> uType = FXCollections.observableArrayList( "Cashier", "Supervisor", "Admin");
    private ObservableList<String> uStatus = FXCollections.observableArrayList( "Active", "Disable");

    @FXML
    private AnchorPane rootpane;

    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private EmployeeHandler employeeHandler = new EmployeeHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UTypeChoiceBox.setValue("Cashier");
        UTypeChoiceBox.setItems(uType);
        UStatusChoiceBox.setValue("Active");
        UStatusChoiceBox.setItems(uStatus);


        loadData();
        //to auto refresh search results
        searchTable();

    }

    @FXML
    private void Employees(ActionEvent event){
        adminManagementHandler.loadEmployees(event);
    }
    //internal methods
    @FXML
    private void SalarySchemes(ActionEvent event){
        employeeHandler.loadSalarySchemes(rootpane);
    }
    @FXML
    private void Allowances(ActionEvent event){
        employeeHandler.loadAllowances(rootpane);
    }
    @FXML
    private void SystemUsers(ActionEvent event){
        employeeHandler.loadSystemUsers(rootpane);
    }
    @FXML
    private void clearFields(){

        try{

            UIDTextField.setText("");
            UNameTextField.setText("");
            UPasswordField.setText("");
            UTypeChoiceBox.setValue("");
            UStatusChoiceBox.setValue("");
            UConfirmPasswordField.setText("");
            UIDTextField.setEditable(true);
            clearLabels();

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            UTypeLabel.setText("");
            UIDLabel.setText("");
            UNameLabel.setText("");
            UPasswordLabel.setText("");
            UConfirmPasswordLabel.setText("");
            UStatusLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate() throws SQLException {
        UserServices userServices = new UserServices();
        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(UIDTextField)
                && DataValidation.TextFieldNotEmpty(UNameTextField)
                && DataValidation.PasswordFieldNotEmpty(UPasswordField)
                && DataValidation.PasswordFieldNotEmpty(UConfirmPasswordField)
                //Checking for Maximum/Minimum Characters
                && DataValidation.isValidMaximumLength(UIDTextField.getText(),45)
                && DataValidation.isValidMaximumLength(UNameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(UPasswordField.getText(),30)
                && DataValidation.isValidMaximumLength(UConfirmPasswordField.getText(),30)
                && DataValidation.isValidMinimumLength(UPasswordField.getText(),5)
                && DataValidation.isValidMinimumLength(UConfirmPasswordField.getText(),5)
                //Checking for Specific Data Validation
                && DataValidation.PasswordFieldMatch(UPasswordField, UConfirmPasswordField)){
            returnVal = true;

        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage() throws SQLException {
        UserServices userServices = new UserServices();
        //checking for being empty

        if(!(DataValidation.TextFieldNotEmpty(UIDTextField)
                && DataValidation.TextFieldNotEmpty(UNameTextField)
                && DataValidation.PasswordFieldNotEmpty(UPasswordField)
                && DataValidation.PasswordFieldNotEmpty(UConfirmPasswordField))){

            DataValidation.TextFieldNotEmpty(UIDTextField, UIDLabel,"User ID is Required");
            DataValidation.TextFieldNotEmpty(UNameTextField, UNameLabel,"User's Name is Required");
            DataValidation.PasswordFieldNotEmpty(UPasswordField, UPasswordLabel,"Password cannot be Empty");
            DataValidation.PasswordFieldNotEmpty(UConfirmPasswordField, UConfirmPasswordLabel, "Password cannot be Empty");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(UIDTextField.getText(),45)
                && DataValidation.isValidMaximumLength(UNameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(UPasswordField.getText(),30)
                && DataValidation.isValidMaximumLength(UConfirmPasswordField.getText(),30)
                && DataValidation.isValidMinimumLength(UPasswordField.getText(),5)
                && DataValidation.isValidMinimumLength(UConfirmPasswordField.getText(),5))){

            DataValidation.isValidMaximumLength(UIDTextField.getText(),45, UIDLabel,"User ID is too Long!..(Limit 45)");
            DataValidation.isValidMaximumLength(UNameTextField.getText(),80, UNameLabel,"Error!..Exceeding Character limit 80");
            DataValidation.isValidMaximumLength(UPasswordField.getText(),30, UPasswordLabel, "Error!..Exceeding Character limit 30");
            DataValidation.isValidMaximumLength(UConfirmPasswordField.getText(),30, UConfirmPasswordLabel,"Error!..Exceeding Character limit 30");
            DataValidation.isValidMinimumLength(UPasswordField.getText(),5, UPasswordLabel,"Password must have at least 5 Characters");
            DataValidation.isValidMinimumLength(UConfirmPasswordField.getText(),5, UConfirmPasswordLabel, "Password must have at least 5 Characters");

        }
        //checking for specific properties
        if(!( DataValidation.PasswordFieldMatch(UPasswordField, UConfirmPasswordField))){
            //Checking for Specific Data Validation

            DataValidation.PasswordFieldMatch(UPasswordField, UConfirmPasswordField, UPasswordLabel, UConfirmPasswordLabel,"Your Passwords does not Match");
        }
    }

    //load data from Main LoginController to View table
    private void loadData() {

        //getting data from Services
        UserServices userServices = new UserServices();

        ObservableList<User> usersData;
        usersData = userServices.loadData();

        //Setting cell value factory to table view
        UIDColumn.setCellValueFactory(new PropertyValueFactory<>("uID"));
        UNameColumn.setCellValueFactory(new PropertyValueFactory<>("uName"));
        UTypeColumn.setCellValueFactory(new PropertyValueFactory<>("uType"));
        UPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("uPassword"));
        UStatusColumn.setCellValueFactory(new PropertyValueFactory<>("uStatus"));

        UserTable.setItems(null);
        UserTable.setItems(usersData);
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
        User userModel = new User();
        UserServices userServices = new UserServices();

        if(dataValidate()){
            if(userServices.DataNotExist(UIDTextField.getText()).getuID().equals("Empty")){
                userModel.setuID(UIDTextField.getText());
                userModel.setuName(UNameTextField.getText());
                userModel.setuType(UTypeChoiceBox.getValue());
                userModel.setuPassword(DataEncryption.passwordEncryption(UPasswordField.getText()));
                userModel.setuStatus(UStatusChoiceBox.getValue());
                Boolean resultVal = userServices.insertData(userModel);
                if(resultVal){
                    refreshTable();
                }
            }else {
                UIDLabel.setText("User ID Already Exist");
            }

        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData(){
        clearFields();
        UIDTextField.setEditable(false);
        User userModel;
        try{
            userModel = UserTable.getSelectionModel().getSelectedItem();
            UIDTextField.setText(userModel.getuID());
            UNameTextField.setText(userModel.getuName());
            UTypeChoiceBox.setValue(userModel.getuType());
            UStatusChoiceBox.setValue(userModel.getuStatus());
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){

        User userModel;
        try{
            userModel = UserTable.getSelectionModel().getSelectedItem();
            existingUserModel = userModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        User userModel = new User();
        UserServices userServices = new UserServices();
        try{
            if(!existingUserModel.getuID().isEmpty()){
                try{
                    if(dataValidate()){

                        //Overriding existing values retreiving from table
                        userModel.setuID(existingUserModel.getuID());
                        userModel.setuName(UNameTextField.getText());
                        userModel.setuType(UTypeChoiceBox.getValue());
                        userModel.setuPassword(DataEncryption.passwordEncryption(UPasswordField.getText()));
                        userModel.setuStatus(UStatusChoiceBox.getValue());
                        Boolean resultVal = userServices.updateData(userModel);
                        if(resultVal){
                            refreshTable();
                        }
                    }else{
                        dataValidateMessage();
                    }
                }catch (NullPointerException ex){
                    AlertPopUp.generalError(ex);

                }
            }
        }catch(NullPointerException ex){
            AlertPopUp.selectRowToUpdate("User Record");
        }



    }
    @FXML
    private void deleteData(){

        /*
        int ID;
        Allowance allowanceModel;
        AllowanceServices allowancesController = new AllowanceServices();
        allowanceModel = AllowanceTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(allowanceModel.getaID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Allowance Scheme");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = allowancesController.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Allowance Scheme");
        }
         */
    }
    public void searchTable(){
        UserServices userServices = new UserServices();
        //Retrieving sorted data from Main LoginController
        SortedList<User> sortedData = userServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(UserTable.comparatorProperty());
        //adding sorted and filtered data to the table
        UserTable.setItems(sortedData);

    }
}
