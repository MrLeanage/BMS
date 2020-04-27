package util.query;

public class UserQueries {
    public static final String LOAD_USER_DATA_QUERY = "SELECT * FROM user";
    public static final String VALIDATE_USER_QUERY = "SELECT * FROM user WHERE UID = ? AND UPassword = ?";
    public static final String INSERT_USER_DATA_QUERY = "INSERT INTO user (UID, UName, UPassword, UType, UStatus) VALUES( ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER_DATA_QUERY = "UPDATE user SET UName = ?, UPassword = ?, UType = ? , UStatus = ? WHERE UID = ?";
    public static final String DELETE_USER_DATA_QUERY = "DELETE FROM user WHERE UID = ? ";
    public static final String LOAD_SPECIFIC_EXISTING_USER_DATA_QUERY = "SELECT * FROM user WHERE UID = ?";
}
