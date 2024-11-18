package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DangKyController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password1;

    @FXML
    private Button signupBtn;

    @FXML
    private TextField username;

    private Alert alert;

    public void SignUp(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Đăng ký thành công");
        alert.showAndWait();
    }

    public void Close(){
        System.exit(0);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
