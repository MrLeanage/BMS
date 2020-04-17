package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AllowancePay {
    private StringProperty aPEID = null;
    private StringProperty aPAID = null;

    public AllowancePay() {
    }

    public AllowancePay(String aPEID, String aPAID) {
        this.aPEID = new SimpleStringProperty(aPEID);
        this.aPAID = new SimpleStringProperty(aPAID);
    }

    public String getaPEID() {
        return aPEID.get();
    }

    public StringProperty aPEIDProperty() {
        return aPEID;
    }

    public void setaPEID(String aPEID) {
        this.aPEID = new SimpleStringProperty(aPEID);
    }

    public String getaPAID() {
        return aPAID.get();
    }

    public StringProperty aPAIDProperty() {
        return aPAID;
    }

    public void setaPAID(String aPAID) {
        this.aPAID = new SimpleStringProperty(aPAID);
    }
}
