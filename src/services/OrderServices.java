package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.Order;
import model.OrderMenu;
import util.dbConnect.DBConnection;
import util.query.OrderQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderServices {



    private ObservableList<Order> ordersData;

    public  ObservableList<Order> loadData(){
        OrderMenu orderMenuData ;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            ResultSet rsLoadOrder = conn.createStatement().executeQuery(OrderQueries.LOAD_ORDER_DATA_QUERY);

            while (rsLoadOrder.next()) {
                orderMenuData = orderMenuServices.loadSpecificData(UtilityMethod.addPrefix("OM",rsLoadOrder.getString(2)));

                ordersData.add(new Order(rsLoadOrder.getString(1), rsLoadOrder.getString(2), orderMenuData.getoMIName(), rsLoadOrder.getString(3), rsLoadOrder.getString(4), rsLoadOrder.getInt(5), rsLoadOrder.getString(6), rsLoadOrder.getString(7), rsLoadOrder.getString(8), rsLoadOrder.getString(9), rsLoadOrder.getString(10), rsLoadOrder.getString(11), rsLoadOrder.getString(12), rsLoadOrder.getString(13), rsLoadOrder.getString(14), rsLoadOrder.getString(15), rsLoadOrder.getString(16), rsLoadOrder.getString(17), orderMenuData.getoMIPrice()));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return ordersData;
    }
    public  ObservableList<Order> loadData(String  sortQuery) throws SQLException {
        OrderMenu orderMenuData ;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        PreparedStatement psLoadOrder = null;
        ResultSet rsLoadOrder = null;
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            psLoadOrder = conn.prepareStatement(OrderQueries.LOAD_ORDER_DATA_WITH_STATUS_QUERY);
            psLoadOrder.setString(1, sortQuery);
            rsLoadOrder = psLoadOrder.executeQuery();

            while (rsLoadOrder.next()) {
                orderMenuData = orderMenuServices.loadSpecificData(UtilityMethod.addPrefix("OM",rsLoadOrder.getString(2)));

                ordersData.add(new Order(rsLoadOrder.getString(1), rsLoadOrder.getString(2), orderMenuData.getoMIName(), rsLoadOrder.getString(3), rsLoadOrder.getString(4), rsLoadOrder.getInt(5), rsLoadOrder.getString(6), rsLoadOrder.getString(7), rsLoadOrder.getString(8), rsLoadOrder.getString(9), rsLoadOrder.getString(10), rsLoadOrder.getString(11), rsLoadOrder.getString(12), rsLoadOrder.getString(13), rsLoadOrder.getString(14), rsLoadOrder.getString(15), rsLoadOrder.getString(16), rsLoadOrder.getString(17), orderMenuData.getoMIPrice()));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        finally {
            psLoadOrder.close();
            rsLoadOrder.close();
        }
        return ordersData;
    }
    public  ObservableList<Order> loadData(String  processQuery, String notPayQuery) throws SQLException {
        OrderMenu orderMenuData ;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        PreparedStatement psLoadOrder = null;
        ResultSet rsLoadOrder = null;
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            psLoadOrder = conn.prepareStatement(OrderQueries.LOAD_ORDER_PENDING_QUERY);
            psLoadOrder.setString(1, processQuery);
            psLoadOrder.setString(2, notPayQuery);
            rsLoadOrder = psLoadOrder.executeQuery();

            while (rsLoadOrder.next()) {
                orderMenuData = orderMenuServices.loadSpecificData(UtilityMethod.addPrefix("OM",rsLoadOrder.getString(2)));

                ordersData.add(new Order(rsLoadOrder.getString(1), rsLoadOrder.getString(2), orderMenuData.getoMIName(), rsLoadOrder.getString(3), rsLoadOrder.getString(4), rsLoadOrder.getInt(5), rsLoadOrder.getString(6), rsLoadOrder.getString(7), rsLoadOrder.getString(8), rsLoadOrder.getString(9), rsLoadOrder.getString(10), rsLoadOrder.getString(11), rsLoadOrder.getString(12), rsLoadOrder.getString(13), rsLoadOrder.getString(14), rsLoadOrder.getString(15), rsLoadOrder.getString(16), rsLoadOrder.getString(17), orderMenuData.getoMIPrice()));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        finally {
            psLoadOrder.close();
            rsLoadOrder.close();
        }
        return ordersData;
    }
    public Order loadSpecificData(int oID){
        Order orderModelData = new Order();
        PreparedStatement psSpecificOrderData;
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            psSpecificOrderData = conn.prepareStatement(OrderQueries.LOAD_SPECIFIC_ORDER_DATA_QUERY);
            psSpecificOrderData.setInt(1, oID);
            ResultSet rsLoadOrder = psSpecificOrderData.executeQuery();

            while (rsLoadOrder.next()) {
                orderModelData.setoID(UtilityMethod.addPrefix("OR", rsLoadOrder.getString(1)));
                orderModelData.setoOMID(UtilityMethod.addPrefix("OMP",rsLoadOrder.getString(2)));
                orderModelData.setoType(rsLoadOrder.getString(3));
                orderModelData.setoDetails(rsLoadOrder.getString(4));
                orderModelData.setoQuantity(rsLoadOrder.getInt(5));
                orderModelData.setoDeliveryDate(rsLoadOrder.getString(6));
                orderModelData.setoDeliveryTime(rsLoadOrder.getString(7));
                orderModelData.setoCustomerName(rsLoadOrder.getString(8));
                orderModelData.setoCustomerNIC(rsLoadOrder.getString(9));
                orderModelData.setoCustomerPhone(rsLoadOrder.getString(10));
                orderModelData.setoTakenDate(rsLoadOrder.getString(11));
                orderModelData.setoTakenTime(rsLoadOrder.getString(12));
                orderModelData.setoTakenUID(rsLoadOrder.getString(13));
                orderModelData.setoStatus(rsLoadOrder.getString(14));
                orderModelData.setoDeliveredDate(rsLoadOrder.getString(15));
                orderModelData.setoDeliveredTime(rsLoadOrder.getString(16));
                orderModelData.setoProcessingStatus(rsLoadOrder.getString(17));

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }

        return orderModelData;
    }
    public boolean insertData(Order order) throws  Exception{
        PreparedStatement psOrder = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrder = conn.prepareStatement(OrderQueries.INSERT_ORDER_DATA_QUERY);

            psOrder.setInt(1, UtilityMethod.seperateID(order.getoOMID()));
            psOrder.setString(2,order.getoType());
            psOrder.setString(3, order.getoDetails());
            psOrder.setInt(4,order.getoQuantity());
            psOrder.setString(5,order.getoDeliveryDate());
            psOrder.setString(6, order.getoDeliveryTime());
            psOrder.setString(7,order.getoCustomerName());
            psOrder.setString(8,order.getoCustomerNIC());
            psOrder.setString(9, order.getoCustomerPhone());
            psOrder.setString(10,order.getoTakenDate());
            psOrder.setString(11,order.getoTakenTime());
            psOrder.setInt(12, UtilityMethod.seperateID(order.getoTakenUID()));
            psOrder.setString(13,order.getoStatus());
            psOrder.setString(14,order.getoProcessingStatus());
            psOrder.execute();
            AlertPopUp.insertSuccesfully("Order");
            resultval = true;

        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Order");
        }
        finally{
            psOrder.close();
        }
        return resultval;
    }

    public boolean updateOrderStatus(String id, String status) throws Exception {
        PreparedStatement psOrder = null;
        PreparedStatement psOrderCompleted = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrder = conn.prepareStatement(OrderQueries.UPDATE_ORDER_STATUS_QUERY);
            psOrderCompleted = conn.prepareStatement(OrderQueries.UPDATE_ORDER_COMPLETE_STATUS_QUERY);
            if(status.equals("Completed")){
                psOrderCompleted.setString(1, String.valueOf(LocalDate.now()));
                psOrderCompleted.setString(2, UtilityMethod.currentTime());
                psOrderCompleted.setString(3, status);
                psOrderCompleted.setInt(4, UtilityMethod.seperateID(id));
                psOrderCompleted.execute();
                resultVal = true;
            }else{
                psOrder.setString(1, status);
                psOrder.setInt(2, UtilityMethod.seperateID(id));
                psOrder.execute();
                resultVal = true;
            }


            AlertPopUp.updateSuccesfully("Order Process");
        } catch (SQLException ex) {

            AlertPopUp.updateFailed(ex, "Order Process ");

        } finally {
            psOrder.close();
        }
        return resultVal;
    }public boolean updateOrderPayStatus(String id, String status) throws Exception {
        PreparedStatement psOrder = null;
        PreparedStatement psOrderCompleted = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrder = conn.prepareStatement(OrderQueries.UPDATE_ORDER_PAY_STATUS_QUERY);

                psOrder.setString(1, status);
                psOrder.setInt(2, UtilityMethod.seperateID(id));
                psOrder.execute();
                resultVal = true;
            //AlertPopUp.updateSuccesfully("Order Process");
        } catch (SQLException ex) {

            AlertPopUp.updateFailed(ex, "Order Process ");

        } finally {
            psOrder.close();
        }
        return resultVal;
    }

    public boolean updateData(Order order) throws Exception {
        PreparedStatement psOrder = null;

        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psOrder = conn.prepareStatement(OrderQueries.UPDATE_ORDER_DATA_QUERY);
            psOrder.setInt(1, UtilityMethod.seperateID(order.getoOMID()));
            psOrder.setString(2,order.getoType());
            psOrder.setString(3, order.getoDetails());
            psOrder.setInt(4,order.getoQuantity());
            psOrder.setString(5,order.getoDeliveryDate());
            psOrder.setString(6, order.getoDeliveryTime());
            psOrder.setString(7,order.getoCustomerName());
            psOrder.setString(8,order.getoCustomerNIC());

            psOrder.setString(9, order.getoCustomerPhone());
            psOrder.setString(10,order.getoTakenDate());
            psOrder.setString(11,order.getoTakenTime());
            psOrder.setInt(12, UtilityMethod.seperateID(order.getoTakenUID()));

            psOrder.setString(13,order.getoStatus());
            psOrder.setString(14,order.getoProcessingStatus());
            psOrder.setInt(15, UtilityMethod.seperateID(order.getoID()));
            psOrder.execute();
            AlertPopUp.updateSuccesfully("Order");
            resultVal = true;
        } catch (SQLException ex) {

            AlertPopUp.updateFailed(ex, "Order");

        } finally {
            psOrder.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psOrder = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psOrder = conn.prepareStatement(OrderQueries.DELETE_ORDER_DATA_QUERY);
            psOrder.setInt(1, itemID);
            psOrder.executeUpdate();
            AlertPopUp.deleteSuccesfull("Order");
            resultVal = true;

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Order");
        }finally{
            psOrder.close();
        }
        return resultVal;
    }
    public SortedList<Order> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<Order> ordersData = loadData();
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Order> filteredData = new FilteredList<>(ordersData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(order -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(order.getoID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerNIC().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerPhone().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }
    public SortedList<Order> searchTable(TextField searchTextField, String  processQuery, String notPayQuery) throws SQLException {
        //Retreiving all data from database
        OrderMenu orderMenuData ;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        PreparedStatement psLoadOrder = null;
        ResultSet rsLoadOrder = null;
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            psLoadOrder = conn.prepareStatement(OrderQueries.LOAD_ORDER_PENDING_QUERY);
            psLoadOrder.setString(1, processQuery);
            psLoadOrder.setString(2, notPayQuery);
            rsLoadOrder = psLoadOrder.executeQuery();

            while (rsLoadOrder.next()) {
                orderMenuData = orderMenuServices.loadSpecificData(UtilityMethod.addPrefix("OM",rsLoadOrder.getString(2)));

                ordersData.add(new Order(rsLoadOrder.getString(1), rsLoadOrder.getString(2), orderMenuData.getoMIName(), rsLoadOrder.getString(3), rsLoadOrder.getString(4), rsLoadOrder.getInt(5), rsLoadOrder.getString(6), rsLoadOrder.getString(7), rsLoadOrder.getString(8), rsLoadOrder.getString(9), rsLoadOrder.getString(10), rsLoadOrder.getString(11), rsLoadOrder.getString(12), rsLoadOrder.getString(13), rsLoadOrder.getString(14), rsLoadOrder.getString(15), rsLoadOrder.getString(16), rsLoadOrder.getString(17), orderMenuData.getoMIPrice()));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        finally {
            psLoadOrder.close();
            rsLoadOrder.close();
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Order> filteredData = new FilteredList<>(ordersData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(order -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(order.getoID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerNIC().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerPhone().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }
    public SortedList<Order> searchTable(TextField searchTextField, String sortQuery) throws SQLException {
        //Retreiving all data from database
        OrderMenu orderMenuData ;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        PreparedStatement psLoadOrder = null;
        ResultSet rsLoadOrder = null;
        try {
            Connection conn = DBConnection.Connect();
            ordersData = FXCollections.observableArrayList();
            psLoadOrder = conn.prepareStatement(OrderQueries.LOAD_ORDER_DATA_WITH_STATUS_QUERY);
            psLoadOrder.setString(1, sortQuery);
            rsLoadOrder = psLoadOrder.executeQuery();

            while (rsLoadOrder.next()) {
                orderMenuData = orderMenuServices.loadSpecificData(UtilityMethod.addPrefix("OM",rsLoadOrder.getString(2)));

                ordersData.add(new Order(rsLoadOrder.getString(1), rsLoadOrder.getString(2), orderMenuData.getoMIName(), rsLoadOrder.getString(3), rsLoadOrder.getString(4), rsLoadOrder.getInt(5), rsLoadOrder.getString(6), rsLoadOrder.getString(7), rsLoadOrder.getString(8), rsLoadOrder.getString(9), rsLoadOrder.getString(10), rsLoadOrder.getString(11), rsLoadOrder.getString(12), rsLoadOrder.getString(13), rsLoadOrder.getString(14), rsLoadOrder.getString(15), rsLoadOrder.getString(16), rsLoadOrder.getString(17), orderMenuData.getoMIPrice()));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        finally {
            psLoadOrder.close();
            rsLoadOrder.close();
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Order> filteredData = new FilteredList<>(ordersData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(order -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(order.getoID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerNIC().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(order.getoCustomerPhone().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
