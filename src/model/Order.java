package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class Order {
    private StringProperty oID = null;
    private StringProperty oOMID = null;
    private StringProperty oOMName = null;
    private StringProperty oType = null;
    private StringProperty oDetails = null;
    private IntegerProperty oQuantity = null;
    private StringProperty oDeliveryDate = null;
    private StringProperty oDeliveryTime = null;
    private StringProperty oCustomerName = null;
    private StringProperty oCustomerNIC = null;
    private StringProperty oCustomerPhone = null;
    private StringProperty oTakenDate = null;
    private StringProperty oTakenTime = null;
    private StringProperty oTakenUID = null;
    private StringProperty oStatus = null;
    private StringProperty oDeliveredDate = null;
    private StringProperty oDeliveredTime = null;
    private StringProperty oProcessingStatus = null;
    private FloatProperty oAdvancePay = null;
    private FloatProperty oTotal = null;

    public Order() {
    }

    public Order(String oID, String oOMID, String oOMName, String oType, String oDetails, Integer oQuantity, String oDeliveryDate, String oDeliveryTime, String oCustomerName, String oCustomerNIC, String oCustomerPhone, String oTakenDate, String oTakenTime, String oTakenUID, String oStatus, String oDeliveredDate, String oDeliveredTime, String oProcessingStatus, Float oMPrice) {

        this.oID = new SimpleStringProperty(UtilityMethod.addPrefix("OR", oID));
        this.oOMID = new SimpleStringProperty(UtilityMethod.addPrefix("OM", oOMID));
        this.oOMName = new SimpleStringProperty(oOMName);
        this.oType = new SimpleStringProperty(oType);
        this.oDetails = new SimpleStringProperty(oDetails);
        this.oQuantity = new SimpleIntegerProperty(oQuantity);
        this.oDeliveryDate = new SimpleStringProperty(oDeliveryDate);
        this.oDeliveryTime = new SimpleStringProperty(oDeliveryTime);
        this.oCustomerName = new SimpleStringProperty(oCustomerName);
        this.oCustomerNIC = new SimpleStringProperty(oCustomerNIC);
        this.oCustomerPhone = new SimpleStringProperty(oCustomerPhone);
        this.oTakenDate = new SimpleStringProperty(oTakenDate);
        this.oTakenTime = new SimpleStringProperty(oTakenTime);
        this.oTakenUID = new SimpleStringProperty(oTakenUID);
        this.oStatus = new SimpleStringProperty(oStatus);
        this.oDeliveredDate = new SimpleStringProperty(oDeliveredDate);
        this.oDeliveredTime = new SimpleStringProperty(oDeliveredTime);
        this.oProcessingStatus = new SimpleStringProperty(oProcessingStatus);
        this.oAdvancePay = new SimpleFloatProperty(UtilityMethod.advancePayCalculator(oQuantity, oMPrice));
        this.oTotal = new SimpleFloatProperty(oQuantity * oMPrice);
    }

    public String getoID() {
        return oID.get();
    }

    public StringProperty oIDProperty() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = new SimpleStringProperty(oID);
    }

    public String getoOMID() {
        return oOMID.get();
    }

    public StringProperty oOMIDProperty() {
        return oOMID;
    }

    public void setoOMID(String oOMID) {
        this.oOMID = new SimpleStringProperty(oOMID);
    }

    public String getoOMName() {
        return oOMName.get();
    }

    public StringProperty oOMNameProperty() {
        return oOMName;
    }

    public void setoOMName(String oOMName) {
        this.oOMName = new SimpleStringProperty(oOMName);
    }

    public String getoType() {
        return oType.get();
    }

    public StringProperty oTypeProperty() {
        return oType;
    }

    public void setoType(String oType) {
        this.oType = new SimpleStringProperty(oType);
    }

    public String getoDetails() {
        return oDetails.get();
    }

    public StringProperty oDetailsProperty() {
        return oDetails;
    }

    public void setoDetails(String oDetails) {
        this.oDetails = new SimpleStringProperty(oDetails);
    }

    public int getoQuantity() {
        return oQuantity.get();
    }

    public IntegerProperty oQuantityProperty() {
        return oQuantity;
    }

    public void setoQuantity(int oQuantity) {
        this.oQuantity  = new SimpleIntegerProperty(oQuantity);
    }

    public String getoDeliveryDate() {
        return oDeliveryDate.get();
    }

    public StringProperty oDeliveryDateProperty() {
        return oDeliveryDate;
    }

    public void setoDeliveryDate(String oDeliveryDate) {
        this.oDeliveryDate = new SimpleStringProperty(oDeliveryDate);
    }

    public String getoDeliveryTime() {
        return oDeliveryTime.get();
    }

    public StringProperty oDeliveryTimeProperty() {
        return oDeliveryTime;
    }

    public void setoDeliveryTime(String oDeliveryTime) {
        this.oDeliveryTime = new SimpleStringProperty(oDeliveryTime);
    }

    public String getoCustomerName() {
        return oCustomerName.get();
    }

    public StringProperty oCustomerNameProperty() {
        return oCustomerName;
    }

    public void setoCustomerName(String oCustomerName) {
        this.oCustomerName = new SimpleStringProperty(oCustomerName);
    }

    public String getoCustomerNIC() {
        return oCustomerNIC.get();
    }

    public StringProperty oCustomerNICProperty() {
        return oCustomerNIC;
    }

    public void setoCustomerNIC(String oCustomerNIC) {
        this.oCustomerNIC = new SimpleStringProperty(oCustomerNIC);
    }

    public String getoCustomerPhone() {
        return oCustomerPhone.get();
    }

    public StringProperty oCustomerPhoneProperty() {
        return oCustomerPhone;
    }

    public void setoCustomerPhone(String oCustomerPhone) {
        this.oCustomerPhone = new SimpleStringProperty(oCustomerPhone);
    }

    public String getoTakenDate() {
        return oTakenDate.get();
    }

    public StringProperty oTakenDateProperty() {
        return oTakenDate;
    }

    public void setoTakenDate(String oTakenDate) {
        this.oTakenDate = new SimpleStringProperty(oTakenDate);
    }

    public String getoTakenTime() {
        return oTakenTime.get();
    }

    public StringProperty oTakenTimeProperty() {
        return oTakenTime;
    }

    public void setoTakenTime(String oTakenTime) {
        this.oTakenTime = new SimpleStringProperty(oTakenTime);
    }

    public String getoTakenUID() {
        return oTakenUID.get();
    }

    public StringProperty oTakenUIDProperty() {
        return oTakenUID;
    }

    public void setoTakenUID(String oTakenUID) {
        this.oTakenUID = new SimpleStringProperty(oTakenUID);
    }

    public String getoStatus() {
        return oStatus.get();
    }

    public StringProperty oStatusProperty() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = new SimpleStringProperty(oStatus);
    }

    public String getoDeliveredDate() {
        return oDeliveredDate.get();
    }

    public StringProperty oDeliveredDateProperty() {
        return oDeliveredDate;
    }

    public void setoDeliveredDate(String oDeliveredDate) {
        this.oDeliveredDate = new SimpleStringProperty(oDeliveredDate);
    }

    public String getoDeliveredTime() {
        return oDeliveredTime.get();
    }

    public StringProperty oDeliveredTimeProperty() {
        return oDeliveredTime;
    }

    public void setoDeliveredTime(String oDeliveredTime) {
        this.oDeliveredTime = new SimpleStringProperty(oDeliveredTime);
    }

    public String getoProcessingStatus() {
        return oProcessingStatus.get();
    }

    public StringProperty oProcessingStatusProperty() {
        return oProcessingStatus;
    }

    public void setoProcessingStatus(String oProcessingStatus) {
        this.oProcessingStatus = new SimpleStringProperty(oProcessingStatus);
    }
    public Float getoAdvancePay() {
        return oAdvancePay.get();
    }

    public FloatProperty oAdvancePayProperty() {
        return oAdvancePay;
    }
    public Float getoTotal() {
        return oTotal.get();
    }

    public FloatProperty oTotalProperty() {
        return oTotal;
    }
}
