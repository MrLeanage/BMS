package view.EmployeeManagement;

import controller.AllowanceController;
import controller.SalarySchemeController;
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
import model.Allowance;
import model.SalaryScheme;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalarySchemeViewController implements Initializable {
    @FXML
    private TableView<SalaryScheme> BasicSalarySchemeTable;

    @FXML
    private TableColumn<SalaryScheme, String> BSSIDColumn;

    @FXML
    private TableColumn<SalaryScheme, String> BSSTitleColumn;

    @FXML
    private TableColumn<SalaryScheme, Float> BSSAmountColumn;

    @FXML
    private TableColumn<SalaryScheme, String> BSSDateColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField BSSTitleTextBox;

    @FXML
    private TextField BSSAmountTextBox;

    @FXML
    private Label BSSTitleLabel;

    @FXML
    private Label BSSAmountLabel;

    private static SalaryScheme existingSalarySchemeModel;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        loadData();
        //to auto refresh search results
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
    //internal methods
    @FXML
    private void SalarySchemes(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/SalarySchemes.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Allowances(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Allowances.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void SystemUsers(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/SystemUsers.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void playBeep(){
        //play.AddItemPlay();
    }
    @FXML
    private void clearFields(){

        try{
            BSSTitleTextBox.setText("");
            BSSAmountTextBox.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            BSSTitleLabel.setText("");
            BSSAmountLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(BSSTitleTextBox)
                && DataValidation.TextFieldNotEmpty(BSSAmountTextBox.getText())
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(BSSTitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(BSSAmountTextBox.getText(),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(BSSAmountTextBox.getText())){
            returnVal = true;
        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(BSSTitleTextBox)
                && DataValidation.TextFieldNotEmpty(BSSAmountTextBox.getText()))){


            DataValidation.TextFieldNotEmpty(BSSTitleTextBox, BSSTitleLabel,"Salary Scheme Title is Required");
            DataValidation.TextFieldNotEmpty(BSSAmountTextBox, BSSAmountLabel, "Salary Scheme Amount is Required");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(BSSTitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(BSSAmountTextBox.getText(),10))){


            DataValidation.isValidMaximumLength(BSSTitleTextBox.getText(),45, BSSTitleLabel,"Salary Scheme Title is too Long!..(Limit 45)");
            DataValidation.isValidMaximumLength(BSSAmountTextBox.getText(),10, BSSAmountLabel, "Error!..Exceeding Character limit 10 Characters");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(BSSAmountTextBox.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(BSSAmountTextBox.getText(), BSSAmountLabel,"Salary Scheme Amount can contain only Digits");
        }
    }

    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        SalarySchemeController salarySchemeController = new SalarySchemeController();

        ObservableList<SalaryScheme> salarySchemesData;
        salarySchemesData = salarySchemeController.loadData();

        //Setting cell value factory to table view
        BSSIDColumn.setCellValueFactory(new PropertyValueFactory<>("bSSID"));
        BSSTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bSSTitle"));
        BSSAmountColumn.setCellValueFactory(new PropertyValueFactory<>("bSSAmount"));
        BSSDateColumn.setCellValueFactory(new PropertyValueFactory<>("bSSAddedDate"));

        BasicSalarySchemeTable.setItems(null);
        BasicSalarySchemeTable.setItems(salarySchemesData);

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
        SalaryScheme salarySchemeModel = new SalaryScheme();
        SalarySchemeController salarySchemeController = new SalarySchemeController();

        if(dataValidate()){

            salarySchemeModel.setbSSTitle(BSSTitleTextBox.getText());
            salarySchemeModel.setbSSAmount(Float.parseFloat(BSSAmountTextBox.getText()));
            salarySchemeModel.setbSSAddedDate(String.valueOf(LocalDate.now()));
            Boolean resultVal = salarySchemeController.insertData(salarySchemeModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        SalaryScheme salarySchemeModel;

        try{
            salarySchemeModel = BasicSalarySchemeTable.getSelectionModel().getSelectedItem();
            BSSTitleTextBox.setText(salarySchemeModel.getbSSTitle());
            BSSAmountTextBox.setText(String.valueOf(salarySchemeModel.getbSSAmount()));
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        SalaryScheme salarySchemeModel;
        try{
            salarySchemeModel = BasicSalarySchemeTable.getSelectionModel().getSelectedItem();
            existingSalarySchemeModel = salarySchemeModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        SalaryScheme salarySchemeModel = new SalaryScheme();
        SalarySchemeController salarySchemeController = new SalarySchemeController();

        try{
            if(!(existingSalarySchemeModel.getbSSID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    salarySchemeModel.setbSSID(existingSalarySchemeModel.getbSSID());
                    salarySchemeModel.setbSSTitle(BSSTitleTextBox.getText());
                    salarySchemeModel.setbSSAmount(Float.parseFloat(BSSAmountTextBox.getText()));
                    salarySchemeModel.setbSSAddedDate(String.valueOf(LocalDate.now()));
                    boolean resultVal = salarySchemeController.updateData(salarySchemeModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Salary Scheme");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        SalaryScheme salarySchemeModel;
        SalarySchemeController salarySchemeController = new SalarySchemeController();
        salarySchemeModel = BasicSalarySchemeTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(salarySchemeModel.getbSSID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Salary Scheme");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = salarySchemeController.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Salary Scheme");
        }
    }
    public void searchTable(){

        SalarySchemeController salarySchemeController = new SalarySchemeController();
        //Retrieving sorted data from Main Controller
        SortedList<SalaryScheme> sortedData = salarySchemeController.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(BasicSalarySchemeTable.comparatorProperty());
        //adding sorted and filtered data to the table
        BasicSalarySchemeTable.setItems(sortedData);
    }

}
