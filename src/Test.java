import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import Service.DangKiService;
import database.DatabaseConnection;

public class Test {

	public static void main(String[] args) throws SQLException {
		DangKiService dangKiService = new DangKiService();
		dangKiService.CheckAcc("131");
	}

}