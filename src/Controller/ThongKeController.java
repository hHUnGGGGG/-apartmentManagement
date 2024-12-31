package Controller;

import Service.HoKhauService;
import Service.NhanKhauService;
import Service.XeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {

    @FXML
    private Label HKLb;

    @FXML
    private Label NKLb;

    @FXML
    private Label PTLb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XeService xeService = new XeService();
        NhanKhauService nhanKhauService = new NhanKhauService();
        HoKhauService hoKhauService = new HoKhauService();
        PTLb.setText(xeService.layDanhSachXe().size()+"");
        NKLb.setText(nhanKhauService.getListNhanKhau().size()+"");
        HKLb.setText(hoKhauService.getListHoKhau().size()+"");
        
    }
}
