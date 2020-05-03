package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class BakeryProduct {
    private StringProperty bPID = null;
    private StringProperty bPName = null;
    private StringProperty bPType = null;
    private StringProperty bPWeight = null;
    private StringProperty bPDescription = null;
    private FloatProperty bPPrice = null;
    private StringProperty bPStatus = null;

    public BakeryProduct(String bPID, String bPName, String bPType, String bPWeight, String bPDescription, Float bPPrice, String bPStatus) {
        this.bPID = new SimpleStringProperty(UtilityMethod.addPrefix("BP", bPID)) ;
        this.bPName = new SimpleStringProperty(bPName) ;
        this.bPType = new SimpleStringProperty(bPType) ;
        this.bPWeight = new SimpleStringProperty(bPWeight);
        this.bPDescription = new SimpleStringProperty(bPDescription);
        this.bPPrice = new SimpleFloatProperty(bPPrice);
        this.bPStatus = new SimpleStringProperty(bPStatus);
    }

    public BakeryProduct() {

    }

    public String getbPID() {
        return bPID.get();
    }

    public StringProperty bPIDProperty() {
        return bPID;
    }

    public void setbPID(String bPID) {
        this.bPID = new SimpleStringProperty(bPID);
    }

    public String getbPName() {
        return bPName.get();
    }

    public StringProperty bPNameProperty() {
        return bPName;
    }

    public void setbPName(String bPName) {
        this.bPName = new SimpleStringProperty(bPName);
    }

    public String getbPType() {
        return bPType.get();
    }

    public StringProperty bPTypeProperty() {
        return bPType;
    }

    public void setbPType(String bPType) {
        this.bPType = new SimpleStringProperty(bPType);
    }

    public String getbPWeight() {
        return bPWeight.get();
    }

    public StringProperty bPWeightProperty() {
        return bPWeight;
    }

    public void setbPWeight(String bPWeight) {
        this.bPWeight = new SimpleStringProperty(bPWeight);
    }

    public String getbPDescription() {
        return bPDescription.get();
    }

    public StringProperty bPDescriptionProperty() {
        return bPDescription;
    }

    public void setbPDescription(String bPDescription) {
        this.bPDescription = new SimpleStringProperty(bPDescription);
    }

    public float getbPPrice() {
        return bPPrice.get();
    }

    public FloatProperty bPPriceProperty() {
        return bPPrice;
    }

    public void setbPPrice(float bPPrice) {
        this.bPPrice = new SimpleFloatProperty(bPPrice);
    }

    public String getbPStatus() {
        return bPStatus.get();
    }

    public StringProperty bPStatusProperty() {
        return bPStatus;
    }

    public void setbPStatus(String bPStatus) {
        this.bPStatus = new SimpleStringProperty(bPStatus);
    }
}
