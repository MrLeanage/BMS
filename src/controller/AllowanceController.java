package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Allowance;
import model.BakeryProduct;
import util.dbConnect.DBConnection;
import util.query.AllowanceQueries;
import util.query.BakeryProductQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllowanceController {



    private ObservableList<Allowance> allowancesData;

    public  ObservableList<Allowance> loadData(){
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
        return allowancesData;
    }

    public boolean insertData(Allowance allowance) throws  Exception{
        PreparedStatement psAllowance = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psAllowance = conn.prepareStatement(AllowanceQueries.INSERT_ALLOWANCE_DATA_QUERY);

            psAllowance.setString(1,allowance.getaTitle());
            psAllowance.setString(2,allowance.getaDescription());
            psAllowance.setString(3, allowance.getaType());
            psAllowance.setFloat(4, allowance.getaValue());
            psAllowance.execute();
            AlertPopUp.insertSuccesfully("Allowance Scheme");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Allowance Scheme");
        }
        finally{
            psAllowance.close();

        }
        return resultval;
    }

    public boolean updateData(Allowance allowance) throws Exception {
        PreparedStatement psAllowance = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psAllowance = conn.prepareStatement(AllowanceQueries.UPDATE_ALLOWANCE_DATA_QUERY);
            psAllowance.setString(1,allowance.getaTitle());
            psAllowance.setString(2,allowance.getaDescription());
            psAllowance.setString(3, allowance.getaType());
            psAllowance.setFloat(4, allowance.getaValue());
            psAllowance.setInt(5, UtilityMethod.seperateID(allowance.getaID()));
            psAllowance.execute();
            AlertPopUp.updateSuccesfully("Allowance Scheme");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Allowance Scheme");

        } finally {
            psAllowance.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psAllowance = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psAllowance = conn.prepareStatement(AllowanceQueries.DELETE_ALLOWANCE_DATA_QUERY);
            psAllowance.setInt(1, itemID);
            psAllowance.executeUpdate();
            AlertPopUp.deleteSuccesfull("Allowance Scheme");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Allowance Scheme");
        }finally{
            psAllowance.close();
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
