package Service;

import Models.LoaiPhiModel;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhiThuongNienService {
    public static boolean suaDonGia(LoaiPhiModel loaiPhiModel){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE LOAIPHI SET DONGIA = ? WHERE MALOAIPHI = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1,loaiPhiModel.getDonGia());
            stmt.setInt(2,loaiPhiModel.getMaLoaiPhi());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Đã sửa thành công!");
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<LoaiPhiModel> layDanhSachPhiThuongNien(){
        List<LoaiPhiModel> danhSach = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM LOAIPHI WHERE LOAITHU='Phí thường niên' AND MALOAIPHI!=0";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel();
                loaiPhiModel.setMaLoaiPhi(rs.getInt("MALOAIPHI"));
                loaiPhiModel.setTenLoaiPhi(rs.getString("TENLOAIPHI"));
                loaiPhiModel.setDonGia(rs.getInt("DONGIA"));
                loaiPhiModel.setLoaiThu(rs.getString("LOAITHU"));
                danhSach.add(loaiPhiModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(danhSach.isEmpty()) System.out.println("ko tim thay loai phi");
        return danhSach;
    }

    public static List<LoaiPhiModel> timLoaiPhiTheoTen(String tenPhi) {
        List<LoaiPhiModel> result= new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        tenPhi="%"+tenPhi+"%";
        String sql="SELECT * FROM LOAIPHI WHERE LOWER(TENLOAIPHI) LIKE LOWER(?) AND LOAITHU='Phí thường niên' AND MALOAIPHI!=0";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1,tenPhi);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel();
                loaiPhiModel.setMaLoaiPhi(rs.getInt("MALOAIPHI"));
                loaiPhiModel.setTenLoaiPhi(rs.getString("TENLOAIPHI"));
                loaiPhiModel.setDonGia(rs.getInt("DONGIA"));
                loaiPhiModel.setLoaiThu(rs.getString("LOAITHU"));
                result.add(loaiPhiModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
