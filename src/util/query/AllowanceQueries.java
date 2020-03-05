package util.query;

public class AllowanceQueries {
    public static final String LOAD_ALLOWANCE_DATA_QUERY = "SELECT * FROM allowance";
    public static final String INSERT_ALLOWANCE_DATA_QUERY = "INSERT INTO allowance (ATitle, ADescription, AType, AValue) VALUES( ?, ?, ?, ?)";
    public static final String UPDATE_ALLOWANCE_DATA_QUERY = "UPDATE allowance SET ATitle = ?, ADescription = ?, AType = ? , AValue = ? WHERE AID = ?";
    public static final String DELETE_ALLOWANCE_DATA_QUERY = "DELETE FROM allowance WHERE AID = ? ";

}
