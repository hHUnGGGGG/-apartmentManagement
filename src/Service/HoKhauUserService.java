package Service;


import Models.NhanKhauModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

public class HoKhauUserService {
    private final Connection connection;

    public HoKhauUserService() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<NhanKhauModel> getListNhanKhau(int maHoKhau) {
        List<NhanKhauModel> danhSachNhanKhau = new ArrayList<>();

        String query =  "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ?";

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

    public List<NhanKhauModel> searchNKbyTen(int maHoKhau, String ten){
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ? AND LOWER(HOTEN) LIKE LOWER(?)";
        String searchPattern = "%" + ten.trim() + "%";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,maHoKhau);
            preparedStatement.setString(2,searchPattern);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    NhanKhauModel nhankhau = new NhanKhauModel(
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            rs.getInt("MAHOKHAU"),
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI"),
                            rs.getInt("SOPHONG"),
                            rs.getBoolean("TRANGTHAITAMVANG")
                    );

                    listNhanKhau.add(nhankhau);
                }
            }
        } catch (SQLException e){
            System.err.println("Lỗi khi tìm kiếm nhân khẩu theo tên:" + e.getMessage());
        }
        return listNhanKhau;
    }

    public List<NhanKhauModel> searchNKbyCCCD(int maHoKhau, String cccd){
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ? AND CCCD LIKE ?";
        String searchPattern = "%" + cccd.trim() + "%";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,maHoKhau);
            preparedStatement.setString(2,searchPattern);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    NhanKhauModel nhankhau = new NhanKhauModel(
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            rs.getInt("MAHOKHAU"),
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI"),
                            rs.getInt("SOPHONG"),
                            rs.getBoolean("TRANGTHAITAMVANG")
                    );

                    listNhanKhau.add(nhankhau);
                }
            }
        } catch (SQLException e){
            System.err.println("Lỗi khi tìm kiếm nhân khẩu theo CCCD:" + e.getMessage());
        }
        return listNhanKhau;
    }

    private NhanKhauModel createNhanKhauFromResultSet(ResultSet rs) throws SQLException {
        return new NhanKhauModel(
                rs.getInt("MANHANKHAU"),
                rs.getString("CCCD"),
                rs.getString("HOTEN"),
                rs.getDate("NGAYSINH"),
                rs.getString("SDT"),
                rs.getInt("MAHOKHAU"),
                rs.getString("QUANHEVOICHUHO"),
                rs.getString("TRANGTHAI"),
                rs.getBoolean("TRANGTHAITAMVANG")
        );
    }
}
