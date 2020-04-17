package util.query;

public class ItemWithdrawQueries {
    public static final String LOAD_ITEM_WITHDRAW_DATA_QUERY = "SELECT * FROM itemwithdraw";
    public static final String LOAD_SPECIFIC_USER_ITEM_WITHDRAW_DATA_QUERY = "SELECT * FROM itemwithdraw WHERE IWUser = ?";
    public static final String INSERT_ITEM_WITHDRAW_DATA_QUERY = "INSERT INTO itemwithdraw (IWIID, IWDescription, IWQuantity, IWUser, IWDate, IWTime) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ALLOWANCE_DATA_QUERY = "UPDATE allowance SET ATitle = ?, ADescription = ?, AType = ? , AValue = ? WHERE AID = ?";
    public static final String DELETE_ALLOWANCE_DATA_QUERY = "DELETE FROM allowance WHERE AID = ? ";
    public static final String LOAD_SPECIFIC_EXISTING_ALLOWANCE_DATA_QUERY = "SELECT * FROM allowance WHERE AID = ?";
    public static final String LOAD_SPECIFIC_NEW_ALLOWANCE_DATA_QUERY = "SELECT * FROM allowance WHERE AID NOT IN (SELECT APAID FROM allowancepay WHERE APEID = ?)";

}
