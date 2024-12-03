package Service;

import Controller.DangNhapVaDangKi.DangKyController;
import Controller.DashboardController;
import Run.Main;
import database.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    private double x;
    private double y;
    //check xem co user password khong
    private boolean checkLogin(String user, String pass) throws SQLException {
        String query = "select TENTAIKHOAN from TAIKHOAN where TENTAIKHOAN ='" + user + "'and MATKHAU ='" +pass +"'";
        ResultSet result = TruyVanDataBaseService.TruyVanDatabase(query);
        return result.next();
    }

    //thông báo
    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ChuyenSangDashBoard(TextField username, PasswordField passwordhide, Button login) throws SQLException {
        String user = username.getText().trim();
        String pass = passwordhide.getText().trim();
        if(user.isEmpty() || pass.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Lỗi","Vui lòng nhập đủ thông tin");
            return;
        }

        if(checkLogin(user,pass)){
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/Dashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Thay đổi scene
                Stage stage = (Stage) login.getScene().getWindow();
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
                DashboardController dashboardController = loader.getController();
                newStage.setScene(scene);
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải giao diện mới");
            }
        }else{
            showAlert(Alert.AlertType.ERROR,"Lỗi","Tài khoản hoặc mật khẩu không tồn tại");
        }
    }

    public void ChuyenSangDangKy(Button dangKy) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/DangKy.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Thay đổi scene
            Stage stage = (Stage) dangKy.getScene().getWindow();
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
            DangKyController dangKyController = loader.getController();
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
