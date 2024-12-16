package Controller;

import Controller.DangNhapVaDangKi.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardUserController implements Initializable {

    @FXML
    private Label AccountLabel;

    @FXML
    private Button BtnHokhau;

    @FXML
    private Button BtnKhoanPhi;

    @FXML
    private Button BtnThanhToan;

    @FXML
    private AnchorPane HoKhau;

    @FXML
    private AnchorPane KhoanPhi;

    @FXML
    private AnchorPane ThanhToan;

    @FXML
    private AnchorPane main_form;
    private LoginController loginController = new LoginController();

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnHokhau){
            HoKhau.setVisible(true);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
        }else if (event.getSource()==BtnKhoanPhi){
            HoKhau.setVisible(false);
            KhoanPhi.setVisible(true);
            ThanhToan.setVisible(false);
        }else if (event.getSource()==BtnThanhToan){
            HoKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(true);
        }
    }

    public void Close(){
        System.exit(0);
    }

    public void Minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountLabel.setText(loginController.getTenChuHoUser());
    }
}
