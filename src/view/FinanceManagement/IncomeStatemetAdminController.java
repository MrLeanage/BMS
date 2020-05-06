package view.FinanceManagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.ChartData;
import model.OtherExpense;
import model.Purchase;
import model.SalesItem;
import services.BillingServices;
import services.IncomeStatementServices;
import services.OtherExpenseServices;
import services.PurchaseServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.FinanceHandler;
import util.utility.PrintReport;
import util.utility.UtilityMethod;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class IncomeStatemetAdminController implements Initializable {



    @FXML
    private Label TotalIncomeLabel;

    @FXML
    private Label TotalExpenceLabel;

    @FXML
    private Label TotalProfitLabel;

    @FXML
    private  Label IncomePeriodLabel;

    @FXML
    private BarChart<?, ?> IncomeReportChart;

    @FXML
    private CategoryAxis XAXISChart;

    @FXML
    private NumberAxis YAXISChart;

    @FXML
    private ComboBox<String> StatPeriodComboBox;

    @FXML
    private ComboBox<String> StatCategoryComboBox;

    @FXML
    private ComboBox<String> IncomeStatementMonthComboBox;


    private ObservableList<SalesItem> salesItemsData;
    private ObservableList<Purchase> purchasesData;
    private ObservableList<OtherExpense> otherExpensesData;
    private ObservableList<String> sortedMonths;
    private ObservableList<Integer> sortedYears;

    @FXML
    private AnchorPane rootpane;
    private AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
    private FinanceHandler financeHandler = new FinanceHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadChoiceBoxes();
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
    private void clearLabels(){
        TotalIncomeLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(0.0));
        TotalExpenceLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(0.0));
        TotalProfitLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(0.0));
    }
    //load sales dates to choiceboxes and Chart
    private void loadChoiceBoxes(){

       try{
           ObservableList<Integer> unSortedYears = FXCollections.observableArrayList();
           ObservableList<String> unSortedMonths = FXCollections.observableArrayList();

           BillingServices billingServices = new BillingServices();
           PurchaseServices purchaseServices = new PurchaseServices();
           OtherExpenseServices otherExpenseServices = new OtherExpenseServices();


           //getting all purchases
           purchasesData = purchaseServices.loadData("Paid");
           //getting all purchases
           otherExpensesData = otherExpenseServices.loadData();

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

           //setting data for stat table choice boxes
           ObservableList<String> choiceBoxTimePeriod = FXCollections.observableArrayList();
           choiceBoxTimePeriod.add("This Month");
           choiceBoxTimePeriod.add("Last 2 Months");
           for(int i = 3; i <= getChartAllSalesData("All Products").size(); i++){
               //adding Month Sorting lists up to 18 months from 3 months to 3 months
               if((i % 3 == 0) && (i <= 18)){
                   choiceBoxTimePeriod.add("Last "+i + " Months");
               }
           }
           //default value
           StatPeriodComboBox.setValue("Last 3 Months");
           StatPeriodComboBox.setItems(choiceBoxTimePeriod);

           ObservableList<String> chartCategoryList = FXCollections.observableArrayList("All Expenses","All Income", "Profit", "All Comparision");
           StatCategoryComboBox.setValue("All Comparision");
           StatCategoryComboBox.setItems(chartCategoryList);

           //loading All Comparision data to chart for last 3 months
           getFilteredChartData(3, "All Comparision");

           //setting data for income statement Month Selection Combo Box
           ObservableList<String> sortedIncomeStatementMonth = FXCollections.observableArrayList();
           ObservableList<String> incomeStatementMonth = FXCollections.observableArrayList();
           for(ChartData chartData : barChartData()){
               incomeStatementMonth.add(chartData.getDataYearMonth());
           }
           sortedIncomeStatementMonth = UtilityMethod.removeStringDuplicates(incomeStatementMonth);
           IncomeStatementMonthComboBox.setValue(sortedIncomeStatementMonth.get(sortedIncomeStatementMonth.size() - 1));
           IncomeStatementMonthComboBox.setItems(sortedIncomeStatementMonth);

           //Updating records in Income Statement
           IncomeStatementServices incomeStatementServices = new IncomeStatementServices();
           incomeStatementServices.UpdateIncomeStatementInfo(barChartData());
       }catch(NullPointerException ex){

       }
    }
    @FXML
    private void loadStatFilterData(ActionEvent actionEvent){
        int noOfMonths = UtilityMethod.seperateIntegerFromString(StatPeriodComboBox.getValue());
        String type = StatCategoryComboBox.getValue();
        getFilteredChartData(noOfMonths, type);
    }
    private void getFilteredChartData(int noOfMonths, String type){
        //clearing old chart data
        IncomeReportChart.getData().clear();
        XYChart.Series All = new XYChart.Series<>();
        XYChart.Series expense = new XYChart.Series<>();
        XYChart.Series income = new XYChart.Series<>();
        XYChart.Series profit = new XYChart.Series<>();

        int size = getChartAllSalesData("All Products").size();
        //declaring starting index of linked list to access relevant month data from the last record to show last month record last at chart
        int startIndex;
        if(size < 2){
            startIndex = 0;
        }else{
            startIndex = size - noOfMonths;
        }


        String typeAll = "All Products";
        double totalIncome = 0, totalExpences = 0, totalProfit = 0;
        String startYear, startMonth, endYear, endMonth;
        //executing loop from 0 to no of months
        for(int i = 0; i < noOfMonths ; i++){
            totalExpences += getChartAllExpendituresData().get(startIndex + i).getDataValue();
            totalIncome += getChartAllSalesData(typeAll).get(startIndex + i).getDataValue();
            expense.getData().add(new XYChart.Data(getChartAllExpendituresData().get(startIndex + i).getDataYear()+ " " + getChartAllExpendituresData().get(startIndex + i).getDataMonth(), getChartAllExpendituresData().get(startIndex + i).getDataValue()));
            income.getData().add(new XYChart.Data(getChartAllSalesData(typeAll).get(startIndex + i).getDataYear()+ " " + getChartAllSalesData(typeAll).get(startIndex + i).getDataMonth(), getChartAllSalesData(typeAll).get(startIndex + i).getDataValue()));
            profit.getData().add(new XYChart.Data(getChartAllSalesData(typeAll).get(startIndex + i).getDataYear()+ " " + getChartAllSalesData(typeAll).get(startIndex + i).getDataMonth(), getChartAllSalesData(typeAll).get(startIndex + i).getDataValue() - getChartAllExpendituresData().get(startIndex + i).getDataValue()));

        }

        startYear = String.valueOf(getChartAllSalesData(typeAll).get(startIndex).getDataYear());
        startMonth = getChartAllSalesData(typeAll).get(startIndex).getDataMonth();
        endYear = String.valueOf(getChartAllSalesData(typeAll).peekLast().getDataYear());
        endMonth = getChartAllSalesData(typeAll).peekLast().getDataMonth();

        IncomePeriodLabel.setText("");
        if(noOfMonths == 1){
            IncomePeriodLabel.setText(startMonth + " " +startYear);
        }else{
            IncomePeriodLabel.setText(startMonth + " " +startYear +" to "+endMonth + " " +endYear);
        }

        totalProfit = totalExpences - totalIncome;

        String lossText = "";
        //setting loss profit Text
        if(totalExpences > totalIncome){
            lossText = " (Loss)";
        }

        if(type.equals("All Expenses")){
            expense.setName("All Expenses");
            IncomeReportChart.getData().addAll(expense);
            clearLabels();
            TotalExpenceLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalExpences));
        }else if(type.equals("All Income")){
            income.setName("All Income");
            IncomeReportChart.getData().addAll(income);
            clearLabels();
            TotalIncomeLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalIncome));
        }else if(type.equals("Profit")){
            profit.setName("Profit");
            IncomeReportChart.getData().addAll(profit);
            clearLabels();
            TotalProfitLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalProfit)+lossText);
        }else if(type.equals("All Comparision")){
            expense.setName("All Expenses");
            IncomeReportChart.getData().addAll(expense);
            income.setName("All Income");
            IncomeReportChart.getData().addAll(income);
            profit.setName("Profit");
            IncomeReportChart.getData().addAll(profit);
            clearLabels();
            TotalIncomeLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalIncome));
            TotalExpenceLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalExpences));
            TotalProfitLabel.setText("Rs : "+UtilityMethod.numberDisplayWithCommasAndDecimalPlaces(totalProfit)+lossText);
        }

    }
    //getting chart data according to type
    private LinkedList<ChartData> getChartAllSalesData(String type){
        LinkedList<ChartData> chartDataLinkedList;
        ObservableList<ChartData> chartDataList = FXCollections.observableArrayList();

        double totalSales = 0;
        ChartData chartModelData;

        //Counting sales for selected category type
        //sort with selected year
        for(Integer dataYears : sortedYears){
            //sort with selected year, month
            for(String dataMonths : sortedMonths){
                //sort with selected year, month, Sales
                for(SalesItem salesItem :salesItemsData){
                    //counting sales all sales from all sales
                    if((UtilityMethod.getYear(salesItem.getsIBDate()).equals(dataYears)) && (UtilityMethod.getMonth(salesItem.getsIBDate()).equals(dataMonths)) && (type.equals("All Products"))){
                        totalSales += salesItem.getsITotalAmount();
                    }
                }
                //setting chart data to a observable list array ex:
                // "2020", "January", 11000, "All Products"
                // "2020", "February", 12000, "All Products"
                // "2020", "March", 13000, "All Products"
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
    //getting chart data according to type
    private LinkedList<ChartData> getChartAllExpendituresData(){
        ObservableList<ChartData> chartDataList = FXCollections.observableArrayList();
        LinkedList<ChartData> chartDataLinkedList;
        double totalExpenditure = 0;
        ChartData chartModelData;

        //Counting sales for selected category type
        //sort with selected year
        for(Integer dataYears : sortedYears){
            //sort with selected year, month
            for(String dataMonths : sortedMonths){
                //sort with selected year, month, Sales

                for(Purchase purchase :purchasesData){
                    //counting all purchases
                    if(!purchase.getpPaidDate().equals("None")){
                        if((UtilityMethod.getYear(purchase.getpPaidDate()).equals(dataYears)) && (UtilityMethod.getMonth(purchase.getpPaidDate()).equals(dataMonths))){
                            totalExpenditure += purchase.getpItemTotal();
                        }
                    }
                }


                for(OtherExpense otherExpense :otherExpensesData){
                    //counting all other expenses
                    if((UtilityMethod.getYear(otherExpense.geteXPPaidDate()).equals(dataYears)) && (UtilityMethod.getMonth(otherExpense.geteXPPaidDate()).equals(dataMonths))){
                        totalExpenditure += otherExpense.geteXPAmount();
                    }
                }
                //setting chart data to a ChartData object ex:
                // "2020", "January", 11000, "All Expenses"
                // "2020", "February", 12000, "All Expenses"
                // "2020", "March", 13000, "All Expenses"
                chartModelData = new ChartData(dataYears, dataMonths, totalExpenditure, "All Expenses");
                //adding chart data to observable list
                chartDataList.add(chartModelData);
                totalExpenditure = 0;
            }
        }
        //adding chart data from observable list to LinkedList
        chartDataLinkedList = new LinkedList<>(chartDataList);
        return chartDataLinkedList;
    }
    public  ObservableList<ChartData> barChartData(){
        ObservableList<ChartData> barChartDataList = FXCollections.observableArrayList();


        for(ChartData chartData :getChartAllSalesData("All Products")){
            chartData.setDataType("All Income");
            barChartDataList.add(chartData);
        }
        barChartDataList.addAll(getChartAllExpendituresData());
        for(ChartData expenseChartData  :getChartAllExpendituresData()){
            double profit = 0;
            ChartData chartData = new ChartData();
            chartData.setDataType("Profit");
            chartData.setDataYearMonth(expenseChartData.getDataYearMonth());
            for(ChartData  incomeChartData: getChartAllSalesData("All Products")){
                if(expenseChartData.getDataYearMonth().equals(incomeChartData.getDataYearMonth())){
                    profit = incomeChartData.getDataValue() - expenseChartData.getDataValue();
                }
            }
            chartData.setDataValue(profit);
            barChartDataList.add(chartData);
        }


        return barChartDataList;
    }
    @FXML
    private void generateIncomeStatement(){
        IncomeStatementServices incomeStatementServices = new IncomeStatementServices();
        incomeStatementServices.UpdateIncomeStatementInfo(barChartData());
        PrintReport printReport = new PrintReport();
        printReport.printIncomeStatement(IncomeStatementMonthComboBox.getValue());
    }

}
