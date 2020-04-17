package view.EmployeeManagement;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Allowance;
import model.Employee;
import model.SalaryScheme;
import services.AllowancePayServices;
import services.AllowanceServices;
import services.SalarySchemeServices;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeAllowancePayPopUPController implements Initializable {

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setEmployeeData();

        try {
            loadData();
            searchTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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
    private TableColumn<Allowance, Float> AValueColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField AIDTextBox;

    @FXML
    private TextField ATitleTextBox;

    @FXML
    private Label AIDLabel;

    @FXML
    private Button SalarySchemeCloseButton1;

    @FXML
    private Label EIDLabel;

    @FXML
    private Label ENameLabel;

    @FXML
    private Button AllowanceCloseButton;

    private static Allowance allowance;
    private static Employee employee;

    public EmployeeAllowancePayPopUPController(){
        setEmployee();
    }
    @FXML
    private void clearFields(){

        try{
            ATitleTextBox.setText("");
            AIDTextBox.setText("");

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            AIDLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(AIDTextBox)){
            returnVal = true;
        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(AIDTextBox))){
            DataValidation.TextFieldNotEmpty(AIDTextBox, AIDLabel,"Select Allowance Scheme to Add");
        }
    }
    @FXML
    private void selectAllowance(ActionEvent event){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmployeeAllowancePopUP.fxml"));

        try{
            loader.load();

        }catch (IOException ex){
            Logger.getLogger(EmployeeAllowancePopUPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EmployeeAllowancePopUPController employeeAllowancePopUPController = loader.getController();


        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.showAndWait();

        try{
            AIDTextBox.setText(allowance.getaID());
            ATitleTextBox.setText(allowance.getaTitle());

        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }
    //getting selected supplier from popup
    @FXML
    public boolean setAllowance(Allowance allowance){

        boolean resultval = false;

        try{
            // setting supplierInfo data to a static variable for later use and returning true to close stage
            this.allowance = allowance;
            resultval = true;
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
        return resultval;
    }
    public void setEmployee(){
        employee = EmployeeController.getEmployee();
    }
    private void setEmployeeData(){
        EIDLabel.setText(employee.geteID());
        ENameLabel.setText(employee.geteName());
    }
    public static Employee getEmployee(){
        return employee;
    }
    private void loadData() throws SQLException {
        //getting data from main LoginController

        AllowancePayServices popUpAllowancePayServices = new AllowancePayServices();

        ObservableList<Allowance> allowanceData;
        allowanceData = popUpAllowancePayServices.loadSpecificExistingData(UtilityMethod.seperateID(EIDLabel.getText()));


        //Setting cell value factory to table view
        AIDColumn.setCellValueFactory(new PropertyValueFactory<>("aID"));
        ATitleColumn.setCellValueFactory(new PropertyValueFactory<>("aTitle"));
        ADescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aDescription"));
        ATypeColumn.setCellValueFactory(new PropertyValueFactory<>("aType"));
        AValueColumn.setCellValueFactory(new PropertyValueFactory<>("aValue"));

        AllowanceTable.setItems(null);
        AllowanceTable.setItems(allowanceData);
    }
    @FXML
    public void refreshTable()throws Exception{
        clearFields();
        loadData();
        clearLabels();
    }
    @FXML
    private void addData(ActionEvent event) throws Exception{

        clearLabels();
        AllowancePayServices allowancesPayController = new AllowancePayServices();

        if(dataValidate()){
            Boolean resultVal = allowancesPayController.insertData(UtilityMethod.seperateID(EIDLabel.getText()), UtilityMethod.seperateID(AIDTextBox.getText()));
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
        Allowance allowanceModel;
        AllowancePayServices allowancesPayController = new AllowancePayServices();
        allowanceModel = AllowanceTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(allowanceModel.getaID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Allowance Scheme");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = allowancesPayController.deleteData(UtilityMethod.seperateID(EIDLabel.getText()),ID);
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
    public void searchTable() throws SQLException {

        AllowancePayServices popUpAllowancePayServices = new AllowancePayServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Allowance> sortedData = popUpAllowancePayServices.searchExistingDataTable(SearchTextBox, UtilityMethod.seperateID(EIDLabel.getText()));

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(AllowanceTable.comparatorProperty());
        //adding sorted and filtered data to the table
        AllowanceTable.setItems(sortedData);


    }
    void closeStage(){
        Stage stage = (Stage) AllowanceCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
