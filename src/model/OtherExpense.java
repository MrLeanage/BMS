package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.utility.UtilityMethod;

public class OtherExpense {
    private StringProperty eXPID = null;
    private StringProperty eXPTitle = null;
    private StringProperty eXPDescription = null;
    private StringProperty eXPPeriod = null;
    private DoubleProperty eXPAmount = null;
    private StringProperty eXPPaidDate = null;
    private StringProperty eXPAddedDate = null;

    public OtherExpense() {
    }

    public OtherExpense(String eXPID, String eXPTitle, String eXPDescription, String eXPPeriod, Double eXPAmount, String eXPPaidDate, String eXPAddedDate) {
        this.eXPID = new SimpleStringProperty(UtilityMethod.addPrefix("OE", eXPID));
        this.eXPTitle = new SimpleStringProperty(eXPTitle);
        this.eXPDescription = new SimpleStringProperty(eXPDescription);
        this.eXPPeriod = new SimpleStringProperty(eXPPeriod);
        this.eXPAmount = new SimpleDoubleProperty(eXPAmount);
        this.eXPPaidDate = new SimpleStringProperty(eXPPaidDate);
        this.eXPAddedDate = new SimpleStringProperty(eXPAddedDate);
    }

    public String geteXPID() {
        return eXPID.get();
    }

    public StringProperty eXPIDProperty() {
        return eXPID;
    }

    public void seteXPID(String eXPID) {
        this.eXPID = new SimpleStringProperty(eXPID);
    }

    public String geteXPTitle() {
        return eXPTitle.get();
    }

    public StringProperty eXPTitleProperty() {
        return eXPTitle;
    }

    public void seteXPTitle(String eXPTitle) {
        this.eXPTitle = new SimpleStringProperty(eXPTitle);
    }

    public String geteXPDescription() {
        return eXPDescription.get();
    }

    public StringProperty eXPDescriptionProperty() {
        return eXPDescription;
    }

    public void seteXPDescription(String eXPDescription) {
        this.eXPDescription = new SimpleStringProperty(eXPDescription);
    }

    public String geteXPPeriod() {
        return eXPPeriod.get();
    }

    public StringProperty eXPPeriodProperty() {
        return eXPPeriod;
    }

    public void seteXPPeriod(String eXPPeriod) {
        this.eXPPeriod = new SimpleStringProperty(eXPPeriod);
    }

    public double geteXPAmount() {
        return eXPAmount.get();
    }

    public DoubleProperty eXPAmountProperty() {
        return eXPAmount;
    }

    public void seteXPAmount(double eXPAmount) {
        this.eXPAmount = new SimpleDoubleProperty(eXPAmount);
    }

    public String geteXPPaidDate() {
        return eXPPaidDate.get();
    }

    public StringProperty eXPPaidDateProperty() {
        return eXPPaidDate;
    }

    public void seteXPPaidDate(String eXPPaidDate) {
        this.eXPPaidDate = new SimpleStringProperty(eXPPaidDate);
    }

    public String geteXPAddedDate() {
        return eXPAddedDate.get();
    }

    public StringProperty eXPAddedDateProperty() {
        return eXPAddedDate;
    }

    public void seteXPAddedDate(String eXPAddedDate) {
        this.eXPAddedDate = new SimpleStringProperty(eXPAddedDate);
    }
}
