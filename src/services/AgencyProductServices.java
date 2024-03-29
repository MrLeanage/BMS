package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.AgencyProduct;
import util.dbConnect.DBConnection;
import util.query.AgencyProductQueries;
import util.query.PurchaseProductQueries;
import util.query.SupplierQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AgencyProductServices {
    private DBConnection dbcon;
    private ObservableList<AgencyProduct> agencyProductsData;


    public  ObservableList<AgencyProduct> loadData(){
        try {
            Connection conn = DBConnection.Connect();
            agencyProductsData = FXCollections.observableArrayList();
            ResultSet rsLoadAgencyProduct = conn.createStatement().executeQuery(AgencyProductQueries.LOAD_AGENCY_PRODUCT_DATA_QUERY);

            while (rsLoadAgencyProduct.next()) {
                try{
                    ResultSet rsFindPurchaseData = conn.createStatement().executeQuery(PurchaseProductQueries.FIND_PURCHASE_AGENCY_DATA_QUERRY + rsLoadAgencyProduct.getString(1));
                    while(rsFindPurchaseData.next()){
                        if(!(rsFindPurchaseData.getString(1).isEmpty() || rsFindPurchaseData.getString(1).equals(null))){
                            try{
                                ResultSet rsFindSupplierData = conn.createStatement().executeQuery(SupplierQueries.FIND_SUPPLIER_DATA_QUERY + rsFindPurchaseData.getString(3));
                                while (rsFindSupplierData.next()){
                                    if(!(rsFindSupplierData.getString(1).isEmpty() || rsFindSupplierData.getString(1).equals(null))){

                                        agencyProductsData.add(new AgencyProduct(rsLoadAgencyProduct.getString(1), rsLoadAgencyProduct.getString(2),rsFindSupplierData.getString(1), rsFindSupplierData.getString(2),rsLoadAgencyProduct.getInt(3),rsLoadAgencyProduct.getString(4),rsLoadAgencyProduct.getFloat(5),rsLoadAgencyProduct.getFloat(6), rsLoadAgencyProduct.getFloat(7),rsLoadAgencyProduct.getString(8),rsLoadAgencyProduct.getString(9),rsLoadAgencyProduct.getString(10),rsLoadAgencyProduct.getString(11)));

                                    }else{
                                        AlertPopUp.sqlRecordNotFound("Supplier");
                                    }
                                }
                            }catch(SQLException ex){
                                AlertPopUp.sqlQueryError(ex);
                            }
                        }else{
                            AlertPopUp.sqlRecordNotFound("Purchase");
                        }
                    }
                }catch (SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return agencyProductsData;
    }
    public  AgencyProduct loadSpecificData(String id){
        AgencyProduct agencyProduct = new AgencyProduct();
        try {
            Connection conn = DBConnection.Connect();
            agencyProductsData = FXCollections.observableArrayList();
            PreparedStatement psLoadAgencyProduct = conn.prepareStatement(AgencyProductQueries.LOAD_SPECIFIC_AGENCY_PRODUCT_DATA_QUERY);
            psLoadAgencyProduct.setInt(1, UtilityMethod.seperateID(id));
            ResultSet rsLoadAgencyProduct = psLoadAgencyProduct.executeQuery();


            while (rsLoadAgencyProduct.next()) {
                try{
                    ResultSet rsFindPurchaseData = conn.createStatement().executeQuery(PurchaseProductQueries.FIND_PURCHASE_AGENCY_DATA_QUERRY + rsLoadAgencyProduct.getString(1));
                    while(rsFindPurchaseData.next()){
                        if(!(rsFindPurchaseData.getString(1).isEmpty() || rsFindPurchaseData.getString(1).equals(null))){
                            try{
                                ResultSet rsFindSupplierData = conn.createStatement().executeQuery(SupplierQueries.FIND_SUPPLIER_DATA_QUERY + rsFindPurchaseData.getString(3));
                                while (rsFindSupplierData.next()){
                                    if(!(rsFindSupplierData.getString(1).isEmpty() || rsFindSupplierData.getString(1).equals(null))){

                                        agencyProduct.setaPID(rsLoadAgencyProduct.getString(1));
                                        agencyProduct.setaPName(rsLoadAgencyProduct.getString(2));
                                        agencyProduct.setaPSupplierID(rsFindSupplierData.getString(1));
                                        agencyProduct.setaPSupplierName(rsFindSupplierData.getString(2));
                                        agencyProduct.setaPTotalUnits(rsLoadAgencyProduct.getInt(3));
                                        agencyProduct.setaPWeightOfUnit(rsLoadAgencyProduct.getString(4));
                                        agencyProduct.setaPBuyingPricePerUnit(rsLoadAgencyProduct.getFloat(5));
                                        agencyProduct.setaPMarketPricePerUnit(rsLoadAgencyProduct.getFloat(6));
                                        agencyProduct.setaPSellingPricePerUnit(rsLoadAgencyProduct.getFloat(7));
                                        agencyProduct.setaPMDate(rsLoadAgencyProduct.getString(8));
                                        agencyProduct.setaPEDate(rsLoadAgencyProduct.getString(9));
                                        agencyProduct.setaPADate(rsLoadAgencyProduct.getString(10));
                                        agencyProduct.setaPDADate(rsLoadAgencyProduct.getString(11));
                                    }else{
                                        AlertPopUp.sqlRecordNotFound("Supplier");
                                    }
                                }
                            }catch(SQLException ex){
                                AlertPopUp.sqlQueryError(ex);
                            }
                        }else{
                            AlertPopUp.sqlRecordNotFound("Purchase");
                        }
                    }
                }catch (SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        return agencyProduct;
    }

    public boolean insertData(AgencyProduct agencyProduct) throws  Exception{
        PreparedStatement psAgencyProduct = null, psPurchase = null;
        boolean status = false;
        boolean resultval = false;
        try {
            Connection conn = DBConnection.Connect();
            psAgencyProduct = conn.prepareStatement(AgencyProductQueries.INSERT_AGENCY_PRODUCT_DATA_QUERY);

            psAgencyProduct.setString(1,agencyProduct.getaPName());
            psAgencyProduct.setInt(2,agencyProduct.getaPTotalUnits());
            psAgencyProduct.setString(3, agencyProduct.getaPWeightOfUnit());
            psAgencyProduct.setFloat(4, agencyProduct.getaPBuyingPricePerUnit());
            psAgencyProduct.setFloat(5, agencyProduct.getaPMarketPricePerUnit());
            psAgencyProduct.setFloat(6, agencyProduct.getaPSellingPricePerUnit());
            psAgencyProduct.setString(7, agencyProduct.getaPMDate());
            psAgencyProduct.setString(8, agencyProduct.getaPEDate());
            psAgencyProduct.setString(9, agencyProduct.getaPADate());
            psAgencyProduct.setString(10, agencyProduct.getaPDADate());
            psAgencyProduct.execute();
            status = true;
            if(status){
                try{
                    ResultSet rsLastAgencyProduct = conn.createStatement().executeQuery(AgencyProductQueries.GET_LAST_ID_DATA_QUERY);
                    while (rsLastAgencyProduct.next()){
                        psPurchase = conn.prepareStatement(PurchaseProductQueries.INSERT_PURCHASE_DATA_QUERY);
                        psPurchase.setInt(1, rsLastAgencyProduct.getInt(1));
                        psPurchase.setInt(2, UtilityMethod.seperateID(agencyProduct.getaPSupplierID()));
                        psPurchase.setString(3, "Agency");
                        psPurchase.setString(4, String.valueOf(LocalDate.now()));
                        psPurchase.setString(5,"Pending");
                        psPurchase.execute();
                        AlertPopUp.insertSuccesfully("Agency SalesItem");
                        resultval = true;
                    }
                }catch(SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.insertionFailed(ex, "Agency SalesItem");
        }
        finally{
            psAgencyProduct.close();
            psPurchase.close();

        }
        return resultval;
    }

    public boolean updateData(AgencyProduct agencyProduct) throws Exception {
        PreparedStatement psAgencyProduct = null, psPurchase = null;
        boolean status = false;
        boolean resultVal = false;
        try {
            Connection conn = DBConnection.Connect();
            psAgencyProduct = conn.prepareStatement(AgencyProductQueries.UPDATE_AGENCY_PRODUCT_DATA_QUERY);
            psAgencyProduct.setString(1,agencyProduct.getaPName());
            psAgencyProduct.setInt(2,agencyProduct.getaPTotalUnits());
            psAgencyProduct.setString(3, agencyProduct.getaPWeightOfUnit());
            psAgencyProduct.setFloat(4, agencyProduct.getaPBuyingPricePerUnit());
            psAgencyProduct.setFloat(5, agencyProduct.getaPMarketPricePerUnit());
            psAgencyProduct.setFloat(6, agencyProduct.getaPSellingPricePerUnit());
            psAgencyProduct.setString(7, agencyProduct.getaPMDate());
            psAgencyProduct.setString(8, agencyProduct.getaPEDate());
            psAgencyProduct.setString(9,agencyProduct.getaPDADate());
            psAgencyProduct.setInt(10,UtilityMethod.seperateID(agencyProduct.getaPID()));
            psAgencyProduct.execute();
            status = true;
            if(status){
                try{
                        psPurchase = conn.prepareStatement(PurchaseProductQueries.UPDATE_PURCHASE_AGENCY_DATA_QUERRY);
                        psPurchase.setInt(1, UtilityMethod.seperateID(agencyProduct.getaPSupplierID()));
                        psPurchase.setString(2,String.valueOf(LocalDate.now()));
                        psPurchase.setInt(3, UtilityMethod.seperateID(agencyProduct.getaPID()));
                        psPurchase.execute();
                        AlertPopUp.updateSuccesfully("Agency SalesItem");
                        resultVal = true;

                }catch(SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }
            }

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Agency SalesItem");

        } finally {
            psAgencyProduct.close();
        }
        return resultVal;
    }
    public Boolean deleteData(int itemID ) throws SQLException {
        PreparedStatement psAgencyProduct = null, psPurchase = null;
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        try{
            psAgencyProduct = conn.prepareStatement(AgencyProductQueries.DELETE_AGENCY_PRODUCT_DATA_QUERY);
            psAgencyProduct.setInt(1, itemID);
            psAgencyProduct.executeUpdate();
            try{
                psPurchase = conn.prepareStatement(PurchaseProductQueries.DELETE_PURCHASE_AGENCY_DATA_QUERRY);
                psPurchase.setInt(1, itemID);
                psPurchase.executeUpdate();
                AlertPopUp.deleteSuccesfull("Agency SalesItem");
                resultVal = true;
            }catch(SQLException ex){
                AlertPopUp.deleteFailed(ex, "Agency SalesItem");
            }

        }catch (SQLException ex) {
            AlertPopUp.deleteFailed(ex, "Agency SalesItem");
        }finally{
            psAgencyProduct.close();
            psPurchase.close();
        }
        return resultVal;
    }

    public SortedList<AgencyProduct> searchTable(TextField searchTextField){
        //Retreiving all data from database
        ObservableList<AgencyProduct> agencyProductData = null;

        try {
            Connection conn = DBConnection.Connect();
            agencyProductData = FXCollections.observableArrayList();
            ResultSet rsLoadAgencyProduct = conn.createStatement().executeQuery(AgencyProductQueries.SEARCH_AGENCY_PRODUCT_DATA_QUERY);

            while (rsLoadAgencyProduct.next()) {
                try{
                    ResultSet rsFindPurchaseData = conn.createStatement().executeQuery(PurchaseProductQueries.FIND_PURCHASE_AGENCY_DATA_QUERRY +rsLoadAgencyProduct.getString(1));
                    while(rsFindPurchaseData.next()){
                        if(!(rsFindPurchaseData.getString(1).isEmpty() || rsFindPurchaseData.getString(1).equals(null))){
                            try{
                                ResultSet rsFindSupplierData = conn.createStatement().executeQuery(SupplierQueries.FIND_SUPPLIER_DATA_QUERY +rsFindPurchaseData.getString(3));
                                while (rsFindSupplierData.next()){
                                    if(!(rsFindSupplierData.getString(1).isEmpty() || rsFindSupplierData.getString(1).equals(null))){

                                        agencyProductData.add(new AgencyProduct(rsLoadAgencyProduct.getString(1), rsLoadAgencyProduct.getString(2),rsFindSupplierData.getString(1), rsFindSupplierData.getString(2),rsLoadAgencyProduct.getInt(3),rsLoadAgencyProduct.getString(4),rsLoadAgencyProduct.getFloat(5),rsLoadAgencyProduct.getFloat(6), rsLoadAgencyProduct.getFloat(7),rsLoadAgencyProduct.getString(8),rsLoadAgencyProduct.getString(9),rsLoadAgencyProduct.getString(10),rsLoadAgencyProduct.getString(11)));

                                    }else{
                                        AlertPopUp.sqlRecordNotFound("Supplier");
                                    }
                                }
                            }catch(SQLException ex){
                                AlertPopUp.sqlQueryError(ex);
                            }
                        }else{
                            AlertPopUp.sqlRecordNotFound("Purchase");
                        }
                    }
                }catch (SQLException ex){
                    AlertPopUp.sqlQueryError(ex);
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<AgencyProduct> filteredData = new FilteredList<>(agencyProductData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(agencyProduct -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(agencyProduct.getaPID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPSupplierID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPSupplierName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(agencyProduct.getaPTotalUnits()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(agencyProduct.getaPWeightOfUnit()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(agencyProduct.getaPBuyingPricePerUnit()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(agencyProduct.getaPMarketPricePerUnit()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(agencyProduct.getaPSellingPricePerUnit()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPMDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPEDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPDADate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(agencyProduct.getaPADate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<AgencyProduct> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
