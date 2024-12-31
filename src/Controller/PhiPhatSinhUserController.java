package Controller;

import Models.KhoanThuModel;
import Models.LoaiPhiModel;
import Service.KhoanThu1Service;
import Service.PhiPhatSinhService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PhiPhatSinhUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TableColumn<?, ?> HanNopCol;

    @FXML
    private AnchorPane KPTablePane;

    @FXML
    private TextField KTSear;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableView<LoaiPhiModel> PhiTable;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TableColumn<?, ?> ThangNopCol;



    private ObservableList<KhoanThuModel> danhSachKhoanPhi; // Dữ liệu hiển thị trên bảng
    private List<LoaiPhiModel> danhSachPhiPhatSinh;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gán dữ liệu cột
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maLoaiPhi"));
        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenLoaiPhi"));
        DonGiaCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        HanNopCol.setCellValueFactory(new PropertyValueFactory<>("hanNop"));
        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiThu"));

//
        // Tải dữ liệu từ DAO
        loadData();
//
//        // Thêm sự kiện cho nút

//        BtnDltPhi.setOnAction(this::handleDeletePhi);
//        //BtnEditPhi.setOnAction(this::handleEditPhi);

        KTSear.textProperty().addListener((observable, oldValue, newValue) -> {
            timKiemTheoTenPhi(newValue); // Gọi phương thức tìm kiếm
        });
        //        KTSear.setOnKeyReleased(event -> handleSearch());
    }

    private void loadData() {
//        List<LoaiPhiModel> danhSach = PhiPhatSinhService.layDanhSachPhiSauKhiThem();// Lấy dữ liệu từ DAO
//        danhSachKhoanPhi = FXCollections.observableArrayList(danhSach);
//        PhiTable.setItems(danhSachKhoanPhi); // Gán dữ liệu cho bảng
        danhSachPhiPhatSinh = PhiPhatSinhService.layDanhSachPhiSauKhiThem(); // Lấy danh sách từ service
        hienThiDuLieu(danhSachPhiPhatSinh); // Hiển thị lên bảng
    }

    private void hienThiDuLieu(List<LoaiPhiModel> danhSach) {
        ObservableList<LoaiPhiModel> observableList = FXCollections.observableArrayList(danhSach);
        PhiTable.setItems(observableList); // Cập nhật dữ liệu cho bảng
    }

    private void timKiemTheoTenPhi(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            hienThiDuLieu(danhSachPhiPhatSinh); // Hiển thị toàn bộ dữ liệu nếu không nhập gì
            return;
        }

        String tuKhoaLower = tuKhoa.toLowerCase(); // Chuyển về chữ thường để không phân biệt hoa/thường
        List<LoaiPhiModel> ketQua = danhSachPhiPhatSinh.stream()
                .filter(phi -> phi.getTenLoaiPhi().toLowerCase().contains(tuKhoaLower)) // Lọc danh sách
                .toList();

        hienThiDuLieu(ketQua); // Hiển thị kết quả lọc
    }
}
