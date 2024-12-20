package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.Date;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLb;

    @FXML
    private Button signupBtn;

    @FXML
    private Label signupLb;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane SignUpPane;

    public void switchForm(ActionEvent event) {
        if (event.getSource()==signupBtn){
            switchLoginToDangKy();
        }
        if (event.getSource()==loginBtn){
            switchDangKyToLogin();
        }
    }

    public void switchLoginToDangKy() {
        loginPane.setVisible(false);
        loginBtn.setVisible(true);
        loginLb.setVisible(true);
        SignUpPane.setVisible(true);
        signupBtn.setVisible(false);
        signupLb.setVisible(false);
    }

    public void switchDangKyToLogin() {
        loginPane.setVisible(true);
        loginBtn.setVisible(false);
        loginLb.setVisible(false);
        SignUpPane.setVisible(false);
        signupBtn.setVisible(true);
        signupLb.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
