package Controller;

import Models.KhoanThuModel;
import Service.KhoanThuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PhiPhatSinhController implements Initializable {

    @FXML
    private Button BtnAddPhi;

    @FXML
    private Button BtnDltPhi;

    @FXML
    private Button BtnEditPhi;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnSave1;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TextField DonGiatf;

    @FXML
    private TextField DonGiatf1;

    @FXML
    private TableColumn<?, ?> HanNopCol;

    @FXML
    private DatePicker HanNoptf;

    @FXML
    private DatePicker HanNoptf1;

    @FXML
    private TextField KTSear;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TextField LoaiPhitf;

    @FXML
    private TextField LoaiPhitf1;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TextField TenPhitf;

    @FXML
    private TextField TenPhitf1;

    @FXML
    private TableColumn<?, ?> ThangNopCol;

    @FXML
    private TextField ThangNoptf;

    @FXML
    private TextField ThangNoptf1;

    @FXML
    private TableView<KhoanThuModel> PhiTable;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane EditPane;

    @FXML
    private AnchorPane KPTablePane;

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddPhi){
            AddPane.setVisible(true);
            KPTablePane.setVisible(false);
            EditPane.setVisible(false);
        }else if (event.getSource()==BtnSave ){
            AddPane.setVisible(false);
            KPTablePane.setVisible(true);
            EditPane.setVisible(false);
        }else if (event.getSource()==BtnEditPhi){
            KhoanThuModel selectedPhi = PhiTable.getSelectionModel().getSelectedItem();
            if (selectedPhi != null) {
                AddPane.setVisible(false);
                KPTablePane.setVisible(false);
                EditPane.setVisible(true);
            }else {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn khoản phí cần sửa!");
            }
        }
    }

    private KhoanThuService khoanThuService = new KhoanThuService(); // Service để làm việc với dữ liệu

    private ObservableList<KhoanThuModel> danhSachKhoanPhi; // Dữ liệu hiển thị trên bảng

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gán dữ liệu cột
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        HanNopCol.setCellValueFactory(new PropertyValueFactory<>("hanNop"));
        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiKhoanThu"));
//        MaHKCol2.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
//        ThangNopCol.setCellValueFactory(new PropertyValueFactory<>("thangNop"));

        // Tải dữ liệu từ DAO
        loadData();

        // Thêm sự kiện cho nút
        BtnAdd.setOnAction(this::handleAddPhi);
        BtnDltPhi.setOnAction(this::handleDeletePhi);
        //BtnEditPhi.setOnAction(this::handleEditPhi);
        BtnSave1.setOnAction(this::handleEditPhi);
        KTSear.setOnKeyReleased(event -> handleSearch());
    }

    private void loadData() {
        List<KhoanThuModel> danhSach = khoanThuService.layDanhSachKhoanThu(); // Lấy dữ liệu từ DAO
        danhSachKhoanPhi = FXCollections.observableArrayList(danhSach);
        PhiTable.setItems(danhSachKhoanPhi); // Gán dữ liệu cho bảng
    }

    // Xử lý thêm khoản phí
    private void handleAddPhi(ActionEvent event) {
        try {
        //    int maPhi = Integer.parseInt(MaPhitf.getText());
            String tenPhi = TenPhitf.getText();
            double donGia = Double.parseDouble(DonGiatf.getText());
            String loaiPhi = LoaiPhitf.getText();
        //    int maHoKhau = Integer.parseInt(MaHKtf2.getText());
            int maHoKhau=0;
            int thangNop = Integer.parseInt(ThangNoptf.getText());
            Date hanNop = Date.from(HanNoptf.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()); // chuyển từ LocalDate sang Date

            KhoanThuModel khoanPhi = new KhoanThuModel( tenPhi, donGia, loaiPhi, hanNop, maHoKhau, thangNop);
            if (khoanThuService.themKhoanThu(khoanPhi)) {
                danhSachKhoanPhi.add(khoanPhi); // Thêm vào danh sách hiển thị
            //    danhSachKhoanPhi = FXCollections.observableArrayList(khoanThuService.layDanhSachKhoanThu());
                loadData();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm khoản phí thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Mã khoản phí đã tồn tại!");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
        }
    }

    // Xử lý xóa khoản phí
    private void handleDeletePhi(ActionEvent event) {
        KhoanThuModel selectedPhi = PhiTable.getSelectionModel().getSelectedItem();
        if (selectedPhi != null) {
            if (khoanThuService.xoaKhoanThu(selectedPhi.getMaKhoanThu())) {
                danhSachKhoanPhi.remove(selectedPhi); // Xóa khỏi danh sách hiển thị
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa khoản phí thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa khoản phí thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn khoản phí cần xóa!");
        }
    }

    // Xử lý sửa khoản phí
    private void handleEditPhi(ActionEvent event) {
        try {
            KhoanThuModel selectedPhi = PhiTable.getSelectionModel().getSelectedItem();
            selectedPhi.setTenKhoanThu(TenPhitf1.getText());
            selectedPhi.setSoTien(Double.parseDouble(DonGiatf1.getText()));
            selectedPhi.setLoaiKhoanThu(LoaiPhitf1.getText());
        //    selectedPhi.setMaHoKhau(0);
            selectedPhi.setThangNop(Integer.parseInt(ThangNoptf1.getText()));
            selectedPhi.setHanNop(Date.from(HanNoptf1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            if (khoanThuService.suaKhoanThu(selectedPhi)) {
                loadData(); // Làm mới bảng
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sửa khoản phí thành công!");
                AddPane.setVisible(false);
                KPTablePane.setVisible(true);
                EditPane.setVisible(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Sửa khoản phí thất bại!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
        }
    }

    // Xử lý tìm kiếm
    private void handleSearch() {
        String keyword = KTSear.getText().toLowerCase();
        List<KhoanThuModel> ketQua = khoanThuService.timKhoanThuTen(keyword);
        danhSachKhoanPhi.setAll(ketQua); // Cập nhật danh sách hiển thị
    }

    // Xóa dữ liệu nhập
    private void clearFields() {
    //    MaPhitf.clear();
        TenPhitf.clear();
        DonGiatf.clear();
        LoaiPhitf.clear();
    //    MaHKtf2.clear();
        ThangNoptf.clear();
        HanNoptf.setValue(null);
    }

    // Hiển thị thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
