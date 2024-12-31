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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HoKhauUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> CCTVCol;

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

    private final HoKhauUserService hoKhauUserService = new HoKhauUserService();
    private final LoginController loginController = new LoginController();
    List<NhanKhauModel> filteredList;
    int maHoKhauUser = loginController.getMaHoKhauUser();
    private ObservableList<NhanKhauModel> danhSachNhanKhau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TenTVCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        SdtTVCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        QheTVCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));
        CCTVCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NSinhTVCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        TVangCol.setCellValueFactory(new PropertyValueFactory<>("tamVangHienThi"));
        loadData();
        System.out.println(maHoKhauUser);

        HokhauSear.setOnKeyReleased(event -> handleSearch());
    }

    private void handleSearch() {
        String keyword = HokhauSear.getText().toLowerCase();

        if(keyword.isEmpty()){
            filteredList = hoKhauUserService.getListNhanKhau(maHoKhauUser);
        } else if (Pattern.matches("\\d{12}", keyword)) {
            filteredList = hoKhauUserService.searchNKbyCCCD(maHoKhauUser, keyword);
        } else {
            // Nếu từ khóa là chữ, tìm theo tên
            filteredList = hoKhauUserService.searchNKbyTen(maHoKhauUser,keyword);
        }

        // Hiển thị kết quả
        danhSachNhanKhau.setAll(filteredList);
    }


    private void loadData() {
        List<NhanKhauModel> listNhanKhau = hoKhauUserService.getListNhanKhau(maHoKhauUser);
        danhSachNhanKhau = FXCollections.observableArrayList(listNhanKhau);
        ThanhVienTable.setItems(danhSachNhanKhau);
    }


}
