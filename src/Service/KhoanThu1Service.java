package Service;

import Models.KhoanThuModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoanThu1Service {
    private List<KhoanThuModel> danhSachKhoanThu;
    private Connection conn;
    public KhoanThu1Service() {
        this.danhSachKhoanThu = new ArrayList<>();
        this.conn = DatabaseConnection.getInstance().getConnection();
    }



//    // Thêm khoản thu mới
//    public boolean themKhoanThu(KhoanThuModel khoanThu) {
//        String sql = "INSERT INTO PHI ( TENPHI, LOAIPHI, DONGIA, HANNOP, MAPHI, THANGNOP) VALUES (?, ?, ?, ?, ?, ?)";
//        String sql2 ="SELECT MAX(MAPHI) as MAX FROM PHI";
//        try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            int maPhi=1;
//            ResultSet rs=stmt2.executeQuery();
//            if(rs.next()) maPhi=rs.getInt("MAX")+1;
//
//            stmt.setString(1, khoanThu.getTenKhoanThu());
//            stmt.setDouble(3, khoanThu.getSoTien());
//            stmt.setString(2, khoanThu.getLoaiKhoanThu());
//            stmt.setDate(4, new java.sql.Date(khoanThu.getHanNop().getTime())); // Chuyển đổi Date sang SQL Date
//            stmt.setInt(5,maPhi);
//            stmt.setInt(6,khoanThu.getThangNop());
//
//            int rowsInserted = stmt.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Đã thêm khoản thu thành công!");
//             //   DatabaseConnection.closeConnection();
//                return true;
//
//            }
//            else {
//             //   DatabaseConnection.closeConnection();
//                return false;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    // Xóa khoản thu theo mã
//    public boolean xoaKhoanThu(int maKhoanThu) {
//        String sql = "DELETE FROM PHI WHERE MAPHI = ?";
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, maKhoanThu);
//
//            int rowsDeleted = stmt.executeUpdate();
//         //   DatabaseConnection.closeConnection();
//            if (rowsDeleted > 0) {
//                System.out.println("Đã xóa khoản thu thành công!");
//                return true;
//            } else {
//                System.out.println("Không tìm thấy khoản thu với mã: " + maKhoanThu);
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Sửa khoản thu theo mã
//    public boolean suaKhoanThu(KhoanThuModel khoanThu) {
//        String sql = "UPDATE PHI SET TENPHI = ?, DONGIA = ?, LOAIPHI = ?, HANNOP = ?, THANGNOP = ? WHERE MAPHI = ?";
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, khoanThu.getTenKhoanThu());
//            stmt.setDouble(2, khoanThu.getSoTien());
//            stmt.setString(3, khoanThu.getLoaiKhoanThu());
//            stmt.setDate(4, new java.sql.Date(khoanThu.getHanNop().getTime()));
//        //    stmt.setInt(5,khoanThu.getMaHoKhau());
//            stmt.setInt(5, khoanThu.getThangNop());
//            stmt.setInt(6, khoanThu.getMaKhoanThu());
//
//            int rowsUpdated = stmt.executeUpdate();
//           // DatabaseConnection.closeConnection();
//            if (rowsUpdated > 0) {
//                System.out.println("Đã sửa khoản thu thành công!");
//                return true;
//            }
//            else return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Tìm khoản thu theo mã
//    public KhoanThuModel timKhoanThuMa(int maKhoanThu) {
//        String sql = "SELECT * FROM PHI WHERE MAPHI = ?";
//        KhoanThuModel khoanThu = null;
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, maKhoanThu);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String tenKhoanThu = rs.getString("TENPHI");
//                double soTien = rs.getDouble("DONGIA");
//                String loaiKhoanThu = rs.getString("LOAIPHI");
//                Date hanNop = rs.getDate("HANNOP");
//                khoanThu = new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop);
//            }
//
//           // DatabaseConnection.closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return khoanThu;
//    }
//
//    // Tìm kiếm khoản thu theo tên
//    public List<KhoanThuModel> timKhoanThuTen(String tenPhi) {
//        List<KhoanThuModel> result= new ArrayList<>();
//        tenPhi="%"+tenPhi+"%";
//        String sql="SELECT * FROM PHI WHERE LOWER(TENPHI) LIKE LOWER(?) AND MAHOKHAU=0";
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1,tenPhi);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                int maKhoanThu = rs.getInt("MAPHI");
//                String tenKhoanThu = rs.getString("TENPHI");
//                double soTien = rs.getDouble("DONGIA");
//                String loaiKhoanThu = rs.getString("LOAIPHI");
//                Date hanNop = rs.getDate("HANNOP");
//                int maHoKhau= rs.getInt("MAHOKHAU");
//                int thangNop= rs.getInt("THANGNOP");
//
//                result.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop, maHoKhau, thangNop));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return  result;
//    }
//
//    public List<KhoanThuModel> timKiemKhoanThu(String tenKhoanThu) {
//        List<KhoanThuModel> ketQuaTimKiem = danhSachKhoanThu.stream()
//                .filter(khoanThu -> khoanThu.getTenKhoanThu().toLowerCase().contains(tenKhoanThu.toLowerCase()))
//                .collect(Collectors.toList());
//
//        if (ketQuaTimKiem.isEmpty()) {
//            System.out.println("Không tìm thấy khoản thu nào với tên: " + tenKhoanThu);
//        }
//
//        return ketQuaTimKiem;
//    }
//
//    // Thống kê tổng số tiền của tất cả các khoản thu
//    public double thongKeTongSoTien() {
//        double tongSoTien = danhSachKhoanThu.stream().mapToDouble(KhoanThuModel::getSoTien).sum();
//        System.out.println("Tổng số tiền của tất cả các khoản thu là: " + tongSoTien);
//        return tongSoTien;
//    }
//
//    // Hiển thị danh sách các khoản thu
//    public List<KhoanThuModel> layDanhSachKhoanThu() {
//        List<KhoanThuModel> danhSach = new ArrayList<>();
//        String sql = "SELECT * FROM PHI WHERE MAHOKHAU=0";
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             Statement stmt = conn.createStatement();
//
//             ResultSet rs = stmt.executeQuery(sql)) {
//            System.out.println("aabc");
//            while (rs.next()) {
//                int maKhoanThu = rs.getInt("MAPHI");
//                String tenKhoanThu = rs.getString("TENPHI");
//                double soTien = rs.getDouble("DONGIA");
//                String loaiKhoanThu = rs.getString("LOAIPHI");
//                Date hanNop = rs.getDate("HANNOP");
//                int maHoKhau= rs.getInt("MAHOKHAU");
//                int thangNop= rs.getInt("THANGNOP");
//
//                danhSach.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop));
//            }
//
//           // DatabaseConnection.closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return danhSach;
//    }
//
//    public void hienThiDanhSachKhoanThu(List<KhoanThuModel> DSKhoanThu) {
//        if (DSKhoanThu.isEmpty()) {
//            System.out.println("Danh sách khoản thu đang trống.");
//        } else {
//            System.out.println("Danh sách các khoản thu:");
//            for (KhoanThuModel khoanThu : DSKhoanThu) {
//                System.out.println("Mã: " + khoanThu.getMaKhoanThu() +
//                        ", Tên: " + khoanThu.getTenKhoanThu() +
//                        ", Số tiền: " + khoanThu.getSoTien() +
//                        ", Loại: " + khoanThu.getLoaiKhoanThu());
//            }
//        }
//    }
}
