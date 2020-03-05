package util.query;

public class BakeryProductQueries {
    public static final String LOAD_BAKERY_PRODUCT_DATA_QUERY = "SELECT * FROM bakeryproduct";
    public static final String INSERT_BAKERY_PRODUCT_DATA_QUERY = "INSERT INTO bakeryproduct (BPName, BPType, BPWeight, BPDescription, BPPrice, BPStatus) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ITEM_STOCK_DATA_QUERY = "UPDATE bakeryproduct SET BPName = ?, BPType = ?, BPWeight = ? , BPDescription = ?, BPPrice = ?, BPStatus = ? WHERE BPID = ?";
    public static final String DELETE_ITEM_STOCK_DATA_QUERY = "DELETE FROM bakeryproduct WHERE BPID = ? ";

}
