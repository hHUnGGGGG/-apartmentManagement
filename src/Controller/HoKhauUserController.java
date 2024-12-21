package Controller;

import Controller.DangNhapVaDangKi.LoginController;
import Models.NhanKhauModel;
import Service.HoKhauUserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HoKhauUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> CCTVCol;

    @FXML
    private AnchorPane CHTablePane;

    @FXML
    private TextField HokhauSear;

    @FXML
    private TableColumn<?, ?> NSinhTVCol;

    @FXML
    private TableColumn<?, ?> QheTVCol;

    @FXML
    private TableColumn<?, ?> SdtTVCol;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TableColumn<?, ?> TenTVCol;

    @FXML
    private TableView<NhanKhauModel> ThanhVienTable;

    private HoKhauUserService hoKhauUserService = new HoKhauUserService();
    private LoginController loginController = new LoginController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TenTVCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        SdtTVCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        QheTVCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));
        CCTVCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NSinhTVCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        TVangCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        loadData();
        System.out.println(loginController.getMaHoKhauUser());
    }

    private void loadData() {
        List<NhanKhauModel> listNhanKhau = hoKhauUserService.getListNhanKhau(loginController.getMaHoKhauUser());
        ObservableList<NhanKhauModel> danhSachNhanKhau = FXCollections.observableArrayList(listNhanKhau);
        ThanhVienTable.setItems(danhSachNhanKhau);
    }
}
