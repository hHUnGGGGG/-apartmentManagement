import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import Models.NhanKhauModel;
import Service.DangKiService;
import Service.NhanKhauService;
import database.DatabaseConnection;

public class Test {

	public static void main(String[] args)  {
        NhanKhauService nhanKhauService = new NhanKhauService();
        for(NhanKhauModel n : nhanKhauService.getListNhanKhau()) {
            System.out.println(n.getMaNhanKhau());
        }
    }
}