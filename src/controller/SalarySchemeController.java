package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Allowance;
import model.SalaryScheme;
import util.dbConnect.DBConnection;
import util.query.AllowanceQueries;
import util.query.BasicSalarySchemeQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalarySchemeController {



    private ObservableList<SalaryScheme> salarySchemesData;

    public  ObservableList<SalaryScheme> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            salarySchemesData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(BasicSalarySchemeQueries.LOAD_BASIC_SALARY_SCHEME_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                salarySchemesData.add(new SalaryScheme(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getFloat(3), rsLoadBakeryProduct.getString(4)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return salarySchemesData;
    }

    public boolean insertData(SalaryScheme salaryScheme) throws  Exception{
        PreparedStatement psSalaryScheme = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psSalaryScheme = conn.prepareStatement(BasicSalarySchemeQueries.INSERT_BASIC_SALARY_SCHEME_DATA_QUERY);

            psSalaryScheme.setString(1,salaryScheme.getbSSTitle());
            psSalaryScheme.setFloat(2,salaryScheme.getbSSAmount());
            psSalaryScheme.setString(3, salaryScheme.getbSSAddedDate());
            psSalaryScheme.execute();
            AlertPopUp.insertSuccesfully("Salary Scheme");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Salary Scheme");
        }
        finally{
            psSalaryScheme.close();
        }
        return resultval;
    }

    public boolean updateData(SalaryScheme salaryScheme) throws Exception {
        PreparedStatement psSalaryScheme = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psSalaryScheme = conn.prepareStatement(BasicSalarySchemeQueries.UPDATE_BASIC_SALARY_SCHEME_DATA_QUERY);
            psSalaryScheme.setString(1,salaryScheme.getbSSTitle());
            psSalaryScheme.setFloat(2,salaryScheme.getbSSAmount());
            psSalaryScheme.setString(3, salaryScheme.getbSSAddedDate());
            psSalaryScheme.setInt(4, UtilityMethod.seperateID(salaryScheme.getbSSID()));
            psSalaryScheme.execute();
            AlertPopUp.updateSuccesfully("Salary Scheme");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Salary Scheme");

        } finally {
            psSalaryScheme.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psSalaryScheme = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psSalaryScheme = conn.prepareStatement(BasicSalarySchemeQueries.DELETE_BASIC_SALARY_SCHEME_DATA_QUERY);
            psSalaryScheme.setInt(1, itemID);
            psSalaryScheme.executeUpdate();
            AlertPopUp.deleteSuccesfull("Salary Scheme");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Salary Scheme");
        }finally{
            psSalaryScheme.close();
        }
        return resultVal;
    }

    public SortedList<SalaryScheme> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<SalaryScheme> salarySchemesData = null;

        try {
            Connection conn = DBConnection.Connect();
            salarySchemesData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(BasicSalarySchemeQueries.LOAD_BASIC_SALARY_SCHEME_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                salarySchemesData.add(new SalaryScheme(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getFloat(3), rsLoadBakeryProduct.getString(4)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<SalaryScheme> filteredData = new FilteredList<>(salarySchemesData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(salaryScheme -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(salaryScheme.getbSSID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(salaryScheme.getbSSTitle().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(salaryScheme.getbSSAmount()).toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(salaryScheme.getbSSAddedDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<SalaryScheme> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
