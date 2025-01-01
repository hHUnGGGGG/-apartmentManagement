package Controller;

//import Models.ThanhToanModel;
//import Service.ThanhToanService;
import Models.KhoanThuModel;
import Service.DataSharingService;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ThanhToanController implements Initializable {

    @FXML
    private Button BtnCf;

    @FXML
    private ChoiceBox<String> CbSear;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TableColumn<KhoanThuModel, String> LoaiPhiCol;

    @FXML
    private TableColumn<KhoanThuModel, Number> MaHK3Col;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private TextField NopSear;

    @FXML
    private TableColumn<?, ?> TThaiCol;

    @FXML
    private TableColumn<KhoanThuModel, String> TenPhiCol;

    @FXML
    private TableColumn<KhoanThuModel, String> TenCHCol;

    @FXML
    private TableView<KhoanThuModel> KhoanThuTable;

    private ObservableList<KhoanThuModel> danhSachThanhToan; // Dữ liệu hiển thị trên bảng
    private List<KhoanThuModel> danhSachKhoanThu = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSharingService.getInstance().setOnDataChanged(this::loadData);
        ObservableList<String> searChoi = FXCollections.observableArrayList();
        searChoi.addAll("Mã Phí", "Tên Phí", "Mã HK", "Tên Chủ Hộ", "Loại Phí", "Số Tiền", "Thời Gian", "Trạng Thái");
        CbSear.setItems(searChoi);
        // Gán dữ liệu cột
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
        TenPhiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getTenLoaiPhi()));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        TenCHCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoKhauModel().getNhanKhauModel().getHoTenNhanKhau()));
        LoaiPhiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiPhiModel().getLoaiThu()));
        MaHK3Col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHoKhauModel().getMaHoKhau()));
        NgayNopCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianThanhToan"));
        TThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        KhoanThuService.themPhiGuiXe();
        KhoanThuService.themPhiChungCu();
        loadData();

        KhoanThuTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        BtnCf.setOnAction(this::handleCf);
//        NopSear.setOnKeyReleased(event -> handleSearch());
        danhSachKhoanThu = KhoanThuService.layThongTinThanhToan();
        KhoanThuTable.getItems().setAll(danhSachKhoanThu);
        NopSear.textProperty().addListener((observable, oldValue, newValue) -> {
            String selectedCriteria = CbSear.getValue(); // Lấy tiêu chí tìm kiếm hiện tại
            if (selectedCriteria != null) {
                handleLiveSearch(selectedCriteria, newValue);
            }
        });

    }

    public void loadData() {
        List<KhoanThuModel> danhSach = KhoanThuService.layThongTinThanhToan(); // Lấy dữ liệu từ DAO
        danhSachThanhToan = FXCollections.observableArrayList(danhSach);
        KhoanThuTable.setItems(danhSachThanhToan); // Gán dữ liệu cho bảng
        System.out.println("a");
    }

    private void handleCf(ActionEvent event) {
        // Lấy danh sách các khoản thu được chọn
        ObservableList<KhoanThuModel> selectedItems = KhoanThuTable.getSelectionModel().getSelectedItems();

        if (selectedItems != null && !selectedItems.isEmpty()) {
            boolean allSuccess = true; // Biến theo dõi trạng thái
            for (KhoanThuModel selected : selectedItems) {
                // Gọi dịch vụ để xác nhận thanh toán từng khoản
                if (!KhoanThuService.xacNhanThanhToan(selected)) {
                    allSuccess = false; // Nếu có khoản thất bại, đánh dấu
                }
            }

            if (allSuccess) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xác nhận thanh toán thành công cho tất cả các khoản!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Hoàn thành một phần", "Một số khoản thanh toán không thể xác nhận.");
            }
            loadData(); // Làm mới bảng sau khi xử lý
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn các khoản thanh toán cần xác nhận!");
        }
    }

    private void handleLiveSearch(String selectedCriteria, String searchValue) {

        if (selectedCriteria == null || selectedCriteria.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn tiêu chí tìm kiếm!");
            return;
        }
        // Nếu người dùng xóa hết văn bản tìm kiếm, hiển thị lại toàn bộ danh sách
        if (searchValue == null || searchValue.trim().isEmpty()) {
            KhoanThuTable.getItems().setAll(danhSachKhoanThu);
            return;
        }

        // Lọc danh sách dựa trên tiêu chí và giá trị tìm kiếm
        List<KhoanThuModel> ketQuaTimKiem = danhSachKhoanThu.stream()
                .filter(khoanThu -> timKiemTheoTieuChi(khoanThu, selectedCriteria, searchValue))
                .toList();

        // Cập nhật lại TableView
        KhoanThuTable.getItems().setAll(ketQuaTimKiem);
    }

    private void handleSearch() {
        String selectedCriteria = CbSear.getValue(); // Lấy tiêu chí tìm kiếm
        String searchValue = NopSear.getText().trim(); // Lấy giá trị tìm kiếm

        // Kiểm tra dữ liệu nhập vào
        if (selectedCriteria == null || selectedCriteria.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn tiêu chí tìm kiếm!");
            return;
        }
//        if (searchValue.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập giá trị tìm kiếm!");
//            return;
//        }

        // Lọc danh sách Khoản thu dựa trên tiêu chí tìm kiếm
        List<KhoanThuModel> ketQuaTimKiem = danhSachThanhToan.stream()
                .filter(khoanThu -> timKiemTheoTieuChi(khoanThu, selectedCriteria, searchValue))
                .toList();


        KhoanThuTable.getItems().setAll(ketQuaTimKiem);
    }

    private boolean timKiemTheoTieuChi(KhoanThuModel khoanThu, String tieuChi, String giaTri) {
        giaTri = giaTri.toLowerCase(); // Chuyển chuỗi tìm kiếm về chữ thường để không phân biệt chữ hoa/thường
        switch (tieuChi) {
            case "Mã Phí":
                return Integer.toString(khoanThu.getMaKhoanThu()).contains(giaTri); // Tìm kiếm chuỗi con trong Mã Phí
            case "Tên Phí":
                return khoanThu.getLoaiPhiModel().getTenLoaiPhi().toLowerCase().contains(giaTri);
            case "Mã HK":
                return Integer.toString(khoanThu.getHoKhauModel().getMaHoKhau()).contains(giaTri);
            case "Tên Chủ Hộ":
                return khoanThu.getHoKhauModel().getNhanKhauModel().getHoTenNhanKhau().toLowerCase().contains(giaTri);
            case "Loại Phí":
                return khoanThu.getLoaiPhiModel().getLoaiThu().toLowerCase().contains(giaTri);
            case "Số Tiền":
                return Integer.toString(khoanThu.getSoTien()).contains(giaTri); // Tìm kiếm chuỗi con trong Số Tiền
            case "Thời Gian":
                return khoanThu.getThoiGianThanhToan() != null &&
                        khoanThu.getThoiGianThanhToan().toString().contains(giaTri); // Tìm kiếm chuỗi con trong Thời Gian
            case "Trạng Thái":
                return khoanThu.getTrangThai().toLowerCase().contains(giaTri);
            default:
                return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
