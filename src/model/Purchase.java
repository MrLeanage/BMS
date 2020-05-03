package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Purchase {
    private StringProperty pID = null;
    private StringProperty pItemID = null;
    private StringProperty pItemName = null;
    private StringProperty pSupplierID = null;
    private StringProperty pSupplierName = null;
    private StringProperty pType = null;
    private StringProperty pPurchaseDate = null;
    private FloatProperty pPricePerUnit = null;
    private IntegerProperty pItemQuantity  = null;
    private FloatProperty pItemTotal  = null;
    private StringProperty pStatus = null;
    private StringProperty pBankName = null;
    private LongProperty pBankAccount = null;
    private StringProperty pBankInfo = null;
    private StringProperty pPaidDate = null;

    public Purchase(){
    }

    public Purchase(String pID, String pItemID, String pSupplierID, String pType, String pPurchaseDate, String pStatus) {
        this.pID = new SimpleStringProperty(UtilityMethod.addPrefix("P", pID));
        this.pItemID = new SimpleStringProperty(UtilityMethod.addPrefix("I", pItemID));
        this.pSupplierID = new SimpleStringProperty(UtilityMethod.addPrefix("S", pSupplierID));
        this.pType = new SimpleStringProperty(pType);
        this.pPurchaseDate = new SimpleStringProperty(pPurchaseDate);
        this.pStatus = new SimpleStringProperty(pStatus);
    }

    public Purchase(String pID, String pItemID, String pItemName, String pSupplierID, String pSupplierName, String pType, String pPurchaseDate, Float pPricePerUnit, Integer pItemQuantity, String pStatus, String pBankName, Long pBankAccount, String pBankInfo, String pPaidDate) {
        this.pID = new SimpleStringProperty(UtilityMethod.addPrefix("P", pID));
        this.pItemID =  new SimpleStringProperty(UtilityMethod.addPrefix("I", pItemID));
        this.pItemName =  new SimpleStringProperty(pItemName);
        this.pSupplierID =  new SimpleStringProperty(UtilityMethod.addPrefix("S", pSupplierID));
        this.pSupplierName =  new SimpleStringProperty(pSupplierName);
        this.pType =  new SimpleStringProperty(pType);
        this.pPurchaseDate =  new SimpleStringProperty(pPurchaseDate);
        this.pPricePerUnit =  new SimpleFloatProperty(pPricePerUnit);
        this.pItemQuantity = new SimpleIntegerProperty(pItemQuantity) ;
        this.pItemTotal =  new SimpleFloatProperty(pItemQuantity * pPricePerUnit);
        this.pStatus =  new SimpleStringProperty(pStatus);
        this.pBankName =  new SimpleStringProperty(pBankName);
        this.pBankAccount =  new SimpleLongProperty(pBankAccount);
        this.pBankInfo = new SimpleStringProperty(pBankInfo);
        this.pPaidDate = new SimpleStringProperty(pPaidDate);
    }

    public String getpID() {
        return pID.get();
    }

    public StringProperty pIDProperty() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = new SimpleStringProperty(pID);
    }

    public String getpItemID() {
        return pItemID.get();
    }

    public StringProperty pItemIDProperty() {
        return pItemID;
    }

    public void setpItemID(String pItemID) {
        this.pItemID = new SimpleStringProperty(pItemID);
    }

    public String getpSupplierID() {
        return pSupplierID.get();
    }

    public StringProperty pSupplierIDProperty() {
        return pSupplierID;
    }

    public void setpSupplierID(String pSupplierID) {
        this.pSupplierID = new SimpleStringProperty(pSupplierID);
    }

    public String getpType() {
        return pType.get();
    }

    public StringProperty pTypeProperty() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = new SimpleStringProperty(pType);
    }

    public String getpPurchaseDate() {
        return pPurchaseDate.get();
    }

    public StringProperty pPurchaseDateProperty() {
        return pPurchaseDate;
    }

    public void setpPurchaseDate(String pPurchaseDate) {
        this.pPurchaseDate = new SimpleStringProperty(pPurchaseDate);
    }

    public String getpStatus() {
        return pStatus.get();
    }

    public StringProperty pStatusProperty() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = new SimpleStringProperty(pStatus);
    }

    public String getpItemName() {
        return pItemName.get();
    }

    public StringProperty pItemNameProperty() {
        return pItemName;
    }

    public void setpItemName(String pItemName) {
        this.pItemName = new SimpleStringProperty(pItemName);
    }

    public String getpSupplierName() {
        return pSupplierName.get();
    }

    public StringProperty pSupplierNameProperty() {
        return pSupplierName;
    }

    public void setpSupplierName(String pSupplierName) {
        this.pSupplierName  = new SimpleStringProperty(pSupplierName);
    }

    public int getpItemQuantity() {
        return pItemQuantity.get();
    }

    public IntegerProperty pItemQuantityProperty() {
        return pItemQuantity;
    }

    public void setpItemQuantity(int pItemQuantity) {
        this.pItemQuantity  = new SimpleIntegerProperty(pItemQuantity);
    }

    public Float getpItemTotal() {
        return pItemTotal.get();
    }

    public FloatProperty pItemTotalProperty() {
        return pItemTotal;
    }

    public void setpItemTotal(Float pItemTotal) {
        this.pItemTotal  = new SimpleFloatProperty(pItemTotal);
    }

    public float getpPricePerUnit() {
        return pPricePerUnit.get();
    }

    public FloatProperty pPricePerUnitProperty() {
        return pPricePerUnit;
    }

    public void setpPricePerUnit(float pPricePerUnit) {
        this.pPricePerUnit = new SimpleFloatProperty(pPricePerUnit);
    }

    public String getpBankName() {
        return pBankName.get();
    }

    public StringProperty pBankNameProperty() {
        return pBankName;
    }

    public void setpBankName(String pBankName) {
        this.pBankName = new SimpleStringProperty(pBankName);
    }

    public long getpBankAccount() {
        return pBankAccount.get();
    }

    public LongProperty pBankAccountProperty() {
        return pBankAccount;
    }

    public void setpBankAccount(long pBankAccount) {
        this.pBankAccount = new SimpleLongProperty(pBankAccount);
    }

    public String getpBankInfo() {
        return pBankInfo.get();
    }

    public StringProperty pBankInfoProperty() {
        return pBankInfo;
    }

    public void setpBankInfo(String pBankInfo) {
        this.pBankInfo = new SimpleStringProperty(pBankInfo);
    }

    public String getpPaidDate() {
        return pPaidDate.get();
    }

    public StringProperty pPaidDateProperty() {
        return pPaidDate;
    }

    public void setpPaidDate(String pPaidDate) {
        this.pPaidDate = new SimpleStringProperty(pPaidDate);
    }
}
