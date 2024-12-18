import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import Models.NhanKhauModel;
import Service.DangKiService;
import Service.LoginService;
import Service.NhanKhauService;
import database.DatabaseConnection;

public class Test {

	public static void main(String[] args)  {
        DangKiService dangKiService = new DangKiService();
        try {
            System.out.println(dangKiService.CheckAcc("1212121212"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}