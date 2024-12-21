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
    private Button BtnPPS;

    @FXML
    private Button BtnPTN;

    @FXML
    private Button BtnXe;

    @FXML
    private Button BtnThanhToan;

    @FXML
    private AnchorPane HoKhau;

    @FXML
    private AnchorPane PPS;

    @FXML
    private AnchorPane PTN;

    @FXML
    private AnchorPane Xe;

    @FXML
    private AnchorPane ThanhToan;

    @FXML
    private AnchorPane main_form;
    private LoginController loginController = new LoginController();

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnHokhau){
            HoKhau.setVisible(true);
            PPS.setVisible(false);
            PTN.setVisible(false);
            Xe.setVisible(false);
            ThanhToan.setVisible(false);
        }else if (event.getSource()== BtnPPS){
            HoKhau.setVisible(false);
            PPS.setVisible(true);
            PTN.setVisible(false);
            Xe.setVisible(false);
            ThanhToan.setVisible(false);
        }else if (event.getSource()== BtnPTN){
            HoKhau.setVisible(false);
            PPS.setVisible(false);
            PTN.setVisible(true);
            Xe.setVisible(false);
            ThanhToan.setVisible(false);
        }else if (event.getSource()==BtnXe){
            HoKhau.setVisible(false);
            PPS.setVisible(false);
            PTN.setVisible(false);
            Xe.setVisible(true);
            ThanhToan.setVisible(false);
        }else if (event.getSource()==BtnThanhToan){
            HoKhau.setVisible(false);
            PPS.setVisible(false);
            PTN.setVisible(false);
            Xe.setVisible(false);
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
