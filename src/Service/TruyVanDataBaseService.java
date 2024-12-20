package Service;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TruyVanDataBaseService {
    public static ResultSet TruyVanDatabase(String sql) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public void closeDatabase () {
    }
}
