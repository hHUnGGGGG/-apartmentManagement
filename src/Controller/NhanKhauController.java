package Controller;

import Models.NhanKhauModel;
import Service.NhanKhauService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NhanKhauController implements Initializable {

    @FXML
    private Label AgeText;

    @FXML
    private Button BtnAddNK;

    @FXML
    private Button BtnDltNK;

    @FXML
    private Button BtnEditNK;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnThoat;

    @FXML
    private TableColumn<?, ?> CCCol;

    @FXML
    private TableColumn<?, ?> MaNKCol;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TableColumn<?, ?> NKAgeCol;

    @FXML
    private TableColumn<?, ?> NKNameCol;

    @FXML
    private TableColumn<?, ?> NKPhoneCol;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TextField NKSear;

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfMaHK;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;

    @FXML
    private TextField tfQHe;

    @FXML
    private CheckBox TVangCheck;

    @FXML
    private DatePicker NSinh;

    @FXML
    private Label AgeText1;

    @FXML
    private Button BtnAdd1;

    @FXML
    private Button BtnThoat1;

    @FXML
    private Label MaHK1;

    @FXML
    private DatePicker NSinh1;

    @FXML
    private CheckBox TVangCheck1;

    @FXML
    private TextField tfCCCD1;

    @FXML
    private TextField tfMaHK1;

    @FXML
    private TextField tfQHe1;

    @FXML
    private TextField tfSDT1;

    @FXML
    private TextField tfTen1;

    @FXML
    private AnchorPane NKTablePane;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane AddPane1;

    @FXML
    private TableView<NhanKhauModel> NhanKhauTable;

    private final NhanKhauService nhanKhauService = new NhanKhauService();
    private ObservableList<NhanKhauModel> danhSachNhanKhau;

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddNK){
            AddPane.setVisible(true);
            NKTablePane.setVisible(false);
        }else if (event.getSource()==BtnEditNK){
            AddPane1.setVisible(true);
            NKTablePane.setVisible(false);
        }else if (event.getSource()==BtnAdd || event.getSource()==BtnThoat){
            AddPane.setVisible(false);
            NKTablePane.setVisible(true);
        }
        else if (event.getSource()==BtnAdd1 || event.getSource()==BtnThoat1) {
            AddPane1.setVisible(false);
            NKTablePane.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gán dữ liệu vào cột
        MaNKCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        CCCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NKNameCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        NKAgeCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        NKPhoneCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        MaHKCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        TVangCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        // Load dữ liệu ban đầu
        loadData();

        // Gắn sự kiện cho các nút
        BtnAdd.setOnAction(event-> {
            handleAddNhanKhau(event);
            switchForm(event);
        });
        BtnDltNK.setOnAction(this::handleDeleteNhanKhau);
        BtnEditNK.setOnAction(event-> {
            handleEditNhanKhau(event);
            switchForm(event);
        });
        NKSear.setOnKeyReleased(event -> handleSearch());
    }

    private void handleAddNhanKhau(ActionEvent actionEvent) {
        NhanKhauModel nhanKhauModel = new NhanKhauModel(Integer.parseInt(tfMaHK.getText()), 0,tfCCCD.getText(), tfTen.getText(), Date.from(NSinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),tfSDT.getText(), tfQHe.getText(), TVangCheck.isSelected());
        nhanKhauService.addNhanKhau(nhanKhauModel);
        loadData();
        clearFields();
    }

    private void handleDeleteNhanKhau(ActionEvent actionEvent) {
        NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
        if (selectedNhanKhau != null) {
            if (nhanKhauService.delNhanKhau(selectedNhanKhau.getMaNhanKhau())) {
                danhSachNhanKhau.remove(selectedNhanKhau); // Xóa khỏi danh sách hiển thị
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân khẩu cần xóa!");
        }
    }

    private void handleEditNhanKhau(ActionEvent actionEvent) {
        NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
        tfTen1.setText(selectedNhanKhau.getHoTenNhanKhau());
        tfSDT1.setText(selectedNhanKhau.getSDT());
        tfCCCD1.setText(selectedNhanKhau.getCCCD());
        tfMaHK1.setText(String.valueOf(selectedNhanKhau.getMaHoKhau()));
        tfQHe1.setText(selectedNhanKhau.getQuanHeVoiChuHo());
        NSinh1.setValue(new java.sql.Date(selectedNhanKhau.getNgaySinh().getTime()).toLocalDate());
        TVangCheck1.setSelected(selectedNhanKhau.isTrangThai());
        BtnAdd1.setOnAction(event-> {
            sua(event, selectedNhanKhau);
            switchForm(event);
        });
    }

    private void sua(ActionEvent actionEvent, NhanKhauModel n) {
        NhanKhauModel nhanKhauModel = new NhanKhauModel(Integer.parseInt(tfMaHK1.getText()), 0,tfCCCD1.getText(), tfTen1.getText(), Date.from(NSinh1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),tfSDT1.getText(), tfQHe1.getText(), TVangCheck1.isSelected());
        nhanKhauModel.setMaNhanKhau(n.getMaNhanKhau());
        nhanKhauService.updateNhanKhau(nhanKhauModel);
        loadData();
    }
    private void handleSearch() {
        String keyword = NKSear.getText().trim();

        if (keyword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }

        List<NhanKhauModel> filteredList = null;

        // Kiểm tra loại tìm kiếm dựa trên keyword
        if (Pattern.matches("\\d{1,12}", keyword)) {
            // Nếu từ khóa là số, tìm theo mã nhân khẩu hoặc CCCD
            if (keyword.length() <= 11) {
                filteredList = nhanKhauService.searchNhanKhauById(Integer.parseInt(keyword)); // Tìm theo mã nhân khẩu
            } else {
                filteredList = nhanKhauService.searchNhanKhauByCCCD(keyword); // Tìm theo CCCD
            }
        } else {
            // Nếu từ khóa là chữ, tìm theo tên
            filteredList = nhanKhauService.searchNhanKhauByTen(keyword);
        }

        // Hiển thị kết quả
        danhSachNhanKhau.setAll(filteredList);

        if (filteredList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Không tìm thấy nhân khẩu phù hợp!");
        }
    }


    private void loadData() {
        List<NhanKhauModel> listNhanKhau = nhanKhauService.getListNhanKhau();
        danhSachNhanKhau = FXCollections.observableArrayList(listNhanKhau);
        NhanKhauTable.setItems(danhSachNhanKhau);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void clearFields() {
        tfMaHK.clear();
        tfTen.clear();
        tfCCCD.clear();
        tfSDT.clear();
        tfQHe.clear();
        NSinh.setValue(null);
        TVangCheck.setSelected(false);
    }
}
