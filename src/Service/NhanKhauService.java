package Service;

import Models.NhanKhauModel;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanKhauService {
    private final Connection connection;

    public NhanKhauService(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


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
        return maHoKhau + 1;  // Nếu không có nhân khẩu nào trong hộ khẩu, bắt đầu từ mã hộ khẩu + 1
    }


    // Xóa nhân khẩu
    public boolean delNhanKhau(int maNhanKhau) {

        String query = "DELETE FROM NHANKHAU WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maNhanKhau);

            // Trả về kết quả trực tiếp từ executeUpdate
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi xóa nhân khẩu với (mã nhân khẩu: " + maNhanKhau + "): " + e.getMessage());
            return false;
        }
    }


    //Xóa nhân khẩu khi xóa hộ khẩu
    public boolean delNhanKhauHoKhau(int maHoKhau) {

        String query = "DELETE FROM NHANKHAU WHERE MAHOKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maHoKhau);

            // Trả về kết quả trực tiếp từ executeUpdate
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi xóa nhân khẩu trong hộ khẩu với (mã hộ khẩu: " + maHoKhau + "): " +e.getMessage());
            return false;
        }
    }


    // Sửa thông tin nhân khẩu
    public boolean updateNhanKhau(NhanKhauModel nhankhauModel) {

        String query = "UPDATE NHANKHAU SET CCCD = ?, HOTEN = ?, NGAYSINH = ?, SDT = ?, MAHOKHAU = ?, QUANHEVOICHUHO = ?, TRANGTHAI = ? WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setNhanKhauParamsUpdate(preparedStatement, nhankhauModel);
            preparedStatement.setInt(8, nhankhauModel.getMaNhanKhau()); // Đảm bảo tham số cuối cùng là mã nhân khẩu

            // Trả về kết quả trực tiếp từ executeUpdate
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi sửa thông tin nhân khẩu với (mã nhân khẩu: " + nhankhauModel.getMaNhanKhau() + "): " + e.getMessage());
            return false;
        }
    }


    // Tìm kiếm nhân khẩu theo mã
    public NhanKhauModel searchNhanKhauById(int maNhanKhau) {

        String query = "SELECT * FROM NHANKHAU WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maNhanKhau);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return createNhanKhauFromResultSet(rs); // Trả về đối tượng nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (mã nhân khẩu: " + maNhanKhau + "): " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy
    }


    // Tìm kiếm nhân khẩu theo cccd
    public NhanKhauModel searchNhanKhauByCCCD(String cccd) {

        String query = "SELECT * FROM NHANKHAU WHERE CCCD = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cccd);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return createNhanKhauFromResultSet(rs);  // Trả về đối tượng nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (CCCD: " + cccd + "): " + e.getMessage());
        }
        return null;  // Trả về null nếu không tìm thấy
    }


    // Tìm kiếm nhân khẩu theo tên
    public List<NhanKhauModel> searchNhanKhauByTen(String ten) {

        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU WHERE LOWER(HOTEN) LIKE LOWER(?)";

        String searchPattern = "%" + ten.trim() + "%";  // Cải thiện việc tạo chuỗi tìm kiếm

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchPattern);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    listNhanKhau.add(createNhanKhauFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (tên: " + ten + "): " + e.getMessage());
        }
        return listNhanKhau;
    }


    // Lấy danh sách nhân khẩu
    public List<NhanKhauModel> getListNhanKhau() {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                listNhanKhau.add(createNhanKhauFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân khẩu: " + e.getMessage());
            // Nếu có lỗi, trả về danh sách rỗng thay vì null
        }
        return listNhanKhau; // Trả về danh sách rỗng nếu có lỗi
    }


    // Cheked mã nhân khẩu đã tồn tại hay chưa
    public boolean existsNhanKhauId(int maNhanKhau) {
        String query = "SELECT COUNT(*) FROM NHANKHAU WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maNhanKhau);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Nếu có kết quả thì trả về true
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra với (mã nhân khẩu : " + maNhanKhau + "):" + e.getMessage());
            return false; // Trả về false nếu có lỗi hoặc không tìm thấy
        }
    }


    // Helper: Gán tham số PreparedStatement
    public void setNhanKhauParamsAdd(PreparedStatement preparedStatement, NhanKhauModel nhankhauModel) throws SQLException {
        preparedStatement.setInt(1, nhankhauModel.getMaNhanKhau());  // Mã nhân khẩu
        preparedStatement.setString(2, nhankhauModel.getCCCD());
        preparedStatement.setString(3, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(4, nhankhauModel.getNgaySinh());
        preparedStatement.setString(5, nhankhauModel.getSDT());
        preparedStatement.setInt(6, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(7, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setBoolean(8, nhankhauModel.isTrangThai());  // Trang thái (tick vào hay không)
    }


    private void setNhanKhauParamsUpdate(PreparedStatement preparedStatement, NhanKhauModel nhankhauModel) throws SQLException {
        preparedStatement.setString(1, nhankhauModel.getCCCD());
        preparedStatement.setString(2, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(3, nhankhauModel.getNgaySinh());
        preparedStatement.setString(4, nhankhauModel.getSDT());
        preparedStatement.setInt(5, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(6, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setBoolean(7, nhankhauModel.isTrangThai());  // Trang thái (tick vào hay không)
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
                rs.getBoolean("TRANGTHAI")
        );
    }

}
