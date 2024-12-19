package Controller;

import Controller.DangNhapVaDangKi.LoginController;
import Models.NhanKhauModel;
import Models.ThanhToanModel;
import Service.ThanhToanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ThanhToanUserController implements Initializable {

    @FXML
    private Button BtnCf;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TableColumn<?, ?> MaHK3Col;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private TextField NopSear;

    @FXML
    private TableColumn<?, ?> TThaiCol;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TableColumn<?, ?> TenCHCol;

    @FXML
    private TableView<ThanhToanModel> ThanhToanTable;

    private LoginController loginController = new LoginController();
    private ThanhToanService thanhToanService = new ThanhToanService();
    private ObservableList<ThanhToanModel> danhSachThanhToan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiKhoanThu"));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        TThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        NgayNopCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
        MaHK3Col.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        TenCHCol.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));

        BtnCf.setOnAction(this::handleCf);
        NopSear.setOnKeyReleased(event -> handleSearch());

        loadData();
    }

    private void loadData() {
        List<ThanhToanModel> listThanhToan = thanhToanService.layDanhSachPhiTheoHoKhau(loginController.getMaHoKhauUser());
        danhSachThanhToan = FXCollections.observableArrayList(listThanhToan);
        ThanhToanTable.setItems(danhSachThanhToan);
    }

    private  void handleCf(ActionEvent event) {

        ThanhToanModel selected = ThanhToanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (thanhToanService.guiThanhToan(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xác nhận gửi thanh toán thành công!");
                loadData();
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thất bại", "Xác nhận gửi thanh toán thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thanh toán cần xác nhận!");
        }

    }

    private void handleSearch() {
        String keyword = NopSear.getText().toLowerCase();
        List<ThanhToanModel> ketQua = thanhToanService.timThanhToanTenPhiUser(keyword, loginController.getMaHoKhauUser());
        danhSachThanhToan.setAll(ketQua); // Cập nhật danh sách hiển thị
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
