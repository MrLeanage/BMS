package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Allowance;
import util.dbConnect.DBConnection;
import util.query.AllowancePayQueries;
import util.query.AllowanceQueries;
import util.userAlerts.AlertPopUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllowancePayServices {


    public ObservableList<Allowance> loadSpecificExistingData(int eID) throws SQLException {
        ObservableList<Allowance> allowancesData = null;
        PreparedStatement psAllowancePay = null;
        PreparedStatement psAllowance = null;
        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();

            psAllowancePay = conn.prepareStatement(AllowancePayQueries.LOAD_SPECIFIC_ALLOWANCE_PAY_DATA_QUERY);
            psAllowance = conn.prepareStatement(AllowanceQueries.LOAD_SPECIFIC_EXISTING_ALLOWANCE_DATA_QUERY);
            psAllowancePay.setInt(1, eID);
            ResultSet rsLoadAllowancePay = psAllowancePay.executeQuery();
            while (rsLoadAllowancePay.next()) {
                psAllowance.setInt(1, rsLoadAllowancePay.getInt(1));
                ResultSet rsLoadAllowance = psAllowance.executeQuery();
                while(rsLoadAllowance.next()){
                    allowancesData.add(new Allowance(rsLoadAllowance.getString(1), rsLoadAllowance.getString(2), rsLoadAllowance.getString(3), rsLoadAllowance.getString(4), rsLoadAllowance.getFloat(5)));
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psAllowance.close();
            psAllowancePay.close();
        }

        return allowancesData;
    }
    public ObservableList<Allowance> loadSpecificNewData(int eID) throws SQLException {
        ObservableList<Allowance> allowancesData = null;
        PreparedStatement psAllowance = null;
        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();


            psAllowance = conn.prepareStatement(AllowanceQueries.LOAD_SPECIFIC_NEW_ALLOWANCE_DATA_QUERY);
            psAllowance.setInt(1, eID);
            ResultSet rsLoadAllowance = psAllowance.executeQuery();
            while (rsLoadAllowance.next()) {
                  allowancesData.add(new Allowance(rsLoadAllowance.getString(1), rsLoadAllowance.getString(2), rsLoadAllowance.getString(3), rsLoadAllowance.getString(4), rsLoadAllowance.getFloat(5)));

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psAllowance.close();
        }

        return allowancesData;
    }
    public ObservableList<Allowance> loadSpecificEmployeeAllowanceData(int eID) throws SQLException {
        ObservableList<Allowance> allowancesData = null;
        PreparedStatement psAllowance = null;
        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();


            psAllowance = conn.prepareStatement(AllowanceQueries.LOAD_SPECIFIC_ALLOWANCES_FOR_EMPLOYEE_DATA_QUERY);
            psAllowance.setInt(1, eID);
            ResultSet rsLoadAllowance = psAllowance.executeQuery();
            while (rsLoadAllowance.next()) {
                allowancesData.add(new Allowance(rsLoadAllowance.getString(1), rsLoadAllowance.getString(2), rsLoadAllowance.getString(3), rsLoadAllowance.getString(4), rsLoadAllowance.getFloat(5)));

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psAllowance.close();
        }

        return allowancesData;
    }
    public boolean insertData(int eID, int aID) throws  Exception{
        PreparedStatement psAllowance = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psAllowance = conn.prepareStatement(AllowancePayQueries.INSERT_ALLOWANCE_PAY_DATA_QUERY);

            psAllowance.setInt(1, eID);
            psAllowance.setInt(2, aID);
            psAllowance.execute();
            AlertPopUp.insertSuccesfully("Allowance");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Allowance");
        }
        finally{
            psAllowance.close();

        }
        return resultval;
    }
    public Boolean deleteData(int eID, int aID ) throws SQLException {
        PreparedStatement psAllowancePay = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psAllowancePay = conn.prepareStatement(AllowancePayQueries.DELETE_ALLOWANCE_PAY_DATA_QUERY);
            psAllowancePay.setInt(1, eID);
            psAllowancePay.setInt(2, aID);
            psAllowancePay.executeUpdate();
            AlertPopUp.deleteSuccesfull("Allowance Scheme");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Allowance Scheme");
        }finally{
            psAllowancePay.close();
        }
        return resultVal;
    }
    public SortedList<Allowance> searchExistingDataTable(TextField searchTextField, int eID) throws SQLException {

        //Retreiving all data from database
        ObservableList<Allowance> allowancesData = null;
        PreparedStatement psAllowancePay = null;
        PreparedStatement psAllowance = null;
        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();

            psAllowancePay = conn.prepareStatement(AllowancePayQueries.LOAD_SPECIFIC_ALLOWANCE_PAY_DATA_QUERY);
            psAllowance = conn.prepareStatement(AllowanceQueries.LOAD_SPECIFIC_EXISTING_ALLOWANCE_DATA_QUERY);
            psAllowancePay.setInt(1, eID);
            ResultSet rsLoadAllowancePay = psAllowancePay.executeQuery();
            while (rsLoadAllowancePay.next()) {
                psAllowance.setInt(1, rsLoadAllowancePay.getInt(1));
                ResultSet rsLoadAllowance = psAllowance.executeQuery();
                while(rsLoadAllowance.next()){
                    allowancesData.add(new Allowance(rsLoadAllowance.getString(1), rsLoadAllowance.getString(2), rsLoadAllowance.getString(3), rsLoadAllowance.getString(4), rsLoadAllowance.getFloat(5)));
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psAllowance.close();
            psAllowancePay.close();
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
    public SortedList<Allowance> searchNewDataTable(TextField searchTextField, int eID) throws SQLException {

        //Retreiving all data from database
        ObservableList<Allowance> allowancesData = null;
        PreparedStatement psAllowance = null;
        try {
            Connection conn = DBConnection.Connect();
            allowancesData = FXCollections.observableArrayList();


            psAllowance = conn.prepareStatement(AllowanceQueries.LOAD_SPECIFIC_NEW_ALLOWANCE_DATA_QUERY);
            psAllowance.setInt(1, eID);
            ResultSet rsLoadAllowance = psAllowance.executeQuery();
            while (rsLoadAllowance.next()) {
                allowancesData.add(new Allowance(rsLoadAllowance.getString(1), rsLoadAllowance.getString(2), rsLoadAllowance.getString(3), rsLoadAllowance.getString(4), rsLoadAllowance.getFloat(5)));

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            psAllowance.close();
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
