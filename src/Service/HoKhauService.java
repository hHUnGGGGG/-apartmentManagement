    package Service;

    import Models.HoKhauModel;
    import Models.NhanKhauModel;
    import database.DatabaseConnection;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class HoKhauService {

        private final Connection connection;

        public HoKhauService() {
            this.connection = DatabaseConnection.getInstance().getConnection();
        }


        // Lấy mã hộ lớn nhất
        public int getMaxMaHoKhauThuongTru() throws SQLException {
            String query = "SELECT COALESCE(MAX(MAHOKHAU), 1000000) AS maxMaHoKhau FROM HOKHAU";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    return resultSet.getInt("maxMaHoKhau");
                }
            }
            return 1000000; // Trả về giá trị mặc định nếu bảng rỗng
        }


        public int getMaxMaHoKhauTamTru() throws SQLException {
            String query =  "SELECT COALESCE(MAX(MAHOKHAU), 100000) AS MaxMahokhauTamChu " +
                    "FROM HOKHAU " +
                    "WHERE MAHOKHAU BETWEEN 100000 AND 999999";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    return resultSet.getInt("MaxMahokhauTamChu");
                }
            }
            return 100000; // Trả về giá trị mặc định nếu bảng rỗng
        }


        // Thêm hộ khẩu
        public boolean addHoKhau(HoKhauModel hokhauModel) {
            String insertQuery = "INSERT INTO HOKHAU (MAHOKHAU) VALUES (?)";

            try {
                // Lấy mã hộ khẩu lớn nhất hiện tại
                int maxMaHoKhau = getMaxMaHoKhau();
                int nextMaHoKhau = maxMaHoKhau + 100; // Tăng thêm 100

                // Gán mã hộ khẩu mới vào đối tượng
                hokhauModel.setMaHoKhau(nextMaHoKhau);

                // Thực hiện thêm hộ khẩu vào cơ sở dữ liệu
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, hokhauModel.getMaHoKhau());
                    int rowsInserted = preparedStatement.executeUpdate();
                    return rowsInserted > 0; // Trả về true nếu thêm thành công
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi thêm hộ khẩu: " + e.getMessage());
                return false; // Trả về false nếu có lỗi xảy ra
            }
        }


        // Xóa hộ khẩu
        public boolean delHoKhau(int maHoKhau) {

            String query = "DELETE FROM HOKHAU WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Gán tham số cho câu lệnh SQL
                preparedStatement.setInt(1, maHoKhau);

                // Thực thi câu lệnh và kiểm tra kết quả
                return preparedStatement.executeUpdate() > 0; // Trả về true nếu xóa thành công
            } catch (SQLException e) {
                // Ghi log lỗi chi tiết
                System.err.println("Lỗi khi xóa hộ khẩu với (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
                return false;
            }
        }


        // Tìm kiếm hộ khẩu theo mã hộ khẩu
        public List<NhanKhauModel> searchHoKhaubyId(String maHoKhau) {

            List<NhanKhauModel> listHoKhau = new ArrayList<>();
            String query = "SELECT * FROM NHANKHAU WHERE QUANHEVOICHUHO = 'Chủ hộ' AND MAHOKHAU LIKE ?" ;

            String searchPattern = "%" + maHoKhau.trim() + "%";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Gán tham số cho câu lệnh SQL
                preparedStatement.setString(1, searchPattern);

                // Thực thi câu lệnh và xử lý kết quả
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        listHoKhau.add(createNhanKhauFromResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                // Ghi log lỗi chi tiết
                System.err.println("Lỗi khi tìm kiếm hộ khẩu (mã hộ khẩu: " + maHoKhau + "): " + e.getMessage());
            }

            return listHoKhau;
        }


        // Tìm kiếm nhân khẩu theo cccd
        public List<NhanKhauModel> searchHoKhauByCCCD(String cccd) {

            List<NhanKhauModel> listNhanKhau = new ArrayList<>();
            String query = "SELECT * FROM NHANKHAU WHERE QUANHEVOICHUHO = 'Chủ hộ' AND CCCD LIKE ?";

            String searchPattern = "%" + cccd.trim() + "%";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, searchPattern);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        listNhanKhau.add(createNhanKhauFromResultSet(rs));  // Trả về đối tượng nếu tìm thấy
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi tìm kiếm chủ hộ với (CCCD: " + cccd + "): " + e.getMessage());
            }
            return listNhanKhau;  // Trả về null nếu không tìm thấy
        }


        // Tìm kiếm nhân khẩu theo tên
        public List<NhanKhauModel> searchHoKhauByTen(String ten) {

            List<NhanKhauModel> listNhanKhau = new ArrayList<>();
            String query = "SELECT * FROM NHANKHAU WHERE LOWER(HOTEN) LIKE LOWER(?) AND QUANHEVOICHUHO = 'Chủ hộ'";

            String searchPattern = "%" + ten.trim() + "%";  // Cải thiện việc tạo chuỗi tìm kiếm

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchPattern);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        listNhanKhau.add(createNhanKhauFromResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi tìm kiếm chủ hộ với (tên: " + ten + "): " + e.getMessage());
            }
            return listNhanKhau;
        }


        // Lấy danh sách hộ khẩu
        public List<NhanKhauModel> getListHoKhau() {
            List<NhanKhauModel> danhSachHoKhau = new ArrayList<>();

            String query =
                    "SELECT MAHOKHAU, MANHANKHAU, HOTEN AS TENCHUHO, CCCD, SDT " +
                            "FROM NHANKHAU " +
                            "WHERE QUANHEVOICHUHO = 'Chủ hộ'";

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {

                while (rs.next()) {
                    NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                            rs.getInt("MAHOKHAU"),
                            rs.getInt("MANHANKHAU"),
                            rs.getString("TENCHUHO"),
                            rs.getString("CCCD"),
                            rs.getString("SDT")
                    );

                    danhSachHoKhau.add(hoKhauChuHo);
                }

            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy danh sách hộ khẩu: " + e.getMessage());
            }

            return danhSachHoKhau;

        }

        public boolean existsHoKhauId(int maHoKhau) throws SQLException {
            String query = "SELECT COUNT(*) FROM HOKHAU WHERE MAHOKHAU = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, maHoKhau);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu có ít nhất 1 bản ghi
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
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

    }