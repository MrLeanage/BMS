package util.query;

public class AllowancePayQueries {
    public static final String LOAD_SPECIFIC_ALLOWANCE_PAY_DATA_QUERY = "SELECT APAID FROM allowancepay WHERE APEID = ?";
    public static final String INSERT_ALLOWANCE_PAY_DATA_QUERY = "INSERT INTO allowancepay (APEID, APAID) VALUES( ?, ?)";
    public static final String DELETE_ALLOWANCE_PAY_DATA_QUERY = "DELETE FROM allowancepay WHERE APEID = ? AND APAID = ? ";




}
