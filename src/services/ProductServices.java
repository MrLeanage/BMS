package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.SalesItem;
import util.dbConnect.DBConnection;
import util.query.AgencyProductQueries;
import util.query.BakeryProductQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;

public class ProductServices {
    public ObservableList<SalesItem> loadData(){
        //Retreiving all data from database
        ObservableList<SalesItem> productsDataList = FXCollections.observableArrayList();
        ObservableList<SalesItem> salesBakeryData = null;
        ObservableList<SalesItem> salesAgencyData = null;
        try {
            Connection conn = DBConnection.Connect();
            salesBakeryData = FXCollections.observableArrayList();
            salesAgencyData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(BakeryProductQueries.LOAD_BAKERY_PRODUCT_DATA_QUERY);
            ResultSet rsLoadAgencyProduct = conn.createStatement().executeQuery(AgencyProductQueries.LOAD_AGENCY_PRODUCT_DATA_QUERY);
            while (rsLoadBakeryProduct.next()) {
                salesBakeryData.add(new SalesItem(UtilityMethod.addPrefix("BP", rsLoadBakeryProduct.getString(1)), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getFloat(6), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(7)));
            }
            while (rsLoadAgencyProduct.next()) {
                salesBakeryData.add(new SalesItem(UtilityMethod.addPrefix("AP", rsLoadAgencyProduct.getString(1)), rsLoadAgencyProduct.getString(2), rsLoadAgencyProduct.getString(4), rsLoadAgencyProduct.getFloat(7), "Agency Product", "Available"));
            }
            productsDataList.addAll(salesBakeryData);
            productsDataList.addAll(salesAgencyData);
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return productsDataList;
    }
    public SortedList<SalesItem> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<SalesItem> productsDataList = loadData();
        ObservableList<SalesItem> productsData = FXCollections.observableArrayList(productsDataList);
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<SalesItem> filteredData = new FilteredList<>(productsData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(salesItem -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(salesItem.getsIPID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(salesItem.getsIPName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(salesItem.getsIPWeight()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(salesItem.getsIUnitPrice()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<SalesItem> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }
}
