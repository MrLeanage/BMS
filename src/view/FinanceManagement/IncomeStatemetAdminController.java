package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ChartData;
import model.SalesItem;
import services.BillingServices;
import util.utility.UtilityMethod;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class IncomeStatemetAdminController implements Initializable {

    @FXML
    private TableView<SalesItem> SalesTable;

    @FXML
    private TableColumn<SalesItem, String> SIDColumn;

    @FXML
    private TableColumn<SalesItem, String> SDateColumn;

    @FXML
    private TableColumn<SalesItem, String> SPNameColumn;

    @FXML
    private TableColumn<SalesItem, Integer> SPQuantityColumn;

    @FXML
    private TableColumn<SalesItem, Double> STotalColumn;

    @FXML
    private ComboBox<Integer> YearChoiceBox;

    @FXML
    private ComboBox<String> CategoryChoiceBox;

    @FXML
    private ComboBox<String> MonthChoiceBox;

    @FXML
    private Label SoldItemsLabel;

    @FXML
    private Label TotalSalesLabel;


    @FXML
    private  Label SalesPeriodLabel;

    @FXML
    private BarChart<?, ?> SalesReportChart;

    @FXML
    private CategoryAxis XAXISChart;

    @FXML
    private NumberAxis YAXISChart;

    @FXML
    private ChoiceBox<String> StatPeriodChoiceBox;

    @FXML
    private ChoiceBox<String> StatCategoryChoiceBox;


    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();
    private ObservableList<SalesItem> salesItemsData;

    private String category = "None";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        loadChoiceBoxes();
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
    private void SalesCounter(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesCounterAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusAdmin.fxml"));

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
    private void SalesReport(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesReportAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PurchasesReport(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PurchasesReportAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PaySheet(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PaySheetAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PayRoll(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PayRollAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OtherExpenses(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/OtherExpensesAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void IncomeStatement(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/IncomeStatementAdmin.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //load sales dates to choiceboxes and Chart
    private void loadChoiceBoxes(){

        ObservableList<Integer> unSortedYears = FXCollections.observableArrayList();
        ObservableList<String> unSortedMonths = FXCollections.observableArrayList();

        BillingServices billingServices = new BillingServices();
        //getting all Sales Items with billing Date
        salesItemsData = billingServices.loadSortedDateData(0, "None", "None", "Claimed");

        for(SalesItem salesItem : salesItemsData){
            //Adding dates to observable list
            unSortedYears.add(UtilityMethod.getYear(salesItem.getsIBDate()));
            unSortedMonths.add(UtilityMethod.getMonth(salesItem.getsIBDate()));
        }


        //setting data for stat table choice boxes
        ObservableList<String> choiceBoxTimePeriod = FXCollections.observableArrayList();
        for(int i = 3; i <= 6/*getChartData("All Products").size() */; i++){
            //adding Month Sorting lists up to 18 months from 3 months to 3 months
            if((i % 3 == 0) && (i <= 18)){
                choiceBoxTimePeriod.add("Last "+i + " Months");
            }
        }
        //default value
        StatPeriodChoiceBox.setValue("Last 3 Months");
        StatPeriodChoiceBox.setItems(choiceBoxTimePeriod);

        ObservableList<String> chartCategoryList = FXCollections.observableArrayList("All Expenses","All Income", "Profit", "All Comparision");
        StatCategoryChoiceBox.setValue("All Comparision");
        StatCategoryChoiceBox.setItems(chartCategoryList);

        //loading All Comparision data to chart for last 3 months
        //getFilteredChartData(3, "All Comparision");
    }
    //load data to View table
    @FXML
    private void loadData() {
        //getting data
        BillingServices billingServices = new BillingServices();


        ObservableList<SalesItem> salesItemsData;
        //salesItemsData = billingServices.loadSortedDateData( year, month, category, "Claimed");
        salesItemLinkedList.clear();
        //salesItemLinkedList.addAll(salesItemsData);
        calculateSales(salesItemLinkedList);
    }
    @FXML
    private  void loadFilteredData(ActionEvent actionEvent){


        if(CategoryChoiceBox.getValue().equals("All Products")){
            category = "None";
        }else if(CategoryChoiceBox.getValue().equals("Agency Products")){
            category = "Agency Product";
        }else if(CategoryChoiceBox.getValue().equals("Bakery Products")){
            category = "Bakery Product";
        }else{
            category = "Order";
        }

        loadData();

    }
    @FXML
    private void resetFilters(){
        loadData();
        SalesPeriodLabel.setText("");

    }
    private void calculateSales(LinkedList<SalesItem> salesItemsList){
        double totalSales = 0;
        int totalItems = 0;
        for(SalesItem salesItem : salesItemsList){
            totalItems ++;
            totalSales += salesItem.getsITotalAmount();
        }
        TotalSalesLabel.setText("Rs : "+totalSales+"0");
       // SoldItemsLabel.setText(totalItems +" Items");
    }



}
