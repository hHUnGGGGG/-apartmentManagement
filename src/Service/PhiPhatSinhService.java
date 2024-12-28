package Service;

import Models.LoaiPhiModel;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhiPhatSinhService {

    public static boolean themPhiPhatSinh(String tenPhi, String loaiPhi, int soTien)  {
        String sql = "INSERT INTO LOAIPHI (TENLOAIPHI, LOAITHU, DONGIA, MALOAIPHI) VALUES (?, ?, ?, ?)";
        String sql2 ="SELECT MAX(MALOAIPHI) as MAX FROM LOAIPHI";
        // Kết nối với cơ sở dữ liệu
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             PreparedStatement stmt2 = connection.prepareStatement(sql2)) {

            int maLoaiPhi = 1;
            ResultSet rs=stmt2.executeQuery();
            if(rs.next()) maLoaiPhi=rs.getInt("MAX")+1;//set mã loại phí là max+1


            // Thiết lập các giá trị cho PreparedStatement
            preparedStatement.setString(1, tenPhi);
            preparedStatement.setString(2, loaiPhi);
            preparedStatement.setInt(3, soTien);
            preparedStatement.setInt(4, maLoaiPhi);

            // Chuyển java.util.Date sang java.sql.Date
//            java.sql.Date sqlHanNop = new java.sql.Date(hanNop.getTime());
//            preparedStatement.setDate(4, sqlHanNop);

            // Thực thi câu lệnh
            int rowsInserted = preparedStatement.executeUpdate();

            // Thông báo kết quả
            if (rowsInserted > 0) {
                System.out.println("Thêm khoản phí thành công!");
                return true;
            } else {
                System.out.println("Thêm khoản phí thất bại.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean themKhoanThuTuLoaiPhi(String tenPhi, String loaiPhi, int soTien, Date hanNop) {
        String sqlLayHoKhau = "SELECT MAHOKHAU FROM HOKHAU";
        String sqlLayLoaiPhi = "SELECT MALOAIPHI, DONGIA FROM LOAIPHI WHERE TENLOAIPHI = ?";
        String sqlThemKhoanThu = "INSERT INTO KHOANTHU (MAHOKHAU, MALOAIPHI, HANNOP, SOTIEN, TRANGTHAI, MAKHOANTHU) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlLayMaxKhoanThu = "SELECT MAX(MAKHOANTHU) AS MAX FROM KHOANTHU";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement psLayHoKhau = connection.prepareStatement(sqlLayHoKhau);
             PreparedStatement psLayLoaiPhi = connection.prepareStatement(sqlLayLoaiPhi);
             PreparedStatement psThemKhoanThu = connection.prepareStatement(sqlThemKhoanThu);
             PreparedStatement psLayMaxKhoanThu = connection.prepareStatement(sqlLayMaxKhoanThu)) {

            // Lấy danh sách các hộ khẩu
            ResultSet rsHoKhau = psLayHoKhau.executeQuery();

            // Lấy mã khoản thu lớn nhất hiện tại
            ResultSet rsMaxKhoanThu = psLayMaxKhoanThu.executeQuery();
            int maxKhoanThu = rsMaxKhoanThu.next() ? rsMaxKhoanThu.getInt("MAX") : 0;

            while (rsHoKhau.next()) {
                int maHoKhau = rsHoKhau.getInt("MAHOKHAU");

                // Lấy thông tin loại phí
                psLayLoaiPhi.setString(1, tenPhi); // Tìm loại phí dựa trên tên phí
                ResultSet rsLoaiPhi = psLayLoaiPhi.executeQuery();

                if (rsLoaiPhi.next()) {
                    int maLoaiPhi = rsLoaiPhi.getInt("MALOAIPHI");
                    int donGia = rsLoaiPhi.getInt("DONGIA");

                    // Thêm khoản thu mới
                    psThemKhoanThu.setInt(1, maHoKhau);          // MAHOKHAU
                    psThemKhoanThu.setInt(2, maLoaiPhi);         // MALOAIPHI
                    psThemKhoanThu.setDate(3, new java.sql.Date(hanNop.getTime())); // HANNOP
                    psThemKhoanThu.setDouble(4, donGia);         // SOTIEN
                    psThemKhoanThu.setString(5, "Chưa thanh toán"); // TRANGTHAI
                    psThemKhoanThu.setInt(6, ++maxKhoanThu);     // MAKHOANTHU

                    psThemKhoanThu.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean themPhiVaKhoanThu(String tenPhi, String loaiPhi, int soTien, Date hanNop) {
        String sqlInsertPhi = "INSERT INTO LOAIPHI (TENLOAIPHI, LOAITHU, DONGIA, MALOAIPHI) VALUES (?, ?, ?, ?)";
        String sqlSelectLoaiPhi = "SELECT MAX(MALOAIPHI) as MAX FROM LOAIPHI";
        String sqlLayHoKhau = "SELECT MAHOKHAU FROM HOKHAU";
        String sqlThemKhoanThu = "INSERT INTO KHOANTHU (MAHOKHAU, MALOAIPHI, HANNOP, SOTIEN, TRANGTHAI, MAKHOANTHU) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlLayMaxKhoanThu = "SELECT MAX(MAKHOANTHU) AS MAX FROM KHOANTHU";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement psInsertPhi = connection.prepareStatement(sqlInsertPhi);
             PreparedStatement psSelectLoaiPhi = connection.prepareStatement(sqlSelectLoaiPhi);
             PreparedStatement psLayHoKhau = connection.prepareStatement(sqlLayHoKhau);
             PreparedStatement psThemKhoanThu = connection.prepareStatement(sqlThemKhoanThu);
             PreparedStatement psLayMaxKhoanThu = connection.prepareStatement(sqlLayMaxKhoanThu)) {

            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Thêm phí vào LOAIPHI
            int maLoaiPhi = 1;
            ResultSet rsMaxLoaiPhi = psSelectLoaiPhi.executeQuery();
            if (rsMaxLoaiPhi.next()) {
                maLoaiPhi = rsMaxLoaiPhi.getInt("MAX") + 1;
            }

            psInsertPhi.setString(1, tenPhi);
            psInsertPhi.setString(2, loaiPhi);
            psInsertPhi.setInt(3, soTien);
            psInsertPhi.setInt(4, maLoaiPhi);
            psInsertPhi.executeUpdate();

            // Lấy mã khoản thu lớn nhất hiện tại
            ResultSet rsMaxKhoanThu = psLayMaxKhoanThu.executeQuery();
            int maxKhoanThu = rsMaxKhoanThu.next() ? rsMaxKhoanThu.getInt("MAX") : 0;

            // Lấy danh sách các hộ khẩu
            ResultSet rsHoKhau = psLayHoKhau.executeQuery();
            while (rsHoKhau.next()) {
                int maHoKhau = rsHoKhau.getInt("MAHOKHAU");

                // Thêm khoản thu
                psThemKhoanThu.setInt(1, maHoKhau);          // MAHOKHAU
                psThemKhoanThu.setInt(2, maLoaiPhi);         // MALOAIPHI
                psThemKhoanThu.setDate(3, new java.sql.Date(hanNop.getTime())); // HANNOP
                psThemKhoanThu.setDouble(4, soTien);         // SOTIEN
                psThemKhoanThu.setString(5, "Chưa thanh toán"); // TRANGTHAI
                psThemKhoanThu.setInt(6, ++maxKhoanThu);     // MAKHOANTHU
                psThemKhoanThu.executeUpdate();
            }

            // Cam kết giao dịch
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
//            try {
//                // Rollback nếu có lỗi
//                if (connection != null) {
//                    connection.rollback();
//                }
//            } catch (SQLException rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
            return false;
        }
    }

    public static boolean suaLoaiPhiVaKhoanThu(int maLoaiPhi, String tenPhi, String loaiPhi, int soTien, Date hanNop){
        String sqlSuaLoaiPhi = "UPDATE LOAIPHI SET TENLOAIPHI = ?, LOAITHU = ?, DONGIA = ? WHERE MALOAIPHI = ?";
        String sqlSuaKhoanThu = "UPDATE KHOANTHU SET SOTIEN = ?, HANNOP = ? WHERE MALOAIPHI = ?";
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
             PreparedStatement psSuaLoaiPhi = connection.prepareStatement(sqlSuaLoaiPhi);
             PreparedStatement psSuaKhoanThu = connection.prepareStatement(sqlSuaKhoanThu)) {


            // Cập nhật thông tin loại phí
            psSuaLoaiPhi.setString(1, tenPhi);          // TENLOAIPHI
            psSuaLoaiPhi.setString(2, loaiPhi);         // LOAITHU
            psSuaLoaiPhi.setInt(3, soTien);            // DONGIA
            psSuaLoaiPhi.setInt(4, maLoaiPhi);         // MALOAIPHI
            psSuaLoaiPhi.executeUpdate();

            // Cập nhật thông tin khoản thu
            psSuaKhoanThu.setInt(1, soTien);           // SOTIEN
            psSuaKhoanThu.setDate(2, new java.sql.Date(hanNop.getTime())); // HANNOP
            psSuaKhoanThu.setInt(3, maLoaiPhi);        // MALOAIPHI
            psSuaKhoanThu.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<LoaiPhiModel> layDanhSachPhiSauKhiThem() {
        // SQL truy vấn thông tin từ LOAIPHI và KHOANTHU
        String sql = """
                SELECT lp.MALOAIPHI, lp.TENLOAIPHI, lp.DONGIA, lp.LOAITHU, kt.HANNOP 
                FROM LOAIPHI lp
                JOIN KHOANTHU kt ON lp.MALOAIPHI = kt.MALOAIPHI
                WHERE LOAITHU!='Phí thường niên'
                GROUP BY MALOAIPHI, TENLOAIPHI, DONGIA, LOAITHU, HANNOP
                """;

        List<LoaiPhiModel> danhSachPhi = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
                int donGia = resultSet.getInt("DONGIA");
                String loaiThu = resultSet.getString("LOAITHU");
                Date hanNop = resultSet.getDate("HANNOP");

                // Tạo đối tượng LoaiPhiModel và thêm vào danh sách
                LoaiPhiModel loaiPhi = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, donGia, loaiThu, hanNop);
                danhSachPhi.add(loaiPhi);
            }

            // Cam kết giao dịch
           // connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachPhi;
    }

}
