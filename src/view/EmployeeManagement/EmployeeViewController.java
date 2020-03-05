package view.EmployeeManagement;

import controller.AllowanceController;
import controller.EmployeeController;
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
import model.Allowance;
import model.Employee;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {
    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, String> EIDColumn;

    @FXML
    private TableColumn<Employee, String> ENameColumn;

    @FXML
    private TableColumn<Employee, String> ENICColumn;

    @FXML
    private TableColumn<Employee, String> EAddressColumn;

    @FXML
    private TableColumn<Employee, String> EGenderColumn;

    @FXML
    private TableColumn<Employee, String> EDOBColumn;

    @FXML
    private TableColumn<Employee, String> ETitleColumn;

    @FXML
    private TableColumn<Employee, String> EBankColumn;

    @FXML
    private TableColumn<Employee, Integer> EAccNoColumn;

    @FXML
    private TableColumn<Employee, Integer> EBSSIDColumn;

    @FXML
    private TableColumn<Employee, String> EMoreColumn;

    @FXML
    private TableColumn<Employee, String> EPhoneColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField ENameTextBox;

    @FXML
    private TextField ENICTextBox;

    @FXML
    private TextField EAddressTextBox;

    @FXML
    private ChoiceBox<String> EGenderChoiceBox;

    @FXML
    private DatePicker EDOBDatePicker;

    @FXML
    private ChoiceBox<String> ETitleChoiceBox;

    @FXML
    private TextField EBankNameTextBox;

    @FXML
    private TextField EAccNoTextBox;

    @FXML
    private TextField EBSSIDTextBox;

    @FXML
    private TextField EPhoneTextBox;

    @FXML
    private Label ENameLabel;

    @FXML
    private Label EGenderLabel;

    @FXML
    private Label EBankNameLabel;

    @FXML
    private Label ENICLabel;

    @FXML
    private Label EDOBLabel;

    @FXML
    private Label EAccNoLabel;

    @FXML
    private Label ETitleLabel;

    @FXML
    private Label EAddressLabel;

    @FXML
    private Label EBSSIDLabel;

    @FXML
    private Label EPhonelabel;

    private static Employee existingEmployeeModel;

    private ObservableList<String> genderChoiceboxList = FXCollections.observableArrayList( "Male", "Female");
    private ObservableList<String> jobTitleChoiceboxList = FXCollections.observableArrayList( "Permanent", "Trainee", "Temporary");


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EGenderChoiceBox.setItems(genderChoiceboxList);
        ETitleChoiceBox.setItems(jobTitleChoiceboxList);


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
            ENameTextBox.setText("");
            ENICTextBox.setText("");
            EAddressTextBox.setText("");
            EGenderChoiceBox.setValue("");
            EDOBDatePicker.setValue(null);
            ETitleChoiceBox.setValue("");
            EBankNameTextBox.setText("");
            EAccNoTextBox.setText("");
            EBSSIDTextBox.setText("");
            EPhoneTextBox.setText("");
            clearLabels();

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            ENameLabel.setText("");
            ENICLabel.setText("");
            EAddressLabel.setText("");
            EGenderLabel.setText("");
            EDOBLabel.setText("");
            ETitleLabel.setText("");
            EBankNameLabel.setText("");
            EAccNoLabel.setText("");
            EBSSIDLabel.setText("");
            EPhonelabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(ENameTextBox)
                && DataValidation.TextFieldNotEmpty(ENICTextBox)
                && DataValidation.TextFieldNotEmpty(EAddressTextBox)
                && DataValidation.TextFieldNotEmpty(EGenderChoiceBox.getValue())
                && DataValidation.DatePickerNotEmpty(EDOBDatePicker)
                && DataValidation.TextFieldNotEmpty(ETitleChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(EBankNameTextBox)
                && DataValidation.TextFieldNotEmpty(EAccNoTextBox)
                && DataValidation.TextFieldNotEmpty(EBSSIDTextBox)
                && DataValidation.TextFieldNotEmpty(EPhoneTextBox)

                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(ENameTextBox.getText(),100)
                && DataValidation.isValidMaximumLength(EAddressTextBox.getText(),100)
                && DataValidation.isValidMaximumLength(EBankNameTextBox.getText(),40)
                && DataValidation.isValidMaximumLength(EAccNoTextBox.getText(),20)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(EAccNoTextBox.getText())
                && DataValidation.isValidNIC(ENICTextBox)
                && DataValidation.isValidPhoneNo(EPhoneTextBox.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(ENameTextBox)
                && DataValidation.TextFieldNotEmpty(ENICTextBox)
                && DataValidation.TextFieldNotEmpty(EAddressTextBox)
                && DataValidation.TextFieldNotEmpty(EGenderChoiceBox.getValue())
                && DataValidation.DatePickerNotEmpty(EDOBDatePicker)
                && DataValidation.TextFieldNotEmpty(ETitleChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(EBankNameTextBox)
                && DataValidation.TextFieldNotEmpty(EAccNoTextBox)
                && DataValidation.TextFieldNotEmpty(EPhoneTextBox)
                && DataValidation.TextFieldNotEmpty(EBSSIDTextBox))){


            DataValidation.TextFieldNotEmpty(ENameTextBox, ENameLabel,"Employee Name is Required");
            DataValidation.TextFieldNotEmpty(ENICTextBox, ENICLabel,"Employee NIC is Required");
            DataValidation.TextFieldNotEmpty(EAddressTextBox, EAddressLabel,"Employee Address is Required");
            DataValidation.TextFieldNotEmpty(EGenderChoiceBox.getValue(), EGenderLabel, "Select Gender");
            DataValidation.DatePickerNotEmpty(EDOBDatePicker, EDOBLabel, "Select Date of Birth");
            DataValidation.TextFieldNotEmpty(ETitleChoiceBox.getValue(), ETitleLabel, "Set a Title for this Employee");
            DataValidation.TextFieldNotEmpty(EBankNameTextBox, EBankNameLabel, "Employee's Bank Acc Name is Required");
            DataValidation.TextFieldNotEmpty(EAccNoTextBox, EAccNoLabel, "Bank Acc No is Required");
            DataValidation.TextFieldNotEmpty(EBSSIDTextBox, EBSSIDLabel, "Basic Salary Scheme for Employee is Required");
            DataValidation.TextFieldNotEmpty(EPhoneTextBox, EPhonelabel, "Employee Contact Number Required");

            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(ENameTextBox.getText(),100)
                && DataValidation.isValidMaximumLength(EAddressTextBox.getText(),100)
                && DataValidation.isValidMaximumLength(EBankNameTextBox.getText(),40)
                && DataValidation.isValidMaximumLength(EAccNoTextBox.getText(),20))){


            DataValidation.isValidMaximumLength(ENameTextBox.getText(),100, ENameLabel,"Employee Name is too Long!..(Limit 100)");
            DataValidation.isValidMaximumLength(EAddressTextBox.getText(),100, EAddressLabel,"Error!..Exceeding Character limit 100");
            DataValidation.isValidMaximumLength(EBankNameTextBox.getText(),40, EBankNameLabel, "Bank Name is too Long!..(Limit 100)");
            DataValidation.isValidMaximumLength(EAccNoTextBox.getText(),20, EAccNoLabel,"Acc No is too Long!..(Limit 20)");


        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(EAccNoTextBox.getText())
                && DataValidation.isValidNIC(ENICTextBox)
                && DataValidation.isValidPhoneNo(EPhoneTextBox.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(EAccNoTextBox.getText(), EAccNoLabel,"Bank Acc No can contain only Numbers!..");
            DataValidation.isValidNIC(ENICTextBox,ENICLabel,"Please Enter a Valid NIC No");
            DataValidation.isValidPhoneNo(EPhoneTextBox.getText(), EPhonelabel, "Please Enter Valid Phone Number");
        }
    }

    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main Controller
        EmployeeController employeeController = new EmployeeController();

        ObservableList<Employee> employeesData;
        employeesData = employeeController.loadData();

        //Setting cell value factory to table view
        EIDColumn.setCellValueFactory(new PropertyValueFactory<>("eID"));
        ENameColumn.setCellValueFactory(new PropertyValueFactory<>("eName"));
        ENICColumn.setCellValueFactory(new PropertyValueFactory<>("eNIC"));
        EAddressColumn.setCellValueFactory(new PropertyValueFactory<>("eAddress"));
        EGenderColumn.setCellValueFactory(new PropertyValueFactory<>("eGender"));
        EDOBColumn.setCellValueFactory(new PropertyValueFactory<>("eDOB"));
        ETitleColumn.setCellValueFactory(new PropertyValueFactory<>("eTitle"));
        EPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("ePhone"));
        EBankColumn.setCellValueFactory(new PropertyValueFactory<>("eBankName"));
        EAccNoColumn.setCellValueFactory(new PropertyValueFactory<>("eAccNo"));
        EBSSIDColumn.setCellValueFactory(new PropertyValueFactory<>("eBSSID"));
        EMoreColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        Callback<TableColumn<Employee, String>, TableCell<Employee, String>> parentCellFactory
                =
                new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Employee, String> param) {
                        final TableCell<Employee, String> cell = new TableCell<Employee, String>() {

                            final Button btn = new Button("Add Allowance");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnMouseClicked(event -> {
                                        // student = StudentTable.getSelectionModel().getSelectedItem();
                                        //String sID = student.getsID();
                                    });
                                    btn.setOnAction(event -> {

                                        FXMLLoader loader = new FXMLLoader();
                                        //loader.setLocation(getClass().getResource("editparentpopup.fxml"));
                                        try{
                                            loader.load();
                                        }catch (IOException ex){
                                            // Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        //EditParentPopUpController ePPC = loader.getController();
                                        // String sID = getID();
                                        //ePPC.setStudentID(sID);

                                        Parent p = loader.getRoot();
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(p));
                                        stage.setResizable(false);

                                        stage.showAndWait();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        EMoreColumn.setCellFactory(parentCellFactory);

        EmployeeTable.setItems(null);
        EmployeeTable.setItems(employeesData);

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
        Employee employeeModel = new Employee();
        EmployeeController employeeController = new EmployeeController();

        if(dataValidate()){

            employeeModel.seteName(ENameTextBox.getText());
            employeeModel.seteNIC(ENICTextBox.getText());
            employeeModel.seteAddress(EAddressTextBox.getText());
            employeeModel.seteGender(EGenderChoiceBox.getValue());
            employeeModel.seteDOB(String.valueOf(EDOBDatePicker.getValue()));
            employeeModel.setePhone(Integer.parseInt(EPhoneTextBox.getText()));
            employeeModel.seteTitle(ETitleChoiceBox.getValue());
            employeeModel.seteBankName(EBankNameTextBox.getText());
            employeeModel.seteAccNo(Integer.parseInt(EAccNoTextBox.getText()));
            employeeModel.seteBSSID(EBSSIDTextBox.getText());
            boolean resultVal = employeeController.insertData(employeeModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        Employee employeeModel;

        try{
            employeeModel = EmployeeTable.getSelectionModel().getSelectedItem();
            ENameTextBox.setText(employeeModel.geteName());
            ENICTextBox.setText(employeeModel.geteNIC());
            EAddressTextBox.setText(employeeModel.geteAddress());
            EGenderChoiceBox.setValue(String.valueOf(employeeModel.geteGender()));
            EDOBDatePicker.setValue(LocalDate.parse(employeeModel.geteDOB()));
            ETitleChoiceBox.setValue(employeeModel.geteTitle());
            EBankNameTextBox.setText(employeeModel.geteBankName());
            EAccNoTextBox.setText(String.valueOf(employeeModel.geteAccNo()));
            EBSSIDTextBox.setText(employeeModel.geteBSSID());
            EPhoneTextBox.setText(String.valueOf(employeeModel.getePhone()));
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        Employee employeeModel;
        try{
            employeeModel = EmployeeTable.getSelectionModel().getSelectedItem();
            existingEmployeeModel = employeeModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        Employee employeeModel = new Employee();
        EmployeeController employeeController = new EmployeeController();

        try{
            if(!(existingEmployeeModel.geteID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID
                    employeeModel.seteID(existingEmployeeModel.geteID());
                    //Overriding existing values retreiving from table
                    employeeModel.seteName(ENameTextBox.getText());
                    employeeModel.seteNIC(ENICTextBox.getText());
                    employeeModel.seteAddress(EAddressTextBox.getText());
                    employeeModel.seteGender(EGenderChoiceBox.getValue());
                    employeeModel.seteDOB(String.valueOf(EDOBDatePicker.getValue()));
                    employeeModel.seteTitle(ETitleChoiceBox.getValue());
                    employeeModel.seteBankName(EBankNameTextBox.getText());
                    employeeModel.seteAccNo(Integer.parseInt(EAccNoTextBox.getText()));
                    employeeModel.seteBSSID(EBSSIDTextBox.getText());
                    employeeModel.setePhone(Integer.parseInt(EPhoneTextBox.getText()));
                    boolean resultVal = employeeController.updateData(employeeModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Employee Info");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        Employee employeeModel;
        EmployeeController employeeController = new EmployeeController();
        employeeModel = EmployeeTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(employeeModel.geteID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Employee Info");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = employeeController.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Employee Info");
        }
    }
    public void searchTable(){

        EmployeeController employeeController = new EmployeeController();
        //Retrieving sorted data from Main Controller
        SortedList<Employee> sortedData = employeeController.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(EmployeeTable.comparatorProperty());
        //adding sorted and filtered data to the table
        EmployeeTable.setItems(sortedData);
    }

}
