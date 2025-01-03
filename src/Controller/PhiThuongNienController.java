package Controller;

import Models.LoaiPhiModel;
import Service.LoaiPhiService;
import Service.PhiThuongNienService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PhiThuongNienController implements Initializable {

    @FXML
    private Button BtnEditPhi;

    @FXML
    private Button BtnSave;

    @FXML
    private AnchorPane EditPane;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TextField PTNSear;

    @FXML
    private TableView<LoaiPhiModel> PTNTable;

    @FXML
    private AnchorPane PTNTablePane;

    @FXML
    private TableColumn<?, ?> SoTienCol;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TextField tfSoTien;

    private ObservableList<LoaiPhiModel> danhSach;

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource()==BtnEditPhi){
            LoaiPhiModel selectedPhi = PTNTable.getSelectionModel().getSelectedItem();

            if(selectedPhi!=null){
                tfSoTien.setText(String.valueOf(selectedPhi.getDonGia()));
                PTNTablePane.setVisible(false);
                EditPane.setVisible(true);
            } else {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn khoản phí cần sửa!");
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TenPhiCol.setCellValueFactory(new PropertyValueFactory<>("tenLoaiPhi"));
        MaPhiCol.setCellValueFactory(new PropertyValueFactory<>("maLoaiPhi"));
        SoTienCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        LoaiPhiCol.setCellValueFactory(new PropertyValueFactory<>("loaiThu"));

        loadData();

        BtnSave.setOnAction(this::handleEditDonGia);
        PTNSear.setOnKeyReleased(event -> handleSearch());
    }

    private void loadData() {
        List<LoaiPhiModel> ds = PhiThuongNienService.layDanhSachPhiThuongNien(); // Lấy dữ liệu từ DAO
        danhSach = FXCollections.observableArrayList(ds);
        PTNTable.setItems(danhSach); // Gán dữ liệu cho bảng
    }

    private void handleEditDonGia(ActionEvent event) {
        try {
            LoaiPhiModel selectedPhi = PTNTable.getSelectionModel().getSelectedItem();

            if(selectedPhi!=null){
                selectedPhi.setDonGia(Integer.parseInt(tfSoTien.getText()));

                if (PhiThuongNienService.suaDonGia(selectedPhi)) {
                    loadData(); // Làm mới bảng
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sửa đơn giá thành công!");
                    PTNTablePane.setVisible(true);
                    EditPane.setVisible(false);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Sửa đơn giá thất bại!");
                }
                tfSoTien.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Cảnh báo", "Vui lòng chọn loại phí cần sửa");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
        }
    }

    private void handleSearch() {
        String keyword = PTNSear.getText().toLowerCase();
        List<LoaiPhiModel> ketQua = PhiThuongNienService.timLoaiPhiTheoTen(keyword);
        danhSach.setAll(ketQua); // Cập nhật danh sách hiển thị
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
