package model;

import javafx.beans.property.*;
import util.utility.UtilityMethod;

public class PaySheet {
    private StringProperty pSID = null;
    private StringProperty pSEID = null;
    private StringProperty pSEName = null;
    private StringProperty pSNIC = null;
    private StringProperty pSBSSTitle = null;
    private DoubleProperty pSBSSAmount = null;
    private DoubleProperty pSATotalAmount = null;
    private DoubleProperty pSNetSalary = null;
    private StringProperty pSBankName = null;
    private LongProperty pSAccountNo = null;
    private StringProperty pSDate = null;

    public PaySheet() {
    }

    public PaySheet(String pSID, String pSEID, String pSEName, String pSNIC, String pSBSSTitle, Double pSBSSAmount, Double pSATotalAmount, String pSBankName, Long pSAccountNo, String pSDate) {
        this.pSID = new SimpleStringProperty(UtilityMethod.addPrefix("P", pSID));
        this.pSEID = new SimpleStringProperty(UtilityMethod.addPrefix("E", pSEID));
        this.pSEName = new SimpleStringProperty(pSEName);
        this.pSNIC = new SimpleStringProperty(pSNIC);
        this.pSBSSTitle = new SimpleStringProperty(pSBSSTitle);
        this.pSBSSAmount = new SimpleDoubleProperty(pSBSSAmount);
        this.pSATotalAmount = new SimpleDoubleProperty(pSATotalAmount);
        this.pSNetSalary = new SimpleDoubleProperty(pSBSSAmount + pSATotalAmount);
        this.pSDate = new SimpleStringProperty(pSDate);
        this.pSBankName = new SimpleStringProperty(pSBankName);
        this.pSAccountNo = new SimpleLongProperty(pSAccountNo);
    }

    public String getpSID() {
        return pSID.get();
    }

    public StringProperty pSIDProperty() {
        return pSID;
    }

    public void setpSID(String pSID) {
        this.pSID = new SimpleStringProperty(pSID);
    }

    public String getpSEID() {
        return pSEID.get();
    }

    public StringProperty pSEIDProperty() {
        return pSEID;
    }

    public void setpSEID(String pSEID) {
        this.pSEID = new SimpleStringProperty(pSEID);
    }

    public String getpSEName() {
        return pSEName.get();
    }

    public StringProperty pSENameProperty() {
        return pSEName;
    }

    public void setpSEName(String pSEName) {
        this.pSEName = new SimpleStringProperty(pSEName);
    }

    public String getpSNIC() {
        return pSNIC.get();
    }

    public StringProperty pSNICProperty() {
        return pSNIC;
    }

    public void setpSNIC(String pSNIC) {
        this.pSNIC = new SimpleStringProperty(pSNIC);
    }

    public String getpSBSSTitle() {
        return pSBSSTitle.get();
    }

    public StringProperty pSBSSTitleProperty() {
        return pSBSSTitle;
    }

    public void setpSBSSTitle(String pSBSSTitle) {
        this.pSBSSTitle = new SimpleStringProperty(pSBSSTitle);
    }

    public double getpSBSSAmount() {
        return pSBSSAmount.get();
    }

    public DoubleProperty pSBSSAmountProperty() {
        return pSBSSAmount;
    }

    public void setpSBSSAmount(double pSBSSAmount) {
        this.pSBSSAmount = new SimpleDoubleProperty(pSBSSAmount);
    }

    public double getpSATotalAmount() {
        return pSATotalAmount.get();
    }

    public DoubleProperty pSATotalAmountProperty() {
        return pSATotalAmount;
    }

    public void setpSATotalAmount(double pSATotalAmount) {
        this.pSATotalAmount = new SimpleDoubleProperty(pSATotalAmount);
    }

    public double getpSNetSalary() {
        return pSNetSalary.get();
    }

    public DoubleProperty pSNetSalaryProperty() {
        return pSNetSalary;
    }

    public void setpSNetSalary(double pSNetSalary) {
        this.pSNetSalary = new SimpleDoubleProperty(pSNetSalary);
    }

    public String getpSDate() {
        return pSDate.get();
    }

    public StringProperty pSDateProperty() {
        return pSDate;
    }

    public void setpSDate(String pSDate) {
        this.pSDate = new SimpleStringProperty(pSDate);
    }

    public String getpSBankName() {
        return pSBankName.get();
    }

    public StringProperty pSBankNameProperty() {
        return pSBankName;
    }

    public void setpSBankName(String pSBankName) {
        this.pSBankName = new SimpleStringProperty(pSBankName);
    }

    public Long getpSAccountNo() {
        return pSAccountNo.get();
    }

    public LongProperty pSAccountNoProperty() {
        return pSAccountNo;
    }

    public void setpSAccountNo(Long pSAccountNo) {
        this.pSAccountNo = new SimpleLongProperty(pSAccountNo);
    }
}
