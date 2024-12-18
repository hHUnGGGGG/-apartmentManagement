package Controller;

import Models.KhoanThuModel;
import Models.ThanhToanModel;
import Service.KhoanThuService;
import Service.ThanhToanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ThanhToanController implements Initializable {

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

    private ThanhToanService thanhToanService = new ThanhToanService(); // Service để làm việc với dữ liệu

    private ObservableList<ThanhToanModel> danhSachThanhToan; // Dữ liệu hiển thị trên bảng
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gán dữ liệu cột
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        TenCHCol.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));
        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiKhoanThu"));
        MaHK3Col.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        NgayNopCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
        TThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        loadData();

        BtnCf.setOnAction(this::handleCf);
        NopSear.setOnKeyReleased(event -> handleSearch());

    }

    private void loadData() {
        thanhToanService.themPhiGuiXe();
        List<ThanhToanModel> danhSach = thanhToanService.layDanhSachThanhToan(); // Lấy dữ liệu từ DAO
        danhSachThanhToan = FXCollections.observableArrayList(danhSach);
        ThanhToanTable.setItems(danhSachThanhToan); // Gán dữ liệu cho bảng
    }

    private  void handleCf(ActionEvent event) {

        ThanhToanModel selected = ThanhToanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (thanhToanService.xacNhanThanhToan(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xác nhận thanh toán thành công!");
                loadData();
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thất bại", "Xác nhận thanh toán thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thanh toán cần xác nhận!");
        }

    }

    // Xử lý tìm kiếm
    private void handleSearch() {
        String keyword = NopSear.getText().toLowerCase();
        List<ThanhToanModel> ketQua = thanhToanService.timThanhToanTenPhi(keyword);
        danhSachThanhToan.setAll(ketQua); // Cập nhật danh sách hiển thị
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
