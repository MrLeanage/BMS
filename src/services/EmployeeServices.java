package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Employee;
import util.dbConnect.DBConnection;
import util.query.EmployeeQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeServices {



    private ObservableList<Employee> employeesData;

    public  ObservableList<Employee> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            employeesData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(EmployeeQueries.LOAD_EMPLOYEE_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                employeesData.add(new Employee(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getString(5), rsLoadBakeryProduct.getString(6), rsLoadBakeryProduct.getString(7), rsLoadBakeryProduct.getInt(8), rsLoadBakeryProduct.getString(9), rsLoadBakeryProduct.getInt(10), rsLoadBakeryProduct.getString(11)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return employeesData;
    }

    public boolean insertData(Employee employee) throws  Exception{
        PreparedStatement psEmployee = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psEmployee = conn.prepareStatement(EmployeeQueries.INSERT_EMPLOYEE_DATA_QUERY);

            psEmployee.setString(1,employee.geteName());
            psEmployee.setString(2, employee.geteGender());
            psEmployee.setString(3,employee.geteNIC());
            psEmployee.setString(4, employee.geteAddress());
            psEmployee.setString(5, employee.geteDOB());
            psEmployee.setString(6, employee.geteTitle());
            psEmployee.setInt(7, employee.getePhone());
            psEmployee.setString(8, employee.geteBankName());
            psEmployee.setInt(9, employee.geteAccNo());
            psEmployee.setInt(10, UtilityMethod.seperateID(employee.geteBSSID()));
            psEmployee.execute();
            AlertPopUp.insertSuccesfully("Employee Info");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Employee Info");
        }
        finally{
            psEmployee.close();

        }
        return resultval;
    }

    public boolean updateData(Employee employee) throws Exception {
        PreparedStatement psEmployee = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psEmployee = conn.prepareStatement(EmployeeQueries.UPDATE_EMPLOYEE_DATA_QUERY);
            psEmployee.setString(1,employee.geteName());
            psEmployee.setString(2, employee.geteGender());
            psEmployee.setString(3,employee.geteNIC());
            psEmployee.setString(4, employee.geteAddress());
            psEmployee.setString(5, employee.geteDOB());
            psEmployee.setString(6, employee.geteTitle());
            psEmployee.setInt(7, employee.getePhone());
            psEmployee.setString(8, employee.geteBankName());
            psEmployee.setInt(9, employee.geteAccNo());
            psEmployee.setInt(10, UtilityMethod.seperateID(employee.geteBSSID()));
            psEmployee.setInt(11, UtilityMethod.seperateID(employee.geteID()));
            psEmployee.execute();
            AlertPopUp.updateSuccesfully("Employee Info");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Employee Info");

        } finally {
            psEmployee.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psEmployee = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psEmployee = conn.prepareStatement(EmployeeQueries.DELETE_EMPLOYEE_DATA_QUERY);
            psEmployee.setInt(1, itemID);
            psEmployee.executeUpdate();
            AlertPopUp.deleteSuccesfull("Employee Info");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Employee Info");
        }finally{
            psEmployee.close();
        }
        return resultVal;
    }

    public SortedList<Employee> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<Employee> employeesData = null;

        try {
            Connection conn = DBConnection.Connect();
            employeesData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(EmployeeQueries.LOAD_EMPLOYEE_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                employeesData.add(new Employee(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getString(5), rsLoadBakeryProduct.getString(6), rsLoadBakeryProduct.getString(7), rsLoadBakeryProduct.getInt(8), rsLoadBakeryProduct.getString(9), rsLoadBakeryProduct.getInt(10), rsLoadBakeryProduct.getString(11)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Employee> filteredData = new FilteredList<>(employeesData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(employee -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(employee.geteID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteNIC().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteAddress().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteGender().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteDOB().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteTitle().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteBankName().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(employee.geteAccNo()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(employee.geteBSSID().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
