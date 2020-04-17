package view.EmployeeManagement;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeAllowancePopUPController implements Initializable {

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


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
    private TableColumn<Allowance, String> AValueColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField AIDTextBox;

    @FXML
    private TextField AValueTextBox;

    @FXML
    private TextField ATitleTextBox;

    @FXML
    private TextField ATypeTextBox;

    @FXML
    private Button AllowanceCloseButton;

    private static Employee employee;

    public EmployeeAllowancePopUPController(){
        setEmployee();
    }
    public void setEmployee(){
        employee = EmployeeAllowancePayPopUPController.getEmployee();
    }
    private void loadData() throws SQLException {

        //getting data from main LoginController
        AllowancePayServices allowancesPayServices = new AllowancePayServices();

        ObservableList<Allowance> allowanceData;
        allowanceData = allowancesPayServices.loadSpecificNewData(UtilityMethod.seperateID(employee.geteID()));

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
    private void loadSelectedData( ){
        Allowance allowanceModel;
        try{

            allowanceModel = AllowanceTable.getSelectionModel().getSelectedItem();
            AIDTextBox.setText(allowanceModel.getaID());
            ATitleTextBox.setText(allowanceModel.getaTitle());
            ATypeTextBox.setText(allowanceModel.getaType());
            AValueTextBox.setText(String.valueOf(allowanceModel.getaValue()));


        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    public void searchTable() throws SQLException {
        AllowancePayServices allowancesPayController = new AllowancePayServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Allowance> sortedData = allowancesPayController.searchNewDataTable(SearchTextBox, UtilityMethod.seperateID(employee.geteID()));

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(AllowanceTable.comparatorProperty());
        //adding sorted and filtered data to the table
        AllowanceTable.setItems(sortedData);
    }
    @FXML
    public void returnSelectedAllowance() throws IOException {
        boolean resultVal = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeAllowancePayPopUP.fxml"));
            Parent root = (Parent) loader.load();
            EmployeeAllowancePayPopUPController employeeAllowancePayPopUPController = loader.getController();
            Allowance allowanceModel = new Allowance();
            allowanceModel.setaID(AIDTextBox.getText());
            allowanceModel.setaTitle(ATitleTextBox.getText());
            resultVal = employeeAllowancePayPopUPController.setAllowance(allowanceModel);

        if(resultVal){
            closeStage();
        }
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
