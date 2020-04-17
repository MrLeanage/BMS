package util.query;

import javafx.beans.property.StringProperty;

public class BillingQueries {
    public static final String LOAD_CASHIER_BILLING_DATA_QUERY = "SELECT * FROM bill WHERE BCashierID = ? AND BClearance = ?";
    public static final String LOAD_BILLING_DATA_BY_STATUS_QUERY = "SELECT * FROM bill WHERE BClearance = ?";
    public static final String LOAD_SALES_DATA_QUERY = "SELECT * FROM salesitem WHERE SIBNo = ? ";
    public static final String INSERT_BILLING_DATA_QUERY = "INSERT INTO bill (BCashierID, BDate, BTime, BClearance) VALUES( ?, ?, ?, ?)";
    public static final String INSERT_SALE_ITEM_DATA_QUERY = "INSERT INTO salesitem (SIPID, SIPName, SIWeight, SIQuantity, SIUnitPrice, SITotalAmount, SIBNo, SIType) VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_LAST_INSERTED_RECORD_ID = "SELECT MAX(BNo) FROM bill";
    public static final String UPDATE_BILL_CLEARANCE = "UPDATE bill SET BClearance = ? WHERE BNo = ?";
}
