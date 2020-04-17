package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Allowance;
import model.ItemStock;
import model.ItemWithdraw;
import util.dbConnect.DBConnection;
import util.query.AllowanceQueries;
import util.query.ItemStockQueries;
import util.query.ItemWithdrawQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ItemWithdrawServices {



    private ObservableList<ItemWithdraw> itemWithdrawsData;

    public  ObservableList<ItemWithdraw> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            itemWithdrawsData = FXCollections.observableArrayList();
            ResultSet rsLoadItemWithdraw = conn.createStatement().executeQuery(ItemWithdrawQueries.LOAD_ITEM_WITHDRAW_DATA_QUERY);

            while (rsLoadItemWithdraw.next()) {
                itemWithdrawsData.add(new ItemWithdraw(rsLoadItemWithdraw.getString(1), rsLoadItemWithdraw.getString(2), rsLoadItemWithdraw.getString(3), rsLoadItemWithdraw.getInt(4), rsLoadItemWithdraw.getString(5), rsLoadItemWithdraw.getString(6), rsLoadItemWithdraw.getString(7)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return itemWithdrawsData;
    }
    public  ObservableList<ItemWithdraw> loadSpecificUserData(String id) throws SQLException {
        ResultSet rsLoadItemWithdraw = null;
        PreparedStatement psLoadItemWithdraw = null;
        ItemStockServices itemStockServices = new ItemStockServices();
        ItemStock itemStock = new ItemStock();
        try {
            Connection conn = DBConnection.Connect();
            itemWithdrawsData = FXCollections.observableArrayList();
            psLoadItemWithdraw = conn.prepareStatement(ItemWithdrawQueries.LOAD_SPECIFIC_USER_ITEM_WITHDRAW_DATA_QUERY);
            psLoadItemWithdraw.setInt(1, UtilityMethod.seperateID(id));
            rsLoadItemWithdraw = psLoadItemWithdraw.executeQuery();

            while (rsLoadItemWithdraw.next()) {
                itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadItemWithdraw.getString(2)));
                itemWithdrawsData.add(new ItemWithdraw(rsLoadItemWithdraw.getString(1), rsLoadItemWithdraw.getString(2),  itemStock.getiName(), rsLoadItemWithdraw.getString(3) , itemStock.getiWeightPerBlock(), rsLoadItemWithdraw.getInt(4), rsLoadItemWithdraw.getString(5), rsLoadItemWithdraw.getString(6), rsLoadItemWithdraw.getString(7)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psLoadItemWithdraw.close();
            rsLoadItemWithdraw.close();
        }
        return itemWithdrawsData;
    }

    public boolean insertData(LinkedList<ItemWithdraw> itemWithdrawsArrayList) throws  Exception{
        PreparedStatement psItemWithdraw = null;
        boolean resultval = false;

        try {
            Connection conn = DBConnection.Connect();
            psItemWithdraw = conn.prepareStatement(ItemWithdrawQueries.INSERT_ITEM_WITHDRAW_DATA_QUERY);
            for(ItemWithdraw itemWithdraw : itemWithdrawsArrayList){

                psItemWithdraw.setInt(1, UtilityMethod.seperateID(itemWithdraw.getiWIID()));
                psItemWithdraw.setString(2, itemWithdraw.getiWDescription());
                psItemWithdraw.setInt(3, itemWithdraw.getiWQuantity());
                psItemWithdraw.setInt(4, UtilityMethod.seperateID(itemWithdraw.getiWUser()));
                psItemWithdraw.setString(5, itemWithdraw.getiWDate());
                psItemWithdraw.setString(6, itemWithdraw.getiWTime());

                psItemWithdraw.execute();
            }

            AlertPopUp.insertSuccesfully("Withdraw Item List");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Withdraw Item List");
        }
        finally{
            psItemWithdraw.close();

        }
        return resultval;
    }
    public boolean withdrawStockQuantity(ItemStock itemStock) throws SQLException {
        boolean resultVal = false;
        PreparedStatement psStockItem = null;
        try {
            Connection conn = DBConnection.Connect();
            psStockItem = conn.prepareStatement(ItemStockQueries.UPDATE_ITEM_STOCK_QUANTITY_DATA_QUERY);
            psStockItem.setInt(1,itemStock.getiAvailableQuantity());
            psStockItem.setInt(2, UtilityMethod.seperateID(itemStock.getiID()));
            psStockItem.execute();
            //AlertPopUp.updateSuccesfully("Withdraw Quantity");
            resultVal = true;

        } catch (SQLException ex) {
            //AlertPopUp.updateFailed(ex, "Withdraw Quantity");

        } finally {
            psStockItem.close();
        }
        return resultVal;
    }
    public boolean reAddStockQuantity(LinkedList<ItemStock> linkedListItemStock) throws SQLException {
        boolean resultVal = false;
        PreparedStatement psStockItem = null;
        try {
            Connection conn = DBConnection.Connect();
            psStockItem = conn.prepareStatement(ItemStockQueries.UPDATE_ITEM_STOCK_QUANTITY_DATA_QUERY);
            for(ItemStock itemStock : linkedListItemStock){
                psStockItem.setInt(1,itemStock.getiAvailableQuantity());
                psStockItem.setInt(2, UtilityMethod.seperateID(itemStock.getiID()));
                psStockItem.execute();
            }

            //AlertPopUp.updateSuccesfully("Withdraw Quantity");
            resultVal = true;

        } catch (SQLException ex) {
            //AlertPopUp.updateFailed(ex, "Withdraw Quantity");

        } finally {
            psStockItem.close();
        }
        return resultVal;
    }



    public SortedList<Allowance> searchTable(TextField searchTextField){

        //Retreiving all data from database
        ObservableList<Allowance> allowancesData = null;

        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(AllowanceQueries.LOAD_ALLOWANCE_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                allowancesData.add(new Allowance(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getFloat(5)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Allowance> filteredData = new FilteredList<>(allowancesData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(allowance -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(allowance.getaID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(allowance.getaTitle().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(allowance.getaDescription().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(allowance.getaType().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(allowance.getaValue()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Allowance> sortedData = new SortedList<>(filteredData);


        return sortedData;
    }

}
