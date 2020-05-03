package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import model.*;
import util.dbConnect.DBConnection;
import util.query.AllowanceQueries;
import util.query.PurchaseProductQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import javax.jnlp.IntegrationService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PurchaseServices {



    private ObservableList<Purchase> purchasesData = FXCollections.observableArrayList();;

    public  ObservableList<Purchase> loadData(){
        ItemStockServices itemStockServices = new ItemStockServices();
        ItemStock itemStock = new ItemStock();
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        AgencyProduct agencyProduct = new AgencyProduct();
        SupplierServices supplierServices = new SupplierServices();
        Supplier supplier = new Supplier();
        Connection conn = DBConnection.Connect();
        ResultSet rsLoadPurchaseProduct = null;
        try {

            rsLoadPurchaseProduct = conn.createStatement().executeQuery(PurchaseProductQueries.LOAD_PURCHASE_DATA_QUERY);

            while (rsLoadPurchaseProduct.next()) {
                supplier = supplierServices.loadSpecificData(UtilityMethod.addPrefix("S", rsLoadPurchaseProduct.getString(3)));

                if(rsLoadPurchaseProduct.getString(4).equals("Stock")){
                    itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                    purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                }else{
                    agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                    purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPurchaseProduct.close();
                conn.close();
            }catch (SQLException ex){

            }
        }
        return purchasesData;
    }
    public  ObservableList<Purchase> loadData(String status){
        ItemStockServices itemStockServices = new ItemStockServices();
        ItemStock itemStock = new ItemStock();
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        AgencyProduct agencyProduct = new AgencyProduct();
        SupplierServices supplierServices = new SupplierServices();
        Supplier supplier = new Supplier();

        Connection conn = DBConnection.Connect();
        PreparedStatement psLoadPurchase = null;
        ResultSet rsLoadPurchaseProduct = null;
        try {

            psLoadPurchase = conn.prepareStatement(PurchaseProductQueries.LOAD_PURCHASE_DATA_BY_STATUS_QUERY);
            psLoadPurchase.setString(1, status);
            rsLoadPurchaseProduct = psLoadPurchase.executeQuery();
            while (rsLoadPurchaseProduct.next()) {
                supplier = supplierServices.loadSpecificData(UtilityMethod.addPrefix("S", rsLoadPurchaseProduct.getString(3)));

                if(rsLoadPurchaseProduct.getString(4).equals("Stock")){
                    itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                    purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                }else{
                    agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                    purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPurchaseProduct.close();
                psLoadPurchase.close();
                conn.close();
            }catch (SQLException ex){

            }
        }
        return purchasesData;
    }
    public  ObservableList<Purchase> loadData(String status, Integer year, String month, String category){
        ItemStockServices itemStockServices = new ItemStockServices();
        ItemStock itemStock ;
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        AgencyProduct agencyProduct;
        SupplierServices supplierServices = new SupplierServices();
        Supplier supplier;

        Connection conn = DBConnection.Connect();
        PreparedStatement psLoadPurchase = null;
        ResultSet rsLoadPurchaseProduct = null;
        try {

            psLoadPurchase = conn.prepareStatement(PurchaseProductQueries.LOAD_PURCHASE_DATA_BY_STATUS_QUERY);
            psLoadPurchase.setString(1, status);
            rsLoadPurchaseProduct = psLoadPurchase.executeQuery();
            while (rsLoadPurchaseProduct.next()) {
                String purchaseDate = rsLoadPurchaseProduct.getString(5);
                String purchaseType = rsLoadPurchaseProduct.getString(4);
                supplier = supplierServices.loadSpecificData(UtilityMethod.addPrefix("S", rsLoadPurchaseProduct.getString(3)));

                //getting all data
                if((year == 0) && (month.equals("None")) && (category.equals("None"))){

                    if(purchaseType.equals("Stock")){
                        itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                        purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                    }else{
                        agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                        purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                    }
                //year match all month all category
                }else if((year != 0) && (month.equals("None")) && (category.equals("None"))){
                    //checking  year and category
                    if((UtilityMethod.getYear(purchaseDate).equals(year))){
                        if(purchaseType.equals("Stock")){
                            itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }else{
                            agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }
                    }
                 //getting matching year, matching month, all category
                }else if((year != 0) && (!month.equals("None")) && (category.equals("None"))){
                    //checking  year and category
                    if((UtilityMethod.getYear(purchaseDate).equals(year)) && (UtilityMethod.getMonth(purchaseDate).equals(month))){
                        if(purchaseType.equals("Stock")){
                            itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }else{
                            agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }
                    }
                //getting matching year, all months, matching category
                }else if((year != 0) && (month.equals("None")) && (!category.equals("None"))){
                    //checking  year and category
                    if((UtilityMethod.getYear(purchaseDate).equals(year)) && (purchaseType).equals(category)){
                        if(purchaseType.equals("Stock")){
                            itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }else{
                            agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }
                    }
                 //getting data where matches all fields
                }else if((year != 0) && (!month.equals("None")) && (!category.equals("None"))){
                    //checking  matching records with year and month Agency and product Categories
                    if (((UtilityMethod.getYear(purchaseDate).equals(year))  && (UtilityMethod.getMonth(purchaseDate).equals(month)) && (purchaseType).equals(category))) {
                        if(purchaseType.equals("Stock")){
                            itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), itemStock.getiName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), itemStock.getiBuyingPrice(),  itemStock.getiBlocks() * itemStock.getiUnitsPerBlock(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }else{
                            agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                            purchasesData.add(new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6), supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8)));
                        }
                    }
                }

            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPurchaseProduct.close();
                psLoadPurchase.close();
                conn.close();
            }catch (SQLException ex){

            }
        }
        return purchasesData;
    }
    public Boolean updatePurchaseStatus(LinkedList<Purchase> purchaseLinkedList){
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        PreparedStatement psLoadPurchaseProduct = null;

        try {
            psLoadPurchaseProduct = conn.prepareStatement(PurchaseProductQueries.UPDATE_PURCHASE_PAYMENT_STATUS);
            for(Purchase purchase : purchaseLinkedList){
                psLoadPurchaseProduct.setString(1,"Processing");
                psLoadPurchaseProduct.setInt(2,UtilityMethod.seperateID(purchase.getpID()));
                psLoadPurchaseProduct.execute();
            }
            AlertPopUp.updateSuccesfully("Purchase Information");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Purchase Information");

        } finally {
            try{
                psLoadPurchaseProduct.close();
                conn.close();
            }catch (SQLException ex){

            }
        }

        return resultVal;
    }
    public  Purchase loadSpecificData(String id){
        ItemStockServices itemStockServices = new ItemStockServices();
        ItemStock itemStock = new ItemStock();
        AgencyProductServices agencyProductServices = new AgencyProductServices();
        AgencyProduct agencyProduct = new AgencyProduct();
        SupplierServices supplierServices = new SupplierServices();
        Supplier supplier = new Supplier();
        Purchase purchaseData = new Purchase();

        Connection conn = DBConnection.Connect();
        PreparedStatement psLoadPurchase = null;
        ResultSet rsLoadPurchaseProduct = null;
        try {
            psLoadPurchase = conn.prepareStatement(PurchaseProductQueries.LOAD_PURCHASE_DATA_BY_ID_QUERY);
            psLoadPurchase.setInt(1, UtilityMethod.seperateID(id));
            rsLoadPurchaseProduct = psLoadPurchase.executeQuery();
            while (rsLoadPurchaseProduct.next()) {
                supplier = supplierServices.loadSpecificData(UtilityMethod.addPrefix("S", rsLoadPurchaseProduct.getString(3)));

                if(rsLoadPurchaseProduct.getString(4).equals("Stock")){

                    itemStock = itemStockServices.loadSpecificData(UtilityMethod.addPrefix("I", rsLoadPurchaseProduct.getString(2)));

                    purchaseData.setpID(rsLoadPurchaseProduct.getString(1));
                    purchaseData.setpItemID(rsLoadPurchaseProduct.getString(2));
                    purchaseData.setpItemName(itemStock.getiName());
                    purchaseData.setpSupplierID(rsLoadPurchaseProduct.getString(3));
                    purchaseData.setpSupplierName(supplier.getsIName());
                    purchaseData.setpType(rsLoadPurchaseProduct.getString(4));
                    purchaseData.setpPurchaseDate(rsLoadPurchaseProduct.getString(5));
                    purchaseData.setpPricePerUnit(itemStock.getiBuyingPrice());
                    purchaseData.setpItemQuantity(itemStock.getiBlocks() * itemStock.getiUnitsPerBlock());
                    purchaseData.setpStatus(rsLoadPurchaseProduct.getString(6));
                    purchaseData.setpBankInfo(rsLoadPurchaseProduct.getString(7));
                    purchaseData.setpPaidDate(rsLoadPurchaseProduct.getString(8));
                }else{

                    agencyProduct = agencyProductServices.loadSpecificData(UtilityMethod.addPrefix("AP", rsLoadPurchaseProduct.getString(2)));
                    purchaseData = new Purchase(rsLoadPurchaseProduct.getString(1), rsLoadPurchaseProduct.getString(2), agencyProduct.getaPName(), rsLoadPurchaseProduct.getString(3), supplier.getsIName(), rsLoadPurchaseProduct.getString(4), rsLoadPurchaseProduct.getString(5), agencyProduct.getaPBuyingPricePerUnit(), agencyProduct.getaPTotalUnits(), rsLoadPurchaseProduct.getString(6),  supplier.getsIBank(), supplier.getsIAccNo(), rsLoadPurchaseProduct.getString(7), rsLoadPurchaseProduct.getString(8));
                }
            }
        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }finally {
            try{
                rsLoadPurchaseProduct.close();
                psLoadPurchase.close();
                conn.close();
            }catch (SQLException ex){

            }
        }
        return purchaseData;
    }
    public Boolean updateBankInfo(Purchase purchase){
        Boolean resultVal = false;
        Connection conn = DBConnection.Connect();
        PreparedStatement psLoadPurchaseProduct = null;

        try {
            psLoadPurchaseProduct = conn.prepareStatement(PurchaseProductQueries.UPDATE_PURCHASE_BANK_INFO);
            psLoadPurchaseProduct.setString(1,purchase.getpStatus());
            psLoadPurchaseProduct.setString(2,purchase.getpBankInfo());
            psLoadPurchaseProduct.setString(3,purchase.getpPaidDate());
            psLoadPurchaseProduct.setInt(4,UtilityMethod.seperateID(purchase.getpID()));
            psLoadPurchaseProduct.execute();

            AlertPopUp.updateSuccesfully("Purchase Bank Information");
            resultVal = true;

        } catch (SQLException ex) {
            AlertPopUp.updateFailed(ex, "Purchase Bank Information");

        } finally {
            try{
                psLoadPurchaseProduct.close();
                conn.close();
            }catch (SQLException ex){

            }
        }

        return resultVal;
    }

    public SortedList<Purchase> searchTable(TextField searchTextField, String status, Integer year, String month, String category){
        //Retreiving all data from database
        ObservableList<Purchase> purchasesData = null;
        purchasesData = loadData(status, year, month, category);

        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Purchase> filteredData = new FilteredList<>(purchasesData, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            filteredData.setPredicate(purchase -> {
                //if filter text is empty display all data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                if(purchase.getpID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpItemID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpItemName().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpSupplierID().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpSupplierName().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpPurchaseDate().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(String.valueOf(purchase.getpItemQuantity()).toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else if(purchase.getpStatus().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    //return if filter matches data
                    return true;
                }else{
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Purchase> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
