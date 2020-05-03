package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class AgencyProduct {
    private StringProperty aPID = null;
    private StringProperty aPName = null;
    private StringProperty aPSupplierID = null;
    private StringProperty aPSupplierName = null;
    private IntegerProperty aPTotalUnits = null;
    private StringProperty aPWeightOfUnit = null;
    private FloatProperty aPBuyingPricePerUnit = null;
    private FloatProperty aPMarketPricePerUnit = null;
    private FloatProperty aPSellingPricePerUnit = null;
    private StringProperty aPMDate = null;
    private StringProperty aPEDate = null;
    private StringProperty aPADate = null;
    private StringProperty aPDADate = null;

    public AgencyProduct(String aPID, String aPName, String aPSupplierID, String aPSupplierName, Integer aPTotalUnits, String aPWeightOfUnit, Float aPBuyingPricePerUnit, Float aPMarketPricePerUnit, Float aPSellingPricePerUnit, String aPMDate, String aPEDate, String aPADate, String aPDADate) {
        this.aPID = new SimpleStringProperty(UtilityMethod.addPrefix("AP", aPID));
        this.aPName = new SimpleStringProperty(aPName);
        this.aPSupplierID = new SimpleStringProperty(UtilityMethod.addPrefix("SI", aPSupplierID));
        this.aPSupplierName = new SimpleStringProperty(aPSupplierName);
        this.aPTotalUnits = new SimpleIntegerProperty(aPTotalUnits);
        this.aPWeightOfUnit = new SimpleStringProperty(aPWeightOfUnit);
        this.aPBuyingPricePerUnit = new SimpleFloatProperty(aPBuyingPricePerUnit);
        this.aPMarketPricePerUnit = new SimpleFloatProperty(aPMarketPricePerUnit);
        this.aPSellingPricePerUnit = new SimpleFloatProperty(aPSellingPricePerUnit);
        this.aPMDate = new SimpleStringProperty(aPMDate);
        this.aPEDate = new SimpleStringProperty(aPEDate);
        this.aPADate = new SimpleStringProperty(aPADate);
        this.aPDADate = new SimpleStringProperty(aPDADate);
    }

    public AgencyProduct() {

    }

    public String getaPID() {
        return aPID.get();
    }

    public StringProperty aPIDProperty() {
        return aPID;
    }

    public void setaPID(String aPID) {
        this.aPID = new SimpleStringProperty(UtilityMethod.addPrefix("AP", aPID));
    }

    public String getaPName() {
        return aPName.get();
    }

    public StringProperty aPNameProperty() {
        return aPName;
    }

    public void setaPName(String aPName) {
        this.aPName = new SimpleStringProperty(aPName);
    }

    public String getaPSupplierID() {
        return aPSupplierID.get();
    }

    public StringProperty aPSupplierIDProperty() {
        return aPSupplierID;
    }

    public void setaPSupplierID(String aPSupplierID) {
        this.aPSupplierID = new SimpleStringProperty(UtilityMethod.addPrefix("aPSupplierID", aPSupplierID));
    }

    public String getaPSupplierName() {
        return aPSupplierName.get();
    }

    public StringProperty aPSupplierNameProperty() {
        return aPSupplierName;
    }

    public void setaPSupplierName(String aPSupplierName) {
        this.aPSupplierName = new SimpleStringProperty(aPSupplierName);
    }

    public int getaPTotalUnits() {
        return aPTotalUnits.get();
    }

    public IntegerProperty aPTotalUnitsProperty() {
        return aPTotalUnits;
    }

    public void setaPTotalUnits(int aPTotalUnits) {
        this.aPTotalUnits = new SimpleIntegerProperty(aPTotalUnits);
    }

    public String getaPWeightOfUnit() {
        return aPWeightOfUnit.get();
    }

    public StringProperty aPWeightOfUnitProperty() {
        return aPWeightOfUnit;
    }

    public void setaPWeightOfUnit(String aPWeightOfUnit) {
        this.aPWeightOfUnit = new SimpleStringProperty(aPWeightOfUnit);
    }

    public float getaPBuyingPricePerUnit() {
        return aPBuyingPricePerUnit.get();
    }

    public FloatProperty aPBuyingPricePerUnitProperty() {
        return aPBuyingPricePerUnit;
    }

    public void setaPBuyingPricePerUnit(float aPBuyingPricePerUnit) {
        this.aPBuyingPricePerUnit = new SimpleFloatProperty(aPBuyingPricePerUnit);
    }

    public float getaPMarketPricePerUnit() {
        return aPMarketPricePerUnit.get();
    }

    public FloatProperty aPMarketPricePerUnitProperty() {
        return aPMarketPricePerUnit;
    }

    public void setaPMarketPricePerUnit(float aPMarketPricePerUnit) {
        this.aPMarketPricePerUnit = new SimpleFloatProperty(aPMarketPricePerUnit);
    }

    public float getaPSellingPricePerUnit() {
        return aPSellingPricePerUnit.get();
    }

    public FloatProperty aPSellingPricePerUnitProperty() {
        return aPSellingPricePerUnit;
    }

    public void setaPSellingPricePerUnit(float aPSellingPricePerUnit) {
        this.aPSellingPricePerUnit = new SimpleFloatProperty(aPSellingPricePerUnit);
    }

    public String getaPMDate() {
        return aPMDate.get();
    }

    public StringProperty aPMDateProperty() {
        return aPMDate;
    }

    public void setaPMDate(String aPMDate) {
        this.aPMDate = new SimpleStringProperty(aPMDate);
    }

    public String getaPEDate() {
        return aPEDate.get();
    }

    public StringProperty aPEDateProperty() {
        return aPEDate;
    }

    public void setaPEDate(String aPEDate) {
        this.aPEDate = new SimpleStringProperty(aPEDate);
    }

    public String getaPADate() {
        return aPADate.get();
    }

    public StringProperty aPADateProperty() {
        return aPADate;
    }

    public void setaPADate(String aPADate) {
        this.aPADate = new SimpleStringProperty(aPADate);
    }

    public String getaPDADate() {
        return aPDADate.get();
    }

    public StringProperty aPDADateProperty() {
        return aPDADate;
    }

    public void setaPDADate(String aPDADate) {
        this.aPDADate = new SimpleStringProperty(aPDADate);
    }
}
