package Service;

import Models.NhanKhauModel;
import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

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

        try (//Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maNhanKhau);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Đã xóa nhân khẩu thành công!");
                return true;
            } else {
                System.out.println("Không tìm thấy nhân khẩu    với mã: " + maNhanKhau);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            preparedStatement.setString(1, nhankhauModel.getCCCD());
            preparedStatement.setString(2, nhankhauModel.getHoTenNhanKhau());
            preparedStatement.setDate(3,new java.sql.Date(nhankhauModel.getNgaySinh().getTime()));
            preparedStatement.setString(4, nhankhauModel.getSDT());
            preparedStatement.setInt(5, nhankhauModel.getMaHoKhau());
            preparedStatement.setString(6, nhankhauModel.getQuanHeVoiChuHo());
            preparedStatement.setBoolean(7, nhankhauModel.isTrangThai());
            // Trả về kết quả trực tiếp từ executeUpdate
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi sửa thông tin nhân khẩu với (mã nhân khẩu: " + nhankhauModel.getMaNhanKhau() + "): " + e.getMessage());
            return false;
        }
    }


    // Tìm kiếm nhân khẩu theo mã
    public List<NhanKhauModel> searchNhanKhauById(int maNhanKhau) {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU WHERE MANHANKHAU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, maNhanKhau);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    listNhanKhau.add(createNhanKhauFromResultSet(rs)); // Trả về đối tượng nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            // Ghi log chi tiết lỗi
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (mã nhân khẩu: " + maNhanKhau + "): " + e.getMessage());
        }
        return listNhanKhau; // Trả về null nếu không tìm thấy
    }


    // Tìm kiếm nhân khẩu theo cccd
    public List<NhanKhauModel> searchNhanKhauByCCCD(String cccd) {
        List<NhanKhauModel> listNhanKhau = new ArrayList<>();
        String query = "SELECT * FROM NHANKHAU WHERE CCCD = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cccd);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    listNhanKhau.add(createNhanKhauFromResultSet(rs));  // Trả về đối tượng nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân khẩu với (CCCD: " + cccd + "): " + e.getMessage());
        }
        return listNhanKhau;  // Trả về null nếu không tìm thấy
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
        preparedStatement.setInt(1, nhankhauModel.getMaNhanKhau());  // Mã nhân khẩu
        preparedStatement.setString(2, nhankhauModel.getCCCD());
        preparedStatement.setString(3, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(4, new java.sql.Date(nhankhauModel.getNgaySinh().getTime()));
        preparedStatement.setString(5, nhankhauModel.getSDT());
        preparedStatement.setInt(6, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(7, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setBoolean(8, nhankhauModel.isTrangThai());  // Trang thái (tick vào hay không)
    }


    private void setNhanKhauParamsUpdate(PreparedStatement preparedStatement, NhanKhauModel nhankhauModel) throws SQLException {
        preparedStatement.setString(1, nhankhauModel.getCCCD());
        preparedStatement.setString(2, nhankhauModel.getHoTenNhanKhau());
        preparedStatement.setDate(3, new java.sql.Date(nhankhauModel.getNgaySinh().getTime()));
        preparedStatement.setString(4, nhankhauModel.getSDT());
        preparedStatement.setInt(5, nhankhauModel.getMaHoKhau());
        preparedStatement.setString(6, nhankhauModel.getQuanHeVoiChuHo());
        preparedStatement.setBoolean(7, nhankhauModel.isTrangThai());  // Trang thái (tick vào hay không)
    }


    // Helper: Tạo đối tượng NhanKhauModel từ ResultSet
    private NhanKhauModel createNhanKhauFromResultSet(ResultSet rs) throws SQLException {
        return new NhanKhauModel(
                rs.getInt("MAHOKHAU"),
                rs.getInt("MANHANKHAU"),
                rs.getString("CCCD"),
                rs.getString("HOTEN"),
                rs.getDate("NGAYSINH"),
                rs.getString("SDT"),
                rs.getString("QUANHEVOICHUHO"),
                rs.getBoolean("TRANGTHAI")
        );
    }

    public void loadData(TableView<NhanKhauModel> NhanKhauTable, ObservableList<NhanKhauModel> danhSachNhanKhau) {
        List<NhanKhauModel> listNhanKhau = getListNhanKhau();
        danhSachNhanKhau = FXCollections.observableArrayList(listNhanKhau);
        NhanKhauTable.setItems(danhSachNhanKhau);
    }
}
