package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Allowance;
import model.OtherExpense;
import util.dbConnect.DBConnection;
import util.query.AllowanceQueries;
import util.query.OtherExpenseQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OtherExpenseServices {


    public  ObservableList<OtherExpense> loadData(){
        ObservableList<OtherExpense> otherExpensesData = null;
        Connection conn = DBConnection.Connect();
        ResultSet rsLoadOtherExpense = null;
        try {

            otherExpensesData = FXCollections.observableArrayList();
            rsLoadOtherExpense = conn.createStatement().executeQuery(OtherExpenseQueries.LOAD_EXPENSE_DATA_QUERY);

            while (rsLoadOtherExpense.next()) {
                otherExpensesData.add(new OtherExpense(rsLoadOtherExpense.getString(1), rsLoadOtherExpense.getString(2), rsLoadOtherExpense.getString(3), rsLoadOtherExpense.getString(4), rsLoadOtherExpense.getDouble(5), rsLoadOtherExpense.getString(6), rsLoadOtherExpense.getString(7)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadOtherExpense.close();
                conn.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return otherExpensesData;
    }

    public boolean insertData(OtherExpense otherExpense) {
        Connection conn = DBConnection.Connect();
        PreparedStatement psOtherExpense = null;
        boolean resultval = false;
        try {

            psOtherExpense = conn.prepareStatement(OtherExpenseQueries.INSERT_EXPENSE_DATA_QUERY);

            psOtherExpense.setString(1,otherExpense.geteXPTitle());
            psOtherExpense.setString(2,otherExpense.geteXPDescription());
            psOtherExpense.setString(3, otherExpense.geteXPPeriod());
            psOtherExpense.setDouble(4, otherExpense.geteXPAmount());
            psOtherExpense.setString(5, otherExpense.geteXPPaidDate());
            psOtherExpense.setString(6, otherExpense.geteXPAddedDate());
            psOtherExpense.execute();
            AlertPopUp.insertSuccesfully("Expense Info");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Expense Info");
        }
        finally{
            try{
                psOtherExpense.close();
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }

        }
        return resultval;
    }

    public boolean updateData(OtherExpense otherExpense) throws Exception {
        Connection conn = DBConnection.Connect();
        PreparedStatement psOtherExpense = null;

        boolean resultVal = false;
        try {

            psOtherExpense = conn.prepareStatement(OtherExpenseQueries.UPDATE_EXPENSE_DATA_QUERY);
            psOtherExpense.setString(1,otherExpense.geteXPTitle());
            psOtherExpense.setString(2,otherExpense.geteXPDescription());
            psOtherExpense.setString(3, otherExpense.geteXPPeriod());
            psOtherExpense.setDouble(4, otherExpense.geteXPAmount());
            psOtherExpense.setString(5, otherExpense.geteXPPaidDate());
            psOtherExpense.setString(6, otherExpense.geteXPAddedDate());
            psOtherExpense.setInt(7, UtilityMethod.seperateID(otherExpense.geteXPID()));
            psOtherExpense.execute();
            AlertPopUp.updateSuccesfully("Expense Info");
            resultVal = true;

        } catch (SQLException | NullPointerException ex) {
            AlertPopUp.updateFailed(ex, "Expense Info");

        } finally {
            try{
                psOtherExpense.close();
                conn.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) {
        Connection conn = DBConnection.Connect();
        PreparedStatement psOtherExpense = null;
        Boolean resultVal = false;

        try{
            psOtherExpense = conn.prepareStatement(OtherExpenseQueries.DELETE_EXPENSE_DATA_QUERY);
            psOtherExpense.setInt(1, itemID);
            psOtherExpense.executeUpdate();
            AlertPopUp.deleteSuccesfull("Expense Info");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Expense Info");
        }finally{
            try{
                psOtherExpense.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultVal;
    }

    public SortedList<OtherExpense> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<OtherExpense> otherExpensesData;
        otherExpensesData = loadData();
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<OtherExpense> filteredData = new FilteredList<>(otherExpensesData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(otherExpense -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(otherExpense.geteXPID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(otherExpense.geteXPTitle().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(otherExpense.geteXPDescription().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(otherExpense.geteXPPeriod().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(otherExpense.geteXPAmount()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(otherExpense.geteXPPaidDate().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(otherExpense.geteXPAddedDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<OtherExpense> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
