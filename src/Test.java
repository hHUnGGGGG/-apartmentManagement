import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import database.DatabaseConnection;

public class Test {

	public static void main(String[] args) {
		System.out.println("A");
		Connection conn = DatabaseConnection.getConnection();
		String sql = "SELECT *  FROM HOKHAU ";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			System.out.println("A");
			while(resultSet.next()) {
				System.out.println("Mã chủ hộ: " + resultSet.getInt("MACHUHO") );
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DatabaseConnection.closeConnection();
		}
	}

}
