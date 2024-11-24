package Service;

import Controller.DangNhapVaDangKi.LoginController;
import Run.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import database.DatabaseConnection;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangKiService {
    private double x;
    private double y;
    private void setAlert(String s){
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
            if((resultSet.getString("TENTAIKHOAN") == sdt)) {
                setAlert("Tài khoản đã tồn tại");
                truyVan.closeDatabase();
                return false;
            }
        }
        return true;
    }

    public void LuuVaoDatabase(String TaiKhoan, String MatKhau) throws SQLException {
        DangKiService dangKiService =  new DangKiService();
        TruyVanDataBaseService truyVan = new TruyVanDataBaseService();
        if(dangKiService.CheckAcc(TaiKhoan)) {
            String sql = "INSERT INTO TAIKHOAN (TENTAIKHOAN, MATKHAU) VALUES ('" + TaiKhoan + "'" + ", '" + MatKhau + "')";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        }
        truyVan.closeDatabase();
    }

    public boolean CheckPass(String pass1, String pass2) {
        if(!pass1.equals(pass2)) {
            setAlert("Nhập lại mật khẩu không khớp");
            return false;
        }
        return true;
    }

    public void DangKiThanhCong(Button signUp) {
        setAlert("Đăng ký thành công vui lòng đăng nhập lại");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
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
            LoginController loginController = loader.getController();
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
