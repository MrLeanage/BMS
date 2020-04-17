package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class SalesItem {
    private StringProperty sIID = null;
    private StringProperty sIPID = null;
    private StringProperty sIPName = null;
    private StringProperty sIPWeight = null;
    private IntegerProperty sIPQuantity = null;
    private FloatProperty sIUnitPrice = null;
    private DoubleProperty sITotalAmount = null;
    private StringProperty sIBNo = null;
    private StringProperty sIType = null;
    private StringProperty sIUser = null;
    private StringProperty sIStatus = null;
    private StringProperty sIBDate = null;

    public SalesItem(String sIID, String sIPID, String sIPName, String sIPWeight, Integer sIPQuantity, Float sIUnitPrice, Double sITotalAmount, String sIBNo, String sIType, String sIUser, String sIBDate) {
        this.sIID = new SimpleStringProperty(UtilityMethod.addPrefix("IS", sIID));
        this.sIPID = new SimpleStringProperty(UtilityMethod.addPrefix("SP", sIPID));
        this.sIPName = new SimpleStringProperty(sIPName);
        this.sIPWeight = new SimpleStringProperty(sIPWeight);
        this.sIPQuantity = new SimpleIntegerProperty(sIPQuantity);
        this.sIUnitPrice = new SimpleFloatProperty(sIUnitPrice);
        this.sITotalAmount = new SimpleDoubleProperty(sITotalAmount);
        this.sIBNo = new SimpleStringProperty(sIBNo);
        this.sIType = new SimpleStringProperty(sIType);
        this.sIUser = new SimpleStringProperty(sIUser);
        this.sIBDate = new SimpleStringProperty(sIBDate);
    }
    public SalesItem(String sIPID, String sIPName, String sIPWeight, Float sIUnitPrice,  String sIType, String sIStatus) {

        this.sIPID = new SimpleStringProperty(sIPID);
        this.sIPName = new SimpleStringProperty(sIPName);
        this.sIPWeight = new SimpleStringProperty(sIPWeight);
        this.sIUnitPrice = new SimpleFloatProperty(sIUnitPrice);
        this.sIType = new SimpleStringProperty(sIType);
        this.sIStatus = new SimpleStringProperty(sIStatus);
    }

    public SalesItem() {
    }

    public String getsIID() {
        return sIID.get();
    }

    public StringProperty sIIDProperty() {
        return sIID;
    }

    public void setsIID(String sIID) {
        this.sIID = new SimpleStringProperty(sIID);
    }

    public String getsIPID() {
        return sIPID.get();
    }

    public StringProperty sIPIDProperty() {
        return sIPID;
    }

    public void setsIPID(String sIPID) {
        this.sIPID = new SimpleStringProperty(UtilityMethod.addPrefix("SP", sIPID));
    }

    public String getsIPName() {
        return sIPName.get();
    }

    public StringProperty sIPNameProperty() {
        return sIPName;
    }

    public void setsIPName(String sIPName) {
        this.sIPName = new SimpleStringProperty(sIPName);
    }

    public String getsIPWeight() {
        return sIPWeight.get();
    }

    public StringProperty sIPWeightProperty() {
        return sIPWeight;
    }

    public void setsIPWeight(String sIPWeight) {
        this.sIPWeight = new SimpleStringProperty(sIPWeight);
    }

    public int getsIPQuantity() {
        return sIPQuantity.get();
    }

    public IntegerProperty sIPQuantityProperty() {
        return sIPQuantity;
    }

    public void setsIPQuantity(int sIPQuantity) {
        this.sIPQuantity = new SimpleIntegerProperty(sIPQuantity);
    }

    public float getsIUnitPrice() {
        return sIUnitPrice.get();
    }

    public FloatProperty sIUnitPriceProperty() {
        return sIUnitPrice;
    }

    public void setsIUnitPrice(float sIUnitPrice) {
        this.sIUnitPrice = new SimpleFloatProperty(sIUnitPrice);
    }

    public double getsITotalAmount() {
        return sITotalAmount.get();
    }

    public DoubleProperty sITotalAmountProperty() {
        return sITotalAmount;
    }

    public void setsITotalAmount(double sITotalAmount) {
        this.sITotalAmount = new SimpleDoubleProperty(sITotalAmount);
    }

    public String getsIBNo() {
        return sIBNo.get();
    }

    public StringProperty sIBNoProperty() {
        return sIBNo;
    }

    public void setsIBNo(String sIBNo) {
        this.sIBNo = new SimpleStringProperty(sIBNo);
    }

    public String getsIType() {
        return sIType.get();
    }

    public StringProperty sITypeProperty() {
        return sIType;
    }

    public void setsIType(String sIType) {
        this.sIType = new SimpleStringProperty(sIType);
    }

    public String getsIUser() {
        return sIUser.get();
    }

    public StringProperty sIUserProperty() {
        return sIUser;
    }

    public void setsIUser(String sIUser) {
        this.sIUser = new SimpleStringProperty(sIUser);
    }

    public String getsIStatus() {
        return sIStatus.get();
    }

    public StringProperty sIStatusProperty() {
        return sIStatus;
    }

    public void setsIStatus(String sIStatus) {
        this.sIStatus = new SimpleStringProperty(sIStatus);
    }

    public String getsIBDate() {
        return sIBDate.get();
    }

    public StringProperty sIBDateProperty() {
        return sIBDate;
    }

    public void setsIBDate(String sIBDate) {
        this.sIBDate =  new SimpleStringProperty(sIBDate);
    }
}
