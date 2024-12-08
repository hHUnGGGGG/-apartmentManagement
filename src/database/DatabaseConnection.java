package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


	private static DatabaseConnection instance = null;//thuộc tính có kiểu dữ liệu là chính nó
	private static final String URL = "jdbc:mysql://junction.proxy.rlwy.net:28148/railway";
	private static final String USER ="root";
	private static final String PASSWORD = "jQzbKmtEvgouzjJBSdTLSCKtOyxATYRU";

	//kết nối csdl
	private Connection connection;//

	//Constructor riêng tư để tránh việc tạo đối tượng ngoài class này
	private DatabaseConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected successfully!");
		}catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}

	//phương thức để lấy/tạo đối tượng kết nối (databaseconnector) , từ đó tạo ra connection duy nhất
	//chỉ cần gọi 1 lần trong chương trình
	public static DatabaseConnection getInstance(){
		if(instance==null){//nếu chưa có thì tạo
			instance = new DatabaseConnection();
		}
		return instance;//trả về instance
	}

	//phương thức lấy kết nối(Connection)
	//phải gọi getInstance trước
	public Connection getConnection(){
		return connection;
	}

	public void closeConnection(){
		try {
			if (connection != null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}