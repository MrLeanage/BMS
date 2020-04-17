package view.EmployeeManagement;

import model.SalaryScheme;
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
import services.SalarySchemeServices;
import util.userAlerts.AlertPopUp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeSalarySchemePopUPController implements Initializable {

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        searchTable();
        loadData();

    }

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
    private TextField BSSIDTextBox;

    @FXML
    private TextField BSSAddedDateTextBox;

    @FXML
    private TextField BSSTitleTextBox;

    @FXML
    private TextField BSSAmountTextBox;

    @FXML
    private Button SalarySchemeCloseButton;

    private void loadData() {
        //getting data from main LoginController

        SalarySchemeServices popUPSalarySchemeServices = new SalarySchemeServices();

        ObservableList<SalaryScheme> salarySchemeData;
        //productSupplierPopUPServices.setSupplierType("'Agency'");
        salarySchemeData = popUPSalarySchemeServices.loadData();

        //Setting cell value factory to table view
        BSSIDColumn.setCellValueFactory(new PropertyValueFactory<>("bSSID"));
        BSSTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bSSTitle"));
        BSSAmountColumn.setCellValueFactory(new PropertyValueFactory<>("bSSAmount"));
        BSSDateColumn.setCellValueFactory(new PropertyValueFactory<>("bSSAddedDate"));

        BasicSalarySchemeTable.setItems(null);
        BasicSalarySchemeTable.setItems(salarySchemeData);
    }

    @FXML
    private void loadSelectedData( ){
        SalaryScheme salarySchemerModel;
        try{

            salarySchemerModel = BasicSalarySchemeTable.getSelectionModel().getSelectedItem();
            BSSIDTextBox.setText(salarySchemerModel.getbSSID());
            BSSTitleTextBox.setText(salarySchemerModel.getbSSTitle());
            BSSAmountTextBox.setText(String.valueOf(salarySchemerModel.getbSSAmount()));
            BSSAddedDateTextBox.setText(salarySchemerModel.getbSSAddedDate());


        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    public void searchTable(){

        SalarySchemeServices popUPSalarySchemeServices = new SalarySchemeServices();
        //Retrieving sorted data from Main LoginController
        SortedList<SalaryScheme> sortedData = popUPSalarySchemeServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(BasicSalarySchemeTable.comparatorProperty());
        //adding sorted and filtered data to the table
        BasicSalarySchemeTable.setItems(sortedData);


    }
    @FXML
    public void returnSelectedSalaryScheme() throws IOException {
        boolean resultVal = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml"));
            Parent root = (Parent) loader.load();
            EmployeeController employeeController = loader.getController();
            SalaryScheme salarySchemeModel = new SalaryScheme();
            salarySchemeModel.setbSSID(BSSIDTextBox.getText());
            resultVal = employeeController.setSalary(salarySchemeModel);

        if(resultVal){
            closeStage();
        }
    }
    void closeStage(){
        Stage stage = (Stage) SalarySchemeCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
