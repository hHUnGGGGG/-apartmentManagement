package Controller;

import Models.NhanKhauModel;
import Service.HoKhauService;
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
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NhanKhauController implements Initializable {

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane AddPane1;

    @FXML
    private Label AgeText;

    @FXML
    private Label AgeText1;

    @FXML
    private TableColumn<?, ?> BatDauTVCol;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnAdd1;

    @FXML
    private Button BtnAddNK;

    @FXML
    private Button BtnDltNK;

    @FXML
    private Button BtnEditNK;

    @FXML
    private Button BtnLichSuTV;

    @FXML
    private Button BtnThoat;

    @FXML
    private Button BtnThoat1;

    @FXML
    private Button BtnThoatLichSuTV;

    @FXML
    private TableColumn<?, ?> CanHoCol;

    @FXML
    private TableColumn<?, ?> KetThucTVCol;

    @FXML
    private TableView<?> LIchSuTVTable;

    @FXML
    private AnchorPane LichSuTVPane;

    @FXML
    private Label MaHK;

    @FXML
    private Label MaHK1;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TableColumn<?, ?> MaNKCol;

    @FXML
    private TableColumn<?, ?> NKAgeCol;

    @FXML
    private TableColumn<?, ?> NKNameCol;

    @FXML
    private TableColumn<?, ?> NKNameCol1;

    @FXML
    private TableColumn<?, ?> NKPhoneCol;

    @FXML
    private TextField NKSear;

    @FXML
    private AnchorPane NKTablePane;

    @FXML
    private DatePicker NSinh;

    @FXML
    private DatePicker NSinh1;

    @FXML
    private TableView<NhanKhauModel> NhanKhauTable;

    @FXML
    private CheckBox TVangCheck;

    @FXML
    private CheckBox TVangCheck1;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TableColumn<?, ?> TrangThaiCol;

    @FXML
    private Label canHoLable;

    @FXML
    private Label canHoLable1;

    @FXML
    private TextField canHotextField;

    @FXML
    private TextField canHotextField1;

    @FXML
    private CheckBox laTVHoCheck;

    @FXML
    private CheckBox laTVHoCheck1;

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfCCCD1;

    @FXML
    private TextField tfMaHK;

    @FXML
    private TextField tfMaHK1;

    @FXML
    private TextField tfQHe;

    @FXML
    private TextField tfQHe1;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfSDT1;

    @FXML
    private TextField tfTen;

    @FXML
    private TextField tfTen1;

    @FXML
    private ChoiceBox<?> trangThaiChoiceBox;

    @FXML
    private ChoiceBox<?> trangThaiChoiceBox1;

    private final NhanKhauService nhanKhauService = new NhanKhauService();
    private final HoKhauService hoKhauService = new HoKhauService();
    private ObservableList<NhanKhauModel> danhSachNhanKhau;

    public void switchForm(ActionEvent event) throws SQLException {
        if (event.getSource() == BtnAddNK){
            AddPane.setVisible(true);
            NKTablePane.setVisible(false);
        }else if (event.getSource() == BtnAdd && validateInput()){
            AddPane.setVisible(true);
            NKTablePane.setVisible(false);
        } else if (event.getSource() == BtnAdd && !validateInput()) {
            AddPane.setVisible(false);
            NKTablePane.setVisible(true);
        } else if (event.getSource() == BtnThoat) {
            AddPane.setVisible(false);
            NKTablePane.setVisible(true);
        }


        if (event.getSource() == BtnEditNK){
            AddPane1.setVisible(true);
            NKTablePane.setVisible(false);
        }else if (event.getSource() == BtnAdd1 && validateInput()) {
            AddPane1.setVisible(true);
            NKTablePane.setVisible(false);
        } else if (event.getSource() == BtnAdd1 && !validateInput()){
            AddPane1.setVisible(false);
            NKTablePane.setVisible(true);
        } else if (event.getSource() == BtnThoat1) {
            AddPane1.setVisible(false);
            NKTablePane.setVisible(true);
        }

        if(event.getSource() == BtnLichSuTV){
            LichSuTVPane.setVisible(true);
            NKTablePane.setVisible(false);
        }
        else if(event.getSource() == BtnThoatLichSuTV) {
            LichSuTVPane.setVisible(false);
            NKTablePane.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaNKCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));

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
            try {
                switchForm(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        BtnDltNK.setOnAction(this::handleDeleteNhanKhau);
        BtnEditNK.setOnAction(event-> {
            handleEditNhanKhau(event);
            try {
                switchForm(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        NKSear.setOnKeyReleased(event -> handleSearch());
    }

    private void handleAddNhanKhau(ActionEvent actionEvent) {
        try{

            NhanKhauModel nhanKhauModel = new NhanKhauModel(Integer.parseInt(tfMaHK.getText().trim()),tfCCCD.getText().trim(), tfTen.getText(), Date.from(NSinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),tfSDT.getText().trim(), tfQHe.getText(), TVangCheck.isSelected());
            if(nhanKhauService.addNhanKhau(nhanKhauModel)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân khẩu thành công!");
                loadData();
                clearFields();
            } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm nhân khẩu thất bại!");
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void handleDeleteNhanKhau(ActionEvent actionEvent) {
        NhanKhauModel selectedNhanKhau = (NhanKhauModel) NhanKhauTable.getSelectionModel().getSelectedItem();
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
        NhanKhauModel selectedNhanKhau = (NhanKhauModel) NhanKhauTable.getSelectionModel().getSelectedItem();
        tfTen1.setText(selectedNhanKhau.getHoTenNhanKhau());
        tfSDT1.setText(selectedNhanKhau.getSDT());
        tfCCCD1.setText(selectedNhanKhau.getCCCD());
        tfMaHK1.setText(String.valueOf(selectedNhanKhau.getMaHoKhau()));
        tfQHe1.setText(selectedNhanKhau.getQuanHeVoiChuHo());
        NSinh1.setValue(new java.sql.Date(selectedNhanKhau.getNgaySinh().getTime()).toLocalDate());
        TVangCheck1.setSelected(selectedNhanKhau.isTrangThai());
        BtnAdd1.setOnAction(event-> {
            update(event, selectedNhanKhau);
            try {
                switchForm(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void update(ActionEvent actionEvent, NhanKhauModel nhankhau) {
        try{

            NhanKhauModel nhanKhauModel = new NhanKhauModel(Integer.parseInt(tfMaHK1.getText()),tfCCCD1.getText(), tfTen1.getText(), Date.from(NSinh1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),tfSDT1.getText(), tfQHe1.getText(), TVangCheck1.isSelected());
            nhanKhauModel.setMaNhanKhau(nhankhau.getMaNhanKhau());

            if(nhanKhauService.updateNhanKhau(nhanKhauModel)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thay đổi thông tin nhân khẩu thành công!");
                loadData();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thay đổi thông tin nhân khẩu thất bại!");
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    private void handleSearch() {
        String keyword = NKSear.getText().toLowerCase();

        List<NhanKhauModel> filteredList;

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


    public void loadData() {
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

    public boolean validateInput() throws SQLException {

        if (!Pattern.matches("\\d{7}", tfMaHK.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập mã hộ khẩu hợp lệ!");
            return true;
        }

        if (!hoKhauService.existsHoKhauId(Integer.parseInt(tfMaHK.getText()))) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Không tồn tại hộ khẩu này!");
            return true;
        }

        if (!Pattern.matches("\\d{12}", tfCCCD.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập CCCD hợp lệ!");
            return true;
        }


        if (tfTen.getText().trim().length() < 2 || tfTen.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập tên hợp lệ!");
            return true;
        }


        if (!Pattern.matches("\\d{10}", tfSDT.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập số điện thoại hợp lệ!");
            return true;
        }

        if (tfQHe.getText().trim().length() < 2 || tfQHe.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập quan hệ hợp lệ!");
            return true;
        }

        return false;
    }


    public ObservableList<NhanKhauModel> getDanhSachNhanKhau() {
        return danhSachNhanKhau;
    }

    public void setDanhSachNhanKhau(ObservableList<NhanKhauModel> danhSachNhanKhau) {
        this.danhSachNhanKhau = danhSachNhanKhau;
    }

    public TableView<NhanKhauModel> getNhanKhauTable() {
        return NhanKhauTable;
    }

    public void setNhanKhauTable(TableView<NhanKhauModel> nhanKhauTable) {
        NhanKhauTable = nhanKhauTable;
    }
}
