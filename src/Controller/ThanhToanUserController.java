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

//        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
//        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
//        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiKhoanThu"));
//        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
//        TThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
//        NgayNopCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
//        MaHK3Col.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
//        TenCHCol.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));
//
//        MaPhiCol1.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
//        TenPhiCol1.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
//        LoaiPhiCol1.setCellValueFactory(new PropertyValueFactory<>("loaiKhoanThu"));
//        DonGiaCol1.setCellValueFactory(new PropertyValueFactory<>("soTien"));
//        NgayNopCol1.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
//        MaHK3Col1.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
//        TenCHCol1.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));
//
        BtnCf.setOnAction(e -> {
            switchForm(e);
            handleCf(e);
        });
//        NopSear.setOnKeyReleased(event -> handleSearch());
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
//        List<ThanhToanModel> listThanhToanRieng = thanhToanService.layDanhSachPhiTheoHoKhau(loginController.getMaHoKhauUser());
//        List<ThanhToanModel> listThanhToanChung = thanhToanService.layDanhSachThanhToanUser();
//        List<ThanhToanModel> listLichSuThanhToan = thanhToanService.layDanhSachLichSuPhiTheoHoKhau(loginController.getMaHoKhauUser());
//        for(ThanhToanModel t : listThanhToanChung) {
//            listThanhToanRieng.add(t);
//        }
//        danhSachThanhToan = FXCollections.observableArrayList(listThanhToanRieng);
//        ThanhToanTable.setItems(danhSachThanhToan);
//        danhSachLichSuThanhToan = FXCollections.observableArrayList(listLichSuThanhToan);
//        ThanhToanTable1.setItems(danhSachLichSuThanhToan);
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
//
//    private void handleSearch() {
//        String keyword = NopSear.getText().toLowerCase();
//        List<ThanhToanModel> ketQua = thanhToanService.timThanhToanTenPhiUser(keyword, loginController.getMaHoKhauUser());
//        danhSachThanhToan.setAll(ketQua); // Cập nhật danh sách hiển thị
//    }
//
//    private void handleHistorySearch() {
//        String keyword = NopSear1.getText().toLowerCase();
//        List<ThanhToanModel> ketQua = thanhToanService.timThanhToanTenPhiUser(keyword, loginController.getMaHoKhauUser());
//        danhSachLichSuThanhToan.setAll(ketQua); // Cập nhật danh sách hiển thị
//
//    }
//
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
