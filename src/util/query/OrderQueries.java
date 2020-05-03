package util.query;

public class OrderQueries {
    public static final String LOAD_ORDER_DATA_QUERY = "SELECT * FROM orders";
    public static final String LOAD_ORDER_DATA_BY_PAYMENT_QUERY = "SELECT * FROM orders WHERE OStatus != ?";
    public static final String LOAD_ORDER_PENDING_QUERY = "SELECT * FROM orders WHERE OProcessingStatus = ? AND OStatus != ?";
    public static final String LOAD_ORDER_DATA_WITH_STATUS_QUERY = "select * from orders WHERE OProcessingStatus = ?";
    public static final String INSERT_ORDER_DATA_QUERY = "INSERT INTO orders ( OOMID, OType, ODetails, OQuantity, ODeliveryDate, ODeliveryTime, OCustomerName, OCustomerNIC, OCustomerPhone, OTakenDate, OTakenTime, OTakenUID, OStatus, OProcessingStatus) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String LOAD_SPECIFIC_ORDER_DATA_QUERY = "SELECT * FROM orders WHERE OID = ?";
    public static final String UPDATE_ORDER_DATA_QUERY = "UPDATE orders SET OOMID = ?, OType = ?, ODetails = ?, OQuantity = ?, ODeliveryDate = ?, ODeliveryTime = ?, OCustomerName = ?, OCustomerNIC = ?, OCustomerPhone = ?, OTakenDate = ?, OTakenTime = ?, OTakenUID = ?, OStatus = ?, OProcessingStatus = ? WHERE OID = ?";
    public static final String UPDATE_ORDER_STATUS_QUERY = "UPDATE orders SET  OProcessingStatus = ? WHERE OID = ?";
    public static final String UPDATE_ORDER_PAY_STATUS_QUERY = "UPDATE orders SET  OStatus = ? WHERE OID = ?";
    public static final String UPDATE_ORDER_COMPLETE_STATUS_QUERY = "UPDATE orders SET  ODeliveredDate = ?, ODeliveredTime = ?, OProcessingStatus = ? WHERE OID = ?";
    public static final String DELETE_ORDER_DATA_QUERY = "DELETE FROM orders WHERE OID = ? ";

}
