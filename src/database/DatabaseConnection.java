package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Connection connection = null;

	// Constructor private để không cho phép tạo đối tượng
	private DatabaseConnection() {}

	// Trả về một connection
	public static Connection getConnection() {
		if (connection == null) {
			try {
				// Đăng ký driver
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

				// Cấu hình URL kết nối
				String url = "jdbc:sqlserver://TU_HOANG:1433;databaseName=QLThuPhi";
				String userName = "sa";
				String passWord = "0181";

				// Tạo kết nối
				connection = DriverManager.getConnection(url, userName, passWord);
				System.out.println("Kết nối thành công");
			} catch (ClassNotFoundException e) {
				System.err.println("Driver không tìm thấy: " + e.getMessage());
			} catch (SQLException e) {
				System.err.println("Kết nối thất bại: " + e.getMessage());
			}
		} else {
			System.out.println("Kết nối đã tồn tại");
		}
		return connection;
	}

	// Ngắt kết nối đến server
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null; // Đặt lại kết nối thành null sau khi đóng
				System.out.println("Đã ngắt kết nối");
			} catch (SQLException e) {
				System.err.println("Lỗi khi ngắt kết nối: " + e.getMessage());
			}
		}
	}
}