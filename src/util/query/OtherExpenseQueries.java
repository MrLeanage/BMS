package util.query;

public class OtherExpenseQueries {
    public static final String LOAD_EXPENSE_DATA_QUERY = "SELECT * FROM otherexpenses";
    public static final String LOAD_SPECIFIC_EXPENSE_DATA_QUERY = "SELECT * FROM otherexpenses WHERE OEID = ?";
    public static final String INSERT_EXPENSE_DATA_QUERY = "INSERT INTO otherexpenses (OETitle, OEDescription, OEPeriod, OEAmount, OEPaidDate, OEAddedDate) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_EXPENSE_DATA_QUERY = "UPDATE otherexpenses SET OETitle = ?, OEDescription = ?, OEPeriod = ? , OEAmount = ?, OEPaidDate = ?, OEAddedDate = ? WHERE OEID = ?";
    public static final String DELETE_EXPENSE_DATA_QUERY = "DELETE FROM otherexpenses WHERE OEID = ? ";

}
