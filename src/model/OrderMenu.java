package model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.utility.UtilityMethod;

import java.io.File;
import java.io.FileInputStream;

public class OrderMenu {
    StringProperty oMIID = null;
    ImageView oMIImage = null;
    StringProperty oMIName = null;
    StringProperty oMIDescription = null;
    FloatProperty oMIWeight = null;
    FloatProperty oMIPrice = null;
    StringProperty oMIStatus = null;

    public OrderMenu(String oMIID, ImageView oMIImage, String oMIName, String oMIDescription, Float oMIWeight, Float oMIPrice, String oMIStatus) {
        this.oMIID = new SimpleStringProperty(UtilityMethod.addPrefix("OMP",oMIID));
        this.oMIImage = oMIImage;
        this.oMIName = new SimpleStringProperty(oMIName);
        this.oMIDescription = new SimpleStringProperty(oMIDescription);
        this.oMIWeight = new SimpleFloatProperty(oMIWeight);
        this.oMIPrice = new SimpleFloatProperty(oMIPrice);
        this.oMIStatus = new SimpleStringProperty(oMIStatus);
    }

    public OrderMenu() {

    }

    public String getoMIID() {
        return oMIID.get();
    }

    public StringProperty oMIIDProperty() {
        return oMIID;
    }

    public void setoMIID(String oMIID) {
        this.oMIID = new SimpleStringProperty(oMIID);
    }

    public ImageView getoMIImage() {
        return oMIImage;
    }

    public void setoMIImage(ImageView oMIImage) {
        this.oMIImage = oMIImage;
    }

    public String getoMIName() {
        return oMIName.get();
    }

    public StringProperty oMINameProperty() {
        return oMIName;
    }

    public void setoMIName(String oMIName) {
        this.oMIName = new SimpleStringProperty(oMIName);
    }

    public String getoMIDescription() {
        return oMIDescription.get();
    }

    public StringProperty oMIDescriptionProperty() {
        return oMIDescription;
    }

    public void setoMIDescription(String oMIDescription) {
        this.oMIDescription = new SimpleStringProperty(oMIDescription);
    }

    public float getoMIWeight() {
        return oMIWeight.get();
    }

    public FloatProperty oMIWeightProperty() {
        return oMIWeight;
    }

    public void setoMIWeight(float oMIWeight) {
        this.oMIWeight = new SimpleFloatProperty(oMIWeight);
    }

    public float getoMIPrice() {
        return oMIPrice.get();
    }

    public FloatProperty oMIPriceProperty() {
        return oMIPrice;
    }

    public void setoMIPrice(float oMIPrice) {
        this.oMIPrice = new SimpleFloatProperty(oMIPrice);
    }

    public String getoMIStatus() {
        return oMIStatus.get();
    }

    public StringProperty oMIStatusProperty() {
        return oMIStatus;
    }

    public void setoMIStatus(String oMIStatus) {
        this.oMIStatus = new SimpleStringProperty(oMIStatus);
    }
}
