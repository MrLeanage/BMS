package services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Employee;
import model.PaySheet;
import model.SalaryScheme;
import util.dbConnect.DBConnection;
import util.query.BasicSalarySchemeQueries;
import util.query.PaySheetQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PaySheetServices {



    private ObservableList<PaySheet> paySheetsData;

    public  ObservableList<PaySheet> loadData(){
        Connection conn = DBConnection.Connect();
        ResultSet rsLoadPaySheet = null;
        try {
            paySheetsData = FXCollections.observableArrayList();
            rsLoadPaySheet = conn.createStatement().executeQuery(PaySheetQueries.LOAD_PAYSHEET_DATA_QUERY);

            while (rsLoadPaySheet.next()) {
                paySheetsData.add(new PaySheet(rsLoadPaySheet.getString(1), rsLoadPaySheet.getString(2), rsLoadPaySheet.getString(3), rsLoadPaySheet.getString(4), rsLoadPaySheet.getString(5), rsLoadPaySheet.getDouble(6), rsLoadPaySheet.getDouble(7), rsLoadPaySheet.getString(8), rsLoadPaySheet.getLong(9), rsLoadPaySheet.getString(10)));
            }
        } catch (SQLException ex) {
                AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPaySheet.close();
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return paySheetsData;
    }
    public  ObservableList<PaySheet> loadData(Integer year, String month){
        Connection conn = DBConnection.Connect();
        ResultSet rsLoadPaySheet = null;
        try {
            paySheetsData = FXCollections.observableArrayList();
            rsLoadPaySheet = conn.createStatement().executeQuery(PaySheetQueries.LOAD_PAYSHEET_DATA_QUERY);

            while (rsLoadPaySheet.next()) {
                String date = rsLoadPaySheet.getString(10);

                if((year == 0) && (month.equals("None"))){
                    paySheetsData.add(new PaySheet(rsLoadPaySheet.getString(1), rsLoadPaySheet.getString(2), rsLoadPaySheet.getString(3), rsLoadPaySheet.getString(4), rsLoadPaySheet.getString(5), rsLoadPaySheet.getDouble(6), rsLoadPaySheet.getDouble(7), rsLoadPaySheet.getString(8), rsLoadPaySheet.getLong(9), rsLoadPaySheet.getString(10)));
                }else if((year != 0) && (month.equals("None"))){
                    if(UtilityMethod.getYear(date).equals(year)){
                        paySheetsData.add(new PaySheet(rsLoadPaySheet.getString(1), rsLoadPaySheet.getString(2), rsLoadPaySheet.getString(3), rsLoadPaySheet.getString(4), rsLoadPaySheet.getString(5), rsLoadPaySheet.getDouble(6), rsLoadPaySheet.getDouble(7), rsLoadPaySheet.getString(8), rsLoadPaySheet.getLong(9), rsLoadPaySheet.getString(10)));
                    }
                }else{
                    if((UtilityMethod.getYear(date).equals(year)) && (UtilityMethod.getMonth(date).equals(month))){
                        paySheetsData.add(new PaySheet(rsLoadPaySheet.getString(1), rsLoadPaySheet.getString(2), rsLoadPaySheet.getString(3), rsLoadPaySheet.getString(4), rsLoadPaySheet.getString(5), rsLoadPaySheet.getDouble(6), rsLoadPaySheet.getDouble(7), rsLoadPaySheet.getString(8), rsLoadPaySheet.getLong(9), rsLoadPaySheet.getString(10)));
                    }
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPaySheet.close();
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return paySheetsData;
    }
    public Boolean insertData(ObservableList<PaySheet> paySheetObservableList){
        Boolean resultVal = false;
        Connection connection = DBConnection.Connect();
        PreparedStatement psPaySheet = null;
        PreparedStatement psExistingPaySheet = null;
        ResultSet rsExistingPaySheet = null;


        try{
            psPaySheet = connection.prepareStatement(PaySheetQueries.INSERT_PAYSHEET_DATA_QUERY);
            psExistingPaySheet = connection.prepareStatement(PaySheetQueries.LOAD_SPECIFIC_EID_PAYSHEET_DATA_QUERY);

            for(PaySheet paySheet : paySheetObservableList){
                System.out.println("ID is :"+paySheet.getpSEID());
                psExistingPaySheet.setInt(1, UtilityMethod.seperateID(paySheet.getpSEID()));
                rsExistingPaySheet = psExistingPaySheet.executeQuery();
                while(rsExistingPaySheet.next()){
                    String date = rsExistingPaySheet.getString(10);

                    psPaySheet.setInt(1, UtilityMethod.seperateID(paySheet.getpSEID()));
                    psPaySheet.setString(2, paySheet.getpSEName());
                    psPaySheet.setString(3, paySheet.getpSNIC());
                    psPaySheet.setString(4, paySheet.getpSBSSTitle());
                    psPaySheet.setDouble(5, paySheet.getpSBSSAmount());
                    psPaySheet.setDouble(6, paySheet.getpSATotalAmount());
                    psPaySheet.setString(7, paySheet.getpSBankName());
                    psPaySheet.setLong(8, paySheet.getpSAccountNo());
                    psPaySheet.setString(9, paySheet.getpSDate());
                    if(!(rsExistingPaySheet.getString(1).isEmpty()) ){
                        System.out.println("DAte is :"+ paySheet.getpSDate() + " " + date);
                        if((UtilityMethod.getYear(date) != UtilityMethod.getYear(paySheet.getpSDate()))
                                && !(UtilityMethod.getMonth(date).equals(UtilityMethod.getMonth(paySheet.getpSDate())))){
                            psPaySheet.execute();
                            AlertPopUp.insertSuccesfully("Paysheet");
                        }else{
                            AlertPopUp.emptyInsertionFailed("Already Exist");
                        }
                    }else{
                        psPaySheet.execute();
                        AlertPopUp.insertSuccesfully("Paysheet");
                    }

                }
            }


            resultVal = true;
        }catch (SQLException ex){
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                psPaySheet.close();
                connection.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultVal;
    }
    public SortedList<PaySheet> searchTable(TextField searchTextField, Integer year, String month){
        //Retreiving all data from database
        ObservableList<PaySheet> paySheetsData = null;
        paySheetsData = loadData(year, month);

        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<PaySheet> filteredData = new FilteredList<>(paySheetsData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(paySheet -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(paySheet.getpSID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSEID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSEName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSNIC().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSBSSTitle().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(paySheet.getpSBSSAmount()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(paySheet.getpSATotalAmount()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(paySheet.getpSBankName().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(paySheet.getpSAccountNo()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<PaySheet> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
