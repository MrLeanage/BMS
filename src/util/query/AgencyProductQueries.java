package util.query;

public class AgencyProductQueries {
    public static final String LOAD_AGENCY_PRODUCT_DATA_QUERY = "SELECT * FROM agencyproduct";
    public static final String LOAD_SPECIFIC_AGENCY_PRODUCT_DATA_QUERY = "SELECT * FROM agencyproduct WHERE APID = ?";
    public static final String INSERT_AGENCY_PRODUCT_DATA_QUERY = "INSERT INTO agencyproduct (APName, APTotalUnits, APWeightOfUnit, APBuyingPricePerUnit, APMarketPricePerUnit, APSellingPricePerUnit, APMDate, APEDate, APADate, APDADate) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_AGENCY_PRODUCT_DATA_QUERY = "UPDATE agencyproduct SET APName = ?, APTotalUnits = ?, APWeightOfUnit = ? , APBuyingPricePerUnit = ?, APMarketPricePerUnit = ?, APSellingPricePerUnit = ?, APMDate = ?, APEDate = ?, APDADate = ? WHERE APID = ?";
    public static final String DELETE_AGENCY_PRODUCT_DATA_QUERY = "DELETE FROM agencyproduct WHERE APID = ? ";
    public static final String SEARCH_AGENCY_PRODUCT_DATA_QUERY = "SELECT  APID, APName, APTotalUnits, APWeightOfUnit, APBuyingPricePerUnit, APMarketPricePerUnit, APSellingPricePerUnit, APMDate, APEDate, APADate, APDADate FROM agencyproduct";
    public static final String GET_LAST_ID_DATA_QUERY = "SELECT MAX(APID) FROM agencyproduct";

}
