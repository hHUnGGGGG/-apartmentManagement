import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import Service.DangKiService;
import database.DatabaseConnection;

public class Test {

	public static void main(String[] args)  {
		DangKiService dangKiService = new DangKiService();
        try {
            dangKiService.LuuVaoDatabase("0124", "adc");
			dangKiService.CheckAcc("132");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}