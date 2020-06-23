package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BakeryProduct;
import model.SalesItem;
import util.dbConnect.DBConnection;
import util.query.BillingQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class BillingServices {
    public ObservableList<SalesItem> loadData(String clearance)  {
        ObservableList<SalesItem> SalesItemData = null;
        ResultSet rsLoadBilling = null;
        ResultSet rsLoadItem = null;
        PreparedStatement psLoadBilling = null;
        PreparedStatement psLoadItem = null;

        try{
            Connection conn = DBConnection.Connect();
            SalesItemData = FXCollections.observableArrayList();
            psLoadBilling = conn.prepareStatement(BillingQueries.LOAD_BILLING_DATA_BY_STATUS_QUERY);
            psLoadItem = conn.prepareStatement(BillingQueries.LOAD_SALES_DATA_QUERY);


            psLoadBilling.setString(1, clearance);
            rsLoadBilling = psLoadBilling.executeQuery();
            while(rsLoadBilling.next()){
                psLoadItem.setInt(1, rsLoadBilling.getInt(1));
                rsLoadItem = psLoadItem.executeQuery();
                while (rsLoadItem.next()){
                    SalesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                }
            }
        }catch(SQLException ex){
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try {
                psLoadBilling.close();
                psLoadItem.close();
                // rsLoadBilling.close();
                //rsLoadItem.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }

        }
        return SalesItemData;
    }
    public ObservableList<SalesItem> loadData(String id, String clearance)  {
        ObservableList<SalesItem> SalesItemData = null;
        ResultSet rsLoadBilling = null;
        ResultSet rsLoadItem = null;
        PreparedStatement psLoadBilling = null;
        PreparedStatement psLoadItem = null;

        try{
            Connection conn = DBConnection.Connect();
            SalesItemData = FXCollections.observableArrayList();
            psLoadBilling = conn.prepareStatement(BillingQueries.LOAD_CASHIER_BILLING_DATA_QUERY);
            psLoadItem = conn.prepareStatement(BillingQueries.LOAD_SALES_DATA_QUERY);

            psLoadBilling.setString(1, id);
            psLoadBilling.setString(2, clearance);
            rsLoadBilling = psLoadBilling.executeQuery();
            while(rsLoadBilling.next()){
                psLoadItem.setInt(1, rsLoadBilling.getInt(1));
                rsLoadItem = psLoadItem.executeQuery();
                while (rsLoadItem.next()){
                    SalesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                }
            }
        }catch(SQLException ex){
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try {
                psLoadBilling.close();
                psLoadItem.close();
                // rsLoadBilling.close();
                //rsLoadItem.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }

        }
        return SalesItemData;
    }
    public ObservableList<SalesItem> loadSortedDateData(Integer year, String month, String category, String clearance){
        ObservableList<SalesItem> salesItemData = null;
        ResultSet rsLoadBilling = null;
        ResultSet rsLoadItem = null;
        PreparedStatement psLoadBilling = null;
        PreparedStatement psLoadItem = null;

        try{
            Connection conn = DBConnection.Connect();
            salesItemData = FXCollections.observableArrayList();

            psLoadBilling = conn.prepareStatement(BillingQueries.LOAD_BILLING_DATA_BY_STATUS_QUERY);
            psLoadItem = conn.prepareStatement(BillingQueries.LOAD_SALES_DATA_QUERY);

            psLoadBilling.setString(1, clearance);
            rsLoadBilling = psLoadBilling.executeQuery();

            String billingDate = null;
            String salesType = null;

            while(rsLoadBilling.next()){
                //getting billing Info and executing Query to get Sales Item for that bill
                psLoadItem.setInt(1, rsLoadBilling.getInt(1));
                rsLoadItem = psLoadItem.executeQuery();

                while (rsLoadItem.next()){
                    billingDate = rsLoadBilling.getString(3);
                    salesType = rsLoadItem.getString(9);

                    //getting all data without checking
                    if((year == 0) && (month.equals("None")) && (category.equals("None"))){
                        if((!rsLoadItem.getString(9).equals("Bakery Product")) && (!rsLoadItem.getString(9).equals("Agency Product"))){
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), "Order Product", rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }else{
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }
                     //getting data where matches year only
                    }else if((year != 0) && (month.equals("None")) && (category.equals("None"))){
                        //checking  year and category
                        if((UtilityMethod.getYear(billingDate).equals(year))){
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }
                     //getting data where match both month and year only
                    }else if((year != 0) && (!month.equals("None")) && (category.equals("None"))){
                        //checking  year and category
                        if((UtilityMethod.getYear(billingDate).equals(year)) && (UtilityMethod.getMonth(billingDate).equals(month))){
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }
                    //getting data where matches all fields
                    }else if((year != 0) && (!month.equals("None")) && (!category.equals("None"))){
                        //checking  matching records with year and month Agency and product Categories
                        if (((UtilityMethod.getYear(billingDate).equals(year))  && (UtilityMethod.getMonth(billingDate).equals(month)) && (salesType).equals(category))) {
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), rsLoadItem.getString(9), rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }
                        //checking  matching records with year and month Order Categories
                        if ((UtilityMethod.getYear(billingDate).equals(year))  && (UtilityMethod.getMonth(billingDate).equals(month)) && (!salesType.equals("Bakery Product")) && (!salesType.equals("Agency Product"))) {
                            salesItemData.add(new SalesItem(rsLoadItem.getString(1), rsLoadItem.getString(2), rsLoadItem.getString(3), rsLoadItem.getString(4), rsLoadItem.getInt(5), rsLoadItem.getFloat(6), rsLoadItem.getDouble(7), rsLoadItem.getString(8), "Order Product", rsLoadBilling.getString(2), rsLoadBilling.getString(3)));
                        }
                    }
                }
            }
        }catch(SQLException ex){
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try {
                psLoadBilling.close();
                psLoadItem.close();
                rsLoadBilling.close();
                rsLoadItem.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }

        }
        return salesItemData;
    }

    public Integer insertData(LinkedList<SalesItem> productsList){
        PreparedStatement psSalesItem = null;
        PreparedStatement psBilling = null;
        ResultSet rsBilling = null;
        int billingID = 0;
        String user = null;
        OrderServices orderServices = new OrderServices();

        for(SalesItem salesItem : productsList){
            user = salesItem.getsIUser();
        }
        try {
            Connection conn = DBConnection.Connect();
            psBilling = conn.prepareStatement(BillingQueries.INSERT_BILLING_DATA_QUERY);
            psSalesItem = conn.prepareStatement(BillingQueries.INSERT_SALE_ITEM_DATA_QUERY);

            psBilling.setString(1, user);
            psBilling.setString(2, String.valueOf(LocalDate.now()));
            psBilling.setString(3, String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            psBilling.setString(4, "Pending");
            psBilling.execute();
            rsBilling = conn.createStatement().executeQuery(BillingQueries.GET_LAST_INSERTED_RECORD_ID);
            while(rsBilling.next()){
                billingID = rsBilling.getInt(1);
            }
            if(billingID != 0){
                for(SalesItem salesItem : productsList){
                    psSalesItem.setInt(1,UtilityMethod.seperateID(salesItem.getsIPID()));
                    psSalesItem.setString(2, salesItem.getsIPName());
                    psSalesItem.setString(3, salesItem.getsIPWeight());
                    psSalesItem.setInt(4, salesItem.getsIPQuantity());
                    psSalesItem.setFloat(5, salesItem.getsIUnitPrice());
                    psSalesItem.setDouble(6, salesItem.getsITotalAmount());
                    psSalesItem.setInt(7, billingID);
                    psSalesItem.setString(8, salesItem.getsIType());
                    psSalesItem.execute();
                    if(salesItem.getsIType().equals("Advance Payment") || salesItem.getsIType().equals("Total Payment")) {

                        String orderStatus = null;
                        if (salesItem.getsIType().equals("Advance Payment")) {
                            orderStatus = "Advance Paid";
                        }
                        if (salesItem.getsIType().equals("Total Payment")) {
                            orderStatus = "Total Paid";
                        }
                        try{
                            orderServices.updateOrderPayStatus(salesItem.getsIPID(),orderStatus );
                        }catch(Exception ex){
                            AlertPopUp.generalError(ex);
                        }
                    }
                }
            }

            AlertPopUp.insertSuccesfully("Billed");

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Billing");
        }
        finally{
            try{
                psBilling.close();
                psSalesItem.close();
                rsBilling.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return billingID;
    }
    public boolean updateClearance(LinkedList<SalesItem> salesItemLinkedList){
        boolean resultVal = false;
        PreparedStatement psSalesItem = null;
        try {
            Connection conn = DBConnection.Connect();
            psSalesItem = conn.prepareStatement(BillingQueries.UPDATE_BILL_CLEARANCE);
            for(SalesItem salesItem : salesItemLinkedList){
                psSalesItem.setString(1, "Claimed");
                psSalesItem.setString(2, salesItem.getsIBNo());
                psSalesItem.execute();
            }
            AlertPopUp.updateSuccesfully("Sales Claimed");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Sales Claimed");

        } finally {
            try{
                psSalesItem.close();
            }catch(SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultVal;
    }
}
