package util.query;

public class PurchaseProductQueries {
    public static final String LOAD_PURCHASE_DATA_QUERY = "SELECT * FROM purchase";
    public static final String LOAD_PURCHASE_DATA_BY_STATUS_QUERY = "SELECT * FROM purchase WHERE PStatus = ?";
    public static final String LOAD_PURCHASE_DATA_BY_ID_QUERY = "SELECT * FROM purchase WHERE PID = ?";
    public static final String INSERT_PURCHASE_DATA_QUERY = "INSERT INTO PURCHASE (PItemID, PSupplierID, PType, PDate, PStatus) VALUES (?, ?, ?, ?, ?)";

    public static final String FIND_PURCHASE_STOCK_DATA_QUERRY ="SELECT * FROM purchase WHERE PType = 'Stock' AND PItemID = ";
    public static final String FIND_PURCHASE_AGENCY_DATA_QUERRY ="SELECT * FROM purchase WHERE PType = 'Agency' AND PItemID = ";

    public static final String UPDATE_PURCHASE_AGENCY_DATA_QUERRY = "UPDATE PURCHASE SET PSupplierID = ?, PDate = ? WHERE PItemID = ? AND PType = 'Agency'";
    public static final String UPDATE_PURCHASE_STOCK_DATA_QUERRY = "UPDATE PURCHASE SET PSupplierID = ?, PDate = ? WHERE PItemID = ? AND PType = 'Stock'";
    public static final String UPDATE_PURCHASE_PAYMENT_STATUS = "UPDATE purchase SET PStatus = ? WHERE PID = ?";
    public static final String UPDATE_PURCHASE_BANK_INFO = "UPDATE purchase SET PStatus = ?, PBankInfo = ?, PBankPaidDate = ? WHERE PID = ?";

    public static final String DELETE_PURCHASE_AGENCY_DATA_QUERRY = "DELETE FROM PURCHASE WHERE PItemID = ? AND PType = 'Agency'";
    public static final String DELETE_PURCHASE_STOCK_DATA_QUERRY = "DELETE FROM PURCHASE WHERE PItemID = ? AND PType = 'Stock'";
}
