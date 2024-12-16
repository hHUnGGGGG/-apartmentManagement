package Controller.DangNhapVaDangKi;

import Service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.sql.SQLException;
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
    private Button loginBtn;

    private LoginService loginService = new LoginService();

    private static int maHoKhauUser;

    private static String tenChuHoUser;

    @FXML
    public void initialize(){
        passwordshow.textProperty().bindBidirectional(passwordhide.textProperty());
    }
    public void Login(ActionEvent event) throws SQLException {
        if(username.getText().equals("tu")) {
            loginService.ChuyenSangDashBoard(username, passwordhide, loginBtn);
        }
        else {
            setMaHoKhauUser(username.getText());
            setTenChuHoUser(username.getText());
            loginService.ChuyenSangDashBoardUser(username, passwordhide, loginBtn);
            System.out.println(getMaHoKhauUser());
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

    public int getMaHoKhauUser() {
        return maHoKhauUser;
    }

    public void setMaHoKhauUser(String sdt) {
        LoginController.maHoKhauUser = loginService.timMaHoKhauBangSDT(sdt);
    }

    public String getTenChuHoUser() {
        return tenChuHoUser;
    }

    public void setTenChuHoUser(String sdt) {
        LoginController.tenChuHoUser = loginService.timTenChuHoBangSDT(sdt);
    }
}
