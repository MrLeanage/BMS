package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Supplier;
import util.dbConnect.DBConnection;
import util.playAudio.Audio;
import util.query.SupplierQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import view.InventoryManagement.SupplierViewController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    private DBConnection dbcon;
    private static PreparedStatement ps;


    Audio play = new Audio();

    SupplierViewController supplierViewController = new SupplierViewController();
    private ObservableList<Supplier> supplierData;
    private ObservableList<String> choiceboxList = FXCollections.observableArrayList("Stock Items","Agency");

    /**
     * Initializes the controller class.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbcon = new DBConnection();
        loadData();

    }
    public  ObservableList<Supplier> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            supplierData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(SupplierQueries.LOAD_DATA_QUERY);

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return supplierData;
    }

    public boolean insertData(Supplier supplier) throws  Exception{
        ps = null;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            ps = conn.prepareStatement(SupplierQueries.INSERT_DATA_QUERY);
            ps.setString(1,supplier.getsIName());
            ps.setString(2,supplier.getsIAddress());
            ps.setInt(3, supplier.getsIPhone1());
            ps.setInt(4, supplier.getsIPhone2());
            ps.setString(5, supplier.getsIEmail());
            ps.setString(6, supplier.getsIType());
            ps.setString(7, supplier.getsIBank());
            ps.setLong(8, supplier.getsIAccNo());

            ps.execute();
            AlertPopUp.insertSuccesfully("Supplier Information");
            //supplierViewController.refreshTable();
            resultval = true;


        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Supplier Information");
        }
        finally{
            ps.close();
        }
        return resultval;
    }

    public boolean updateData(Supplier supplier) throws Exception {
        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            ps = conn.prepareStatement(SupplierQueries.UPDATE_DATA_QUERY);
            ps.setString(1,supplier.getsIName());
            ps.setString(2,supplier.getsIAddress());
            ps.setInt(3, supplier.getsIPhone1());
            ps.setInt(4, supplier.getsIPhone2());
            ps.setString(5, supplier.getsIEmail());
            ps.setString(6, supplier.getsIType());
            ps.setString(7, supplier.getsIBank());
            ps.setLong(8, supplier.getsIAccNo());
            ps.setInt(9,UtilityMethod.seperateID(supplier.getsIID()));

            ps.execute();
            AlertPopUp.updateSuccesfully("Supplier Information");
                resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Supplier Information");

        } finally {
            ps.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int ID) throws SQLException {
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            ps = conn.prepareStatement(SupplierQueries.DELETE_DATA_QUERY);
            ps.setInt(1, ID);

            ps.executeUpdate();
            AlertPopUp.deleteSuccesfull("Supplier Information");
            resultVal = true;

        }catch (Exception ex) {
            AlertPopUp.deleteFailed(ex, "Supplier Information");
        }finally{
            ps.close();
        }
        return resultVal;
    }

    public SortedList<Supplier> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<Supplier> supplierData = null;
        try {
            Connection conn = DBConnection.Connect();
            supplierData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(SupplierQueries.SEARCH_DATA_QUERY);

            while (rs.next()) {
                supplierData.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getLong(9)));
                //System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Supplier> filteredData = new FilteredList<>(supplierData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(supplier -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(supplier.getsIID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIAddress().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone1()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(supplier.getsIPhone2()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIEmail().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(supplier.getsIType().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Supplier> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
