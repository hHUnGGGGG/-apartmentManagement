package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label AccountLabel;

    @FXML
    private Button BtnHokhau;

    @FXML
    private Button BtnKhoanphi;

    @FXML
    private Button BtnNhankhau;

    @FXML
    private Button BtnThanhToan;

    @FXML
    private Button BtnXe;

    @FXML
    private Button BtnThongke;

    @FXML
    private AnchorPane HoKhau;

    @FXML
    private AnchorPane KhoanPhi;

    @FXML
    private AnchorPane NhanKhau;

    @FXML
    private AnchorPane ThanhToan;

    @FXML
    private AnchorPane Xe;

    @FXML
    private AnchorPane ThongKe;

    @FXML
    private AnchorPane main_form;


    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnHokhau){
            HoKhau.setVisible(true);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            Xe.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnNhankhau){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(true);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            Xe.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnKhoanphi){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(true);
            ThanhToan.setVisible(false);
            Xe.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnThanhToan){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(true);
            Xe.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnXe){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            Xe.setVisible(true);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnThongke) {
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            Xe.setVisible(false);
            ThongKe.setVisible(true);
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

    }
}
