package Controller;

import Controller.DangNhapVaDangKi.LoginController;
import Models.KhoanThuModel;
import Models.NhanKhauModel;
import Service.KhoanThuService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ThanhToanUserController implements Initializable {

    @FXML
    private Button BtnCf;

    @FXML
    private Button BtnLsuThanhToan;

    @FXML
    private Button BtnThoat;

    @FXML
    private Button BtnThoat2;

    @FXML
    private Label tongTien;

    @FXML
    private Button BtnHienThi;

    @FXML
    private DatePicker batDauDate;

    @FXML
    private DatePicker ketThucDate;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TableColumn<?, ?> DonGiaCol1;

    @FXML
    private TableColumn<KhoanThuModel, String> LoaiPhiCol;

    @FXML
    private TableColumn<KhoanThuModel, String> LoaiPhiCol1;

    @FXML
    private TableColumn<KhoanThuModel, Number> MaHK3Col;

    @FXML
    private TableColumn<KhoanThuModel, Number> MaHK3Col1;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> MaPhiCol1;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private TableColumn<?, ?> NgayNopCol1;

    @FXML
    private TextField NopSear;

    @FXML
    private TextField NopSear1;

    @FXML
    private TableColumn<?, ?> TThaiCol;

    @FXML
    private TableColumn<KhoanThuModel, String> TenCHCol;

    @FXML
    private TableColumn<KhoanThuModel, String> TenCHCol1;

    @FXML
    private TableColumn<KhoanThuModel, String> TenPhiCol;

    @FXML
    private TableColumn<KhoanThuModel, String> TenPhiCol1;

    @FXML
    private TableView<KhoanThuModel> KhoanThuTable1;

    @FXML
    private AnchorPane anchorPaneThanhToan;

    @FXML
    private AnchorPane anchorPaneThanhToan1;

    @FXML
    private AnchorPane anchorPaneThanhToan11;

    @FXML
    private TableView<KhoanThuModel> KhoanThuTable;

    private LoginController loginController = new LoginController();

    private ObservableList<KhoanThuModel> danhSachThanhToan;
    private ObservableList<KhoanThuModel> danhSachLichSuThanhToan;
    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource() == BtnLsuThanhToan) {
            anchorPaneThanhToan.setVisible(false);
            anchorPaneThanhToan1.setVisible(true);
        }
        if(event.getSource() == BtnThoat) {
            anchorPaneThanhToan1.setVisible(false);
            anchorPaneThanhToan.setVisible(true);
        }
        if(event.getSource() == BtnThoat2) {
            anchorPaneThanhToan11.setVisible(false);
            anchorPaneThanhToan.setVisible(true);
        }
        if(event.getSource() == BtnCf) {
            anchorPaneThanhToan11.setVisible(true);
            anchorPaneThanhToan.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gán dữ liệu cột
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getTenLoaiPhi()));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        TenCHCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoKhauModel().getNhanKhauModel().getHoTenNhanKhau()));
        LoaiPhiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getLoaiThu()));
        MaHK3Col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHoKhauModel().getMaHoKhau()));
        NgayNopCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
        TThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        MaPhiCol1.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getTenLoaiPhi()));
        DonGiaCol1.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        TenCHCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoKhauModel().getNhanKhauModel().getHoTenNhanKhau()));
        LoaiPhiCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getLoaiThu()));
        MaHK3Col1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHoKhauModel().getMaHoKhau()));
        NgayNopCol1.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));

        BtnCf.setOnAction(e -> {
            switchForm(e);
            handleCf(e);
        });
        NopSear.setOnKeyReleased(event -> handleSearch(event));
//        NopSear1.setOnKeyReleased(event -> handleHistorySearch());
//
        KhoanThuTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        BtnHienThi.setOnAction(this::handleHienThi);
        loadData();
    }

    private void loadData() {
        List<KhoanThuModel> danhSach = KhoanThuService.layThongTinThanhToanUser(loginController.getMaHoKhauUser()); // Lấy dữ liệu từ DAO
        danhSachThanhToan = FXCollections.observableArrayList(danhSach);
        KhoanThuTable.setItems(danhSachThanhToan); // Gán dữ liệu cho bảng

        List<KhoanThuModel> lichSuTT = KhoanThuService.layLichSuThanhToanUser(loginController.getMaHoKhauUser());
        danhSachLichSuThanhToan = FXCollections.observableArrayList(lichSuTT);
        KhoanThuTable1.setItems(danhSachLichSuThanhToan);
    }
//
    private  void handleCf(ActionEvent event) {
        List<KhoanThuModel> selected = KhoanThuTable.getSelectionModel().getSelectedItems();
        if (selected != null) {
            boolean l = true;
            double tongSoTien = 0;
            for(KhoanThuModel t : selected) {
                if(!KhoanThuService.guiThanhToan(t)) l = false;
                else {
                    tongSoTien += t.getSoTien();
                    tongTien.setText(Double.toString(tongSoTien));
                }
            }
            if (l) {
                loadData();
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thất bại", "Vui lòng thử lại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thanh toán cần xác nhận!");
        }

    }

    private void handleHienThi(ActionEvent event) {
        LocalDate batDau = batDauDate.getValue();
        LocalDate ketThuc = ketThucDate.getValue();

        // Kiểm tra các giá trị ngày
        if (batDau == null || ketThuc == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn ngày bắt đầu và ngày kết thúc!");
            return;
        }

        if (batDau.isAfter(ketThuc)) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Ngày bắt đầu không được sau ngày kết thúc!");
            return;
        }

        // Gọi phương thức từ KhoanThuService để lấy danh sách
        List<KhoanThuModel> danhSach = KhoanThuService.layLichSuThanhToanUserTheoNgay(
                loginController.getMaHoKhauUser(), Date.valueOf(batDau), Date.valueOf(ketThuc)
        );

        if (danhSach != null && !danhSach.isEmpty()) {
            KhoanThuTable1.getItems().setAll(danhSach);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Không có khoản thu nào trong khoảng thời gian đã chọn.");
        }
    }

    private void handleSearch(KeyEvent event) {
        String keyword = NopSear.getText().toLowerCase(); // Lấy từ khóa tìm kiếm và chuyển về chữ thường

        if (keyword.isEmpty()) {
            KhoanThuTable.setItems(danhSachThanhToan); // Hiển thị lại danh sách ban đầu nếu TextField trống
        } else {
            // Lọc danh sách dựa trên từ khóa tìm kiếm
            ObservableList<KhoanThuModel> filteredList = danhSachThanhToan.filtered(khoanThu ->
                    khoanThu.getLoaiPhiModel().getTenLoaiPhi().toLowerCase().contains(keyword)
            );
            KhoanThuTable.setItems(filteredList); // Hiển thị danh sách đã lọc
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
