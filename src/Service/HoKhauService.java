package Service;

import Models.HoKhauModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoKhauService {

    private final Connection connection;

    public HoKhauService() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    // Lấy mã hộ lớn nhất
    public int getMaxMaHoKhau() throws SQLException {
        String query = "SELECT COALESCE(MAX(MAHOKHAU), 1000000) AS maxMaHoKhau FROM HOKHAU";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("maxMaHoKhau");
            }
        }
        return 1000000; // Trả về giá trị mặc định nếu bảng rỗng
    }



    // Thêm hộ khẩu
    public boolean addHoKhau(HoKhauModel hokhauModel) {
        String insertQuery = "INSERT INTO HOKHAU (MAHOKHAU) VALUES (?)";

        try {
            // Lấy mã hộ khẩu lớn nhất hiện tại
            int maxMaHoKhau = getMaxMaHoKhau();
            int nextMaHoKhau = maxMaHoKhau + 100; // Tăng thêm 100

            // Gán mã hộ khẩu mới vào đối tượng
            hokhauModel.setMaHoKhau(nextMaHoKhau);

            // Thực hiện thêm hộ khẩu vào cơ sở dữ liệu
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, hokhauModel.getMaHoKhau());
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0; // Trả về true nếu thêm thành công
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hộ khẩu: " + e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }



    // Xóa hộ khẩu
    public boolean delHoKhau(int maHoKhau) {

        String query = "DELETE FROM HOKHAU WHERE MAHOKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán tham số cho câu lệnh SQL
            preparedStatement.setInt(1, maHoKhau);

            // Thực thi câu lệnh và kiểm tra kết quả
            return preparedStatement.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi xóa hộ khẩu với (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
            return false;
        }
    }


    // Tìm kiếm hộ khẩu
    public List<HoKhauModel> searchHoKhau(int maHoKhau) {

        List<HoKhauModel> listHoKhau = new ArrayList<>();
        String query = "SELECT MAHOKHAU FROM HOKHAU WHERE MAHOKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán tham số cho câu lệnh SQL
            preparedStatement.setInt(1, maHoKhau);

            // Thực thi câu lệnh và xử lý kết quả
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    HoKhauModel hoKhauModel = new HoKhauModel();
                    hoKhauModel.setMaHoKhau(rs.getInt("MAHOKHAU"));
                    listHoKhau.add(hoKhauModel);
                }
            }
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi tìm kiếm hộ khẩu (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
        }

        return listHoKhau;
    }


    // Lấy danh sách hộ khẩu
    public List<HoKhauModel> getListHoKhau(){
        List<HoKhauModel> listHoKhau = new ArrayList<>();
        String query = "SELECT * FROM HOKHAU";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(rs.getInt("MAHOKHAU"));
                listHoKhau.add(hoKhauModel);
            }
        } catch (SQLException e){
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi lấy danh sách hộ khẩu: " + e.getMessage());
        }
        return listHoKhau;
    }


    // Cheked mã hộ khẩu đã tồn tại hay chưa
    public boolean existsHoKhauId(int maHoKhau) {
        String query = "SELECT 1 FROM HOKHAU WHERE MAHOKHAU = ? LIMIT 1"; // Tối ưu hóa truy vấn
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maHoKhau);

            // Sử dụng ResultSet để kiểm tra sự tồn tại
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Nếu có kết quả thì trả về true
            }
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi kiểm với (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
            return false;
        }
    }

}