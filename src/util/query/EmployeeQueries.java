package util.query;

public class EmployeeQueries {
    public static final String LOAD_EMPLOYEE_DATA_QUERY = "SELECT * FROM employee";
    public static final String INSERT_EMPLOYEE_DATA_QUERY = "INSERT INTO employee (EName, EGender, ENIC, EAddress, EDOB, ETitle, EPhone, EBankName, EAccNo, EBSSID) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_EMPLOYEE_DATA_QUERY = "UPDATE employee SET EName = ?, EGender = ?, ENIC = ?, EAddress = ?, EDOB = ?, ETitle = ?, EPhone = ?, EBankName = ?, EAccNo = ?, EBSSID = ? WHERE EID = ?";
    public static final String DELETE_EMPLOYEE_DATA_QUERY = "DELETE FROM employee WHERE EID = ? ";

}
