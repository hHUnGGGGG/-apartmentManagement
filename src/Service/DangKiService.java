package Service;

import Controller.DangNhapVaDangKi.LoginController;
import Controller.StartPageController;
import Run.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import database.DatabaseConnection;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangKiService {
    private double x;
    private double y;
    private StartPageController stController;
    private final Connection connection;

    public DangKiService() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    public void setAlert(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public boolean CheckAcc(String sdt) throws SQLException {
        TruyVanDataBaseService truyVan = new TruyVanDataBaseService();
        ResultSet resultSet = null;
        resultSet = truyVan.TruyVanDatabase("SELECT TENTAIKHOAN FROM TAIKHOAN");
        while(resultSet.next()) {
            if((resultSet.getString("TENTAIKHOAN").equals(sdt))) {
                setAlert("Tài khoản đã tồn tại");
                return false;
            }
        }
        return true;
    }

    public void LuuVaoDatabase(String TaiKhoan, String MatKhau) throws SQLException {
        DangKiService dangKiService =  new DangKiService();
        TruyVanDataBaseService truyVan = new TruyVanDataBaseService();

        String sql = "INSERT INTO TAIKHOAN (TENTAIKHOAN, MATKHAU) VALUES ('" + TaiKhoan + "'" + ", '" + MatKhau + "')";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();

    }

    public boolean CheckPass(String pass1, String pass2) {
        if(!pass1.equals(pass2)) {
            setAlert("Nhập lại mật khẩu không khớp");
            return false;
        }
        return true;
    }

    public void DangKiThanhCong() {
        setAlert("Đăng ký thành công vui lòng đăng nhập lại");
    }

    public void ChuyenVeLogin(Button signUp) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/StartPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Thay đổi scene
            Stage stage = (Stage) signUp.getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            root.setOnMousePressed((MouseEvent event) ->{
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) ->{
                newStage.setX(event.getScreenX() - x);
                newStage.setY(event.getScreenY() - y);
                newStage.setOpacity(.8);
            });

            root.setOnMouseReleased((MouseEvent event) ->{
                newStage.setOpacity(1);
            });
            StartPageController startPageController = loader.getController();
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int timMaHoKhauBangSDT(String sdt) {
        // Chuẩn bị câu lệnh SQL để tìm MAHOKHAU theo SDT
        int maHoKhau;
        String query = "SELECT MAHOKHAU FROM NHANKHAU WHERE SDT = ? AND QUANHEVOICHUHO = 'Chủ hộ'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán tham số cho câu lệnh SQL
            preparedStatement.setString(1, sdt);

            // Thực thi câu lệnh và xử lý kết quả
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    maHoKhau = rs.getInt("MAHOKHAU");
                    return maHoKhau;
                }
            }
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết
            System.err.println("Lỗi khi tìm kiếm mã hộ khẩu bằng số điện thoại (" + sdt + "): " + e.getMessage());
        }
        setAlert("số điện thoại không phải của chủ hộ");
        return 0;
    }

}
