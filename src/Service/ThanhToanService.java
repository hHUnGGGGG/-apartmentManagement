package Service;

import Models.KhoanThuModel;
import Models.ThanhToanModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanService {
    private static final double PHI_XE_MAY = 70000;  // 70,000 đ/xe máy/tháng
    private static final double PHI_XE_OTO = 1200000; // 1,200,000 đ/ô tô/tháng
    private Connection conn;
    public ThanhToanService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void themPhiGuiXe() {
        String layXeQuery = """
                    SELECT X.MAHOKHAU, 
                           SUM(CASE WHEN X.LOAIXE = 'Xe máy' THEN 1 ELSE 0 END) AS soLuongXeMay,
                           SUM(CASE WHEN X.LOAIXE = 'Ô tô' THEN 1 ELSE 0 END) AS soLuongXeOto
                    FROM XE X
                    GROUP BY X.MAHOKHAU
                """;

        String themPhiQuery = """
                INSERT INTO PHI (TENPHI, DONGIA, TRANGTHAI, THOIGIANTHANHTOAN, PHUONGTHUCTHANHTOAN, MAHOKHAU, MAPHI)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        String kiemTraPhiQuery = """
                    SELECT COUNT(*) AS count
                    FROM PHI
                    WHERE TENPHI = ? AND MAHOKHAU = ?
                """;

        String sql2 ="SELECT MAX(MAPHI) as MAX FROM PHI";

        try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
             PreparedStatement layXeStmt = conn.prepareStatement(layXeQuery);
             PreparedStatement kiemTraPhiStmt = conn.prepareStatement(kiemTraPhiQuery);
             PreparedStatement themPhiStmt = conn.prepareStatement(themPhiQuery)) {

            ResultSet rs = layXeStmt.executeQuery();

            while (rs.next()) {
                int maHoKhau = rs.getInt("MAHOKHAU");
                int soLuongXeMay = rs.getInt("soLuongXeMay");
                int soLuongXeOto = rs.getInt("soLuongXeOto");

                // Tính tổng phí gửi xe
                double donGia = (PHI_XE_MAY * soLuongXeMay) + (PHI_XE_OTO * soLuongXeOto);
                if (donGia == 0) {
                    // Bỏ qua nếu không có xe
                    continue;
                }

                // Kiểm tra khoản phí đã tồn tại chưa
                kiemTraPhiStmt.setString(1, "Phí gửi xe");
                kiemTraPhiStmt.setInt(2, maHoKhau);
            //    kiemTraPhiStmt.setDate(3, new Date(System.currentTimeMillis())); // Ngày hiện tại
                ResultSet checkRs = kiemTraPhiStmt.executeQuery();
                checkRs.next();
                int count = checkRs.getInt("count");

                if (count > 0) {
                    // Nếu khoản phí đã tồn tại, bỏ qua
                    continue;
                }

                // Thêm khoản phí nếu chưa tồn tại
                int maPhi=1;
                ResultSet rs2=stmt2.executeQuery();
                if(rs2.next()) maPhi=rs2.getInt("MAX")+1;

                themPhiStmt.setString(1, "Phí gửi xe"); // TENPHI
                themPhiStmt.setDouble(2, donGia); // DONGIA
                themPhiStmt.setString(3, "Chưa thanh toán"); // TRANGTHAI
                themPhiStmt.setDate(4, new Date(System.currentTimeMillis())); // THOIGIANTHANHTOAN
                themPhiStmt.setString(5, null); // PHUONGTHUCTHANHTOAN
                themPhiStmt.setInt(6, maHoKhau); // MAHOKHAU
                themPhiStmt.setInt(7, maPhi);

                // Thực thi câu lệnh thêm
                themPhiStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm thanh toán theo tên phí
    public List<ThanhToanModel> timThanhToanTenPhi(String tenPhi) {
        List<ThanhToanModel> result= new ArrayList<>();
        tenPhi="%"+tenPhi+"%";
        String sql="SELECT * FROM PHI WHERE LOWER(TENPHI) LIKE LOWER(?) AND MAHOKHAU!=0";

        try (//Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,tenPhi);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int maKhoanThu = rs.getInt("MAPHI");
                String tenKhoanThu = rs.getString("TENPHI");
                double soTien = rs.getDouble("DONGIA");
                String loaiKhoanThu = rs.getString("LOAIPHI");
                Date hanNop = rs.getDate("HANNOP");
                int maHoKhau= rs.getInt("MAHOKHAU");
                int thangNop= rs.getInt("THANGNOP");
                String trangThai= rs.getString("TRANGTHAI");
                Date  thoiGianThanhToan= rs.getDate("THOIGIANTHANHTOAN");
                String phuongThucThanhToan= rs.getString("PHUONGTHUCTHANHTOAN");

                //Lấy tên chủ hộ
                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                    stmt2.setInt(1,maHoKhau);
                    stmt2.setString(2,"Chủ Hộ");
                    ResultSet rs2 = stmt2.executeQuery();
                    String tenChuHo = null;
                    if (rs2.next()) {
                        tenChuHo = rs2.getString("HOTEN");
                    }

                    result.add(new ThanhToanModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop, trangThai,thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //Xác nhận thanh toán
    public boolean xacNhanThanhToan(ThanhToanModel thanhToan) {
        String sql = "UPDATE PHI SET TRANGTHAI=? WHERE MAPHI=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Đã xác nhận");
            stmt.setInt(2, thanhToan.getMaKhoanThu());

            int rowsUpdated = stmt.executeUpdate();
            // DatabaseConnection.closeConnection();
            if (rowsUpdated > 0) {
                System.out.println("Đã xác nhận thanh toán thành công!");
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hiển thị danh sách các thanh toán
    public List<ThanhToanModel> layDanhSachThanhToan() {
        List<ThanhToanModel> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PHI WHERE MAHOKHAU!=0";

        try (//Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("aabc");
            while (rs.next()) {
                int maKhoanThu = rs.getInt("MAPHI");
                String tenKhoanThu = rs.getString("TENPHI");
                double soTien = rs.getDouble("DONGIA");
                String loaiKhoanThu = rs.getString("LOAIPHI");
                Date hanNop = rs.getDate("HANNOP");
                int maHoKhau= rs.getInt("MAHOKHAU");
                int thangNop= rs.getInt("THANGNOP");
                String trangThai= rs.getString("TRANGTHAI");
                Date  thoiGianThanhToan= rs.getDate("THOIGIANTHANHTOAN");
                String phuongThucThanhToan= rs.getString("PHUONGTHUCTHANHTOAN");

                //Lấy tên chủ hộ
                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                    stmt2.setInt(1,maHoKhau);
                    stmt2.setString(2,"Chủ Hộ");
                    ResultSet rs2 = stmt2.executeQuery();
                    String tenChuHo = null;
                    if (rs2.next()) {
                        tenChuHo = rs2.getString("HOTEN");
                    }

                    danhSach.add(new ThanhToanModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop, trangThai,thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            //    danhSach.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop));
            }

            // DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}
