package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Purchase {
    StringProperty pID = null;
    StringProperty pItemID = null;
    StringProperty pSupplierID = null;
    StringProperty pPurchaseDate = null;
    StringProperty pStatus = null;

    public Purchase(String pID, String pItemID, String pSupplierID, String pPurchaseDate, String pStatus) {
        this.pID = new SimpleStringProperty(pID);
        this.pItemID = new SimpleStringProperty(pItemID);
        this.pSupplierID = new SimpleStringProperty(pSupplierID);
        this.pPurchaseDate = new SimpleStringProperty(pPurchaseDate);
        this.pStatus = new SimpleStringProperty(pStatus);
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
}
