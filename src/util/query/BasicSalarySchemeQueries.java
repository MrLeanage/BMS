package util.query;

public class BasicSalarySchemeQueries {
    public static final String LOAD_BASIC_SALARY_SCHEME_DATA_QUERY = "SELECT * FROM basicsalaryscheme";
    public static final String LOAD_SPECIFIC_BASIC_SALARY_SCHEME_DATA_QUERY = "SELECT * FROM basicsalaryscheme WHERE BSSID = ?";
    public static final String INSERT_BASIC_SALARY_SCHEME_DATA_QUERY = "INSERT INTO basicsalaryscheme (BSSTitle, BSSAmount, BSSAddedDate) VALUES( ?, ?, ?)";
    public static final String UPDATE_BASIC_SALARY_SCHEME_DATA_QUERY = "UPDATE basicsalaryscheme SET BSSTitle = ?, BSSAmount = ?, BSSAddedDate = ? WHERE BSSID = ?";
    public static final String DELETE_BASIC_SALARY_SCHEME_DATA_QUERY = "DELETE FROM basicsalaryscheme WHERE BSSID = ? ";

}
