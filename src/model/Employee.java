package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class Employee {
    private StringProperty eID = null;
    private StringProperty eName = null;
    private StringProperty eNIC = null;
    private StringProperty eAddress = null;
    private StringProperty eGender = null;
    private StringProperty eDOB = null;
    private StringProperty eTitle = null;
    private IntegerProperty ePhone = null;
    private StringProperty eBankName = null;
    private LongProperty eAccNo = null;
    private StringProperty eBSSID = null;

    public Employee(String eID, String eName, String eNIC, String eAddress, String eGender, String eDOB, String eTitle, Integer ePhone, String eBankName, Long eAccNo, String eBSSID) {
        this.eID = new SimpleStringProperty(UtilityMethod.addPrefix("E", eID));
        this.eName = new SimpleStringProperty(eName);
        this.eGender = new SimpleStringProperty(eGender);
        this.eNIC = new SimpleStringProperty(eNIC);
        this.eAddress = new SimpleStringProperty(eAddress);
        this.eDOB = new SimpleStringProperty(eDOB);
        this.eTitle = new SimpleStringProperty(eTitle);
        this.ePhone = new SimpleIntegerProperty(ePhone);
        this.eBankName = new SimpleStringProperty(eBankName);
        this.eAccNo = new SimpleLongProperty(eAccNo);
        this.eBSSID = new SimpleStringProperty(UtilityMethod.addPrefix("BSS", eBSSID));
    }

    public Employee() {

    }

    public String geteID() {
        return eID.get();
    }

    public StringProperty eIDProperty() {
        return eID;
    }

    public void seteID(String eID) {
        this.eID = new SimpleStringProperty(eID);
    }

    public String geteName() {
        return eName.get();
    }

    public StringProperty eNameProperty() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = new SimpleStringProperty(eName);
    }

    public String geteGender() {
        return eGender.get();
    }

    public StringProperty eGenderProperty() {
        return eGender;
    }

    public void seteGender(String eGender) {
        this.eGender = new SimpleStringProperty(eGender);
    }

    public String geteNIC() {
        return eNIC.get();
    }

    public StringProperty eNICProperty() {
        return eNIC;
    }

    public void seteNIC(String eNIC) {
        this.eNIC = new SimpleStringProperty(eNIC);
    }

    public String geteAddress() {
        return eAddress.get();
    }

    public StringProperty eAddressProperty() {
        return eAddress;
    }

    public void seteAddress(String eAddress) {
        this.eAddress = new SimpleStringProperty(eAddress);
    }

    public String geteDOB() {
        return eDOB.get();
    }

    public StringProperty eDOBProperty() {
        return eDOB;
    }

    public void seteDOB(String eDOB) {
        this.eDOB = new SimpleStringProperty(eDOB);
    }

    public String geteTitle() {
        return eTitle.get();
    }

    public StringProperty eTitleProperty() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = new SimpleStringProperty(eTitle);
    }

    public int getePhone() {
        return ePhone.get();
    }

    public IntegerProperty ePhoneProperty() {
        return ePhone;
    }

    public void setePhone(int ePhone) {
        this.ePhone = new SimpleIntegerProperty(ePhone);
    }

    public String geteBankName() {
        return eBankName.get();
    }

    public StringProperty eBankNameProperty() {
        return eBankName;
    }

    public void seteBankName(String eBankName) {
        this.eBankName = new SimpleStringProperty(eBankName);
    }

    public Long geteAccNo() {
        return eAccNo.get();
    }

    public LongProperty eAccNoProperty() {
        return eAccNo;
    }

    public void seteAccNo(Long eAccNo) {
        this.eAccNo = new SimpleLongProperty(eAccNo);
    }

    public String geteBSSID() {
        return eBSSID.get();
    }

    public StringProperty eBSSIDProperty() {
        return eBSSID;
    }

    public void seteBSSID(String eBSSID) {
        this.eBSSID = new SimpleStringProperty(eBSSID);
    }
}
