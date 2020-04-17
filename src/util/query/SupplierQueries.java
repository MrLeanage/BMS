package util.query;

public class SupplierQueries {
    public static final String LOAD_DATA_QUERY = "SELECT  * FROM supplierinfo";
    public static final String LOAD_SPECIFIC_SUPPLIER_DATA_QUERY = "SELECT  * FROM supplierinfo WHERE SIID = ?";
    public static final String INSERT_DATA_QUERY = "INSERT INTO supplierinfo (SIName, SIAddress, SIPhone1, SIPhone2, SIEmail, SIType, SIBankName, SIAccNo) VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_SUPPLIER_DATA_QUERY = "SELECT * FROM supplierinfo WHERE SIID =";
    public static final String UPDATE_DATA_QUERY = "UPDATE supplierinfo SET SIName = ?, SIAddress = ?, SIPhone1 = ? , SIPhone2 = ?, SIEmail = ?, SIType = ?, SIBankName = ?, SIAccNo = ? WHERE SIID = ?";
    public static final String DELETE_DATA_QUERY = "DELETE FROM supplierinfo WHERE SIID = ? ";
    public static final String SEARCH_DATA_QUERY = "SELECT  SIID, SIName, SIAddress, SIPhone1, SIPhone2, SIEmail, SIType, SIBankName, SIAccNo FROM supplierinfo";

}
