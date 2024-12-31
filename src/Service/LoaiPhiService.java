package Service;

import Models.LoaiPhiModel;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoaiPhiService {
    public static LoaiPhiModel timLoaiPhiTheoMa(int maLoaiPhi){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM LOAIPHI WHERE MALOAIPHI = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maLoaiPhi);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel();
                loaiPhiModel.setMaLoaiPhi(rs.getInt("MALOAIPHI"));
                loaiPhiModel.setTenLoaiPhi(rs.getString("TENLOAIPHI"));
                loaiPhiModel.setDonGia(rs.getInt("DONGIA"));
                loaiPhiModel.setLoaiThu(rs.getString("LOAITHU"));
                return loaiPhiModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("ko tim thay loai phi");
        return null;
    }

    public static List<LoaiPhiModel> layDanhSachLoaiPhi(){
        List<LoaiPhiModel> danhSachLoaiPhi = null;
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM LOAIPHI";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel();
                loaiPhiModel.setMaLoaiPhi(rs.getInt("MALOAIPHI"));
                loaiPhiModel.setTenLoaiPhi(rs.getString("TENLOAIPHI"));
                loaiPhiModel.setDonGia(rs.getInt("DONGIA"));
                loaiPhiModel.setLoaiThu(rs.getString("LOAITHU"));
                danhSachLoaiPhi.add(loaiPhiModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(danhSachLoaiPhi.isEmpty()) System.out.println("ko tim thay loai phi");
        return danhSachLoaiPhi;
    }

    //sua loai phi
    public static boolean suaLoaiPhi(LoaiPhiModel loaiPhiModel){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE LOAIPHI SET TENLOAIPHI = ?, DONGIA = ?, LOAITHU =? WHERE MALOAIPHI = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1,loaiPhiModel.getTenLoaiPhi());
            stmt.setInt(2,loaiPhiModel.getDonGia());
            stmt.setString(3,loaiPhiModel.getLoaiThu());
            stmt.setInt(4,loaiPhiModel.getMaLoaiPhi());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Đã sửa khoản thu thành công!");
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
