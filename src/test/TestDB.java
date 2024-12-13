package test;

import Service.TruyVanDataBaseService;
import database.DatabaseConnection;

import javax.sound.midi.SysexMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) throws SQLException {
        String sql = "select * from TAIKHOAN;";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        //resultSet = statement.executeUpdate();
        ResultSet resultSet1 = statement.executeQuery();
        while(resultSet1.next()){
            System.out.println("Ma chu ho    " + resultSet1.getString("MATKHAU"));
        }
    }

}
