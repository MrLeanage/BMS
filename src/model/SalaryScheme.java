package model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.utility.UtilityMethod;

public class SalaryScheme {
    private StringProperty bSSID = null;
    private StringProperty bSSTitle = null;
    private FloatProperty bSSAmount = null;
    private  StringProperty bSSAddedDate = null;

    public SalaryScheme(String bSSID, String bSSTitle, Float bSSAmount, String bSSAddedDate) {
        this.bSSID = new SimpleStringProperty(UtilityMethod.addPrefix("BSS", bSSID));
        this.bSSTitle = new SimpleStringProperty(bSSTitle);
        this.bSSAmount = new SimpleFloatProperty(bSSAmount);
        this.bSSAddedDate = new SimpleStringProperty(bSSAddedDate);
    }

    public SalaryScheme() {

    }

    public String getbSSID() {
        return bSSID.get();
    }

    public StringProperty bSSIDProperty() {
        return bSSID;
    }

    public void setbSSID(String bSSID) {
        this.bSSID = new SimpleStringProperty(UtilityMethod.addPrefix("BSS", bSSID));
    }

    public String getbSSTitle() {
        return bSSTitle.get();
    }

    public StringProperty bSSTitleProperty() {
        return bSSTitle;
    }

    public void setbSSTitle(String bSSTitle) {
        this.bSSTitle = new SimpleStringProperty(bSSTitle);
    }

    public float getbSSAmount() {
        return bSSAmount.get();
    }

    public FloatProperty bSSAmountProperty() {
        return bSSAmount;
    }

    public void setbSSAmount(float bSSAmount) {
        this.bSSAmount = new SimpleFloatProperty(bSSAmount);
    }

    public String getbSSAddedDate() {
        return bSSAddedDate.get();
    }

    public StringProperty bSSAddedDateProperty() {
        return bSSAddedDate;
    }

    public void setbSSAddedDate(String bSSAddedDate) {
        this.bSSAddedDate = new SimpleStringProperty(bSSAddedDate);
    }
}
