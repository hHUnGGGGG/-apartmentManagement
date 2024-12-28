//package Controller;
//
//import Controller.DangNhapVaDangKi.LoginController;
//import Models.NhanKhauModel;
//import Models.ThanhToanModel;
//import Service.ThanhToanService;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.AnchorPane;
//
//import java.net.URL;
//import java.util.Collections;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class ThanhToanUserController implements Initializable {
//
//    @FXML
//    private Button BtnCf;
//
//    @FXML
//    private Button BtnLsuThanhToan;
//
//    @FXML
//    private Button BtnThoat;
//
//    @FXML
//    private Button BtnThoat2;
//
//    @FXML
//    private Label tongTien;
//
//    @FXML
//    private Button BtnHienThi;
//
//    @FXML
//    private TableColumn<?, ?> DonGiaCol;
//
//    @FXML
//    private TableColumn<?, ?> DonGiaCol1;
//
//    @FXML
//    private TableColumn<?, ?> LoaiPhiCol;
//
//    @FXML
//    private TableColumn<?, ?> LoaiPhiCol1;
//
//    @FXML
//    private TableColumn<?, ?> MaHK3Col;
//
//    @FXML
//    private TableColumn<?, ?> MaHK3Col1;
//
//    @FXML
//    private TableColumn<?, ?> MaPhiCol;
//
//    @FXML
//    private TableColumn<?, ?> MaPhiCol1;
//
//    @FXML
//    private TableColumn<?, ?> NgayNopCol;
//
//    @FXML
//    private TableColumn<?, ?> NgayNopCol1;
//
//    @FXML
//    private TextField NopSear;
//
//    @FXML
//    private TextField NopSear1;
//
//    @FXML
//    private TableColumn<?, ?> TThaiCol;
//
//    @FXML
//    private TableColumn<?, ?> TenCHCol;
//
//    @FXML
//    private TableColumn<?, ?> TenCHCol1;
//
//    @FXML
//    private TableColumn<?, ?> TenPhiCol;
//
//    @FXML
//    private TableColumn<?, ?> TenPhiCol1;
//
//    @FXML
//    private TableView<ThanhToanModel> ThanhToanTable1;
//
//    @FXML
//    private AnchorPane anchorPaneThanhToan;
//
//    @FXML
//    private AnchorPane anchorPaneThanhToan1;
//
//    @FXML
//    private AnchorPane anchorPaneThanhToan11;
//
//    @FXML
//    private TableView<ThanhToanModel> ThanhToanTable;
//
//    private LoginController loginController = new LoginController();
//    private ThanhToanService thanhToanService = new ThanhToanService();
//    private ObservableList<ThanhToanModel> danhSachThanhToan;
//    private ObservableList<ThanhToanModel> danhSachLichSuThanhToan;
//    @FXML
//    void switchForm(ActionEvent event) {
//        if(event.getSource() == BtnLsuThanhToan) {
//            anchorPaneThanhToan.setVisible(false);
//            anchorPaneThanhToan1.setVisible(true);
//        }
//        if(event.getSource() == BtnThoat) {
//            anchorPaneThanhToan1.setVisible(false);
//            anchorPaneThanhToan.setVisible(true);
//        }
//        if(event.getSource() == BtnThoat2) {
//            anchorPaneThanhToan11.setVisible(false);
//            anchorPaneThanhToan.setVisible(true);
//        }
//        if(event.getSource() == BtnCf) {
//            anchorPaneThanhToan11.setVisible(true);
//            anchorPaneThanhToan.setVisible(false);
//        }
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
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
//        BtnCf.setOnAction(e -> {
//            switchForm(e);
//            handleCf(e);
//        });
//        NopSear.setOnKeyReleased(event -> handleSearch());
//        NopSear1.setOnKeyReleased(event -> handleHistorySearch());
//
//        ThanhToanTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        //BtnHienThi.setOnAction(this::handleHistorySearch);
//        loadData();
//    }
//
//    private void loadData() {
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
//    }
//
//    private  void handleCf(ActionEvent event) {
//        List<ThanhToanModel> selected = ThanhToanTable.getSelectionModel().getSelectedItems();
//        if (selected != null) {
//            boolean l = true;
//            double tongSoTien = 0;
//            for(ThanhToanModel t : selected) {
//                if(!thanhToanService.guiThanhToan(t)) l = false;
//                else {
//                    tongSoTien += t.getSoTien();
//                    tongTien.setText(Double.toString(tongSoTien));
//                }
//            }
//            if (l) {
//                loadData();
//            } else {
//                showAlert(Alert.AlertType.INFORMATION, "Thất bại", "Vui lòng thử lại!");
//            }
//        } else {
//            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thanh toán cần xác nhận!");
//        }
//
//    }
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
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.show();
//    }
//}
