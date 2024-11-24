package Service;

import Models.KhoanThuModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KhoanThuService {
    private List<KhoanThuModel> danhSachKhoanThu;

    public KhoanThuService() {
        this.danhSachKhoanThu = new ArrayList<>();
    }

    // Thêm khoản thu mới
    public void themKhoanThu(KhoanThuModel khoanThu) {
        String sql = "INSERT INTO PHI ( TENPHI, LOAIPHI, DONGIA, HANNOP) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, khoanThu.getTenKhoanThu());
            stmt.setDouble(3, khoanThu.getSoTien());
            stmt.setString(2, khoanThu.getLoaiKhoanThu());
            stmt.setDate(4, new java.sql.Date(khoanThu.getHanNop().getTime())); // Chuyển đổi Date sang SQL Date

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Đã thêm khoản thu thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa khoản thu theo mã
    public boolean xoaKhoanThu(int maKhoanThu) {
        String sql = "DELETE FROM PHI WHERE MAPHI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhoanThu);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Đã xóa khoản thu thành công!");
                return true;
            } else {
                System.out.println("Không tìm thấy khoản thu với mã: " + maKhoanThu);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa khoản thu theo mã
    public boolean suaKhoanThu(int maKhoanThu, String tenKhoanThuMoi, double soTienMoi, String loaiKhoanThuMoi) {
        for (KhoanThuModel khoanThu : danhSachKhoanThu) {
            if (khoanThu.getMaKhoanThu() == maKhoanThu) {
                khoanThu.setTenKhoanThu(tenKhoanThuMoi);
                khoanThu.setSoTien(soTienMoi);
                khoanThu.setLoaiKhoanThu(loaiKhoanThuMoi);
                System.out.println("Đã cập nhật khoản thu với mã: " + maKhoanThu);
                return true;
            }
        }
        System.out.println("Không tìm thấy khoản thu với mã: " + maKhoanThu);
        return false;
    }

    // Tìm khoản thu theo mã
    public KhoanThuModel timKhoanThuMa(int maKhoanThu) {
        String sql = "SELECT * FROM PHI WHERE MAPHI = ?";
        KhoanThuModel khoanThu = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhoanThu);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tenKhoanThu = rs.getString("TENPHI");
                double soTien = rs.getDouble("DONGIA");
                String loaiKhoanThu = rs.getString("LOAIPHI");
                Date hanNop = rs.getDate("HANNOP");
                khoanThu = new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khoanThu;
    }

    // Tìm kiếm khoản thu theo tên
    public List<KhoanThuModel> timKiemKhoanThu(String tenKhoanThu) {
        List<KhoanThuModel> ketQuaTimKiem = danhSachKhoanThu.stream()
                .filter(khoanThu -> khoanThu.getTenKhoanThu().toLowerCase().contains(tenKhoanThu.toLowerCase()))
                .collect(Collectors.toList());

        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Không tìm thấy khoản thu nào với tên: " + tenKhoanThu);
        }

        return ketQuaTimKiem;
    }

    // Thống kê tổng số tiền của tất cả các khoản thu
    public double thongKeTongSoTien() {
        double tongSoTien = danhSachKhoanThu.stream().mapToDouble(KhoanThuModel::getSoTien).sum();
        System.out.println("Tổng số tiền của tất cả các khoản thu là: " + tongSoTien);
        return tongSoTien;
    }

    // Hiển thị danh sách các khoản thu
    public void hienThiDanhSachKhoanThu() {
        if (danhSachKhoanThu.isEmpty()) {
            System.out.println("Danh sách khoản thu đang trống.");
        } else {
            System.out.println("Danh sách các khoản thu:");
            for (KhoanThuModel khoanThu : danhSachKhoanThu) {
                System.out.println("Mã: " + khoanThu.getMaKhoanThu() +
                        ", Tên: " + khoanThu.getTenKhoanThu() +
                        ", Số tiền: " + khoanThu.getSoTien() +
                        ", Loại: " + khoanThu.getLoaiKhoanThu());
            }
        }
    }
}