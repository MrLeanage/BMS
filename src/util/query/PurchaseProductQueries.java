package util.query;

public class PurchaseProductQueries {
    public static final String LOAD_PURCHASE_DATA_QUERRY = "SELECT * FROM purchase";
    public static final String INSERT_PURCHASE_DATA_QUERRY = "INSERT INTO PURCHASE (PItemID, PSupplierID, PDate, PStatus) VALUES (?, ?, ?, ?)";
    public static final String FIND_PURCHASE_DATA_QUERRY ="SELECT * FROM purchase WHERE PStatus = 'Pending' AND PItemID = ";
    public static final String UPDATE_PURCHASE_DATA_QUERRY = "UPDATE PURCHASE SET PSupplierID = ?, PDate = ? WHERE PItemID = ? AND PStatus = 'Pending'";
    public static final String DELETE_PURCHASE_DATA_QUERRY = "DELETE FROM PURCHASE WHERE PItemID = ?";
}
