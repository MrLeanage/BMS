package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty uID = null;
    private StringProperty uName = null;
    private StringProperty uPassword = null;
    private StringProperty uType = null;
    private StringProperty uStatus = null;

    public User(String uID, String uName, String uPassword, String uType, String uStatus) {
        this.uID = new SimpleStringProperty(uID);
        this.uName = new SimpleStringProperty(uName);
        this.uPassword = new SimpleStringProperty(uPassword);
        this.uType = new SimpleStringProperty(uType);
        this.uStatus = new SimpleStringProperty(uStatus);
    }
    public User(){

    }
    public String getuID() {
        return uID.get();
    }

    public StringProperty uIDProperty() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = new SimpleStringProperty(uID);
    }

    public String getuName() {
        return uName.get();
    }

    public StringProperty uNameProperty() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = new SimpleStringProperty(uName);
    }

    public String getuPassword() {
        return uPassword.get();
    }

    public StringProperty uPasswordProperty() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = new SimpleStringProperty(uPassword);
    }

    public String getuType() {
        return uType.get();
    }

    public StringProperty uTypeProperty() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = new SimpleStringProperty(uType);
    }
    public String getuStatus() {
        return uStatus.get();
    }

    public StringProperty uStatusProperty() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = new SimpleStringProperty(uStatus);
    }
}
