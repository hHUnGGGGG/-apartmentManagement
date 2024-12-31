package Service;

import Models.HoKhauModel;
import Models.KhoanThuModel;
import Models.LoaiPhiModel;
//import Models.ThanhToanModel;
import Models.NhanKhauModel;
import database.DatabaseConnection;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class KhoanThuService {
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

    public static List<KhoanThuModel> timKiemTheoTieuChi(String tieuChi, String giaTri) {
        String sqlBase = """
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, lp.LOAITHU, kt.MAHOKHAU, kt.SOTIEN, 
               kt.HANNOP, kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
        JOIN HOKHAU hk ON kt.MAHOKHAU = hk.MAHOKHAU
    """;

        String condition = "";
        switch (tieuChi) {
            case "Mã Phí":
                condition = "WHERE kt.MAKHOANTHU = ?";
                break;
            case "Tên Phí":
                condition = "WHERE lp.TENLOAIPHI LIKE ?";
                giaTri = "%" + giaTri + "%"; // Tìm kiếm với LIKE
                break;
            case "Mã HK":
                condition = "WHERE kt.MAHOKHAU = ?";
                break;
            case "Tên Chủ Hộ":
                condition = """
                WHERE hk.MAHOKHAU IN (
                    SELECT h.MAHOKHAU
                    FROM HOKHAU h
                    JOIN NHANKHAU n ON h.MAHOKHAU = n.MAHOKHAU
                    WHERE n.HOTEN LIKE ? AND n.QUANHEVOICHUHO = 'Chủ Hộ'
                )
            """;
                giaTri = "%" + giaTri + "%";
                break;
            case "Loại Phí":
                condition = "WHERE lp.LOAITHU LIKE ?";
                giaTri = "%" + giaTri + "%";
                break;
            case "Số Tiền":
                condition = "WHERE kt.SOTIEN = ?";
                break;
            case "Thời Gian":
                condition = "WHERE DATE(kt.THOIGIANTHANHTOAN) = ?";
                break;
            case "Trạng Thái":
                condition = "WHERE kt.TRANGTHAI LIKE ?";
                giaTri = "%" + giaTri + "%";
                break;
            default:
                return new ArrayList<>(); // Trường hợp tiêu chí không hợp lệ
        }

        String sql = sqlBase + " " + condition;

        List<KhoanThuModel> danhSachKhoanThu = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Gán giá trị tham số cho câu lệnh SQL
            if (tieuChi.equals("Số Tiền") || tieuChi.equals("Mã Phí") || tieuChi.equals("Mã HK")) {
                preparedStatement.setInt(1, Integer.parseInt(giaTri)); // Chuyển đổi sang số nguyên
            } else {
                preparedStatement.setString(1, giaTri); // Với các tiêu chí khác
            }

            // Thực thi truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKhoanThu = resultSet.getInt("MAKHOANTHU");
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
                String loaiPhi = resultSet.getString("LOAITHU");
                int maHoKhau = resultSet.getInt("MAHOKHAU");
                int soTien = resultSet.getInt("SOTIEN");
                Date hanNop = resultSet.getDate("HANNOP");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");

                // Tạo các đối tượng cần thiết
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(maHoKhau);
                NhanKhauModel nhanKhauModel = new NhanKhauModel();
                nhanKhauModel.setHoTenNhanKhau(HoKhauService.layTenChuHo(maHoKhau));
                hoKhauModel.setNhanKhauModel(nhanKhauModel);

                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, hanNop);

                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);

                // Thêm vào danh sách
                danhSachKhoanThu.add(khoanThuModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachKhoanThu;
    }

    public static void themPhiChungCu() {
        String layDienTichQuery = """
            SELECT C.MAHOKHAU, SUM(C.DIENTICH) AS tongDienTich
            FROM CANHO C
            WHERE C.MAHOKHAU IS NOT NULL AND C.DIENTICH IS NOT NULL
            GROUP BY C.MAHOKHAU
        """;

        String layLoaiPhiQuery = """
            SELECT MALOAIPHI, TENLOAIPHI, DONGIA
            FROM LOAIPHI
            WHERE TENLOAIPHI IN ('Phí dịch vụ chung cư', 'Phí quản lý chung cư')
        """;

        String kiemTraKhoanThuQuery = """
            SELECT COUNT(*)
            FROM KHOANTHU
            WHERE HANNOP = ? AND MALOAIPHI = ? AND MAHOKHAU=?
        """;

        String themKhoanThuQuery = """
            INSERT INTO KHOANTHU (MAKHOANTHU, MAHOKHAU, MALOAIPHI, HANNOP, SOTIEN, TRANGTHAI)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
             PreparedStatement psLayDienTich = connection.prepareStatement(layDienTichQuery);
             PreparedStatement psLayLoaiPhi = connection.prepareStatement(layLoaiPhiQuery);
             PreparedStatement psKiemTraKhoanThu = connection.prepareStatement(kiemTraKhoanThuQuery);
             PreparedStatement psThemKhoanThu = connection.prepareStatement(themKhoanThuQuery)) {

            // Lấy danh sách loại phí (Phí dịch vụ chung cư, Phí quản lý chung cư)
            ResultSet rsLoaiPhi = psLayLoaiPhi.executeQuery();
            Map<String, Integer> loaiPhiMap = new HashMap<>(); // Map tên phí -> MALOAIPHI
            Map<String, Integer> donGiaMap = new HashMap<>(); // Map tên phí -> DONGIA
            while (rsLoaiPhi.next()) {
                loaiPhiMap.put(rsLoaiPhi.getString("TENLOAIPHI"), rsLoaiPhi.getInt("MALOAIPHI"));
                donGiaMap.put(rsLoaiPhi.getString("TENLOAIPHI"), rsLoaiPhi.getInt("DONGIA"));
            }

            // Lấy tổng diện tích của mỗi hộ khẩu
            ResultSet rsDienTich = psLayDienTich.executeQuery();
            while (rsDienTich.next()) {
                int maHoKhau = rsDienTich.getInt("MAHOKHAU");
                int tongDienTich = rsDienTich.getInt("tongDienTich");

                // Tính hạn nộp là ngày cuối tháng hiện tại
                LocalDate now = LocalDate.now();
                LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
                java.sql.Date hanNop = java.sql.Date.valueOf(lastDayOfMonth);

                // Duyệt qua từng loại phí
                for (String tenPhi : loaiPhiMap.keySet()) {
                    int maLoaiPhi = loaiPhiMap.get(tenPhi);
                    int donGia = donGiaMap.get(tenPhi);
                    int tongSoTien = tongDienTich * donGia;

                    // Kiểm tra khoản thu đã tồn tại chưa
                    psKiemTraKhoanThu.setDate(1, hanNop);
                    psKiemTraKhoanThu.setInt(2, maLoaiPhi);
                    psKiemTraKhoanThu.setInt(3,maHoKhau);
                    ResultSet rsCheck = psKiemTraKhoanThu.executeQuery();
                    rsCheck.next();
                    if (rsCheck.getInt(1) == 0) {
                        // Thêm khoản thu mới
                        psThemKhoanThu.setInt(1, generateMaKhoanThu(connection)); // Tạo mã khoản thu mới
                        psThemKhoanThu.setInt(2, maHoKhau);
                        psThemKhoanThu.setInt(3, maLoaiPhi);
                        psThemKhoanThu.setDate(4, hanNop);
                        psThemKhoanThu.setInt(5, tongSoTien);
                        psThemKhoanThu.setString(6, "Chưa thanh toán");
                        psThemKhoanThu.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void themPhiGuiXe() {
        String layXeQuery = """
            SELECT X.MAHOKHAU,
                   SUM(CASE WHEN X.LOAIXE = 'Xe máy' THEN 1 ELSE 0 END) AS soLuongXeMay,
                   SUM(CASE WHEN X.LOAIXE = 'Ô tô' THEN 1 ELSE 0 END) AS soLuongXeOto
            FROM XE X
            GROUP BY X.MAHOKHAU
        """;

        String layDonGiaQuery = """
            SELECT MALOAIPHI, TENLOAIPHI, DONGIA
            FROM LOAIPHI
            WHERE TENLOAIPHI IN ('Phí gửi xe máy', 'Phí gửi ô tô')
        """;

        String kiemTraKhoanThuQuery = """
            SELECT COUNT(*)
            FROM KHOANTHU
            WHERE MAHOKHAU = ? AND MALOAIPHI = ? AND HANNOP = ?
        """;

        String themKhoanThuQuery = """
            INSERT INTO KHOANTHU (MAKHOANTHU, MAHOKHAU, MALOAIPHI, HANNOP, SOTIEN, TRANGTHAI)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
             PreparedStatement psLayXe = connection.prepareStatement(layXeQuery);
             PreparedStatement psLayDonGia = connection.prepareStatement(layDonGiaQuery);
             PreparedStatement psKiemTraKhoanThu = connection.prepareStatement(kiemTraKhoanThuQuery);
             PreparedStatement psThemKhoanThu = connection.prepareStatement(themKhoanThuQuery)) {

            // Lấy thông tin đơn giá từ LOAIPHI
            ResultSet rsDonGia = psLayDonGia.executeQuery();
            Map<String, Integer> donGiaMap = new HashMap<>();
            Map<String, Integer> loaiPhiMap = new HashMap<>();
            while (rsDonGia.next()) {
                String tenPhi = rsDonGia.getString("TENLOAIPHI");
                int donGia = rsDonGia.getInt("DONGIA");
                int maLoaiPhi = rsDonGia.getInt("MALOAIPHI");
                donGiaMap.put(tenPhi, donGia);
                loaiPhiMap.put(tenPhi, maLoaiPhi);
            }

            // Lấy danh sách xe theo hộ khẩu
            ResultSet rsXe = psLayXe.executeQuery();
            while (rsXe.next()) {
                int maHoKhau = rsXe.getInt("MAHOKHAU");
                int soLuongXeMay = rsXe.getInt("soLuongXeMay");
                int soLuongXeOto = rsXe.getInt("soLuongXeOto");

                // Tính hạn nộp là ngày cuối tháng hiện tại
                LocalDate now = LocalDate.now();
                LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
                java.sql.Date hanNop = java.sql.Date.valueOf(lastDayOfMonth);

                // Thêm phí gửi xe máy
                if (soLuongXeMay!=0 || soLuongXeOto!=0) {
                    int soTienXeMay = soLuongXeMay * donGiaMap.get("Phí gửi xe máy");
                    int maLoaiPhiXeMay = loaiPhiMap.get("Phí gửi xe máy");
                    int soTienGuiXe= soLuongXeMay * donGiaMap.get("Phí gửi xe máy")+soLuongXeOto * donGiaMap.get("Phí gửi ô tô");

                    // Kiểm tra khoản thu đã tồn tại chưa
                    psKiemTraKhoanThu.setInt(1, maHoKhau);
                    psKiemTraKhoanThu.setInt(2, 0);
                    psKiemTraKhoanThu.setDate(3, hanNop);
                    ResultSet rsCheck = psKiemTraKhoanThu.executeQuery();
                    rsCheck.next();
                    if (rsCheck.getInt(1) == 0) {
                        // Thêm khoản thu mới
                        psThemKhoanThu.setInt(1, generateMaKhoanThu(connection)); // Tạo mã khoản thu mới
                        psThemKhoanThu.setInt(2, maHoKhau);
                        psThemKhoanThu.setInt(3, 0);
                        psThemKhoanThu.setDate(4, hanNop);
                        psThemKhoanThu.setInt(5, soTienGuiXe);
                        psThemKhoanThu.setString(6, "Chưa thanh toán");
                        psThemKhoanThu.executeUpdate();
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static int generateMaKhoanThu(Connection connection)  {
        String sql = "SELECT MAX(MAKHOANTHU) AS MAX FROM KHOANTHU";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MAX") + 1;
            } else {
                return 1; // Bắt đầu từ 1 nếu chưa có khoản thu nào
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

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
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, kt.MAHOKHAU, lp.LOAITHU, kt.SOTIEN, 
               kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN, kt.HANNOP
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
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
                String loaiPhi = resultSet.getString("LOAITHU");
                int soTien = resultSet.getInt("SOTIEN");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");
                Date hanNop = resultSet.getDate("HANNOP");


                // Cập nhật trạng thái dựa trên hạn nộp và thời gian thanh toán
            //    java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
                // Chuyển đổi hạn nộp thành java.util.Date để so sánh
                java.util.Date utilHanNop = new java.util.Date(hanNop.getTime());
                java.util.Date now = new java.util.Date();
//                if ("Chưa thanh toán".equals(trangThai) && now.after(hanNop)) {
//                    trangThai = "Chưa thanh toán (quá hạn)";
//                } else if ("Đã thanh toán".equals(trangThai) && thoiGianThanhToan != null && thoiGianThanhToan.after(hanNop)) {
//                    trangThai = "Đã thanh toán (nộp muộn)";
//                } else if ("Chờ xác nhận".equals(trangThai) && thoiGianThanhToan != null && thoiGianThanhToan.after(hanNop)) {
//                    trangThai = "Chờ xác nhận (nộp muộn)";
//                }

                // So sánh và cập nhật trạng thái
                if ("Chưa thanh toán".equals(trangThai) && now.after(utilHanNop)) {
                    trangThai = "Chưa thanh toán (quá hạn)";
                } else if ("Đã thanh toán".equals(trangThai) && thoiGianThanhToan != null && thoiGianThanhToan.after(hanNop)) {
                    trangThai = "Đã thanh toán (nộp muộn)";
                } else if ("Chờ xác nhận".equals(trangThai) && thoiGianThanhToan != null && thoiGianThanhToan.after(hanNop)) {
                    trangThai = "Chờ xác nhận (nộp muộn)";
                }

                // Tùy chỉnh tên phí nếu cần
            //    if ("Phí gửi xe".equals(tenLoaiPhi) || "Phí dịch vụ chung cư".equals(tenLoaiPhi) || "Phí quản lý chung cư".equals(tenLoaiPhi)) {
                    // Lấy tháng/năm từ hạn nộp
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(hanNop);
                    int month = calendar.get(Calendar.MONTH) + 1; // Tháng từ 0-11
                    int year = calendar.get(Calendar.YEAR);
                    tenLoaiPhi = tenLoaiPhi + " " + String.format("%02d/%d", month, year); // Định dạng "Phí gửi xe 12/2024"
            //    }

                // Lấy tên chủ hộ
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(maHoKhau);
                NhanKhauModel nhanKhauModel = new NhanKhauModel();
                nhanKhauModel.setHoTenNhanKhau(HoKhauService.layTenChuHo(maHoKhau));
                hoKhauModel.setNhanKhauModel(nhanKhauModel);

                // Tạo đối tượng LoaiPhiModel và KhoanThuModel
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, null);
                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);

                // Thêm vào danh sách
                danhSachThanhToan.add(khoanThuModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(danhSachThanhToan.size());
        return danhSachThanhToan;
    }

    public static List<KhoanThuModel> layLichSuThanhToanUserTheoNgay(int maHoKhau,Date batDau, Date ketThuc) {
        String sql = """
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, kt.MAHOKHAU, lp.LOAITHU, kt.SOTIEN, 
               kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN, kt.HANNOP
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
        WHERE kt.MAHOKHAU=? AND kt.TRANGTHAI='Đã thanh toán' AND kt.THOIGIANTHANHTOAN BETWEEN ? AND ?
    """;

        List<KhoanThuModel> danhSachThanhToan = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maHoKhau);
            preparedStatement.setDate(2, batDau);
            preparedStatement.setDate(3, ketThuc);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKhoanThu = resultSet.getInt("MAKHOANTHU");
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
                //    int maHoKhau = resultSet.getInt("MAHOKHAU");
                String loaiPhi = resultSet.getString("LOAITHU");
                int soTien = resultSet.getInt("SOTIEN");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");
                Date hanNop = resultSet.getDate("HANNOP");

                // Tùy chỉnh tên phí nếu cần
                //    if ("Phí gửi xe".equals(tenLoaiPhi) || "Phí dịch vụ chung cư".equals(tenLoaiPhi) || "Phí quản lý chung cư".equals(tenLoaiPhi)) {
                // Lấy tháng/năm từ hạn nộp
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(hanNop);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng từ 0-11
                int year = calendar.get(Calendar.YEAR);
                tenLoaiPhi = tenLoaiPhi + " " + String.format("%02d/%d", month, year); // Định dạng "Phí gửi xe 12/2024"
                //    }

                // Lấy tên chủ hộ
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(maHoKhau);
                NhanKhauModel nhanKhauModel = new NhanKhauModel();
                nhanKhauModel.setHoTenNhanKhau(HoKhauService.layTenChuHo(maHoKhau));
                hoKhauModel.setNhanKhauModel(nhanKhauModel);

                // Tạo đối tượng LoaiPhiModel và KhoanThuModel
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, null);
                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);

                // Thêm vào danh sách
                danhSachThanhToan.add(khoanThuModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("user");
        return danhSachThanhToan;
    }

    // Hiển thị danh sách các thanh toán của user
    public static List<KhoanThuModel> layThongTinThanhToanUser(int maHoKhau) {
        String sql = """
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, kt.MAHOKHAU, lp.LOAITHU, kt.SOTIEN, 
               kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN, kt.HANNOP
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
        JOIN HOKHAU hk ON kt.MAHOKHAU = hk.MAHOKHAU
        WHERE kt.MAHOKHAU=? AND kt.TRANGTHAI!='Đã thanh toán'
    """;

        List<KhoanThuModel> danhSachThanhToan = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setInt(1, maHoKhau);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKhoanThu = resultSet.getInt("MAKHOANTHU");
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
            //    int maHoKhau = resultSet.getInt("MAHOKHAU");
                String loaiPhi = resultSet.getString("LOAITHU");
                int soTien = resultSet.getInt("SOTIEN");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");
                Date hanNop = resultSet.getDate("HANNOP");

                // Tùy chỉnh tên phí nếu cần
                //    if ("Phí gửi xe".equals(tenLoaiPhi) || "Phí dịch vụ chung cư".equals(tenLoaiPhi) || "Phí quản lý chung cư".equals(tenLoaiPhi)) {
                // Lấy tháng/năm từ hạn nộp
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(hanNop);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng từ 0-11
                int year = calendar.get(Calendar.YEAR);
                tenLoaiPhi = tenLoaiPhi + " " + String.format("%02d/%d", month, year); // Định dạng "Phí gửi xe 12/2024"
                //    }

                // Lấy tên chủ hộ
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(maHoKhau);
                NhanKhauModel nhanKhauModel = new NhanKhauModel();
                nhanKhauModel.setHoTenNhanKhau(HoKhauService.layTenChuHo(maHoKhau));
                hoKhauModel.setNhanKhauModel(nhanKhauModel);

                // Tạo đối tượng LoaiPhiModel và KhoanThuModel
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, null);
                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);

                // Thêm vào danh sách
                danhSachThanhToan.add(khoanThuModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("user");
        return danhSachThanhToan;
    }

    public static List<KhoanThuModel> layLichSuThanhToanUser(int maHoKhau) {
        String sql = """
        SELECT kt.MAKHOANTHU, lp.MALOAIPHI, lp.TENLOAIPHI, kt.MAHOKHAU, lp.LOAITHU, kt.SOTIEN, 
               kt.THOIGIANTHANHTOAN, kt.TRANGTHAI, kt.PHUONGTHUCTHANHTOAN, kt.HANNOP
        FROM KHOANTHU kt
        JOIN LOAIPHI lp ON kt.MALOAIPHI = lp.MALOAIPHI
        JOIN HOKHAU hk ON kt.MAHOKHAU = hk.MAHOKHAU
        WHERE kt.MAHOKHAU=? AND kt.TRANGTHAI='Đã thanh toán'
    """;

        List<KhoanThuModel> danhSachThanhToan = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maHoKhau);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKhoanThu = resultSet.getInt("MAKHOANTHU");
                int maLoaiPhi = resultSet.getInt("MALOAIPHI");
                String tenLoaiPhi = resultSet.getString("TENLOAIPHI");
                //    int maHoKhau = resultSet.getInt("MAHOKHAU");
                String loaiPhi = resultSet.getString("LOAITHU");
                int soTien = resultSet.getInt("SOTIEN");
                Date thoiGianThanhToan = resultSet.getDate("THOIGIANTHANHTOAN");
                String trangThai = resultSet.getString("TRANGTHAI");
                String phuongThucThanhToan = resultSet.getString("PHUONGTHUCTHANHTOAN");
                Date hanNop = resultSet.getDate("HANNOP");

                // Tùy chỉnh tên phí nếu cần
                //    if ("Phí gửi xe".equals(tenLoaiPhi) || "Phí dịch vụ chung cư".equals(tenLoaiPhi) || "Phí quản lý chung cư".equals(tenLoaiPhi)) {
                // Lấy tháng/năm từ hạn nộp
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(hanNop);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng từ 0-11
                int year = calendar.get(Calendar.YEAR);
                tenLoaiPhi = tenLoaiPhi + " " + String.format("%02d/%d", month, year); // Định dạng "Phí gửi xe 12/2024"
                //    }

                // Lấy tên chủ hộ
                HoKhauModel hoKhauModel = new HoKhauModel();
                hoKhauModel.setMaHoKhau(maHoKhau);
                NhanKhauModel nhanKhauModel = new NhanKhauModel();
                nhanKhauModel.setHoTenNhanKhau(HoKhauService.layTenChuHo(maHoKhau));
                hoKhauModel.setNhanKhauModel(nhanKhauModel);

                // Tạo đối tượng LoaiPhiModel và KhoanThuModel
                LoaiPhiModel loaiPhiModel = new LoaiPhiModel(maLoaiPhi, tenLoaiPhi, soTien, loaiPhi, null);
                KhoanThuModel khoanThuModel = new KhoanThuModel(maKhoanThu, hoKhauModel, loaiPhiModel, soTien, thoiGianThanhToan, trangThai, phuongThucThanhToan);

                // Thêm vào danh sách
                danhSachThanhToan.add(khoanThuModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("user");
        return danhSachThanhToan;
    }

    public static boolean xacNhanThanhToan(KhoanThuModel khoanThu) {
        String sql = """
        UPDATE KHOANTHU
        SET TRANGTHAI = 'Đã thanh toán',
            THOIGIANTHANHTOAN = ?
        WHERE MAKHOANTHU = ?
    """;

        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Gán giá trị cho các tham số trong câu lệnh SQL
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis())); // Thời gian thanh toán hiện tại
            preparedStatement.setInt(2, khoanThu.getMaKhoanThu()); // Mã khoản thu cần cập nhật

            // Thực thi câu lệnh SQL
            int rowsAffected = preparedStatement.executeUpdate();

            // Nếu cập nhật thành công ít nhất một dòng, trả về true
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu có lỗi hoặc không cập nhật được, trả về false
        return false;
    }

    public static boolean guiThanhToan(KhoanThuModel khoanThu) {
        String sql = """
        UPDATE KHOANTHU
        SET TRANGTHAI = 'Chờ xác nhận',
            THOIGIANTHANHTOAN = ?
        WHERE MAKHOANTHU = ?
    """;

        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Gán giá trị cho các tham số trong câu lệnh SQL
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis())); // Thời gian thanh toán hiện tại
            preparedStatement.setInt(2, khoanThu.getMaKhoanThu()); // Mã khoản thu cần cập nhật

            // Thực thi câu lệnh SQL
            int rowsAffected = preparedStatement.executeUpdate();

            // Nếu cập nhật thành công ít nhất một dòng, trả về true
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu có lỗi hoặc không cập nhật được, trả về false
        return false;
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
