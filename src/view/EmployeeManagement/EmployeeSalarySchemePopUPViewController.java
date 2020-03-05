package view.EmployeeManagement;

import services.ProductSupplierPopUPServices;
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
import model.Supplier;
import util.userAlerts.AlertPopUp;
import view.InventoryManagement.AgencyProductsViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeSalarySchemePopUPViewController implements Initializable {

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
    private TableView<Supplier> SupplierTable;

    @FXML
    private TableColumn<Supplier, String> SIIDColumn;

    @FXML
    private TableColumn<Supplier, String> SINameColumn;

    @FXML
    private TableColumn<Supplier, Integer> SIPhone1Column;

    @FXML
    private TableColumn<Supplier, Integer> SIPhone2Column;

    @FXML
    private TableColumn<Supplier, String> SIEmailColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField SIAPIDTextBox;

    @FXML
    private TextField SIAPPhone1TextBox;

    @FXML
    private TextField SIAPNameTextBox;

    @FXML
    private TextField SIAPPhone2TextBox;

    @FXML
    private TextField SIAPEmailTextBox;

    @FXML
    private Button SIAPCloseButton;


    private void loadData() {
        //getting data from main Controller

        ProductSupplierPopUPServices productSupplierPopUPServices = new ProductSupplierPopUPServices();

        ObservableList<Supplier> supplierData;
        productSupplierPopUPServices.setSupplierType("'Agency'");
        supplierData = productSupplierPopUPServices.loadData();

        //Setting cell value factory to table view
        SIIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIID"));
        SINameColumn.setCellValueFactory(new PropertyValueFactory<>("sIName"));
        SIPhone1Column.setCellValueFactory(new PropertyValueFactory<>("sIPhone1"));
        SIPhone2Column.setCellValueFactory(new PropertyValueFactory<>("sIPhone2"));
        SIEmailColumn.setCellValueFactory(new PropertyValueFactory<>("sIEmail"));

        SupplierTable.setItems(null);
        SupplierTable.setItems(supplierData);
    }

    @FXML
    private void loadSelectedData( ){
        Supplier supplierModel;
        try{

            supplierModel = SupplierTable.getSelectionModel().getSelectedItem();
            SIAPIDTextBox.setText(supplierModel.getsIID());
            SIAPNameTextBox.setText(supplierModel.getsIName());
            SIAPPhone1TextBox.setText("0"+ supplierModel.getsIPhone1());
            SIAPPhone2TextBox.setText("0" + supplierModel.getsIPhone2());
            SIAPEmailTextBox.setText(supplierModel.getsIEmail());


        }catch(Exception ex){
            AlertPopUp.generalError(ex);

        }
    }
    public void searchTable(){

        ProductSupplierPopUPServices productSupplierPopUPServices = new ProductSupplierPopUPServices();
        //Retrieving sorted data from Main Controller
        SortedList<Supplier> sortedData = productSupplierPopUPServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(SupplierTable.comparatorProperty());
        //adding sorted and filtered data to the table
        SupplierTable.setItems(sortedData);


    }
    @FXML
    public void returnSelectedSupplier() throws IOException {
        boolean resultVal = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgencyProduct.fxml"));
            Parent root = (Parent) loader.load();
            AgencyProductsViewController agencyProductsViewController = loader.getController();
            Supplier supplierModel = new Supplier();
            supplierModel.setsIID(SIAPIDTextBox.getText());
            supplierModel.setsIName(SIAPNameTextBox.getText());
            resultVal = agencyProductsViewController.setSupplier(supplierModel);

        if(resultVal){
            closeStage();
        }
    }
    void closeStage(){
        Stage stage = (Stage) SIAPCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
