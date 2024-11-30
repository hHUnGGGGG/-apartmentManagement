package Controller.DangNhapVaDangKi;

import Service.DangKiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DangKyController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password1;

    @FXML
    private Button signupBtn;

    @FXML
    private TextField username;
    private DangKiService dangKiService = new DangKiService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void SignUp(ActionEvent event) {
        try {
            dangKiService.LuuVaoDatabase(username.getText(), password.getText());
            if (dangKiService.CheckPass(password.getText(), password1.getText())) {
                dangKiService.DangKiThanhCong();
                dangKiService.ChuyenVeLogin(signupBtn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Close(ActionEvent event) {
        dangKiService.ChuyenVeLogin(signupBtn);
    }
}
