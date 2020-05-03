package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

import java.time.LocalDate;

public class ItemStock {
    private StringProperty iID = null;
    private StringProperty iName = null;
    private StringProperty iSIID = null;
    private StringProperty iSISupplierName = null;
    private IntegerProperty iUnitsPerBlock = null;
    private IntegerProperty iBlocks = null;
    private StringProperty iWeightPerBlock = null;
    private FloatProperty iBuyingPrice = null;
    private StringProperty iExpireDate = null;
    private StringProperty iAddedDate = null;
    private IntegerProperty iMinQuantityLimit = null;
    private IntegerProperty iAvailableQuantity = null;
    private StringProperty iStatus = null;

    public ItemStock(String iID, String iName, String iSID, String iSISupplierName, Integer iUnitsPerBlock, Integer iBlocks, String iWeightPerBlock, Float iBuyingPrice, String iExpireDate, String iAddedDate, Integer iMinQuantityLimit, Integer iAvailableQuantity) {

        this.iID = new SimpleStringProperty(UtilityMethod.addPrefix("I", iID));
        this.iName = new SimpleStringProperty(iName);
        this.iSIID = new SimpleStringProperty(UtilityMethod.addPrefix("SI", iSID));
        this.iSISupplierName = new SimpleStringProperty(iSISupplierName);
        this.iUnitsPerBlock = new SimpleIntegerProperty(iUnitsPerBlock);
        this.iBlocks = new SimpleIntegerProperty(iBlocks);
        this.iWeightPerBlock = new SimpleStringProperty(iWeightPerBlock);
        this.iBuyingPrice = new SimpleFloatProperty(iBuyingPrice);
        this.iExpireDate = new SimpleStringProperty(iExpireDate);
        this.iAddedDate = new SimpleStringProperty(iAddedDate);
        this.iMinQuantityLimit = new SimpleIntegerProperty(iMinQuantityLimit);
        this.iAvailableQuantity = new SimpleIntegerProperty(iAvailableQuantity);
        this.iStatus = new SimpleStringProperty(UtilityMethod.setItemAvailabilityStatus(iAvailableQuantity, iMinQuantityLimit));
    }

    public ItemStock() {

    }

    public String getiID() {
        return iID.get();
    }

    public StringProperty iIDProperty() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = new SimpleStringProperty(iID);
    }

    public String getiName() {
        return iName.get();
    }

    public StringProperty iNameProperty() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = new SimpleStringProperty(iName);
    }

    public String getiSIID() {
        return iSIID.get();
    }

    public StringProperty iSIIDProperty() {
        return iSIID;
    }

    public void setiSIID(String iSIID) {
        this.iSIID = new SimpleStringProperty(iSIID);
    }

    public String getiSISupplierName() {
        return iSISupplierName.get();
    }

    public StringProperty iSISupplierNameProperty() {
        return iSISupplierName;
    }

    public void setiSISupplierName(String iSISupplierName) {
        this.iSISupplierName = new SimpleStringProperty(iSISupplierName);
    }

    public int getiUnitsPerBlock() {
        return iUnitsPerBlock.get();
    }

    public IntegerProperty iUnitsPerBlockProperty() {
        return iUnitsPerBlock;
    }

    public void setiUnitsPerBlock(int iUnitsPerBlock) {
        this.iUnitsPerBlock = new SimpleIntegerProperty(iUnitsPerBlock);
    }

    public int getiBlocks() {
        return iBlocks.get();
    }

    public IntegerProperty iBlocksProperty() {
        return iBlocks;
    }

    public void setiBlocks(int iBlocks) {
        this.iBlocks = new SimpleIntegerProperty(iBlocks);
    }

    public String getiWeightPerBlock() {
        return iWeightPerBlock.get();
    }

    public StringProperty iWeightPerBlockProperty() {
        return iWeightPerBlock;
    }

    public void setiWeightPerBlock(String iWeightPerBlock) {
        this.iWeightPerBlock = new SimpleStringProperty(iWeightPerBlock);
    }

    public float getiBuyingPrice() {
        return iBuyingPrice.get();
    }

    public FloatProperty iBuyingPriceProperty() {
        return iBuyingPrice;
    }

    public void setiBuyingPrice(Float iBuyingPrice) {
        this.iBuyingPrice = new SimpleFloatProperty(iBuyingPrice);
    }

    public String getiExpireDate() {
        return iExpireDate.get();
    }

    public StringProperty iExpireDateProperty() {
        return iExpireDate;
    }

    public void setiExpireDate(String iExpireDate) {
        this.iExpireDate = new SimpleStringProperty(iExpireDate);
    }

    public String getiAddedDate() {
        return String.valueOf(LocalDate.now());
    }

    public StringProperty iAddedDateProperty() {
        return iAddedDate;
    }

    public void setiAddedDate(String iAddedDate) {
        this.iAddedDate = new SimpleStringProperty(iAddedDate);
    }

    public int getiMinQuantityLimit() {
        return iMinQuantityLimit.get();
    }

    public IntegerProperty iMinQuantityLimitProperty() {
        return iMinQuantityLimit;
    }

    public void setiMinQuantityLimit(int iMinQuantityLimit) {
        this.iMinQuantityLimit = new SimpleIntegerProperty(iMinQuantityLimit);
    }

    public int getiAvailableQuantity() {
        return iAvailableQuantity.get();
    }

    public int getiInitialAvailableQuantity(){ return iUnitsPerBlock.get() * iBlocks.get(); }

    public IntegerProperty iAvailableQuantityProperty() {
        return iAvailableQuantity;
    }

    public void setiAvailableQuantity(int iAvailableQuantity) {
        this.iAvailableQuantity = new SimpleIntegerProperty(iAvailableQuantity);
    }

    public String getiStatus() {
        return iStatus.getName();
    }

    public StringProperty iStatusProperty() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = new SimpleStringProperty(iStatus);
    }
}
