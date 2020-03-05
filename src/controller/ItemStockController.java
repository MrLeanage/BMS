package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.ItemStock;
import util.dbConnect.DBConnection;
import util.playAudio.Audio;
import util.query.ItemStockQueries;
import util.query.PurchaseProductQueries;
import util.query.SupplierQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ItemStockController {
    private DBConnection dbcon;


    private ObservableList<ItemStock> itemStockData;

    public  ObservableList<ItemStock> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            itemStockData = FXCollections.observableArrayList();
            ResultSet rsLoadItemStock = conn.createStatement().executeQuery(ItemStockQueries.LOAD_ITEM_STOCK_DATA_QUERY);

            while (rsLoadItemStock.next()) {
                try{
                    ResultSet rsFindPurchaseData = conn.createStatement().executeQuery(PurchaseProductQueries.FIND_PURCHASE_STOCK_DATA_QUERRY +rsLoadItemStock.getString(1));
                    while(rsFindPurchaseData.next()){
                        if(!(rsFindPurchaseData.getString(1).isEmpty() || rsFindPurchaseData.getString(1).equals(null))){
                            try{
                                ResultSet rsFindSupplierData = conn.createStatement().executeQuery(SupplierQueries.FIND_SUPPLIER_DATA_QUERY +rsFindPurchaseData.getString(3));
                                while (rsFindSupplierData.next()){
                                    if(!(rsFindSupplierData.getString(1).isEmpty() || rsFindSupplierData.getString(1).equals(null))){

                                        itemStockData.add(new ItemStock(rsLoadItemStock.getString(1), rsLoadItemStock.getString(2),rsFindSupplierData.getString(1), rsFindSupplierData.getString(2),rsLoadItemStock.getInt(3),rsLoadItemStock.getInt(4),rsLoadItemStock.getFloat(5),rsLoadItemStock.getFloat(6), rsLoadItemStock.getString(7),rsLoadItemStock.getString(8),rsLoadItemStock.getInt(9),rsLoadItemStock.getInt(10)));

                                    }else{
                                        AlertPopUp.sqlRecordNotFound("Supplier");
                                    }
                                }
                            }catch(SQLException ex){
                                AlertPopUp.sqlQueryError(ex);
                            }
                        }else{
                            AlertPopUp.sqlRecordNotFound("Purchase");
                        }
                    }
                }catch (SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return itemStockData;
    }

    public boolean insertData(ItemStock itemStock) throws  Exception{
        PreparedStatement psItemStock = null, psPurchase = null;
        boolean status = false;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psItemStock = conn.prepareStatement(ItemStockQueries.INSERT_ITEM_STOCK_DATA_QUERY);

            psItemStock.setString(1,itemStock.getiName());
            psItemStock.setInt(2,itemStock.getiUnitsPerBlock());
            psItemStock.setFloat(3, itemStock.getiBlocks());
            psItemStock.setFloat(4, itemStock.getiWeightPerBlock());
            psItemStock.setFloat(5, itemStock.getiBuyingPrice());
            psItemStock.setFloat(6, itemStock.getiMinQuantityLimit());
            psItemStock.setString(7, itemStock.getiExpireDate());
            psItemStock.setString(8, itemStock.getiAddedDate());
            psItemStock.setFloat(9, itemStock.getiInitialAvailableQuantity());
            psItemStock.execute();
            status = true;
            if(status){
                try{
                    ResultSet rsLastStockItem = conn.createStatement().executeQuery(ItemStockQueries.GET_LAST_ID_DATA_QUERY);
                    while (rsLastStockItem.next()){
                        psPurchase = conn.prepareStatement(PurchaseProductQueries.INSERT_PURCHASE_DATA_QUERRY);
                        psPurchase.setInt(1, rsLastStockItem.getInt(1));
                        psPurchase.setInt(2, UtilityMethod.seperateID(itemStock.getiSID()));
                        psPurchase.setString(3, "Stock");
                        psPurchase.setString(4, String.valueOf(LocalDate.now()));
                        psPurchase.setString(5,"Pending");
                        psPurchase.execute();
                        AlertPopUp.insertSuccesfully("Stock Item");
                        resultval = true;
                    }
                }catch(SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Stock Item");
        }
        finally{
            psItemStock.close();
            psPurchase.close();

        }
        return resultval;
    }

    public boolean updateData(ItemStock oldItemStock, ItemStock newItemStock) throws Exception {
        PreparedStatement psItemStock = null, psPurchase = null;

        boolean status = false;
        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psItemStock = conn.prepareStatement(ItemStockQueries.UPDATE_ITEM_STOCK_DATA_QUERY);
            psItemStock.setString(1,newItemStock.getiName());
            psItemStock.setInt(2,newItemStock.getiUnitsPerBlock());
            psItemStock.setInt(3, newItemStock.getiBlocks());
            psItemStock.setFloat(4, newItemStock.getiWeightPerBlock());
            psItemStock.setFloat(5, newItemStock.getiBuyingPrice());
            psItemStock.setInt(6, newItemStock.getiMinQuantityLimit());
            psItemStock.setString(7, newItemStock.getiExpireDate());
            psItemStock.setString(8, newItemStock.getiAddedDate());
            psItemStock.setInt(9, UtilityMethod.setUpdateItemAvalableQuantity(oldItemStock, newItemStock));
            psItemStock.setInt(10,UtilityMethod.seperateID(newItemStock.getiID()));
            psItemStock.execute();
            status = true;
            if(status){
                try{
                        psPurchase = conn.prepareStatement(PurchaseProductQueries.UPDATE_PURCHASE_STOCK_DATA_QUERRY);
                        psPurchase.setInt(1, UtilityMethod.seperateID(newItemStock.getiSID()));
                        psPurchase.setString(2,String.valueOf(LocalDate.now()));
                        psPurchase.setInt(3, UtilityMethod.seperateID(newItemStock.getiID()));
                        psPurchase.execute();
                        AlertPopUp.updateSuccesfully("Stock Item");
                        resultVal = true;

                }catch(SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }
            }

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Stock Item");

        } finally {
            psItemStock.close();
            psPurchase.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psItemStock = null, psPurchase = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psItemStock = conn.prepareStatement(ItemStockQueries.DELETE_ITEM_STOCK_DATA_QUERY);
            psItemStock.setInt(1, itemID);
            psItemStock.executeUpdate();
            try{
                psPurchase = conn.prepareStatement(PurchaseProductQueries.DELETE_PURCHASE_STOCK_DATA_QUERRY);
                psPurchase.setInt(1, itemID);
                psPurchase.executeUpdate();
                AlertPopUp.deleteSuccesfull("Stock Item");
                resultVal = true;
            }catch(SQLException ex){
                AlertPopUp.deleteFailed(ex, "Stock Item");
            }

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Stock Item");
        }finally{
            psItemStock.close();
            psPurchase.close();
        }
        return resultVal;
    }

    public SortedList<ItemStock> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<ItemStock> itemStockData = null;

        try {
            Connection conn = DBConnection.Connect();
            itemStockData = FXCollections.observableArrayList();
            ResultSet rsLoadItemStock = conn.createStatement().executeQuery(ItemStockQueries.LOAD_ITEM_STOCK_DATA_QUERY);

            while (rsLoadItemStock.next()) {
                try{
                    ResultSet rsFindPurchaseData = conn.createStatement().executeQuery(PurchaseProductQueries.FIND_PURCHASE_STOCK_DATA_QUERRY +rsLoadItemStock.getString(1));
                    while(rsFindPurchaseData.next()){
                        if(!(rsFindPurchaseData.getString(1).isEmpty() || rsFindPurchaseData.getString(1).equals(null))){
                            try{
                                ResultSet rsFindSupplierData = conn.createStatement().executeQuery(SupplierQueries.FIND_SUPPLIER_DATA_QUERY +rsFindPurchaseData.getString(3));
                                while (rsFindSupplierData.next()){
                                    if(!(rsFindSupplierData.getString(1).isEmpty() || rsFindSupplierData.getString(1).equals(null))){

                                        itemStockData.add(new ItemStock(rsLoadItemStock.getString(1), rsLoadItemStock.getString(2),rsFindSupplierData.getString(1), rsFindSupplierData.getString(2),rsLoadItemStock.getInt(3),rsLoadItemStock.getInt(4),rsLoadItemStock.getFloat(5),rsLoadItemStock.getFloat(6), rsLoadItemStock.getString(7),rsLoadItemStock.getString(8),rsLoadItemStock.getInt(9),rsLoadItemStock.getInt(10)));

                                    }else{
                                        AlertPopUp.sqlRecordNotFound("Supplier");
                                    }
                                }
                            }catch(SQLException ex){
                                AlertPopUp.sqlQueryError(ex);
                            }
                        }else{
                            AlertPopUp.sqlRecordNotFound("Purchase");
                        }
                    }
                }catch (SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<ItemStock> filteredData = new FilteredList<>(itemStockData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(itemStock -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(itemStock.getiID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiSID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiSISupplierName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiUnitsPerBlock()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiBlocks()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiWeightPerBlock()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiBuyingPrice()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiMinQuantityLimit()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiExpireDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiAddedDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(itemStock.getiAvailableQuantity()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(itemStock.getiStatus().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<ItemStock> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
