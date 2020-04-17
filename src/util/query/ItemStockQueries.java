package util.query;

public class ItemStockQueries {
    public static final String LOAD_ITEM_STOCK_DATA_QUERY = "SELECT * FROM item";
    public static final String LOAD_SPECIFIC_ITEM_STOCK_DATA_QUERY = "SELECT * FROM item WHERE IID = ?";
    public static final String INSERT_ITEM_STOCK_DATA_QUERY = "INSERT INTO item (IName, IUnitsPerBlock, IBlocks, IWeightOfUnit, IBuyingPricePerUnit, IMinQuantityLimit, IExpireDate, IAddedDate, IAvailableQuantity) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ITEM_STOCK_DATA_QUERY = "UPDATE item SET IName = ?, IUnitsPerBlock = ?, IBlocks = ? , IWeightOfUnit = ?, IBuyingPricePerUnit = ?, IMinQuantityLimit = ?, IExpireDate = ?, IAddedDate = ?, IAvailableQuantity = ? WHERE IID = ?";
    public static final String UPDATE_ITEM_STOCK_QUANTITY_DATA_QUERY = "UPDATE item SET IAvailableQuantity = ? WHERE IID = ?";
    public static final String DELETE_ITEM_STOCK_DATA_QUERY = "DELETE FROM item WHERE IID = ? ";
    public static final String GET_LAST_ID_DATA_QUERY = "SELECT MAX(IID) FROM item";

}
