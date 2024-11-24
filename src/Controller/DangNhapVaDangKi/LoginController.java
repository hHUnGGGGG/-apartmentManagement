package Controller.DangNhapVaDangKi;

import Service.TruyVanDataBaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import javafx.event.ActionEvent;
import utils.WindowsUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController  {

    @FXML
    private TextField username;

    @FXML
    private PasswordField passwordhide;

    @FXML
    private TextField passwordshow;

    @FXML
    private CheckBox showPasswordCheckbox;

    @FXML

    public void initialize(){
        passwordshow.textProperty().bindBidirectional(passwordhide.textProperty());
    }
    public void Login(ActionEvent event) throws SQLException {
        String user = username.getText().trim();
        String pass = passwordhide.getText().trim();
        if(user.isEmpty() || pass.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Lỗi","Vui lòng nhập đủ thông tin");
            return;
        }

        if(checkLogin(user,pass)){
            try {
                // Tải file FXML mới
                Parent root = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));

                // Lấy stage hiện tại
                Stage stage = (Stage) username.getScene().getWindow();
                stage.centerOnScreen();
                // Thiết lập scene mới
                Scene scene = new Scene(root);

                stage.setScene(scene);
                WindowsUtils.DraggableAndTransparent(stage,scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải giao diện mới");
            }
        }else{
            showAlert(Alert.AlertType.ERROR,"Lỗi","Tài khoản hoặc mật khẩu không tồn tại");
        }
    }

    @FXML
    public void Close(ActionEvent actionEvent){
        System.exit(0);
    }

    @FXML
    private void togglePasswordVisibility(){
        if (showPasswordCheckbox.isSelected()) {
            passwordshow.setVisible(true);
            passwordhide.setVisible(false);

        } else {
            passwordhide.setVisible(true);
            passwordshow.setVisible(false);
        }
    }

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
}
