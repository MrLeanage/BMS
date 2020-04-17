package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.BakeryProduct;
import util.dbConnect.DBConnection;
import util.query.BakeryProductQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BakeryProductServices {



    private ObservableList<BakeryProduct> bakeryProductsData;

    public  ObservableList<BakeryProduct> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            bakeryProductsData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(BakeryProductQueries.LOAD_BAKERY_PRODUCT_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                bakeryProductsData.add(new BakeryProduct(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getFloat(4), rsLoadBakeryProduct.getString(5), rsLoadBakeryProduct.getFloat(6), rsLoadBakeryProduct.getString(7)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return bakeryProductsData;
    }
    public  BakeryProduct loadSpecificData(String id){
        BakeryProduct bakeryProduct = new BakeryProduct();
        try {
            Connection conn = DBConnection.Connect();
            bakeryProductsData = FXCollections.observableArrayList();
            PreparedStatement psLoadBakeryProduct = conn.prepareStatement(BakeryProductQueries.LOAD_BAKERY_PRODUCT_DATA_QUERY);
            psLoadBakeryProduct.setInt(1, UtilityMethod.seperateID(id));
            ResultSet rsLoadBakeryProduct = psLoadBakeryProduct.executeQuery();
            while (rsLoadBakeryProduct.next()) {
                bakeryProduct.setbPID(rsLoadBakeryProduct.getString(1));
                bakeryProduct.setbPName(rsLoadBakeryProduct.getString(2));
                bakeryProduct.setbPType(rsLoadBakeryProduct.getString(3));
                bakeryProduct.setbPWeight(rsLoadBakeryProduct.getFloat(4));
                bakeryProduct.setbPDescription(rsLoadBakeryProduct.getString(5));
                bakeryProduct.setbPPrice(rsLoadBakeryProduct.getFloat(6));
                bakeryProduct.setbPStatus(rsLoadBakeryProduct.getString(7));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return bakeryProduct;
    }

    public boolean insertData(BakeryProduct bakeryProduct) throws  Exception{
        PreparedStatement psBakeryProduct = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psBakeryProduct = conn.prepareStatement(BakeryProductQueries.INSERT_BAKERY_PRODUCT_DATA_QUERY);

            psBakeryProduct.setString(1,bakeryProduct.getbPName());
            psBakeryProduct.setString(2,bakeryProduct.getbPType());
            psBakeryProduct.setFloat(3, bakeryProduct.getbPWeight());
            psBakeryProduct.setString(4, bakeryProduct.getbPDescription());
            psBakeryProduct.setFloat(5, bakeryProduct.getbPPrice());
            psBakeryProduct.setString(6, bakeryProduct.getbPStatus());
            psBakeryProduct.execute();
            AlertPopUp.insertSuccesfully("Bakery SalesItem");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Bakery SalesItem");
        }
        finally{
            psBakeryProduct.close();

        }
        return resultval;
    }

    public boolean updateData(BakeryProduct bakeryProduct) throws Exception {
        PreparedStatement psBackeryProduct = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psBackeryProduct = conn.prepareStatement(BakeryProductQueries.UPDATE_ITEM_STOCK_DATA_QUERY);
            psBackeryProduct.setString(1,bakeryProduct.getbPName());
            psBackeryProduct.setString(2,bakeryProduct.getbPType());
            psBackeryProduct.setFloat(3, bakeryProduct.getbPWeight());
            psBackeryProduct.setString(4, bakeryProduct.getbPDescription());
            psBackeryProduct.setFloat(5, bakeryProduct.getbPPrice());
            psBackeryProduct.setString(6, bakeryProduct.getbPStatus());
            psBackeryProduct.setInt(7, UtilityMethod.seperateID(bakeryProduct.getbPID()));
            psBackeryProduct.execute();
            AlertPopUp.updateSuccesfully("Bakery SalesItem");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Bakery SalesItem");

        } finally {
            psBackeryProduct.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psBakeryProduct = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psBakeryProduct = conn.prepareStatement(BakeryProductQueries.DELETE_ITEM_STOCK_DATA_QUERY);
            psBakeryProduct.setInt(1, itemID);
            psBakeryProduct.executeUpdate();
            AlertPopUp.deleteSuccesfull("Bakery SalesItem");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Bakery SalesItem");
        }finally{
            psBakeryProduct.close();
        }
        return resultVal;
    }

    public SortedList<BakeryProduct> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<BakeryProduct> bakeryProductsData = null;

        try {
            Connection conn = DBConnection.Connect();
            bakeryProductsData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(BakeryProductQueries.LOAD_BAKERY_PRODUCT_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                bakeryProductsData.add(new BakeryProduct(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getFloat(4), rsLoadBakeryProduct.getString(5), rsLoadBakeryProduct.getFloat(6), rsLoadBakeryProduct.getString(7)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<BakeryProduct> filteredData = new FilteredList<>(bakeryProductsData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(bakeryProduct -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(bakeryProduct.getbPID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(bakeryProduct.getbPName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(bakeryProduct.getbPType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(bakeryProduct.getbPWeight()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(bakeryProduct.getbPDescription().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(bakeryProduct.getbPPrice()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(bakeryProduct.getbPStatus().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<BakeryProduct> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
