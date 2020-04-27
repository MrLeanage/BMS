package view.FinanceManagement;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.OtherExpense;
import model.Purchase;
import services.OtherExpenseServices;
import services.PurchaseServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceSessionHandler;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OtherExpensesAdminController implements Initializable {

    @FXML
    private TableView<OtherExpense> ExpenseTable;

    @FXML
    private TableColumn<OtherExpense, String> EXPIDColumn;

    @FXML
    private TableColumn<OtherExpense, String> EXPTitleColumn;

    @FXML
    private TableColumn<OtherExpense, String> EXPDescriptionColumn;

    @FXML
    private TableColumn<OtherExpense, String> EXPPeriodColumn;

    @FXML
    private TableColumn<OtherExpense, Double> EXPAmountColumn;

    @FXML
    private TableColumn<OtherExpense, String> EXPPaidDateColumn;

    @FXML
    private TableColumn<OtherExpense, String> EXPAddedDateColumn;

    @FXML
    private Label PurchasePeriodLabel;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private Label PayForSupplierLabel;

    @FXML
    private TextField EXPTitleTextBox;

    @FXML
    private TextArea EXPDescriptionTextArea;

    @FXML
    private TextField EXPYearTextBox;

    @FXML
    private ComboBox<String> EXPMonthComboBox;

    @FXML
    private TextField EXPAmountTextBox;

    @FXML
    private DatePicker EXPDateDatePicker;

    @FXML
    private Label EXPTitleLabel;

    @FXML
    private Label EXPDescriptionLabel;

    @FXML
    private Label EXPPeriodLabel;

    @FXML
    private Label EXPAmountLabel;

    @FXML
    private Label EXPPaidDateLabel;

    private Integer year = UtilityMethod.getYear(String.valueOf(LocalDate.now()));
    private String month = UtilityMethod.getMonth(String.valueOf(LocalDate.now()));
    private static OtherExpense existingOtherExpenseModel;

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceSessionHandler financeSessionHandler = new FinanceSessionHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        loadChoiceBoxes();
        searchTable();
    }

    @FXML
    private void SalesCounter(ActionEvent event){
        adminManagementHandler.loadSalesCounter(event);
    }
    @FXML
    private void SalesReport(ActionEvent event){
        financeSessionHandler.loadSalesReport(rootpane);
    }
    @FXML
    private void PurchasesReport(ActionEvent event){
        financeSessionHandler.loadPurchasesReport(rootpane);
    }
    @FXML
    private void PaySheet(ActionEvent event){
        financeSessionHandler.loadPaySheet(rootpane);
    }
    @FXML
    private void PayRoll(ActionEvent event) {
        financeSessionHandler.loadPayRoll(rootpane);
    }
    @FXML
    private void OtherExpenses(ActionEvent event){
        financeSessionHandler.loadOtherExpenses(rootpane);
    }
    @FXML
    private void IncomeStatement(ActionEvent event) {
        financeSessionHandler.loadIncomeStatement(rootpane);
    }
    @FXML
    private void clearFields(){
        EXPTitleTextBox.setText("");
        EXPDescriptionTextArea.setText("");
        EXPYearTextBox.setText(String.valueOf(UtilityMethod.getYear(String.valueOf(LocalDate.now()))));
        EXPMonthComboBox.setValue(UtilityMethod.getMonth(String.valueOf(LocalDate.now())));
        EXPAmountTextBox.setText("");
        EXPDateDatePicker.setValue(LocalDate.now());
        clearLabels();
    }
    private void clearLabels(){
        EXPTitleLabel.setText("");
        EXPDescriptionLabel.setText("");
        EXPPeriodLabel.setText("");
        EXPAmountLabel.setText("");
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(EXPTitleTextBox)
                && DataValidation.TextAreaNotEmpty(EXPDescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(EXPAmountTextBox)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(EXPTitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(EXPDescriptionTextArea.getText(),100)
                && DataValidation.isValidMaximumLength(EXPAmountTextBox.getText(),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(EXPAmountTextBox.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(EXPTitleTextBox)
                && DataValidation.TextAreaNotEmpty(EXPDescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(EXPAmountTextBox))){


            DataValidation.TextFieldNotEmpty(EXPTitleTextBox, EXPTitleLabel,"Expense Title is Required");
            DataValidation.TextFieldNotEmpty(EXPDescriptionTextArea, EXPDescriptionLabel,"Expense Description is Required");
            DataValidation.TextFieldNotEmpty(EXPAmountTextBox, EXPAmountLabel,"Expense Amount is Required");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(EXPTitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(EXPDescriptionTextArea.getText(),100)
                && DataValidation.isValidMaximumLength(EXPAmountTextBox.getText(),10))){


            DataValidation.isValidMaximumLength(EXPTitleTextBox.getText(),45, EXPTitleLabel,"Expense Title is too Long!..(Limit 45)");
            DataValidation.isValidMaximumLength(EXPDescriptionTextArea.getText(),100, EXPDescriptionLabel,"Error!..Exceeding Character limit 100");
            DataValidation.isValidMaximumLength(EXPAmountTextBox.getText(),10, EXPAmountLabel, "Error!..Exceeding Character limit 10 Characters");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(EXPAmountTextBox.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(EXPAmountTextBox.getText(), EXPAmountLabel,"Expense Value can contain only Digits");
        }
    }
    //load sales dates to choiceboxes and Chart
    private void loadChoiceBoxes(){
        EXPYearTextBox.setText(String.valueOf(UtilityMethod.getYear(String.valueOf(LocalDate.now()))));
        ObservableList<String> months = FXCollections.observableArrayList("All Months", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        EXPMonthComboBox.setValue(UtilityMethod.getMonth(String.valueOf(LocalDate.now())));
        EXPMonthComboBox.setItems(months);
        EXPDateDatePicker.setValue(LocalDate.now());

    }

    //load data to View table
    private void loadData() {
        //getting data
        OtherExpenseServices otherExpenseServices = new OtherExpenseServices();
        ObservableList<OtherExpense> otherExpensesData;
        otherExpensesData = otherExpenseServices.loadData();


        //Setting cell value factory to table view
        EXPIDColumn.setCellValueFactory(new PropertyValueFactory<>("eXPID"));
        EXPTitleColumn.setCellValueFactory(new PropertyValueFactory<>("eXPTitle"));
        EXPDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eXPDescription"));
        EXPPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("eXPPeriod"));
        EXPAmountColumn.setCellValueFactory(new PropertyValueFactory<>("eXPAmount"));
        EXPPaidDateColumn.setCellValueFactory(new PropertyValueFactory<>("eXPPaidDate"));
        EXPAddedDateColumn.setCellValueFactory(new PropertyValueFactory<>("eXPAddedDate"));
        ExpenseTable.setItems(null);
        ExpenseTable.setItems(otherExpensesData);
    }
    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{
        clearFields();
        loadData();
        searchTable();
    }
    //Adding Data
    @FXML
    private void addData(ActionEvent event) throws Exception {

        clearLabels();
        OtherExpense otherExpenseModel = new OtherExpense();
        OtherExpenseServices otherExpenseServices = new OtherExpenseServices();

        if(dataValidate()){

            otherExpenseModel.seteXPTitle(EXPTitleTextBox.getText());
            otherExpenseModel.seteXPDescription(EXPDescriptionTextArea.getText());
            otherExpenseModel.seteXPPeriod(EXPMonthComboBox.getValue() + "-" +EXPYearTextBox.getText());
            otherExpenseModel.seteXPAmount(Double.parseDouble(EXPAmountTextBox.getText()));
            otherExpenseModel.seteXPPaidDate(String.valueOf(EXPDateDatePicker.getValue()));
            otherExpenseModel.seteXPAddedDate(String.valueOf(LocalDate.now()));
            Boolean resultVal = otherExpenseServices.insertData(otherExpenseModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        OtherExpense otherExpenseModel;

        try{
            otherExpenseModel = ExpenseTable.getSelectionModel().getSelectedItem();
            EXPTitleTextBox.setText(otherExpenseModel.geteXPTitle());
            EXPDescriptionTextArea.setText(otherExpenseModel.geteXPDescription());
            EXPYearTextBox.setText(String.valueOf(UtilityMethod.seperateIntegerFromString(otherExpenseModel.geteXPPeriod())));
            EXPMonthComboBox.setValue(UtilityMethod.seperateLettersFromText(otherExpenseModel.geteXPPeriod()));
            EXPAmountTextBox.setText(String.valueOf(otherExpenseModel.geteXPAmount()));
            EXPDateDatePicker.setValue(LocalDate.parse(otherExpenseModel.geteXPPaidDate()));
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }
    @FXML
    public void loadSelectedModelData(){
        OtherExpense otherExpenseModel;
        try{
            otherExpenseModel = ExpenseTable.getSelectionModel().getSelectedItem();
            existingOtherExpenseModel = otherExpenseModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }
    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        OtherExpense otherExpenseModel = new OtherExpense();
        OtherExpenseServices otherExpenseServices = new OtherExpenseServices();


        try{
            if(!(existingOtherExpenseModel.geteXPID().isEmpty())){
                if(dataValidate()){
                    otherExpenseModel.seteXPID(existingOtherExpenseModel.geteXPID());
                    otherExpenseModel.seteXPTitle(EXPTitleTextBox.getText());
                    otherExpenseModel.seteXPDescription(EXPDescriptionTextArea.getText());
                    otherExpenseModel.seteXPPeriod(EXPMonthComboBox.getValue() + "-" +EXPYearTextBox.getText());
                    otherExpenseModel.seteXPAmount(Double.parseDouble(EXPAmountTextBox.getText()));
                    otherExpenseModel.seteXPPaidDate(String.valueOf(EXPDateDatePicker.getValue()));
                    otherExpenseModel.seteXPAddedDate(String.valueOf(LocalDate.now()));
                    Boolean resultVal = otherExpenseServices.updateData(otherExpenseModel);
                    if(resultVal){
                        refreshTable();
                        existingOtherExpenseModel = null;
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Expense Info");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        OtherExpenseServices otherExpenseServices = new OtherExpenseServices();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(existingOtherExpenseModel.geteXPID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Expense info");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = otherExpenseServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                        existingOtherExpenseModel = null;
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Expense Info");
        }
    }
    public void searchTable(){

        OtherExpenseServices otherExpenseServices = new OtherExpenseServices();

        SortedList<OtherExpense> sortedData = otherExpenseServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(ExpenseTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ExpenseTable.setItems(sortedData);
    }


}
