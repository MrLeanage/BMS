package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class Supplier {
    private StringProperty sIID = null;
    private StringProperty sIName = null;
    private StringProperty sIAddress = null;
    private IntegerProperty sIPhone1 = null;
    private IntegerProperty sIPhone2 = null;
    private StringProperty sIEmail = null;
    private StringProperty sIType = null;
    private StringProperty sIBank = null;
    private LongProperty sIAccNo = null;

    UtilityMethod utilityMethod = new UtilityMethod();
    public Supplier(String sIID, String sIName, String sIAddress, Integer sIPhone1, Integer sIPhone2, String sIEmail, String sIType, String sIBank, Long sIAccNo) {
        this.sIID = new SimpleStringProperty(utilityMethod.addPrefix("SI", sIID));
        this.sIName = new SimpleStringProperty(sIName);
        this.sIAddress =  new SimpleStringProperty(sIAddress);
        this.sIPhone1 = new SimpleIntegerProperty(sIPhone1);
        this.sIPhone2 = new SimpleIntegerProperty(sIPhone2);
        this.sIEmail = new SimpleStringProperty(sIEmail);
        this.sIType = new SimpleStringProperty(sIType);
        this.sIBank = new SimpleStringProperty(sIBank);
        this.sIAccNo = new SimpleLongProperty(sIAccNo);
    }

    public Supplier() {

    }

    public Supplier(String sIName, String sIAddress, int sIPhone1, int sIPhone2, String sIEmail, String sIType, String sIBank, Long sIAccNo) {
    }

    public String getsIID() {
        return sIID.get();
    }

    public StringProperty sIIDProperty() {
        return sIID;
    }

    public void setsIID(String sIID) {
        this.sIID = new SimpleStringProperty(sIID);
    }

    public String getsIName() {
        return sIName.get();
    }

    public StringProperty sINameProperty() {
        return sIName;
    }

    public void setsIName(String sIName) {

        this.sIName = new SimpleStringProperty(sIName);
    }

    public String getsIAddress() {
        return sIAddress.get();
    }

    public StringProperty sIAddressProperty() {
        return sIAddress;
    }

    public void setsIAddress(String sIAddress) {
        this.sIAddress = new SimpleStringProperty(sIAddress);
    }

    public int getsIPhone1() {
        return sIPhone1.get();
    }

    public IntegerProperty sIPhone1Property() {
        return sIPhone1;
    }

    public void setsIPhone1(int sIPhone1) {
        this.sIPhone1 = new SimpleIntegerProperty(sIPhone1);
    }

    public int getsIPhone2() {
        return sIPhone2.get();
    }

    public IntegerProperty sIPhone2Property() {
        return sIPhone2;
    }

    public void setsIPhone2(int sIPhone2) {
        this.sIPhone2 = new SimpleIntegerProperty(sIPhone2);
    }

    public String getsIEmail() {
        return sIEmail.get();
    }

    public StringProperty sIEmailProperty() {
        return sIEmail;
    }

    public void setsIEmail(String sIEmail) {
        this.sIEmail = new SimpleStringProperty(sIEmail);
    }

    public String getsIType() {
        return sIType.get();
    }

    public StringProperty sITypeProperty() {
        return sIType;
    }

    public void setsIType(String sIType) {
        this.sIType = new SimpleStringProperty(sIType);
    }

    public String getsIBank() {
        return sIBank.get();
    }

    public StringProperty sIBankProperty() {
        return sIBank;
    }

    public void setsIBank(String sIBank) {
        this.sIBank = new SimpleStringProperty(sIBank);
    }

    public Long getsIAccNo() {
        return sIAccNo.get();
    }

    public LongProperty sIAccNoProperty() {
        return sIAccNo;
    }

    public void setsIAccNo(Long sIAccNo) {
        this.sIAccNo = new SimpleLongProperty(sIAccNo);
    }
}
