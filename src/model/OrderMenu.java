package model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.utility.UtilityMethod;

import java.io.File;
import java.io.FileInputStream;

public class OrderMenu {
    private StringProperty oMIID = null;
    private ObjectProperty<ImageView> oMIImage;
    private StringProperty oMIName = null;
    private StringProperty oMIDescription = null;
    private StringProperty oMIWeight = null;
    private FloatProperty oMIPrice = null;
    private StringProperty oMIStatus = null;

    public OrderMenu(String oMIID, ImageView oMIImage, String oMIName, String oMIDescription, String oMIWeight, Float oMIPrice, String oMIStatus) {
        this.oMIID = new SimpleStringProperty(UtilityMethod.addPrefix("OMP",oMIID));
        this.oMIImage = new SimpleObjectProperty<>(oMIImage);
        this.oMIName = new SimpleStringProperty(oMIName);
        this.oMIDescription = new SimpleStringProperty(oMIDescription);
        this.oMIWeight = new SimpleStringProperty(oMIWeight);
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
        return oMIImage.get();
    }
    public ObjectProperty<ImageView> oMIImageProperty() {
        return oMIImage;
    }
    public void setoMIImage(ImageView oMIImage) {
        this.oMIImage = new SimpleObjectProperty<>(oMIImage);
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

    public String getoMIWeight() {
        return oMIWeight.get();
    }

    public StringProperty oMIWeightProperty() {
        return oMIWeight;
    }

    public void setoMIWeight(String oMIWeight) {
        this.oMIWeight = new SimpleStringProperty(oMIWeight);
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
