package Controller.DangNhapVaDangKi;

import Service.DangKiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DangKyController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private TextField passwordshow;

    @FXML
    private PasswordField passwordhide;

    @FXML
    private CheckBox showPasswordCheckbox;

    @FXML
    private TextField password1show;

    @FXML
    private PasswordField password1hide;

    @FXML
    private Button signupBtn;

    @FXML
    private TextField username;
    private DangKiService dangKiService = new DangKiService();

    @FXML
    private void togglePasswordVisibility(){
        if (showPasswordCheckbox.isSelected()) {
            passwordshow.setVisible(true);
            password1show.setVisible(true);
            passwordhide.setVisible(false);
            password1hide.setVisible(false);

        } else {
            passwordhide.setVisible(true);
            password1hide.setVisible(true);
            password1show.setVisible(false);
            passwordshow.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordshow.textProperty().bindBidirectional(passwordhide.textProperty());
        password1show.textProperty().bindBidirectional(password1hide.textProperty());
    }

    public void SignUp(ActionEvent event) {
        try {
            if (dangKiService.CheckPass(passwordhide.getText(), password1hide.getText()) && dangKiService.CheckAcc(username.getText()) && dangKiService.timMaHoKhauBangSDT(username.getText()) != 0) {
                dangKiService.LuuVaoDatabase(username.getText(), passwordhide.getText());
                dangKiService.DangKiThanhCong();
                dangKiService.ChuyenVeLogin(signupBtn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Close(ActionEvent event) {
        System.exit(0);
    }
}
