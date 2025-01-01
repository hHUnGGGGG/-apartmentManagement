package Service;

import Models.NhanKhauModel;
import Models.TamVangModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanKhauService {
    private final Connection connection;

    public NhanKhauService(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    HoKhauService hoKhauService = new HoKhauService();

    // Thêm nhân khẩu
    public boolean addNhanKhau(NhanKhauModel nhankhauModel) {

        String query = "INSERT INTO NHANKHAU (MANHANKHAU, CCCD, HOTEN, NGAYSINH, SDT, MAHOKHAU, QUANHEVOICHUHO, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int maHoKhau = nhankhauModel.getMaHoKhau();  // Mã hộ khẩu hiện tại

            // Lấy mã nhân khẩu cuối cùng trong hộ khẩu đó
            int nextMaNhanKhau = getNextMaNhanKhau(maHoKhau);

            // Cập nhật mã nhân khẩu cho đối tượng nhân khẩu
            nhankhauModel.setMaNhanKhau(nextMaNhanKhau);

            // Thiết lập các tham số cho PreparedStatement
            setNhanKhauParamsAdd(preparedStatement, nhankhauModel);

            return preparedStatement.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi thêm nhân khẩu: " + e.getMessage());
            return false;
        }
    }

    //Thêm chủ hộ
    public boolean addChuHo(NhanKhauModel nhankhauModel) {

        String query = "INSERT INTO NHANKHAU (MANHANKHAU, CCCD, HOTEN, NGAYSINH, SDT, MAHOKHAU, QUANHEVOICHUHO, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int maHoKhau = nhankhauModel.getMaHoKhau();  // Mã hộ khẩu hiện tại

            // Cập nhật mã nhân khẩu cho đối tượng nhân khẩu
            nhankhauModel.setMaNhanKhau(maHoKhau + 1);

            // Thiết lập các tham số cho PreparedStatement
            setNhanKhauParamsAdd(preparedStatement, nhankhauModel);

            return preparedStatement.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi thêm nhân khẩu: " + e.getMessage());
            return false;
        }
    }


    // Phương thức này để lấy mã nhân khẩu tiếp theo trong hộ khẩu
    public int getNextMaNhanKhau(int maHoKhau) throws SQLException {

        String query = "SELECT MAX(MANHANKHAU) FROM NHANKHAU WHERE MAHOKHAU = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maHoKhau);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxMaNhanKhau = resultSet.getInt(1);
                return maxMaNhanKhau + 1;  // Tính mã nhân khẩu tiếp theo
            }
        }
        return 0;
    }


    // Xóa nhân khẩu
    public boolean delNhanKhau(int maNhanKhau) {

        String query = "DELETE FROM NHANKHAU WHERE MANHANKHAU = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maNhanKhau);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân khẩu với (mã nhân khẩu: " + maNhanKhau + "): " + e.getMessage());
            return false;
        }
    }


    // Sửa thông tin nhân khẩu
    public boolean updateNhanKhau(NhanKhauModel nhankhauModel) {

        String query = "UPDATE NHANKHAU SET CCCD = ?, HOTEN = ?, NGAYSINH = ?, SDT = ?, MAHOKHAU = ?, QUANHEVOICHUHO = ?, TRANGTHAI = ?, TRANGTHAITAMVANG = ? WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setNhanKhauParamsUpdate(preparedStatement, nhankhauModel);
            // Trả về kết quả trực tiếp từ executeUpdate
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi sửa thông tin nhân khẩu với (mã nhân khẩu: " + nhankhauModel.getMaNhanKhau() + "): " + e.getMessage());
            return false;
        }
    }


    // Tìm kiếm nhân khẩu theo mã
    public List<NhanKhauModel> searchNhanKhauBySoPhong(String soPhong) {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query =  "SELECT NK.MANHANKHAU, NK.CCCD, NK.HOTEN, NK.NGAYSINH, NK.SDT, NK.MAHOKHAU, NK.QUANHEVOICHUHO, NK.TRANGTHAI, NK.TRANGTHAITAMVANG, CH.SOPHONG " +
                        "FROM NHANKHAU NK " +
                        "JOIN CANHO CH ON CH.MAHOKHAU = NK.MAHOKHAU " +
                        "WHERE CH.SOPHONG LIKE ?";
        String searchPattern = "%" + soPhong.trim() + "%";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchPattern);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int maHoKhau;
                    if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                    else maHoKhau = 0;
                    NhanKhauModel nhanKhau = new NhanKhauModel(
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            maHoKhau,
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI"),
                            rs.getInt("SOPHONG"),
                            rs.getBoolean("TRANGTHAITAMVANG")
                    );
                    listNhanKhau.add(nhanKhau);
                }
            }
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (số phòng: " + soPhong + "): " + e.getMessage());
        }
        return listNhanKhau; // Trả về null nếu không tìm thấy
    }


    // Tìm kiếm nhân khẩu theo cccd
    public List<NhanKhauModel> searchNhanKhauByCCCD(String CCCD) {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query =  "SELECT NK.MANHANKHAU, NK.CCCD, NK.HOTEN, NK.NGAYSINH, NK.SDT, NK.MAHOKHAU, NK.QUANHEVOICHUHO, NK.TRANGTHAI, NK.TRANGTHAITAMVANG, CH.SOPHONG " +
                        "FROM NHANKHAU NK " +
                        "JOIN CANHO CH ON CH.MAHOKHAU = NK.MAHOKHAU " +
                        "WHERE NK.CCCD LIKE ?";
        String searchPattern = "%" + CCCD.trim() + "%";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchPattern);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int maHoKhau;
                    if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                    else maHoKhau = 0;
                    NhanKhauModel nhanKhau = new NhanKhauModel(
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            maHoKhau,
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI"),
                            rs.getInt("SOPHONG"),
                            rs.getBoolean("TRANGTHAITAMVANG")
                    );
                    listNhanKhau.add(nhanKhau);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (CCCD: " + CCCD + "): " + e.getMessage());
        }
        return listNhanKhau;  // Trả về null nếu không tìm thấy
    }


    // Tìm kiếm nhân khẩu theo tên
    public List<NhanKhauModel> searchNhanKhauByTen(String ten) {

        List<NhanKhauModel> listNhanKhau = new ArrayList<>();

        String query =  "SELECT NK.MANHANKHAU, NK.CCCD, NK.HOTEN, NK.NGAYSINH, NK.SDT, NK.MAHOKHAU, NK.QUANHEVOICHUHO, NK.TRANGTHAI, NK.TRANGTHAITAMVANG, CH.SOPHONG " +
                        "FROM NHANKHAU NK " +
                        "JOIN CANHO CH ON CH.MAHOKHAU = NK.MAHOKHAU " +
                        "WHERE LOWER(HOTEN) LIKE LOWER(?)";

        String searchPattern = "%" + ten.trim() + "%";  // Cải thiện việc tạo chuỗi tìm kiếm

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchPattern);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int maHoKhau;
                    if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                    else maHoKhau = 0;
                    NhanKhauModel nhanKhau = new NhanKhauModel(
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            maHoKhau,
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI"),
                            rs.getInt("SOPHONG"),
                            rs.getBoolean("TRANGTHAITAMVANG")
                    );
                    listNhanKhau.add(nhanKhau);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (tên: " + ten + "): " + e.getMessage());
        }
        return listNhanKhau;
    }


    public void updateTamVangBatDau(int maNhanKhau, String liDo){
        String query =  "INSERT INTO TAMVANG (MANHANKHAU, THOIGIANBATDAU, LYDO) " +
                        "VALUES (?, NOW(), ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,maNhanKhau);
            preparedStatement.setString(2,liDo);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.err.println("Lỗi khi cập nhật tạm vắng:" + e.getMessage());
        }
    }

    public void updateTamVangKetThuc(int maNhanKhau){
        String query =  "UPDATE TAMVANG SET THOIGIANKETTHUC = NOW() " +
                        "WHERE THOIGIANBATDAU is not null AND THOIGIANKETTHUC is null AND MANHANKHAU = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maNhanKhau);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.err.println("Lỗi khi cập nhật tạm vắng:" + e.getMessage());
        }
    }


    public List<TamVangModel> getListTamVang(int maNhanKhau){
        List<TamVangModel> listTamVang = new ArrayList<>();
        String query = "SELECT * FROM TAMVANG WHERE MANHANKHAU = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, maNhanKhau);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    listTamVang.add(createTamVangFromResultSet(rs));
                }
            }
        } catch (SQLException e){
            System.err.println("Lỗi khi lấy lịch sử tạm vắng:" + e.getMessage());
        }
        return listTamVang;
    }


    // Lấy danh sách nhân khẩu
    public List<NhanKhauModel> getListNhanKhau() {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query =  "SELECT NK.MANHANKHAU, NK.CCCD, NK.HOTEN, NK.NGAYSINH, NK.SDT, NK.MAHOKHAU, NK.QUANHEVOICHUHO, NK.TRANGTHAI, NK.TRANGTHAITAMVANG, CH.SOPHONG " +
                        "FROM NHANKHAU NK " +
                        "LEFT JOIN CANHO CH ON CH.MAHOKHAU = NK.MAHOKHAU";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int maHoKhau;
                if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                else maHoKhau = 0;
                NhanKhauModel nhanKhau = new NhanKhauModel(
                        rs.getInt("MANHANKHAU"),
                        rs.getString("CCCD"),
                        rs.getString("HOTEN"),
                        rs.getDate("NGAYSINH"),
                        rs.getString("SDT"),
                        maHoKhau,
                        rs.getString("QUANHEVOICHUHO"),
                        rs.getString("TRANGTHAI"),
                        rs.getInt("SOPHONG"),
                        rs.getBoolean("TRANGTHAITAMVANG")
                );
                listNhanKhau.add(nhanKhau);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân khẩu: " + e.getMessage());
        }
        return listNhanKhau;
    }


    public List<NhanKhauModel> getTVtrongHK(boolean laMotHo) throws SQLException {
        List<NhanKhauModel> listTV = new ArrayList<>();

        String query = "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ?";

        int maHoKhau;
        if(laMotHo) maHoKhau = hoKhauService.getMaxMaHoKhau();
        else maHoKhau = hoKhauService.getMaxMaHoKhauTamTru();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement .setInt(1,maHoKhau);

             try (ResultSet rs = preparedStatement.executeQuery()) {
                 while (rs.next()) {
                     listTV.add(createNhanKhauFromResultSet(rs));  // Trả về đối tượng nếu tìm thấy
                 }
             }

         } catch (SQLException e){
             System.err.println("Lỗi khi lấy danh sách thành viên: " + e.getMessage());
         }
         return listTV;
    }


    // Helper: Gán tham số PreparedStatement
    public void setNhanKhauParamsAdd(PreparedStatement preparedStatement, NhanKhauModel nhankhauModel) throws SQLException {
        preparedStatement.setInt(1, nhankhauModel.getMaNhanKhau());
        preparedStatement.setString(2, nhankhauModel.getCCCD());
        preparedStatement.setString(3, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(4, new java.sql.Date(nhankhauModel.getNgaySinh().getTime()));
        preparedStatement.setString(5, nhankhauModel.getSDT());
        preparedStatement.setInt(6, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(7, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setString(8, nhankhauModel.getTrangThai());
    }


    private void setNhanKhauParamsUpdate(PreparedStatement preparedStatement, NhanKhauModel nhankhauModel) throws SQLException {
        preparedStatement.setString(1, nhankhauModel.getCCCD());
        preparedStatement.setString(2, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(3, new java.sql.Date(nhankhauModel.getNgaySinh().getTime()));
        preparedStatement.setString(4, nhankhauModel.getSDT());
        preparedStatement.setInt(5, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(6, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setString(7, nhankhauModel.getTrangThai());
        preparedStatement.setBoolean(8,nhankhauModel.isTamVang());
        preparedStatement.setInt(9,nhankhauModel.getMaNhanKhau());
    }


    // Helper: Tạo đối tượng NhanKhauModel từ ResultSet
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

    private  TamVangModel createTamVangFromResultSet(ResultSet rs) throws SQLException {
        return new TamVangModel(
                rs.getString("LYDO"),
                rs.getDate("THOIGIANBATDAU"),
                rs.getDate("THOIGIANKETTHUC")
        );
    }

}
