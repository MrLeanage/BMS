package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class ItemWithdraw {
    private StringProperty iWID = null;
    private StringProperty iWIID = null;
    private StringProperty iWIName = null;
    private StringProperty iWDescription = null;
    private StringProperty iWWeight = null;
    private IntegerProperty iWQuantity = null;
    private StringProperty iWUser = null;
    private StringProperty iWDate = null;
    private StringProperty iWTime = null;

    public ItemWithdraw(String iWID, String iWIID, String iWIName, String iWDescription, String iWWeight, Integer iWQuantity, String iWUser, String iWDate, String iWTime) {
        this.iWID = new SimpleStringProperty(UtilityMethod.addPrefix("WI", iWID));
        this.iWIID = new SimpleStringProperty(UtilityMethod.addPrefix("I", iWIID));
        this.iWIName = new SimpleStringProperty(iWIName);
        this.iWDescription = new SimpleStringProperty(iWDescription);
        this.iWWeight = new SimpleStringProperty(iWWeight);
        this.iWQuantity = new SimpleIntegerProperty(iWQuantity);
        this.iWUser = new SimpleStringProperty(iWUser);
        this.iWDate = new SimpleStringProperty(iWDate);
        this.iWTime = new SimpleStringProperty(iWTime);
    }
    public ItemWithdraw(String iWID, String iWIID, String iWDescription, Integer iWQuantity, String iWUser, String iWDate, String iWTime) {
        this.iWID = new SimpleStringProperty(iWID);
        this.iWIID = new SimpleStringProperty(iWIID);
        this.iWDescription = new SimpleStringProperty(iWDescription);
        this.iWQuantity = new SimpleIntegerProperty(iWQuantity);
        this.iWUser = new SimpleStringProperty(iWUser);
        this.iWDate = new SimpleStringProperty(iWDate);
        this.iWTime = new SimpleStringProperty(iWTime);
    }
    public ItemWithdraw() {
    }

    public String getiWID() {
        return iWID.get();
    }

    public StringProperty iWIDProperty() {
        return iWID;
    }

    public void setiWID(String iWID) {
        this.iWID = new SimpleStringProperty(iWID);
    }

    public String getiWIID() {
        return iWIID.get();
    }

    public StringProperty iWIIDProperty() {
        return iWIID;
    }

    public void setiWIID(String iWIID) {
        this.iWIID = new SimpleStringProperty(iWIID);
    }

    public String getiWIName() {
        return iWIName.get();
    }

    public StringProperty iWINameProperty() {
        return iWIName;
    }

    public void setiWIName(String iWIName) {
        this.iWIName = new SimpleStringProperty(iWIName);
    }

    public String getiWDescription() {
        return iWDescription.get();
    }

    public StringProperty iWDescriptionProperty() {
        return iWDescription;
    }

    public void setiWDescription(String iWDescription) {
        this.iWDescription = new SimpleStringProperty(iWDescription);
    }

    public String getiWWeight() {
        return iWWeight.get();
    }

    public StringProperty iWWeightProperty() {
        return iWWeight;
    }

    public void setiWWeight(String iWWeight) {
        this.iWWeight = new SimpleStringProperty(iWWeight);
    }

    public int getiWQuantity() {
        return iWQuantity.get();
    }

    public IntegerProperty iWQuantityProperty() {
        return iWQuantity;
    }

    public void setiWQuantity(int iWQuantity) {
        this.iWQuantity = new SimpleIntegerProperty(iWQuantity);
    }

    public String getiWUser() {
        return iWUser.get();
    }

    public StringProperty iWUserProperty() {
        return iWUser;
    }

    public void setiWUser(String iWUser) {
        this.iWUser = new SimpleStringProperty(iWUser);
    }

    public String getiWDate() {
        return iWDate.get();
    }

    public StringProperty iWDateProperty() {
        return iWDate;
    }

    public void setiWDate(String iWDate) {
        this.iWDate = new SimpleStringProperty(iWDate);
    }

    public String getiWTime() {
        return iWTime.get();
    }

    public StringProperty iWTimeProperty() {
        return iWTime;
    }

    public void setiWTime(String iWTime) {
        this.iWTime = new SimpleStringProperty(iWTime);
    }
}
