package Service;

import Models.HoKhauChuHoModel;
import Models.HoKhauModel;
import Models.NhanKhauModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoKhauUserService {
    private final Connection connection;

    public HoKhauUserService() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<NhanKhauModel> getListNhanKhau(int maHoKhau) {
        List<NhanKhauModel> danhSachNhanKhau = new ArrayList<>();

        String query = "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán tham số cho câu lệnh SQL
            preparedStatement.setInt(1, maHoKhau);

            // Thực thi câu lệnh và xử lý kết quả
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    danhSachNhanKhau.add(createNhanKhauFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi tìm kiếm hộ khẩu (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
        }

        return danhSachNhanKhau;
    }

    private NhanKhauModel createNhanKhauFromResultSet(ResultSet rs) throws SQLException {
        return new NhanKhauModel(
                rs.getInt("MAHOKHAU"),
                rs.getInt("MANHANKHAU"),
                rs.getString("CCCD"),
                rs.getString("HOTEN"),
                rs.getDate("NGAYSINH"),
                rs.getString("SDT"),
                rs.getString("QUANHEVOICHUHO"),
                rs.getBoolean("TRANGTHAI")
        );
    }
}
