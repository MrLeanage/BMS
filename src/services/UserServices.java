package services;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.User;
import util.dbConnect.DBConnection;
import util.query.UserQueries;
import util.userAlerts.AlertPopUp;
import util.utility.DataEncryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServices {



    private ObservableList<User> usersData;

    public  ObservableList<User> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            usersData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(UserQueries.LOAD_USER_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                usersData.add(new User(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getString(5)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Generating Admin Account if No Admin Account Exist
        if(usersData.size() == 0){
            User user = new User();
            user.setuID("administrator");
            user.setuName("administrator");
            user.setuType("Admin");
            user.setuPassword("admin");
            user.setuStatus("Active");
            Boolean resultVal = insertData(user);
            if(resultVal){
                loadData();
            }
        }
        return usersData;
    }
    public User userValidation(String uID,  String password) {
        User user = new User();
        user.setuID("Empty");
        PreparedStatement psUser = null;
        ResultSet rsLoadUser = null;
        try {
            Connection conn = DBConnection.Connect();
            psUser = conn.prepareStatement(UserQueries.VALIDATE_USER_QUERY);
            psUser.setString(1, uID);
            psUser.setString(2, DataEncryption.passwordEncryption(password));
            rsLoadUser = psUser.executeQuery();
            while (rsLoadUser.next()) {
                if(rsLoadUser.getString(1) != null){
                    user.setuID(rsLoadUser.getString((1)));
                    user.setuName(rsLoadUser.getString((2)));
                    user.setuType(rsLoadUser.getString((4)));
                    user.setuStatus(rsLoadUser.getString(5));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rsLoadUser.close();
                psUser.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return user;
    }
    public User DataNotExist(String uID){
        User user = new User();
        user.setuID("Empty");
        PreparedStatement psLoadUser = null;
        ResultSet rsLoadUser = null;
        try {
            Connection conn = DBConnection.Connect();
            psLoadUser = conn.prepareStatement(UserQueries.LOAD_SPECIFIC_EXISTING_USER_DATA_QUERY);
            psLoadUser.setString(1, uID);
            rsLoadUser = psLoadUser.executeQuery();
            while (rsLoadUser.next()) {
                if(rsLoadUser.getString(1) != null){
                    user.setuID(rsLoadUser.getString(1));
                }
                //userModelData.setuID(rsLoadUser.getString(1));
            }
        } catch (SQLException | NullPointerException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadUser.close();
                psLoadUser.close();
            }catch (SQLException ex){
                AlertPopUp.generalError(ex);
            }
        }
        System.out.println(user.getuID());
        return user;
    }
    public Boolean insertData(User user) {
        PreparedStatement psUser = null;
        Boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psUser = conn.prepareStatement(UserQueries.INSERT_USER_DATA_QUERY);

            psUser.setString(1,user.getuID());
            psUser.setString(2,user.getuName());
            psUser.setString(3, user.getuPassword());
            psUser.setString(4, user.getuType());
            psUser.setString(5, user.getuStatus());
            psUser.execute();
            AlertPopUp.insertSuccesfully("User Record");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "User Record");
        }
        finally{
            try{
                psUser.close();
            }catch(Exception ex){
                AlertPopUp.generalError(ex);
            }

        }
        return resultval;
    }

    public Boolean updateData(User user) throws Exception {
        PreparedStatement psUser = null;

        Boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psUser = conn.prepareStatement(UserQueries.UPDATE_USER_DATA_QUERY);
            psUser.setString(1,user.getuName());
            psUser.setString(2,user.getuPassword());
            psUser.setString(3, user.getuType());
            psUser.setString(4, user.getuStatus());
            psUser.setString(5, user.getuID());
            psUser.execute();
            AlertPopUp.updateSuccesfully("User Record");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "User Record");

        } finally {
            psUser.close();
        }
        return resultVal;
    }
    public Boolean deleteData(String uID) throws SQLException {
        PreparedStatement psUser = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psUser = conn.prepareStatement(UserQueries.DELETE_USER_DATA_QUERY);
            psUser.setString(1, uID);
            psUser.executeUpdate();
            AlertPopUp.deleteSuccesfull("User Record");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "User Record");
        }finally{
            psUser.close();
        }
        return resultVal;
    }

    public SortedList<User> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<User> usersData = null;

        try {
            Connection conn = DBConnection.Connect();
            usersData = FXCollections.observableArrayList();
            ResultSet rsLoadBakeryProduct = conn.createStatement().executeQuery(UserQueries.LOAD_USER_DATA_QUERY);

            while (rsLoadBakeryProduct.next()) {
                usersData.add(new User(rsLoadBakeryProduct.getString(1), rsLoadBakeryProduct.getString(2), rsLoadBakeryProduct.getString(3), rsLoadBakeryProduct.getString(4), rsLoadBakeryProduct.getString(5)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<User> filteredData = new FilteredList<>(usersData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(user -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(user.getuID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(user.getuName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(user.getuType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(user.getuStatus().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<User> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
