package model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.utility.UtilityMethod;

public class Allowance {
    StringProperty aID = null;
    StringProperty aTitle = null;
    StringProperty aDescription = null;
    StringProperty aType = null;
    FloatProperty aValue = null;

    public Allowance(String aID, String aTitle, String aDescription, String aType, Float aValue) {
        this.aID = new SimpleStringProperty(UtilityMethod.addPrefix("A", aID));
        this.aTitle = new SimpleStringProperty(aTitle);
        this.aDescription = new SimpleStringProperty(aDescription);
        this.aType = new SimpleStringProperty(aType);
        this.aValue = new SimpleFloatProperty(aValue);
    }

    public Allowance() {

    }

    public String getaID() {
        return aID.get();
    }

    public StringProperty aIDProperty() {
        return aID;
    }

    public void setaID(String aID) {
        this.aID = new SimpleStringProperty(aID);
    }

    public String getaTitle() {
        return aTitle.get();
    }

    public StringProperty aTitleProperty() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = new SimpleStringProperty(aTitle);
    }

    public String getaDescription() {
        return aDescription.get();
    }

    public StringProperty aDescriptionProperty() {
        return aDescription;
    }

    public void setaDescription(String aDescription) {
        this.aDescription = new SimpleStringProperty(aDescription);
    }

    public String getaType() {
        return aType.get();
    }

    public StringProperty aTypeProperty() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = new SimpleStringProperty(aType);
    }

    public float getaValue() {
        return aValue.get();
    }

    public FloatProperty aValueProperty() {
        return aValue;
    }

    public void setaValue(float aValue) {
        this.aValue = new SimpleFloatProperty(aValue);
    }
}
