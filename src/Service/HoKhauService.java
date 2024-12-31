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

        public static String layTenChuHo(int maHoKhau) {
            String sql = """
        SELECT nk.HOTEN
        FROM NHANKHAU nk
        JOIN HOKHAU hk ON nk.MAHOKHAU = hk.MAHOKHAU
        WHERE nk.QUANHEVOICHUHO = 'Chủ Hộ' AND hk.MAHOKHAU = ?
        """;

            Connection connection = DatabaseConnection.getInstance().getConnection();
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, maHoKhau);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("HOTEN");
                } else {
                    return "Không tìm thấy chủ hộ";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return "Lỗi khi lấy tên chủ hộ";
            }
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


        // Tìm kiếm nhân khẩu theo cccd
        public List<NhanKhauModel> searchHoKhauByCCCD(String CCCD) {

            List<NhanKhauModel> listHoKhau = new ArrayList<>();
            String query =  "SELECT NK.MAHOKHAU, NK.HOTEN, NK.CCCD, NK.SDT, CH.SOPHONG " +
                            "FROM NHANKHAU NK " +
                            "JOIN CANHO CH ON NK.MAHOKHAU = CH.MAHOKHAU " +
                            "WHERE NK.QUANHEVOICHUHO = 'Chủ hộ' AND NK.CCCD LIKE ?";

            String searchPattern = "%" + CCCD.trim() + "%";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, searchPattern);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int maHoKhau;
                        if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                        else maHoKhau = 0;
                        NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                                maHoKhau,
                                rs.getString("TENCHUHO"),
                                rs.getString("CCCD"),
                                rs.getString("SDT"),
                                rs.getInt("SOPHONG")
                        );

                        listHoKhau.add(hoKhauChuHo);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi tìm kiếm hộ khẩu với (CCCD: " + CCCD + "): " + e.getMessage());
            }
            return listHoKhau;
        }


        // Tìm kiếm nhân khẩu theo tên
        public List<NhanKhauModel> searchHoKhauByTen(String Ten) {

            List<NhanKhauModel> listHoKhau = new ArrayList<>();

            String query =  "SELECT NK.MAHOKHAU, NK.HOTEN, NK.CCCD, NK.SDT, CH.SOPHONG " +
                            "FROM NHANKHAU NK " +
                            "JOIN CANHO CH ON NK.MAHOKHAU = CH.MAHOKHAU " +
                            "WHERE QUANHEVOICHUHO = 'Chủ hộ' AND LOWER(HOTEN) LIKE LOWER(?)";

            String searchPattern = "%" + Ten.trim() + "%";  // Cải thiện việc tạo chuỗi tìm kiếm

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchPattern);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int maHoKhau;
                        if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                        else maHoKhau = 0;
                        NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                                maHoKhau,
                                rs.getString("TENCHUHO"),
                                rs.getString("CCCD"),
                                rs.getString("SDT"),
                                rs.getInt("SOPHONG")
                        );

                        listHoKhau.add(hoKhauChuHo);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi tìm kiếm hộ khẩu với (tên: " + Ten + "): " + e.getMessage());
            }
            return listHoKhau;
        }

        // Tìm kiếm hộ khẩu theo số phòng
        public List<NhanKhauModel> searchHoKhaubySoPhong(String soPhong){
            List<NhanKhauModel> listHoKhau = new ArrayList<>();

            String query =  "SELECT NK.MAHOKHAU, NK.HOTEN, NK.CCCD, NK.SDT, CH.SOPHONG " +
                            "FROM NHANKHAU NK " +
                            "JOIN CANHO CH ON NK.MAHOKHAU = CH.MAHOKHAU " +
                            "WHERE QUANHEVOICHUHO = 'Chủ hộ' AND CH.SOPHONG LIKE ?";
            String serachPattern = "%" + soPhong.trim() + "%";

            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1,serachPattern);

                try(ResultSet rs = preparedStatement.executeQuery()){
                    while (rs.next()){
                        int maHoKhau;
                        if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                        else maHoKhau = 0;
                        NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                                maHoKhau,
                                rs.getString("TENCHUHO"),
                                rs.getString("CCCD"),
                                rs.getString("SDT"),
                                rs.getInt("SOPHONG")
                        );

                        listHoKhau.add(hoKhauChuHo);
                    }
                }
            }catch (SQLException e){
                System.err.println("Lỗi khi tìm kiếm hộ khẩu với (số phòng: " + soPhong + "): " + e.getMessage());
            }

            return listHoKhau;
        }


        // Lấy danh sách hộ khẩu
        public List<NhanKhauModel> getListHoKhau() {
            List<NhanKhauModel> listHoKhau = new ArrayList<>();

            String query =
                    """
                            SELECT\s
                                NHANKHAU.MAHOKHAU,\s
                                NHANKHAU.HOTEN AS TENCHUHO,\s
                                NHANKHAU.CCCD,\s
                                NHANKHAU.SDT,\s
                                CANHO.SOPHONG\s
                            FROM\s
                                NHANKHAU\s
                            JOIN\s
                                CANHO ON CANHO.MAHOKHAU = NHANKHAU.MAHOKHAU\s
                            WHERE\s
                                NHANKHAU.QUANHEVOICHUHO = 'Chủ hộ'""";

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {

                while (rs.next()) {
                    int maHoKhau;
                    if(rs.getInt("MAHOKHAU")/1000000 != 0) maHoKhau = rs.getInt("MAHOKHAU");
                    else maHoKhau = 0;
                    NhanKhauModel hoKhauChuHo = new NhanKhauModel(
                            maHoKhau,
                            rs.getString("TENCHUHO"),
                            rs.getString("CCCD"),
                            rs.getString("SDT"),
                            rs.getInt("SOPHONG")
                    );

                    listHoKhau.add(hoKhauChuHo);
                }

            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy danh sách hộ khẩu: " + e.getMessage());
            }

            return listHoKhau;

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
                            rs.getInt("MANHANKHAU"),
                            rs.getString("CCCD"),
                            rs.getString("HOTEN"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("SDT"),
                            rs.getInt("MAHOKHAU"),
                            rs.getString("QUANHEVOICHUHO"),
                            rs.getString("TRANGTHAI")
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
                System.err.println("Lỗi khi lấy mã căn hộ: " + e.getMessage());
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
                System.err.println("Lỗi khi lấy tầng căn hộ: " + e.getMessage());
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
                System.err.println("Lỗi khi lưu căn hộ:" + e.getMessage());
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
                System.err.println("Lỗi khi lấy mã hộ khẩu: " + e.getMessage());
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
                System.err.println("Lỗi khi thêm căn hộ:" + e.getMessage());
            }
        }
    }