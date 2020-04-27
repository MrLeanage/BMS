package util.query;

public class PaySheetQueries {
    public static final String LOAD_PAYSHEET_DATA_QUERY = "SELECT * FROM paysheet";
    public static final String LOAD_SPECIFIC_EID_PAYSHEET_DATA_QUERY = "SELECT * FROM paysheet WHERE PSEID = ?";
    public static final String INSERT_PAYSHEET_DATA_QUERY = "INSERT INTO paysheet (PSEID, PSEName, PSENIC, PSBSSTitle, PSBSSAmount, PSTotalAllowances, PSBank, PSAccount, PSGeneratedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_LAST_INSERTED_RECORD_ID = "SELECT MAX(PSID) FROM paysheet";
    public static final String GET_SPECIFIC_DATE_PAYSHEET = "SELECT PSID, PSGeneratedDate FROM paysheet WHERE PSEID = ?";
}
