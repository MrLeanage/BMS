package util.query;

public class UsedItemStockQueries {
    public static final String LOAD_USED_ITEM_STOCK_DATA_QUERY = "SELECT * FROM itemsused";
    public static final String INSERT_USED_ITEM_STOCK_DATA_QUERY = "INSERT INTO itemsused (IUIID, IUDescription, IUNoOfBlocks, IUNoOfUnits, IUDate, IUTime) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USED_ITEM_STOCK_DATA_QUERY = "UPDATE itemsused SET IUIID = ?, IUDescription = ?, IUNoOfBlocks = ? , IUNoOfUnits = ?, IUDate = ?, IUTime = ? WHERE IUID = ?";
    public static final String DELETE_USED_ITEM_STOCK_DATA_QUERY = "DELETE FROM itemsused WHERE IUID = ? ";
    public static final String SEARCH_USED_ITEM_STOCK_DATA_QUERY = "SELECT IUIID, IUDescription, IUNoOfBlocks, IUNoOfUnits, IUDate, IUTime FROM itemsused";
    public static final String GET_LAST_ID_DATA_QUERY = "SELECT MAX(IUID) FROM itemsused";

}
