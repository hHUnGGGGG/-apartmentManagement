package Service;

import Models.HoKhauModel;
import Models.KhoanThuModel;
import Models.LoaiPhiModel;
//import Models.ThanhToanModel;
import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanService {
    private static final double PHI_QUAN_LY_CHUNG_CU_M2 = 7000;
    private static final double PHI_DICH_VU_M2 = 16500; // Đơn giá dịch vụ chung cư tính theo m^2
    private static final double PHI_XE_MAY = 70000;  // 70,000 đ/xe máy/tháng
    private static final double PHI_XE_OTO = 1200000; // 1,200,000 đ/ô tô/tháng
//    private Connection conn;
//    public ThanhToanService() {
//        this.conn = DatabaseConnection.getInstance().getConnection();
//    }

//    public List<ThanhToanModel> timThanhToanTenPhiUser(String tenPhi, int maHoKhau) {
//        List<ThanhToanModel> result= new ArrayList<>();
//        tenPhi="%"+tenPhi+"%";
//        String sql="SELECT * FROM PHI WHERE LOWER(TENPHI) LIKE LOWER(?) AND MAHOKHAU=?";
//
//        try (//Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1,tenPhi);
//            stmt.setInt(2,maHoKhau);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                int maKhoanThu = rs.getInt("MAPHI");
//                String tenKhoanThu = rs.getString("TENPHI");
//                double soTien = rs.getDouble("DONGIA");
//                String loaiKhoanThu = rs.getString("LOAIPHI");
//                Date hanNop = rs.getDate("HANNOP");
//            //    int maHoKhau= rs.getInt("MAHOKHAU");
//                int thangNop= rs.getInt("THANGNOP");
//                String trangThai= rs.getString("TRANGTHAI");
//                Date  thoiGianThanhToan= rs.getDate("THOIGIANTHANHTOAN");
//                String phuongThucThanhToan= rs.getString("PHUONGTHUCTHANHTOAN");
//
//                //Lấy tên chủ hộ
//                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
//                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//                    stmt2.setInt(1,maHoKhau);
//                    stmt2.setString(2,"Chủ Hộ");
//                    ResultSet rs2 = stmt2.executeQuery();
//                    String tenChuHo = null;
//                    if (rs2.next()) {
//                        tenChuHo = rs2.getString("HOTEN");
//                    }
//
//                    result.add(new ThanhToanModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop, trangThai,thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return  result;
//    }
//
//    public List<ThanhToanModel> layDanhSachPhiTheoHoKhau(int maHoKhau) {
//        List<ThanhToanModel> danhSachPhi = new ArrayList<>();
//
//        String query = """
//        SELECT MAPHI, TENPHI, HANNOP, THANGNOP, LOAIPHI, DONGIA, TRANGTHAI, THOIGIANTHANHTOAN, PHUONGTHUCTHANHTOAN
//        FROM PHI
//        WHERE MAHOKHAU = ? AND TRANGTHAI != 'Đã xác nhận'
//    """;
//
//        try (
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, maHoKhau); // Gán mã hộ khẩu vào câu lệnh SQL
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                // Lấy các thông tin từ ResultSet
//                int maPhi = rs.getInt("MAPHI");
//                String tenPhi = rs.getString("TENPHI");
//                Date hanNopSql = rs.getDate("HANNOP");
//                int thangNop = rs.getInt("THANGNOP");
//                String loaiPhi = rs.getString("LOAIPHI");
//                double donGia = rs.getDouble("DONGIA");
//                String trangThai = rs.getString("TRANGTHAI");
//                Date thoiGianThanhToan = rs.getDate("THOIGIANTHANHTOAN");
//                String phuongThucThanhToan = rs.getString("PHUONGTHUCTHANHTOAN");
//
//                // Chuyển java.sql.Date sang java.util.Date
//                java.util.Date hanNop = (hanNopSql != null) ? new java.util.Date(hanNopSql.getTime()) : null;
////                java.util.Date thoiGianThanhToan = (thoiGianThanhToanSql != null)
////                        ? new java.util.Date(thoiGianThanhToanSql.getTime())
////                        : null;
//
//                //Lấy tên chủ hộ
//                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
//                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//                    stmt2.setInt(1,maHoKhau);
//                    stmt2.setString(2,"Chủ Hộ");
//                    ResultSet rs2 = stmt2.executeQuery();
//                    String tenChuHo = null;
//                    if (rs2.next()) {
//                        tenChuHo = rs2.getString("HOTEN");
//                    }
//
//                    danhSachPhi.add(new ThanhToanModel(maPhi,tenPhi,donGia,loaiPhi, hanNop, maHoKhau, thangNop, trangThai, thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return danhSachPhi;
//    }
//
//    public List<ThanhToanModel> layDanhSachLichSuPhiTheoHoKhau(int maHoKhau) {
//        List<ThanhToanModel> danhSachPhi = new ArrayList<>();
//
//        String query = """
//        SELECT MAPHI, TENPHI, HANNOP, THANGNOP, LOAIPHI, DONGIA, TRANGTHAI, THOIGIANTHANHTOAN, PHUONGTHUCTHANHTOAN
//        FROM PHI
//        WHERE MAHOKHAU = ? AND TRANGTHAI = 'Đã xác nhận'
//    """;
//
//        try (
//                PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, maHoKhau); // Gán mã hộ khẩu vào câu lệnh SQL
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                // Lấy các thông tin từ ResultSet
//                int maPhi = rs.getInt("MAPHI");
//                String tenPhi = rs.getString("TENPHI");
//                Date hanNopSql = rs.getDate("HANNOP");
//                int thangNop = rs.getInt("THANGNOP");
//                String loaiPhi = rs.getString("LOAIPHI");
//                double donGia = rs.getDouble("DONGIA");
//                String trangThai = rs.getString("TRANGTHAI");
//                Date thoiGianThanhToan = rs.getDate("THOIGIANTHANHTOAN");
//                String phuongThucThanhToan = rs.getString("PHUONGTHUCTHANHTOAN");
//
//                // Chuyển java.sql.Date sang java.util.Date
//                java.util.Date hanNop = (hanNopSql != null) ? new java.util.Date(hanNopSql.getTime()) : null;
////                java.util.Date thoiGianThanhToan = (thoiGianThanhToanSql != null)
////                        ? new java.util.Date(thoiGianThanhToanSql.getTime())
////                        : null;
//
//                //Lấy tên chủ hộ
//                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
//                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//                    stmt2.setInt(1,maHoKhau);
//                    stmt2.setString(2,"Chủ Hộ");
//                    ResultSet rs2 = stmt2.executeQuery();
//                    String tenChuHo = null;
//                    if (rs2.next()) {
//                        tenChuHo = rs2.getString("HOTEN");
//                    }
//
//                    danhSachPhi.add(new ThanhToanModel(maPhi,tenPhi,donGia,loaiPhi, hanNop, maHoKhau, thangNop, trangThai, thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return danhSachPhi;
//    }
//
//
//    public void themPhiQuanLyChungCu() {
//        String layCanHoQuery = """
//        SELECT C.MAHOKHAU, C.DIENTICH
//        FROM CANHO C
//    """;
//
//        String kiemTraPhiQuery = """
//        SELECT COUNT(*) AS count
//        FROM PHI
//        WHERE TENPHI = ? AND MAHOKHAU = ?
//    """;
//
//        String themPhiQuery = """
//        INSERT INTO PHI (TENPHI, DONGIA, TRANGTHAI,  PHUONGTHUCTHANHTOAN, MAHOKHAU, MAPHI, LOAIPHI)
//        VALUES (?, ?, ?, ?, ?, ?, ?)
//    """;
//
//        String sql2 ="SELECT MAX(MAPHI) as MAX FROM PHI";
//
//        try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
//             PreparedStatement layCanHoStmt = conn.prepareStatement(layCanHoQuery);
//             PreparedStatement kiemTraPhiStmt = conn.prepareStatement(kiemTraPhiQuery);
//             PreparedStatement themPhiStmt = conn.prepareStatement(themPhiQuery)) {
//
//            ResultSet rs = layCanHoStmt.executeQuery();
//
//            // Lấy tháng và năm hiện tại
//            LocalDate currentDate = LocalDate.now();
//            String monthYear = currentDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//
//            while (rs.next()) {
//                int maHoKhau = rs.getInt("MAHOKHAU");
//                double dienTich = rs.getDouble("DIENTICH");
//
//                // Tính tổng phí dịch vụ dựa trên diện tích
//                double donGia = PHI_QUAN_LY_CHUNG_CU_M2 * dienTich;
//                if (donGia == 0) {
//                    // Bỏ qua nếu diện tích không hợp lệ
//                    continue;
//                }
//
//                // Tạo tên phí dịch vụ chung cư kèm tháng/năm
//                String tenPhi = "Phí quản lý chung cư " + monthYear;
//
//                // Kiểm tra khoản phí đã tồn tại chưa
//                kiemTraPhiStmt.setString(1, tenPhi);
//                kiemTraPhiStmt.setInt(2, maHoKhau);
//                ResultSet checkRs = kiemTraPhiStmt.executeQuery();
//                checkRs.next();
//                int count = checkRs.getInt("count");
//
//                if (count > 0) {
//                    // Nếu khoản phí đã tồn tại, bỏ qua
//                    continue;
//                }
//
//                // Thêm khoản phí nếu chưa tồn tại
//                int maPhi=1;
//                ResultSet rs2=stmt2.executeQuery();
//                if(rs2.next()) maPhi=rs2.getInt("MAX")+1;
//
//                themPhiStmt.setString(1, tenPhi); // TENPHI
//                themPhiStmt.setDouble(2, donGia); // DONGIA
//                themPhiStmt.setString(3, "Chưa thanh toán"); // TRANGTHAI
//                //    themPhiStmt.setDate(4, java.sql.Date.valueOf(currentDate)); // THOIGIANTHANHTOAN
//                themPhiStmt.setString(4, null); // PHUONGTHUCTHANHTOAN
//                themPhiStmt.setInt(5, maHoKhau); // MAHOKHAU
//                themPhiStmt.setInt(6,maPhi);
//                themPhiStmt.setString(7, "Bắt buộc");
//
//                // Thực thi câu lệnh thêm
//                themPhiStmt.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void themPhiDichVuChungCu() {
//        String layCanHoQuery = """
//        SELECT C.MAHOKHAU, C.DIENTICH
//        FROM CANHO C
//    """;
//
//        String kiemTraPhiQuery = """
//        SELECT COUNT(*) AS count
//        FROM PHI
//        WHERE TENPHI = ? AND MAHOKHAU = ?
//    """;
//
//        String themPhiQuery = """
//        INSERT INTO PHI (TENPHI, DONGIA, TRANGTHAI,  PHUONGTHUCTHANHTOAN, MAHOKHAU, MAPHI, LOAIPHI)
//        VALUES (?, ?, ?, ?, ?, ?, ?)
//    """;
//
//        String sql2 ="SELECT MAX(MAPHI) as MAX FROM PHI";
//
//        try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
//             PreparedStatement layCanHoStmt = conn.prepareStatement(layCanHoQuery);
//             PreparedStatement kiemTraPhiStmt = conn.prepareStatement(kiemTraPhiQuery);
//             PreparedStatement themPhiStmt = conn.prepareStatement(themPhiQuery)) {
//
//            ResultSet rs = layCanHoStmt.executeQuery();
//
//            // Lấy tháng và năm hiện tại
//            LocalDate currentDate = LocalDate.now();
//            String monthYear = currentDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//
//            while (rs.next()) {
//                int maHoKhau = rs.getInt("MAHOKHAU");
//                double dienTich = rs.getDouble("DIENTICH");
//
//                // Tính tổng phí dịch vụ dựa trên diện tích
//                double donGia = PHI_DICH_VU_M2 * dienTich;
//                if (donGia == 0) {
//                    // Bỏ qua nếu diện tích không hợp lệ
//                    continue;
//                }
//
//                // Tạo tên phí dịch vụ chung cư kèm tháng/năm
//                String tenPhi = "Phí dịch vụ chung cư " + monthYear;
//
//                // Kiểm tra khoản phí đã tồn tại chưa
//                kiemTraPhiStmt.setString(1, tenPhi);
//                kiemTraPhiStmt.setInt(2, maHoKhau);
//                ResultSet checkRs = kiemTraPhiStmt.executeQuery();
//                checkRs.next();
//                int count = checkRs.getInt("count");
//
//                if (count > 0) {
//                    // Nếu khoản phí đã tồn tại, bỏ qua
//                    continue;
//                }
//
//                // Thêm khoản phí nếu chưa tồn tại
//                int maPhi=1;
//                ResultSet rs2=stmt2.executeQuery();
//                if(rs2.next()) maPhi=rs2.getInt("MAX")+1;
//
//                themPhiStmt.setString(1, tenPhi); // TENPHI
//                themPhiStmt.setDouble(2, donGia); // DONGIA
//                themPhiStmt.setString(3, "Chưa thanh toán"); // TRANGTHAI
//            //    themPhiStmt.setDate(4, java.sql.Date.valueOf(currentDate)); // THOIGIANTHANHTOAN
//                themPhiStmt.setString(4, null); // PHUONGTHUCTHANHTOAN
//                themPhiStmt.setInt(5, maHoKhau); // MAHOKHAU
//                themPhiStmt.setInt(6,maPhi);
//                themPhiStmt.setString(7, "Bắt buộc");
//
//                // Thực thi câu lệnh thêm
//                themPhiStmt.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void themPhiGuiXe() {
//        String layXeQuery = """
//                    SELECT X.MAHOKHAU,
//                           SUM(CASE WHEN X.LOAIXE = 'Xe máy' THEN 1 ELSE 0 END) AS soLuongXeMay,
//                           SUM(CASE WHEN X.LOAIXE = 'Ô tô' THEN 1 ELSE 0 END) AS soLuongXeOto
//                    FROM XE X
//                    GROUP BY X.MAHOKHAU
//                """;
//
//        String themPhiQuery = """
//                INSERT INTO PHI (TENPHI, DONGIA, TRANGTHAI, PHUONGTHUCTHANHTOAN, MAHOKHAU, MAPHI, LOAIPHI)
//                VALUES (?, ?, ?, ?, ?, ?, ?)
//                """;
//
//        String kiemTraPhiQuery = """
//                    SELECT COUNT(*) AS count
//                    FROM PHI
//                    WHERE TENPHI = ? AND MAHOKHAU = ?
//                """;
//
//        String sql2 ="SELECT MAX(MAPHI) as MAX FROM PHI";
//
//        try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
//             PreparedStatement layXeStmt = conn.prepareStatement(layXeQuery);
//             PreparedStatement kiemTraPhiStmt = conn.prepareStatement(kiemTraPhiQuery);
//             PreparedStatement themPhiStmt = conn.prepareStatement(themPhiQuery)) {
//
//            ResultSet rs = layXeStmt.executeQuery();
//
//            LocalDate currentDate = LocalDate.now();
//            String monthYear = currentDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//
//            while (rs.next()) {
//                int maHoKhau = rs.getInt("MAHOKHAU");
//                int soLuongXeMay = rs.getInt("soLuongXeMay");
//                int soLuongXeOto = rs.getInt("soLuongXeOto");
//
//                // Tính tổng phí gửi xe
//                double donGia = (PHI_XE_MAY * soLuongXeMay) + (PHI_XE_OTO * soLuongXeOto);
//                if (donGia == 0) {
//                    // Bỏ qua nếu không có xe
//                    continue;
//                }
//
//                // Tạo tên phí gửi xe kèm tháng/năm
//                String tenPhi = "Phí gửi xe " + monthYear;
//
//                // Kiểm tra khoản phí đã tồn tại chưa
//                kiemTraPhiStmt.setString(1, tenPhi);
//                kiemTraPhiStmt.setInt(2, maHoKhau);
//
//            //    kiemTraPhiStmt.setDate(3, new Date(System.currentTimeMillis())); // Ngày hiện tại
//                ResultSet checkRs = kiemTraPhiStmt.executeQuery();
//                checkRs.next();
//                int count = checkRs.getInt("count");
//
//                if (count > 0) {
//                    // Nếu khoản phí đã tồn tại, bỏ qua
//                    continue;
//                }
//
//                // Thêm khoản phí nếu chưa tồn tại
//                int maPhi=1;
//                ResultSet rs2=stmt2.executeQuery();
//                if(rs2.next()) maPhi=rs2.getInt("MAX")+1;
//
//                themPhiStmt.setString(1, tenPhi); // TENPHI
//                themPhiStmt.setDouble(2, donGia); // DONGIA
//                themPhiStmt.setString(3, "Chưa thanh toán"); // TRANGTHAI
//            //    themPhiStmt.setDate(4, new Date(System.currentTimeMillis())); // THOIGIANTHANHTOAN
//                themPhiStmt.setString(4, null); // PHUONGTHUCTHANHTOAN
//                themPhiStmt.setInt(5, maHoKhau); // MAHOKHAU
//                themPhiStmt.setInt(6, maPhi);
//                themPhiStmt.setString(7, "Bắt buộc");
//
//                // Thực thi câu lệnh thêm
//                themPhiStmt.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // Tìm kiếm thanh toán theo tên phí
//    public List<ThanhToanModel> timThanhToanTenPhi(String tenPhi) {
//        List<ThanhToanModel> result= new ArrayList<>();
//        tenPhi="%"+tenPhi+"%";
//        String sql="SELECT * FROM PHI WHERE LOWER(TENPHI) LIKE LOWER(?) AND MAHOKHAU!=0";
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
//                String trangThai= rs.getString("TRANGTHAI");
//                Date  thoiGianThanhToan= rs.getDate("THOIGIANTHANHTOAN");
//                String phuongThucThanhToan= rs.getString("PHUONGTHUCTHANHTOAN");
//
//                //Lấy tên chủ hộ
//                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
//                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//                    stmt2.setInt(1,maHoKhau);
//                    stmt2.setString(2,"Chủ Hộ");
//                    ResultSet rs2 = stmt2.executeQuery();
//                    String tenChuHo = null;
//                    if (rs2.next()) {
//                        tenChuHo = rs2.getString("HOTEN");
//                    }
//
//                    result.add(new ThanhToanModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop, trangThai,thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return  result;
//    }

    //Gửi thanh toán (bên user)
//    public boolean guiThanhToan(ThanhToanModel thanhToan) {
//        String sql = "UPDATE PHI SET TRANGTHAI=?, THOIGIANTHANHTOAN=? WHERE MAPHI=?";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, "Chờ xác nhận");
//            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//            stmt.setInt(3,thanhToan.getMaKhoanThu());
//
//            int rowsUpdated = stmt.executeUpdate();
//            // DatabaseConnection.closeConnection();
//            if (rowsUpdated > 0) {
//                System.out.println("Đã gửi thanh toán thành công!");
//                return true;
//            }
//            else return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    //Xác nhận thanh toán (bên admin)
//    public boolean xacNhanThanhToan(ThanhToanModel thanhToan) {
//        String sql = "UPDATE PHI SET TRANGTHAI=? WHERE MAPHI=?";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, "Đã xác nhận");
//            stmt.setInt(2, thanhToan.getMaKhoanThu());
//
//            int rowsUpdated = stmt.executeUpdate();
//            // DatabaseConnection.closeConnection();
//            if (rowsUpdated > 0) {
//                System.out.println("Đã xác nhận thanh toán thành công!");
//                return true;
//            }
//            else return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    // Hiển thị danh sách các thanh toán
    public static List<KhoanThuModel> layThongTinThanhToan() {
        String sql = """
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, kt.MAHOKHAU, hk.TENCHUHO, lp.LOAITHU, kt.SOTIEN, 
               kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
        JOIN HOKHAU hk ON kt.MAHOKHAU = hk.MAHOKHAU
        JOIN NHANKHAU nk ON kt.MAHOKHAU = nk.MAHOKHAU
        """;

        List<KhoanThuModel> danhSachThanhToan = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int maKhoanThu = resultSet.getInt("MAKHOANTHU");
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
                int maHoKhau = resultSet.getInt("MAHOKHAU");
                String tenChuHo = resultSet.getString("TENCHUHO");
                String loaiPhi = resultSet.getString("LOAITHU");
                int soTien = resultSet.getInt("SOTIEN");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");

//                HoKhauModel hoKhauModel = new HoKhauModel(maHoKhau, tenChuHo);
//                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, null);
//
//                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, tenLoaiPhi, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);
//                danhSachThanhToan.add(khoanThuModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachThanhToan;
    }

//    public List<ThanhToanModel> layDanhSachThanhToanUser() {
//        List<ThanhToanModel> danhSach = new ArrayList<>();
//        String sql = "SELECT * FROM PHI WHERE MAHOKHAU!=0 AND TRANGTHAI != 'Đã xác nhận'";
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
//                String trangThai= rs.getString("TRANGTHAI");
//                Date  thoiGianThanhToan= rs.getDate("THOIGIANTHANHTOAN");
//                String phuongThucThanhToan= rs.getString("PHUONGTHUCTHANHTOAN");
//
//                //Lấy tên chủ hộ
//                String sql2="SELECT HOTEN FROM NHANKHAU WHERE MAHOKHAU=? AND QUANHEVOICHUHO=?";
//                try(PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//                    stmt2.setInt(1,maHoKhau);
//                    stmt2.setString(2,"Chủ Hộ");
//                    ResultSet rs2 = stmt2.executeQuery();
//                    String tenChuHo = null;
//                    if (rs2.next()) {
//                        tenChuHo = rs2.getString("HOTEN");
//                    }
//
//                    danhSach.add(new ThanhToanModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop, trangThai,thoiGianThanhToan, phuongThucThanhToan, tenChuHo));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                //    danhSach.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop,maHoKhau, thangNop));
//            }
//
//            // DatabaseConnection.closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return danhSach;
//    }
}
