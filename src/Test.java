import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import Models.NhanKhauModel;
import Models.ThanhToanModel;
import Service.*;
import database.DatabaseConnection;

public class Test {

	public static void main(String[] args)  {
        String query = "INSERT INTO CANHO (MACANHO, TANG, SOPHONG) VALUES (?, ?, ?)" ;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            HoKhauService hoKhauService = new HoKhauService();
            System.out.println(hoKhauService.getListHoKhau().get(1));
            int tang = 1;
            int i = 10;
            int h = 1;
            while(tang <= 24) {
                if(i - ((tang) * 10) > 9) tang++;
                //preparedStatement.setInt(1, h);
                //preparedStatement.setInt(2, tang);
                //preparedStatement.setInt(3, i);
                //preparedStatement.executeUpdate();
                i = i + 1;
                //System.out.println(tang);
                h ++;
            }
        }

        catch (SQLException e) {
        // Ghi log lỗi chi tiết
            System.err.println("Lỗi" + e.getMessage());
        }
    }
}