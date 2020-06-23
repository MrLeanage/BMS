package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import net.sf.jasperreports.engine.JRException;
import services.*;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceHandler;
import util.userAlerts.AlertPopUp;
import util.utility.PrintReport;
import util.utility.UtilityMethod;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class PaySheetAdminController implements Initializable {

    @FXML
    private TextField SearchTextBox;

    @FXML
    private Label DateLabel;

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, String> EIDColumn;

    @FXML
    private TableColumn<Employee, String> ENameColumn;

    @FXML
    private TableColumn<Employee, String> ENICColumn;

    @FXML
    private TableColumn<Employee, String> EGenderColumn;

    @FXML
    private TableColumn<Employee, String> ETitleColumn;

    @FXML
    private Label ENameLabel;

    @FXML
    private Label ENICLabel;

    @FXML
    private Label EYearLabel;

    @FXML
    private Label EMonthLabel;

    @FXML
    private Label ETypeLabel;

    @FXML
    private Label EAmountLabel;

    @FXML
    private Label EAllowanceLabel;

    @FXML
    private Label ETotalSalaryLabel;

    @FXML
    private Label EBankBranchLabel;

    @FXML
    private Label EAccountLabel;

    @FXML
    private Label EDateLabel;

    @FXML
    private Label EIDLabel;

    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceHandler financeHandler = new FinanceHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        searchTable();
    }

    @FXML
    private void SalesCounter(ActionEvent event){
        adminManagementHandler.loadSalesCounter(event);
    }
    @FXML
    private void SalesReport(ActionEvent event){
        financeHandler.loadSalesReport(rootpane);
    }
    @FXML
    private void PurchasesReport(ActionEvent event){
        financeHandler.loadPurchasesReport(rootpane);
    }
    @FXML
    private void PaySheet(ActionEvent event){
        financeHandler.loadPaySheet(rootpane);
    }
    @FXML
    private void PayRoll(ActionEvent event) {
        financeHandler.loadPayRoll(rootpane);
    }
    @FXML
    private void OtherExpenses(ActionEvent event){
        financeHandler.loadOtherExpenses(rootpane);
    }
    @FXML
    private void IncomeStatement(ActionEvent event) {
        financeHandler.loadIncomeStatement(rootpane);
    }
    //Clear Fields
    private void clearFields(){
        EIDLabel.setText("");
        ENameLabel.setText("");
        ENICLabel.setText("");
        ETypeLabel.setText("");
        EAmountLabel.setText("");
        EAllowanceLabel.setText("");
        ETotalSalaryLabel.setText("");
        EYearLabel.setText("");
        EMonthLabel.setText("");
        EDateLabel.setText("");
        EBankBranchLabel.setText("");
        EAccountLabel.setText("");
    }
    //load data to View table

    private void loadData() {
        EmployeeServices employeeServices = new EmployeeServices();

        ObservableList<Employee> employeesData;
        employeesData = employeeServices.loadData();

        //Setting cell value factory to table view
        EIDColumn.setCellValueFactory(new PropertyValueFactory<>("eID"));
        ENameColumn.setCellValueFactory(new PropertyValueFactory<>("eName"));
        ENICColumn.setCellValueFactory(new PropertyValueFactory<>("eNIC"));
        EGenderColumn.setCellValueFactory(new PropertyValueFactory<>("eGender"));
        ETitleColumn.setCellValueFactory(new PropertyValueFactory<>("eTitle"));
        EmployeeTable.setItems(null);
        EmployeeTable.setItems(employeesData);
    }
    @FXML
    private void loadSelectedData() throws SQLException {
        LinkedList<Allowance> allowanceLinkedList = new LinkedList<>();
        double totalAllowancesValue = 0;
        double totalNetSalary = 0;

        Employee employee = new Employee();
        try{
            employee = EmployeeTable.getSelectionModel().getSelectedItem();

            SalarySchemeServices salarySchemeServices = new SalarySchemeServices();
            SalaryScheme salaryScheme = new SalaryScheme();
            salaryScheme = salarySchemeServices.loadSpecificData(employee.geteBSSID());

            AllowancePayServices allowancePayServices = new AllowancePayServices();
            ObservableList<Allowance> allowancesList = null;
            allowancesList = allowancePayServices.loadSpecificEmployeeAllowanceData(UtilityMethod.seperateID(employee.geteID()));

            allowanceLinkedList.addAll(allowancesList);

            for(Allowance allowance : allowanceLinkedList){
                if(allowance.getaType().equals("Fixed Value")){
                    totalAllowancesValue += allowance.getaValue();
                }else{
                    totalAllowancesValue += salaryScheme.getbSSAmount() * allowance.getaValue() / 100;
                }
            }
            totalNetSalary = totalAllowancesValue + salaryScheme.getbSSAmount();
            EIDLabel.setText(employee.geteID());
            ENameLabel.setText(employee.geteName());
            ENICLabel.setText(employee.geteNIC());
            ETypeLabel.setText(employee.geteBSSID());
            EAmountLabel.setText(salaryScheme.getbSSAmount()+"0");
            EAllowanceLabel.setText(totalAllowancesValue + "0");
            ETotalSalaryLabel.setText(totalNetSalary+"0");
            EYearLabel.setText(String.valueOf(YearMonth.now().getYear()));
            EMonthLabel.setText(String.valueOf(YearMonth.now().getMonth()));
            EDateLabel.setText(String.valueOf(LocalDate.now()));
            EBankBranchLabel.setText(employee.geteBankName());
            EAccountLabel.setText(String.valueOf(employee.geteAccNo()));
        }catch (NullPointerException ex){
           // AlertPopUp.generalError(ex);
        }
    }
    @FXML
    private void addData(ActionEvent event) throws JRException {
        PaySheet paySheet = new PaySheet();
        if(!(EIDLabel.getText().isEmpty()) ){
            paySheet.setpSEID(EIDLabel.getText());
            paySheet.setpSEName(ENameLabel.getText());
            paySheet.setpSNIC(ENICLabel.getText());
            paySheet.setpSBSSTitle(ETypeLabel.getText());
            paySheet.setpSBSSAmount(Double.parseDouble(EAmountLabel.getText()));
            paySheet.setpSATotalAmount(Double.parseDouble(EAllowanceLabel.getText()));
            paySheet.setpSBankName(EBankBranchLabel.getText());
            paySheet.setpSAccountNo(Long.parseLong(EAccountLabel.getText()));
            paySheet.setpSDate(EDateLabel.getText());
            ObservableList<PaySheet> paySheetObservableList = FXCollections.observableArrayList();
            paySheetObservableList.add(paySheet);
            PaySheetServices paySheetServices = new PaySheetServices();
            Integer resultVal = paySheetServices.insertData(paySheetObservableList);

            if(resultVal != 0){
                paySheet.setpSID(String.valueOf(resultVal));
                PrintReport printReport =  new PrintReport();
                printReport.printPaySheet(paySheet);
                clearFields();
            }
        }else{
            AlertPopUp.emptyInsertionFailed("No Paysheet Record Found");
        }
    }
    @FXML
    private void addAllTableData(ActionEvent event) throws SQLException {
        ObservableList<Employee> employeeObservableList = EmployeeTable.getItems();
        ObservableList<Allowance> allowanceObservableList = FXCollections.observableArrayList();
        ObservableList<PaySheet> paySheetObservableList = FXCollections.observableArrayList();
        PaySheet paySheet = new PaySheet();

        double totalNetSalary = 0;

        for(Employee employee : employeeObservableList){
            double totalAllowancesValue = 0;
            SalarySchemeServices salarySchemeServices = new SalarySchemeServices();
            SalaryScheme salaryScheme = new SalaryScheme();
            salaryScheme = salarySchemeServices.loadSpecificData(employee.geteBSSID());

            AllowancePayServices allowancePayServices = new AllowancePayServices();
            ObservableList<Allowance> allowancesList = null;

            allowancesList = allowancePayServices.loadSpecificEmployeeAllowanceData(UtilityMethod.seperateID(employee.geteID()));

            allowanceObservableList.addAll(allowancesList);


            for(Allowance allowance : allowancesList){
                if(allowance.getaType().equals("Fixed Value")){
                    totalAllowancesValue += allowance.getaValue();
                }else {
                    totalAllowancesValue += (salaryScheme.getbSSAmount() * allowance.getaValue() / 100);
                }
            }

            paySheet.setpSEID(employee.geteID());
            paySheet.setpSEName(employee.geteName());
            paySheet.setpSNIC(employee.geteNIC());
            paySheet.setpSBSSTitle(employee.geteBSSID());
            paySheet.setpSBSSAmount(salaryScheme.getbSSAmount());
            paySheet.setpSATotalAmount(totalAllowancesValue);
            paySheet.setpSDate(String.valueOf(LocalDate.now()));
            paySheet.setpSBankName(employee.geteBankName());
            paySheet.setpSAccountNo(employee.geteAccNo());


            paySheetObservableList.add(paySheet);
        }
        PaySheetServices paySheetServices = new PaySheetServices();
        Integer resultVal = paySheetServices.insertData(paySheetObservableList);
        if(resultVal != 0){
            PrintReport printReport =  new PrintReport();
            printReport.printPaySheetList();
            clearFields();
        }
    }
    private void searchTable(){
        EmployeeServices employeeServices = new EmployeeServices();
        //Retrieving sorted data
        SortedList<Employee> sortedData = employeeServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(EmployeeTable.comparatorProperty());
        //adding sorted and filtered data to the table
        EmployeeTable.setItems(sortedData);

    }
}
