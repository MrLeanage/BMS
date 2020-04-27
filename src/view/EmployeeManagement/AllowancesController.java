package view.EmployeeManagement;

import services.AllowanceServices;
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
import util.authenticate.AdminManagementHandler;
import util.authenticate.EmployeeSessionHandler;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AllowancesController implements Initializable {
    @FXML
    private TableView<Allowance> AllowanceTable;

    @FXML
    private TableColumn<Allowance, String> AIDColumn;

    @FXML
    private TableColumn<Allowance, String> ATitleColumn;

    @FXML
    private TableColumn<Allowance, String> ADescriptionColumn;

    @FXML
    private TableColumn<Allowance, String> ATypeColumn;

    @FXML
    private TableColumn<Allowance, String> AValueColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField ATitleTextBox;

    @FXML
    private TextArea ADescriptionTextArea;

    @FXML
    private TextField AValueTextBox;

    @FXML
    private ChoiceBox<String> ATypeChoiceBox;

    @FXML
    private Label ATitleLabel;

    @FXML
    private Label ADescriptionLabel;

    @FXML
    private Label ATypeLabel;

    @FXML
    private Label AValueLabel;

    private static Allowance existingAllowaneModel;

    private ObservableList<String> typeChoiceboxList = FXCollections.observableArrayList( "Rate Value", "Fixed Value");

    @FXML
    private AnchorPane rootpane;

    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private EmployeeSessionHandler employeeSessionHandler = new EmployeeSessionHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ATypeChoiceBox.setItems(typeChoiceboxList);


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
        employeeSessionHandler.loadSalarySchemes(rootpane);
    }
    @FXML
    private void Allowances(ActionEvent event){
        employeeSessionHandler.loadAllowances(rootpane);
    }
    @FXML
    private void SystemUsers(ActionEvent event){
        employeeSessionHandler.loadSystemUsers(rootpane);
    }
    @FXML
    private void playBeep(){
        //play.AddItemPlay();
    }
    @FXML
    private void clearFields(){

        try{
            ATitleTextBox.setText("");
            ADescriptionTextArea.setText("");
            ATypeChoiceBox.setValue("");
            AValueTextBox.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            ATitleLabel.setText("");
            ADescriptionLabel.setText("");
            ATypeLabel.setText("");
            AValueLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(ATitleTextBox)
                && DataValidation.TextAreaNotEmpty(ADescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(ATypeChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(AValueTextBox)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(ATitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(ADescriptionTextArea.getText(),100)
                && DataValidation.isValidMaximumLength(AValueTextBox.getText(),10)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(AValueTextBox.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(ATitleTextBox)
                && DataValidation.TextAreaNotEmpty(ADescriptionTextArea)
                && DataValidation.TextFieldNotEmpty(ATypeChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(AValueTextBox))){


            DataValidation.TextFieldNotEmpty(ATitleTextBox, ATitleLabel,"Allowance Title is Required");
            DataValidation.TextFieldNotEmpty(ADescriptionTextArea, ADescriptionLabel,"Allowance Description is Required");
            DataValidation.TextFieldNotEmpty(ATypeChoiceBox.getValue(), ATypeLabel,"Please Select Allowance Type");
            DataValidation.TextFieldNotEmpty(AValueTextBox, AValueLabel, "Allowance Value is Required");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(ATitleTextBox.getText(),45)
                && DataValidation.isValidMaximumLength(ADescriptionTextArea.getText(),100)
                && DataValidation.isValidMaximumLength(AValueTextBox.getText(),10))){


            DataValidation.isValidMaximumLength(ATitleTextBox.getText(),45, ATitleLabel,"Allowance Title is too Long!..(Limit 45)");
            DataValidation.isValidMaximumLength(ADescriptionTextArea.getText(),100, ADescriptionLabel,"Error!..Exceeding Character limit 100");
            DataValidation.isValidMaximumLength(AValueTextBox.getText(),10, ATypeLabel, "Error!..Exceeding Character limit 10 Characters");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(AValueTextBox.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(AValueTextBox.getText(), AValueLabel,"Allowance Value can contain only Digits");
        }
    }

    //load data from Main LoginController to View table
    private void loadData() {
        //getting data from main LoginController
        AllowanceServices allowancesController = new AllowanceServices();

        ObservableList<Allowance> allowanceData;
        allowanceData = allowancesController.loadData();

        //Setting cell value factory to table view
        AIDColumn.setCellValueFactory(new PropertyValueFactory<>("aID"));
        ATitleColumn.setCellValueFactory(new PropertyValueFactory<>("aTitle"));
        ADescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aDescription"));
        ATypeColumn.setCellValueFactory(new PropertyValueFactory<>("aType"));
        AValueColumn.setCellValueFactory(new PropertyValueFactory<>("aValue"));

        AllowanceTable.setItems(null);
        AllowanceTable.setItems(allowanceData);

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
        Allowance allowanceModel = new Allowance();
        AllowanceServices allowancesController = new AllowanceServices();

        if(dataValidate()){

            allowanceModel.setaTitle(ATitleTextBox.getText());
            allowanceModel.setaDescription(ADescriptionTextArea.getText());
            allowanceModel.setaType(ATypeChoiceBox.getValue());
            allowanceModel.setaValue(Float.parseFloat(AValueTextBox.getText()));
            Boolean resultVal = allowancesController.insertData(allowanceModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        Allowance allowanceModel;

        try{
            allowanceModel = AllowanceTable.getSelectionModel().getSelectedItem();
            ATitleTextBox.setText(allowanceModel.getaTitle());
            ADescriptionTextArea.setText(allowanceModel.getaDescription());
            ATypeChoiceBox.setValue(allowanceModel.getaType());
            AValueTextBox.setText(String.valueOf(allowanceModel.getaValue()));
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        Allowance allowanceModel;
        try{
            allowanceModel = AllowanceTable.getSelectionModel().getSelectedItem();
            existingAllowaneModel = allowanceModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        Allowance allowanceModel = new Allowance();
        AllowanceServices allowancesController = new AllowanceServices();

        try{
            if(!(existingAllowaneModel.getaID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    allowanceModel.setaID(existingAllowaneModel.getaID());
                    allowanceModel.setaTitle(ATitleTextBox.getText());
                    allowanceModel.setaDescription(ADescriptionTextArea.getText());
                    allowanceModel.setaType(ATypeChoiceBox.getValue());
                    allowanceModel.setaValue(Float.parseFloat(AValueTextBox.getText()));
                    boolean resultVal = allowancesController.updateData(allowanceModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Allowance Scheme");
        }


    }
    @FXML
    private void deleteData(){

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
    }
    public void searchTable(){

        AllowanceServices allowancesController = new AllowanceServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Allowance> sortedData = allowancesController.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(AllowanceTable.comparatorProperty());
        //adding sorted and filtered data to the table
        AllowanceTable.setItems(sortedData);
    }

}
