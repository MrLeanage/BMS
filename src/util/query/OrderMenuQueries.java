package util.query;

public class OrderMenuQueries {
    public static final String LOAD_ORDER_MENU_DATA_QUERY = "SELECT * FROM ordermenuitem";
    public static final String LOAD_SPECIFIC_ORDER_MENU_DATA_QUERY = "SELECT * FROM ordermenuitem WHERE OMIID = ?";
    public static final String INSERT_ORDER_MENU_DATA_QUERY = "INSERT INTO ordermenuitem (OMIImage, OMIName, OMIDescription, OMIWeight, OMIPrice, OMIStatus) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ORDER_MENU_DATA_QUERY = "UPDATE ordermenuitem SET OMIImage = ?, OMIName = ?, OMIDescription = ? , OMIWeight = ?, OMIPrice = ?, OMIStatus = ? WHERE OMIID = ?";
    public static final String DELETE_ORDER_MENU_DATA_QUERY = "DELETE FROM ordermenuitem WHERE OMIID = ? ";

}
