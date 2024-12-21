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
        public int getMaxMaHoKhau() throws SQLException {
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
        public boolean addHoKhau(HoKhauModel hokhauModel, boolean laMotHo) {
            String insertQuery = "INSERT INTO HOKHAU (MAHOKHAU) VALUES (?)";

            try {
                // Lấy mã hộ khẩu lớn nhất hiện tại
                int maxMaHoKhau;
                if(laMotHo) maxMaHoKhau = getMaxMaHoKhau();
                else maxMaHoKhau = getMaxMaHoKhauTamTru();
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
            String query = "SELECT * FROM NHANKHAU WHERE QUANHEVOICHUHO = 'Chủ hộ' AND CAST(MAHOKHAU AS CHAR) LIKE ? LIMIT 12" ;

            String searchPattern = "%" + maHoKhau.trim() + "%"; // Tìm kiếm chứa chuỗi con

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
            String query = "SELECT * FROM NHANKHAU WHERE QUANHEVOICHUHO = 'Chủ hộ' AND CAST(CCCD AS CHAR) LIKE ? LIMIT 12";

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
                    "SELECT \n" +
                            "    NHANKHAU.MAHOKHAU, \n" +
                            "    NHANKHAU.MANHANKHAU, \n" +
                            "    NHANKHAU.HOTEN AS TENCHUHO, \n" +
                            "    NHANKHAU.CCCD, \n" +
                            "    NHANKHAU.SDT, \n" +
                            "    CANHO.SOPHONG \n" +
                            "FROM \n" +
                            "    NHANKHAU \n" +
                            "JOIN \n" +
                            "    CANHO ON CANHO.MAHOKHAU = NHANKHAU.MAHOKHAU \n" +
                            "WHERE \n" +
                            "    NHANKHAU.QUANHEVOICHUHO = 'Chủ hộ'";

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {

                while (rs.next()) {
                    int maHoKhau;
                    if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                    else maHoKhau = 0;
                    NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                            maHoKhau,
                            rs.getInt("MANHANKHAU"),
                            rs.getString("TENCHUHO"),
                            rs.getString("CCCD"),
                            rs.getString("SDT"),
                            rs.getInt("SOPHONG")
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
            try (Connection connection =DatabaseConnection.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

        public List<NhanKhauModel> getListNhanKhauTrongHo(int maHoKhau) {
            List<NhanKhauModel> danhSachNhanKhauTrongHo = new ArrayList<>();

            String query = "SELECT * FROM NHANKHAU WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    NhanKhauModel nhanKhauTrongHo = new NhanKhauModel(
                            rs.getInt("MAHOKHAU"),
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getBoolean("TRANGTHAI")
                    );

                    danhSachNhanKhauTrongHo.add(nhanKhauTrongHo);
                }

            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy danh sách hộ khẩu: " + e.getMessage());
            }

            return danhSachNhanKhauTrongHo;
        }

        public int soCanHo(int maHoKhau) {
            String query = "SELECT SOPHONG FROM CANHO WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy Số căn hộ: " + e.getMessage());
            }
            return 0;
        }

        public List<Integer> listCanHo(int Tang) {
            List<Integer> danhSachCanHo = new ArrayList<>();
            String query = "SELECT SOPHONG FROM CANHO WHERE TANG = ? AND MAHOKHAU IS NULL";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, Tang);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    danhSachCanHo.add(rs.getInt(1));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy Số căn hộ: " + e.getMessage());
            }
            return danhSachCanHo;
        }

        public int dienTichCanHo(int maHoKhau) {
            String query = "SELECT DIENTICH FROM CANHO WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy diện tích căn hộ: " + e.getMessage());
            }
            return 0;
        }

        public int maCanHo(int maHoKhau) {
            String query = "SELECT MACANHO FROM CANHO WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy diện tích căn hộ: " + e.getMessage());
            }
            return 0;
        }

        public int TangCanHo(int maHoKhau) {
            String query = "SELECT TANG FROM CANHO WHERE MAHOKHAU = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy diện tích căn hộ: " + e.getMessage());
            }
            return 0;
        }

        public int soXeMayCuaHo(int maHoKhau) {
            String query = "SELECT COUNT(BIENSO) FROM XE WHERE MAHOKHAU = ? AND LOAIXE = 'Xe máy'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy số xe máy: " + e.getMessage());
            }
            return 0;
        }

        public int soOToCuaHo(int maHoKhau) {
            String query = "SELECT COUNT(BIENSO) FROM XE WHERE MAHOKHAU = ? AND LOAIXE = 'Ô tô'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, maHoKhau);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy số ô tô: " + e.getMessage());
            }
            return 0;
        }

        public void luuCanHo(int soPhong, int maHoKhau) {
            String query = "UPDATE CANHO SET MAHOKHAU = ? WHERE SOPHONG = ?" ;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, maHoKhau);
                preparedStatement.setInt(2, soPhong);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                // Ghi log lỗi chi tiết
                System.err.println("Lỗi" + e.getMessage());
            }
        }

        public int layMaHoKhauTuSoPhong(int soPhong) {
            String query = "SELECT MAHOKHAU FROM CANHO WHERE SOPHONG = ?" ;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập giá trị cho tham số
                preparedStatement.setInt(1, soPhong);

                // Thực hiện truy vấn
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy số ô tô: " + e.getMessage());
            }
            return 0;
        }
        public void themCanHo(int maCanHo, int tangCanHo, int soCanHo) {
            String query = "INSERT INTO CANHO (MACANHO, TANG, SOPHONG) VALUES (?, ?, ?)" ;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1,maCanHo);
                preparedStatement.setInt(2, tangCanHo);
                preparedStatement.setInt(3, soCanHo);
                preparedStatement.executeUpdate();
            }

            catch (SQLException e) {
                // Ghi log lỗi chi tiết
                System.err.println("Lỗi" + e.getMessage());
            }
        }
    }