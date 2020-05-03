package util.query;

public class IncomeStatementQueries {
    public static final String LOAD_DATA_QUERY = "SELECT * FROM incomestatement ";
    public static final String LOAD_SPECIFIC_DATA_QUERY = "SELECT * FROM incomestatement WHERE ISType = ? AND ISPeriod = ?";
    public static final String INSERT_DATA_QUERY = "INSERT INTO incomestatement ( ISType, ISPeriod, ISAmount) VALUES(?, ?, ?)";
    public static final String UPDATE_DATA_QUERRY = "UPDATE incomestatement SET ISType = ?, ISPeriod = ?, ISAmount = ? WHERE ISID = ?";
}
