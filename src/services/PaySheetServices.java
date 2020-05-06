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
    public Integer insertData(ObservableList<PaySheet> paySheetObservableList){
        Boolean resultVal = false;
        int resultID = 0;
        Connection connection = DBConnection.Connect();
        PreparedStatement psInsertPaySheet = null;
        PreparedStatement psSpecificPaysheet = null;
        PreparedStatement psUpdatePaysheet = null;

        ResultSet rsSpecificPaysheet = null;
        ResultSet rsInsertedPaysheet = null;

        try{
            psInsertPaySheet = connection.prepareStatement(PaySheetQueries.INSERT_PAYSHEET_DATA_QUERY);
            psSpecificPaysheet = connection.prepareStatement(PaySheetQueries.LOAD_SPECIFIC_EID_PAYSHEET_DATA_QUERY);
            psUpdatePaysheet = connection.prepareStatement(PaySheetQueries.UPDATE_PAYSHEET_DATA_QUERY);
            for(PaySheet paySheet :paySheetObservableList){
                psSpecificPaysheet.setInt(1, UtilityMethod.seperateID(paySheet.getpSEID()));
                psSpecificPaysheet.setString(2,UtilityMethod.getYear(paySheet.getpSDate()) + "-"+UtilityMethod.getMonthNumber(UtilityMethod.getMonth(paySheet.getpSDate())));
                //psSpecificPaysheet.setInt(3, UtilityMethod.getYear(paySheet.getpSDate()));
                rsSpecificPaysheet = psSpecificPaysheet.executeQuery();
                while(rsSpecificPaysheet.next()){
                    //set true if records available
                    resultVal = true;
                    resultID = rsSpecificPaysheet.getInt(1);
                }
                if(!resultVal){
                    psInsertPaySheet.setInt(1, UtilityMethod.seperateID(paySheet.getpSEID()));
                    psInsertPaySheet.setString(2, paySheet.getpSEName());
                    psInsertPaySheet.setString(3, paySheet.getpSNIC());
                    psInsertPaySheet.setString(4, paySheet.getpSBSSTitle());
                    psInsertPaySheet.setDouble(5, paySheet.getpSBSSAmount());
                    psInsertPaySheet.setDouble(6, paySheet.getpSATotalAmount());
                    psInsertPaySheet.setString(7, paySheet.getpSBankName());
                    psInsertPaySheet.setLong(8, paySheet.getpSAccountNo());
                    psInsertPaySheet.setString(9, paySheet.getpSDate());
                    psInsertPaySheet.execute();
                    //getting last inserted value
                    rsInsertedPaysheet = connection.createStatement().executeQuery(PaySheetQueries.GET_LAST_INSERTED_RECORD_ID);
                    while(rsInsertedPaysheet.next()){
                        resultID = rsInsertedPaysheet.getInt(1);
                    }
                }else{
                    psUpdatePaysheet.setInt(1, UtilityMethod.seperateID(paySheet.getpSEID()));
                    psUpdatePaysheet.setString(2, paySheet.getpSEName());
                    psUpdatePaysheet.setString(3, paySheet.getpSNIC());
                    psUpdatePaysheet.setString(4, paySheet.getpSBSSTitle());
                    psUpdatePaysheet.setDouble(5, paySheet.getpSBSSAmount());
                    psUpdatePaysheet.setDouble(6, paySheet.getpSATotalAmount());
                    psUpdatePaysheet.setString(7, paySheet.getpSBankName());
                    psUpdatePaysheet.setLong(8, paySheet.getpSAccountNo());
                    psUpdatePaysheet.setString(9, paySheet.getpSDate());
                    psUpdatePaysheet.setInt(10, resultID);
                    psUpdatePaysheet.execute();

                }

            }
        }catch (SQLException ex){
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{

                psUpdatePaysheet.close();
                //rsInsertedPaysheet.close();
                psInsertPaySheet.close();
                psSpecificPaysheet.close();
                rsSpecificPaysheet.close();
                connection.close();
            }catch (SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultID;
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
