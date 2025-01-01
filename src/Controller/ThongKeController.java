package Controller;

import Service.HoKhauService;
import Service.NhanKhauService;
import Service.XeService;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {
    private static StringProperty HKProperty;
    private static StringProperty NKProperty;
    private static StringProperty PTProperty;
    @FXML
    private Label HKLb;

    @FXML
    private Label NKLb;

    @FXML
    private Label PTLb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HKLb.textProperty().bindBidirectional(HKProperty);
        NKLb.textProperty().bindBidirectional(NKProperty);
        PTLb.textProperty().bindBidirectional(PTProperty);
        loadDataTK();
    }
    public static void loadDataTK(){
        XeService xeService = new XeService();
        NhanKhauService nhanKhauService = new NhanKhauService();
        HoKhauService hoKhauService = new HoKhauService();

        PTProperty.setValue(xeService.layDanhSachXe().size()+"");
        NKProperty.setValue(nhanKhauService.getListNhanKhau().size()+"");
        HKProperty.setValue(hoKhauService.getListHoKhau().size()+"");

    }
}
