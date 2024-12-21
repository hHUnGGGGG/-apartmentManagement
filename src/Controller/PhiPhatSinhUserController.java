package Controller;

import Models.KhoanThuModel;
import Service.KhoanThuService;
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
    private TableView<KhoanThuModel> PhiTable;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TableColumn<?, ?> ThangNopCol;

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

        loadData();

        KTSear.setOnKeyReleased(event -> handleSearch());
    }

    private void loadData() {
        List<KhoanThuModel> danhSach = khoanThuService.layDanhSachKhoanThu(); // Lấy dữ liệu từ DAO
        danhSachKhoanPhi = FXCollections.observableArrayList(danhSach);
        PhiTable.setItems(danhSachKhoanPhi); // Gán dữ liệu cho bảng
    }

    private void handleSearch() {
        String keyword = KTSear.getText().toLowerCase();
        List<KhoanThuModel> ketQua = khoanThuService.timKhoanThuTen(keyword);
        danhSachKhoanPhi.setAll(ketQua); // Cập nhật danh sách hiển thị
    }
}
