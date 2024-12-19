package Service;

import Models.XeModel;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XeService {
    private Connection conn;
    public XeService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public boolean themXe(XeModel xe) {
        String query = "INSERT INTO XE (BIENSO, LOAIXE, MAHOKHAU) VALUES (?, ?, ?)";
        try (
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, xe.getBienSo());
            stmt.setString(2, xe.getLoaiXe());
            stmt.setInt(3, xe.getMaHoKhau());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa xe dựa trên biển số
    public boolean xoaXe(String bienSo) {
        String query = "DELETE FROM XE WHERE BIENSO = ?";
        try (
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bienSo);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức hiển thị danh sách xe
    public List<XeModel> layDanhSachXe() {
        List<XeModel> xeList = new ArrayList<>();
        String query = "SELECT * FROM XE";

        try (
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String bienSo = rs.getString("BIENSO");
                String loaiXe = rs.getString("LOAIXE");
                int maHoKhau = rs.getInt("MAHOKHAU");

                XeModel xe = new XeModel(bienSo, loaiXe, maHoKhau);
                xeList.add(xe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xeList;
    }

    public List<XeModel> layDanhSachXeTheoHo(int maHoKhau) {
        List<XeModel> xeList = new ArrayList<>();
        String query = "SELECT * FROM XE WHERE MAHOKHAU=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,maHoKhau);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String bienSo = rs.getString("BIENSO");
                String loaiXe = rs.getString("LOAIXE");

                XeModel xe = new XeModel(bienSo, loaiXe, maHoKhau);
                xeList.add(xe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xeList;
    }


}
