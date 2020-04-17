package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import model.*;
import services.AllowancePayServices;
import services.BillingServices;
import services.EmployeeServices;
import services.SalarySchemeServices;
import sun.java2d.pipe.OutlineTextRenderer;
import sun.text.normalizer.Utility;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesReportAdminController implements Initializable {

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
    private ObservableList<String> sortedMonths;
    private ObservableList<Integer> sortedYears;
    private ObservableList<SalesItem> salesItemsData;

    private Integer year = UtilityMethod.getYear(String.valueOf(LocalDate.now()));
    private String month = UtilityMethod.getMonth(String.valueOf(LocalDate.now()));
    //defining "None" as default value to load all data, -> respectively "Agency Product", "Bakery Product",  other values results Orders
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

        ObservableList<Integer> choiceBoxYears = FXCollections.observableArrayList();
        ObservableList<String> choiceBoxMonths = FXCollections.observableArrayList();

        ObservableList<String> categoryList = FXCollections.observableArrayList("All Products","Agency Products", "Bakery Products", "Orders");
        BillingServices billingServices = new BillingServices();
        //getting all Sales Items with billing Date
        salesItemsData = billingServices.loadSortedDateData(0, "None", "None", "Claimed");

        for(SalesItem salesItem : salesItemsData){
            //Adding dates to observable list
            unSortedYears.add(UtilityMethod.getYear(salesItem.getsIBDate()));
            unSortedMonths.add(UtilityMethod.getMonth(salesItem.getsIBDate()));
        }
        //setting sorted data for Table sorting choice boxes
        sortedYears = UtilityMethod.removeIntegerDuplicates(unSortedYears);
        sortedMonths = UtilityMethod.removeStringDuplicates(unSortedMonths);

        choiceBoxMonths.addAll(sortedMonths);
        choiceBoxYears.addAll(sortedYears);

        //default value
        MonthChoiceBox.setValue(UtilityMethod.getMonth(String.valueOf(LocalDate.now())));
        choiceBoxMonths.add("All Months");
        MonthChoiceBox.setItems(choiceBoxMonths);

        //default value
        YearChoiceBox.setValue(UtilityMethod.getYear(String.valueOf(LocalDate.now())));
        YearChoiceBox.setItems(choiceBoxYears);

        //default value
        CategoryChoiceBox.setValue("All Products");
        CategoryChoiceBox.setItems(categoryList);

        SalesPeriodLabel.setText(month + " "+ year + " - "+"All Products");

        //setting data for stat table choice boxes
        ObservableList<String> choiceBoxTimePeriod = FXCollections.observableArrayList();
        for(int i = 3; i <= getChartData("All Products").size(); i++){
            //adding Month Sorting lists up to 18 months from 3 months to 3 months
            if((i % 3 == 0) && (i <= 18)){
                choiceBoxTimePeriod.add("Last "+i + " Months");
            }
        }
        //default value
        StatPeriodChoiceBox.setValue("Last 3 Months");
        StatPeriodChoiceBox.setItems(choiceBoxTimePeriod);

        ObservableList<String> chartCategoryList = FXCollections.observableArrayList("All Products","Agency Products", "Bakery Products", "Orders", "All Comparision");
        StatCategoryChoiceBox.setValue("All Comparision");
        StatCategoryChoiceBox.setItems(chartCategoryList);

        //loading All Comparision data to chart for last 3 months
        getFilteredChartData(3, "All Comparision");
    }
    //load data to View table
    @FXML
    private void loadData() {
        //getting data
        BillingServices billingServices = new BillingServices();


        ObservableList<SalesItem> salesItemsData;
        salesItemsData = billingServices.loadSortedDateData( year, month, category, "Claimed");
        salesItemLinkedList.clear();
        salesItemLinkedList.addAll(salesItemsData);
        calculateSales(salesItemLinkedList);

        //Setting cell value factory to table view
        SIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIID"));
        SDateColumn.setCellValueFactory(new PropertyValueFactory<>("sIBDate"));
        SPNameColumn.setCellValueFactory(new PropertyValueFactory<>("sIPName"));
        SPQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sIPQuantity"));
        STotalColumn.setCellValueFactory(new PropertyValueFactory<>("sITotalAmount"));
        SalesTable.setItems(null);
        SalesTable.setItems(salesItemsData);
    }
    @FXML
    private  void loadFilteredData(ActionEvent actionEvent){
        year = YearChoiceBox.getValue();

        if(MonthChoiceBox.getValue().equals("All Months")){
            month = "None";
        }else{
            month = MonthChoiceBox.getValue();
        }

        if(CategoryChoiceBox.getValue().equals("All Products")){
            category = "None";
        }else if(CategoryChoiceBox.getValue().equals("Agency Products")){
            category = "Agency Product";
        }else if(CategoryChoiceBox.getValue().equals("Bakery Products")){
            category = "Bakery Product";
        }else{
            category = "Order";
        }
        SalesPeriodLabel.setText(MonthChoiceBox.getValue() + " "+ year + " - "+CategoryChoiceBox.getValue());

        loadData();

    }
    @FXML
    private void resetFilters(){
        year = -1;
        month = "-1";
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
        SoldItemsLabel.setText(totalItems +" Items");
    }

    @FXML
    private void loadStatFilterData(ActionEvent actionEvent){
        int noOfMonths = UtilityMethod.seperateIntegerFromString(StatPeriodChoiceBox.getValue());
        String type = StatCategoryChoiceBox.getValue();
        getFilteredChartData(noOfMonths, type);
    }
    private void getFilteredChartData(int noOfMonths, String type){
        SalesReportChart.getData().clear();
        XYChart.Series All = new XYChart.Series<>();
        XYChart.Series bakery = new XYChart.Series<>();
        XYChart.Series agency = new XYChart.Series<>();
        XYChart.Series order = new XYChart.Series<>();

        int size = getChartData(type).size();
        int startIndex = size - noOfMonths;
        String typeBakery = "Bakery Product";
        String typeAgency = "Agency Product";
        String typeOrder = "Order Product";
        String typeAll = "All Products";
        for(int i = 0; i < noOfMonths ; i++){

                All.getData().add(new XYChart.Data(getChartData(typeAll).get(startIndex + i).getDataYear()+ " " + getChartData(typeAll).get(startIndex + i).getDataMonth(), getChartData(typeAll).get(startIndex + i).getDataValue()));
                bakery.getData().add(new XYChart.Data(getChartData(typeBakery).get(startIndex + i).getDataYear()+ " " + getChartData(typeBakery).get(startIndex + i).getDataMonth(), getChartData(typeBakery).get(startIndex + i).getDataValue()));
                agency.getData().add(new XYChart.Data(getChartData(typeAgency).get(startIndex + i).getDataYear()+ " " + getChartData(typeAgency).get(startIndex + i).getDataMonth(), getChartData(typeAgency).get(startIndex + i).getDataValue()));
                order.getData().add(new XYChart.Data(getChartData(typeOrder).get(startIndex + i).getDataYear()+ " " + getChartData(typeOrder).get(startIndex + i).getDataMonth(), getChartData(typeOrder).get(startIndex + i).getDataValue()));

        }
        if(type.equals("Bakery Products")){
            bakery.setName("Bakery");
            SalesReportChart.getData().addAll(bakery);
        }else if(type.equals("Agency Products")){
            agency.setName("Agency");
            SalesReportChart.getData().addAll(agency);
        }else if(type.equals("Orders")){
            order.setName("Order");
            SalesReportChart.getData().addAll(order);
        }else if(type.equals("All Comparision")){
            bakery.setName("Bakery");
            SalesReportChart.getData().addAll(bakery);
            agency.setName("Agency");
            SalesReportChart.getData().addAll(agency);
            order.setName("Order");
            SalesReportChart.getData().addAll(order);
        }else if(type.equals("All Products")){
            All.setName("All Sales");
            SalesReportChart.getData().addAll(All);
        }

    }
    //getting chart data according to type
    private LinkedList<ChartData> getChartData(String type){
        ObservableList<ChartData> chartDataList = FXCollections.observableArrayList();
        LinkedList<ChartData> chartDataLinkedList;
        double totalSales = 0;
        ChartData chartModelData;

        //Counting sales for selected category type
        //sort with selected year
        for(Integer dataYears : sortedYears){
            //sort with selected year, month
            for(String dataMonths : sortedMonths){
                //sort with selected year, month, Sales
                for(SalesItem salesItem :salesItemsData){
                    //counting sales for specific category from all sales
                    if((UtilityMethod.getYear(salesItem.getsIBDate()).equals(dataYears)) && (UtilityMethod.getMonth(salesItem.getsIBDate()).equals(dataMonths)) && (salesItem.getsIType().equals(type))){
                        totalSales += salesItem.getsITotalAmount();
                    }
                    //counting sales all sales from all sales
                    if((UtilityMethod.getYear(salesItem.getsIBDate()).equals(dataYears)) && (UtilityMethod.getMonth(salesItem.getsIBDate()).equals(dataMonths)) && (type.equals("All Products"))){
                        totalSales += salesItem.getsITotalAmount();
                    }
                }
                //setting chart data to a observable list array ex:
                    // "2020", "January", 11000, "Agency Product"
                    // "2020", "February", 12000, "Agency Product"
                    // "2020", "March", 13000, "Agency Product"
                chartModelData = new ChartData(dataYears, dataMonths, totalSales, type);
                //adding chart data to observable list
                chartDataList.add(chartModelData);
                totalSales = 0;
            }
        }
        //adding chart data from observable list to LinkedList
        chartDataLinkedList = new LinkedList<>(chartDataList);
        return chartDataLinkedList;
    }
}
