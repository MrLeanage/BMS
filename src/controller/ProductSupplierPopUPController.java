package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Supplier;
import util.dbConnect.DBConnection;
import util.query.AgencyProductSupplierPopUPQueries;
import util.userAlerts.AlertPopUp;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductSupplierPopUPController implements Initializable {
    private DBConnection dbcon;
    private static PreparedStatement ps;
    private static String supplierType;




    /**
     * Initializes the controller class.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbcon = new DBConnection();
        loadData();

    }
    public void setSupplierType(String inputType){
    supplierType = inputType;

    }
    public  ObservableList<Supplier> loadData(){
        ObservableList<Supplier> supplierData = null;

        try {
            Connection conn = DBConnection.Connect();
            supplierData = FXCollections.observableArrayList();
            //System.out.println(supplierType);
            ResultSet rs = conn.createStatement().executeQuery(AgencyProductSupplierPopUPQueries.LOAD_DATA_QUERY + supplierType);

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return supplierData;
    }



    public SortedList<Supplier> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<Supplier> supplierData = null;
        try {
            Connection conn = DBConnection.Connect();
            supplierData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(AgencyProductSupplierPopUPQueries.SEARCH_DATA_QUERY + supplierType);

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Supplier> filteredData = new FilteredList<>(supplierData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(supplier -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(supplier.getsIID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone1()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone2()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIEmail().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Supplier> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
