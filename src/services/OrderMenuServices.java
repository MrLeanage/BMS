package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.OrderMenu;
import util.dbConnect.DBConnection;
import util.query.OrderMenuQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.io.*;
import java.sql.*;

public class OrderMenuServices {



    private ObservableList<OrderMenu> orderMenuData;

    public  ObservableList<OrderMenu> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            orderMenuData = FXCollections.observableArrayList();
            ResultSet rsLoadOrderMenuProduct = conn.createStatement().executeQuery(OrderMenuQueries.LOAD_ORDER_MENU_DATA_QUERY);

            while (rsLoadOrderMenuProduct.next()) {
               InputStream inputStream = rsLoadOrderMenuProduct.getBinaryStream(2);
                orderMenuData.add(new OrderMenu(rsLoadOrderMenuProduct.getString(1), UtilityMethod.convertInputStreamToImage(inputStream), rsLoadOrderMenuProduct.getString(3), rsLoadOrderMenuProduct.getString(4), rsLoadOrderMenuProduct.getString(5), rsLoadOrderMenuProduct.getFloat(6), rsLoadOrderMenuProduct.getString(7)));
            }
        } catch (SQLException | FileNotFoundException ex) {
            AlertPopUp.sqlQueryError(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderMenuData;
    }
    public  OrderMenu loadSpecificData(String id){
        PreparedStatement psLoadOrderMenuProduct;
        ResultSet rsLoadOrderMenuProduct;
        OrderMenu orderMenuModelData = new OrderMenu();
        try {
            Connection conn = DBConnection.Connect();
            psLoadOrderMenuProduct = conn.prepareStatement(OrderMenuQueries.LOAD_SPECIFIC_ORDER_MENU_DATA_QUERY);

            psLoadOrderMenuProduct.setInt(1, UtilityMethod.seperateID(id));
            rsLoadOrderMenuProduct = psLoadOrderMenuProduct.executeQuery();
            while (rsLoadOrderMenuProduct.next()) {
                orderMenuModelData.setoMIID(rsLoadOrderMenuProduct.getString(1));
                InputStream inputStream = rsLoadOrderMenuProduct.getBinaryStream(2);
                orderMenuModelData.setoMIImage(UtilityMethod.convertInputStreamToImage(inputStream));
                orderMenuModelData.setoMIName(rsLoadOrderMenuProduct.getString(3));
                orderMenuModelData.setoMIDescription(rsLoadOrderMenuProduct.getString(4));
                orderMenuModelData.setoMIWeight(rsLoadOrderMenuProduct.getString(5));
                orderMenuModelData.setoMIPrice(rsLoadOrderMenuProduct.getFloat(6));
                orderMenuModelData.setoMIStatus(rsLoadOrderMenuProduct.getString(7));
            }
        } catch (SQLException | FileNotFoundException ex) {
            AlertPopUp.sqlQueryError(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderMenuModelData;
    }
    public boolean insertData(OrderMenu orderMenu) throws  Exception{
        PreparedStatement psOrderMenu = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrderMenu = conn.prepareStatement(OrderMenuQueries.INSERT_ORDER_MENU_DATA_QUERY);

                psOrderMenu.setBinaryStream(1, UtilityMethod.convertImageToInputStream(orderMenu.getoMIImage()));
            psOrderMenu.setString(2,orderMenu.getoMIName());
            psOrderMenu.setString(3, orderMenu.getoMIDescription());
            psOrderMenu.setString(4, orderMenu.getoMIWeight());
            psOrderMenu.setFloat(5, orderMenu.getoMIPrice());
            psOrderMenu.setString(6, orderMenu.getoMIStatus());
            psOrderMenu.execute();
            AlertPopUp.insertSuccesfully("Order Menu SalesItem");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Order Menu SalesItem");
        }
        finally{
            psOrderMenu.close();

        }
        return resultval;
    }

    public boolean updateData(OrderMenu orderMenu) throws Exception {
        PreparedStatement psOrderMenu = null;
        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrderMenu = conn.prepareStatement(OrderMenuQueries.UPDATE_ORDER_MENU_DATA_QUERY);
            psOrderMenu.setBinaryStream(1, UtilityMethod.convertImageToInputStream(orderMenu.getoMIImage()));
            psOrderMenu.setString(2,orderMenu.getoMIName());
            psOrderMenu.setString(3, orderMenu.getoMIDescription());
            psOrderMenu.setString(4, orderMenu.getoMIWeight());
            psOrderMenu.setFloat(5, orderMenu.getoMIPrice());
            psOrderMenu.setString(6, orderMenu.getoMIStatus());
            psOrderMenu.setInt(7, UtilityMethod.seperateID(orderMenu.getoMIID()));
            psOrderMenu.execute();
            AlertPopUp.updateSuccesfully("Order Menu SalesItem");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Order Menu SalesItem");

        } finally {
            psOrderMenu.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psOrderMenu = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psOrderMenu = conn.prepareStatement(OrderMenuQueries.DELETE_ORDER_MENU_DATA_QUERY);
            psOrderMenu.setInt(1, itemID);
            psOrderMenu.executeUpdate();
            AlertPopUp.deleteSuccesfull("Order Menu SalesItem");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Order Menu SalesItem");
        }finally{
            psOrderMenu.close();
        }
        return resultVal;
    }

    public SortedList<OrderMenu> searchTable(TextField searchTextField){
        //Retreiving all data from database

        try {
            Connection conn = DBConnection.Connect();
            orderMenuData = FXCollections.observableArrayList();
            File file;
            ResultSet rsLoadOrderMenuProduct = conn.createStatement().executeQuery(OrderMenuQueries.LOAD_ORDER_MENU_DATA_QUERY);

            while (rsLoadOrderMenuProduct.next()) {
                InputStream inputStream = rsLoadOrderMenuProduct.getBinaryStream(2);
                orderMenuData.add(new OrderMenu(rsLoadOrderMenuProduct.getString(1), UtilityMethod.convertInputStreamToImage(inputStream), rsLoadOrderMenuProduct.getString(3), rsLoadOrderMenuProduct.getString(4), rsLoadOrderMenuProduct.getString(5), rsLoadOrderMenuProduct.getFloat(6), rsLoadOrderMenuProduct.getString(7)));
            }
        } catch (SQLException | FileNotFoundException ex) {
            AlertPopUp.sqlQueryError(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<OrderMenu> filteredData = new FilteredList<>(orderMenuData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(orderMenu -> {
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }else if(orderMenu.getoMIID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(orderMenu.getoMIName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(orderMenu.getoMIDescription().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(orderMenu.getoMIWeight()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(orderMenu.getoMIPrice()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(orderMenu.getoMIStatus().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<OrderMenu> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
