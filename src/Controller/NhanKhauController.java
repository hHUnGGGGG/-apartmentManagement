package Controller;

import Models.NhanKhauModel;
import Models.TamVangModel;
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
import java.util.*;
import java.util.regex.Pattern;

public class NhanKhauController implements Initializable {

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane AddPane1;

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
    private TableView<TamVangModel> LichSuTVTable;

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
    private TableColumn<?, ?> LidoTVCol;

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
    private CheckBox TVangCheck1;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TableColumn<?, ?> TrangThaiCol;

    @FXML
    private TableColumn<?, ?> CCCDCol;

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
    private TextField tfLido;

    @FXML
    private ChoiceBox<String> trangThaiChoiceBox;

    @FXML
    private ChoiceBox<String> trangThaiChoiceBox1;

    private final NhanKhauService nhanKhauService = new NhanKhauService();
    private final HoKhauService hoKhauService = new HoKhauService();
    private ObservableList<NhanKhauModel> danhSachNhanKhau;
    List<NhanKhauModel> filteredList;

    public void switchForm(ActionEvent event) throws SQLException {
        if (event.getSource() == BtnAddNK){
            AddPane.setVisible(true);
            NKTablePane.setVisible(false);
        } else if (event.getSource() == BtnAdd){
            AddPane.setVisible(false);
            NKTablePane.setVisible(true);
        } else if (event.getSource() == BtnThoat) {
            AddPane.setVisible(false);
            NKTablePane.setVisible(true);
        }


        if (event.getSource() == BtnEditNK) {
            // Kiểm tra nếu có nhân khẩu được chọn
            NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
            if (selectedNhanKhau != null) {
                AddPane1.setVisible(true);
                NKTablePane.setVisible(false);
            }
        } else if (event.getSource() == BtnAdd1 && validateInputUpdate()) {
            AddPane1.setVisible(false);
            NKTablePane.setVisible(true);

        } else if (event.getSource() == BtnAdd1 && !validateInputUpdate()) {
            AddPane1.setVisible(true);
            NKTablePane.setVisible(false);
        } else if (event.getSource() == BtnThoat1){
            AddPane1.setVisible(false);
            NKTablePane.setVisible(true);
        }

        if(event.getSource() == BtnLichSuTV){
            NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
            if (selectedNhanKhau != null) {
                LichSuTVPane.setVisible(true);
                NKTablePane.setVisible(false);
            }
        }
        else if(event.getSource() == BtnThoatLichSuTV) {
            LichSuTVPane.setVisible(false);
            NKTablePane.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        MaNKCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        CanHoCol.setCellValueFactory(new PropertyValueFactory<>("soPhong"));
        NKNameCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        CCCDCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NKAgeCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        NKPhoneCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        MaHKCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        TrangThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        //TVangCol.setCellValueFactory(new PropertyValueFactory<>("tamVang"));
        TVangCol.setCellValueFactory(new PropertyValueFactory<>("tamVangHienThi"));


        LidoTVCol.setCellValueFactory(new PropertyValueFactory<>("liDo"));
        BatDauTVCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianBatDau"));
        KetThucTVCol.setCellValueFactory(new PropertyValueFactory<>("thoiGianKetThuc"));


        // Load dữ liệu ban đầu
        loadData();

        // Gắn sự kiện cho các nút

        BtnAdd.setOnAction(event-> {
            if(validateInputAdd()) {
                handleAddNhanKhau(event);
                try {
                    switchForm(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
        BtnLichSuTV.setOnAction(event -> {
            LichsuTamvang(event);
            try {
                switchForm(event);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
                });

        // Thiết lập giá trị cho trạng thái
        ObservableList<String> trangThaiOptions = FXCollections.observableArrayList("Thường trú", "Tạm trú");
        trangThaiChoiceBox.setItems(trangThaiOptions);
        trangThaiChoiceBox1.setItems(trangThaiOptions);

        // Thiết lập giá trị mặc định
        trangThaiChoiceBox.setValue("Thường trú");

        // Sự kiện cho checkbox "Là thành viên một hộ"
        laTVHoCheck.setOnAction(event -> toggleTVHo(laTVHoCheck, MaHK, tfMaHK, canHoLable, canHotextField));
        laTVHoCheck1.setOnAction(event -> toggleTVHo(laTVHoCheck1, MaHK1, tfMaHK1, canHoLable1, canHotextField1));

        // Sự kiện cho checkbox "Tạm vắng"
        TVangCheck1.setOnAction(event -> toggleTamVang(TVangCheck1, tfLido));
    }

    private void toggleTVHo(CheckBox checkBox, Label maHKLabel, TextField maHKField, Label canHoLabel, TextField canHoField) {
        boolean isSelected = checkBox.isSelected();
        maHKLabel.setVisible(isSelected);
        maHKField.setVisible(isSelected);
        canHoLabel.setVisible(!isSelected);
        canHoField.setVisible(!isSelected);
    }

    private void toggleTamVang(CheckBox checkBox, TextField liDoField) {
        boolean isSelected = checkBox.isSelected();
        liDoField.setVisible(isSelected);
    }

    private void handleAddNhanKhau(ActionEvent actionEvent) {
        try{

            String CCCD = tfCCCD.getText().trim();
            String Ten = tfTen.getText();
            Date NgaySinh = Date.from(NSinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String SDT = tfSDT.getText().trim();
            boolean latv1ho = laTVHoCheck.isSelected();
            String MaHoKhau;
            if(latv1ho){
                MaHoKhau = tfMaHK.getText().trim();
            } else{
                String SoPhong = canHotextField.getText().trim();
                MaHoKhau = String.valueOf(hoKhauService.layMaHoKhauTuSoPhong(Integer.parseInt(SoPhong)));
            }
            String Quanhe = tfQHe.getText();
            String Trangthai = trangThaiChoiceBox.getValue();
            NhanKhauModel nhanKhauModel = new NhanKhauModel(CCCD, Ten, NgaySinh, SDT, Integer.parseInt(MaHoKhau), Quanhe, Trangthai);
            if(nhanKhauService.addNhanKhau(nhanKhauModel)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân khẩu thành công!");
            } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm nhân khẩu thất bại!");
            }
            loadData();
            clearFieldsAdd();
        } catch (NumberFormatException e){
            System.err.println("Lỗi khi thêm nhân khẩu: " + e.getMessage());
        }
    }


    private void handleDeleteNhanKhau(ActionEvent actionEvent) {

        NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
        if (selectedNhanKhau != null) {
            if (nhanKhauService.delNhanKhau(selectedNhanKhau.getMaNhanKhau())) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa thành công!");

            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa thất bại!");
            }
            loadData();
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân khẩu cần xóa!");
        }
    }

    private void handleEditNhanKhau(ActionEvent actionEvent) {

        NhanKhauModel selectedNhanKhau = NhanKhauTable.getSelectionModel().getSelectedItem();
        if(selectedNhanKhau != null) {
            int maHoKhau = selectedNhanKhau.getMaHoKhau();
            boolean tamVang = selectedNhanKhau.isTamVang();

            tfCCCD1.setText(selectedNhanKhau.getCCCD());
            tfTen1.setText(selectedNhanKhau.getHoTenNhanKhau());
            NSinh1.setValue(new java.sql.Date(selectedNhanKhau.getNgaySinh().getTime()).toLocalDate());
            tfSDT1.setText(selectedNhanKhau.getSDT());
            tfMaHK1.setText(String.valueOf(maHoKhau));
            tfQHe1.setText(selectedNhanKhau.getQuanHeVoiChuHo());
            trangThaiChoiceBox1.setValue(selectedNhanKhau.getTrangThai());
            canHotextField1.setText(String.valueOf(selectedNhanKhau.getSoPhong()));
            if (maHoKhau / 1000000 != 0) {
                laTVHoCheck1.setSelected(true);
                canHoLable1.setVisible(false);
                canHotextField1.setVisible(false);
                MaHK1.setVisible(true);
                tfMaHK1.setVisible(true);
            } else {
                laTVHoCheck1.setSelected(false);
                canHoLable1.setVisible(true);
                canHotextField1.setVisible(true);
                MaHK1.setVisible(false);
                tfMaHK1.setVisible(false);
            }
            if(tamVang) {
                TVangCheck1.setSelected(true);
                tfLido.setVisible(true);
            }else{
                TVangCheck1.setSelected(false);
                tfLido.setVisible(false);
            }
            BtnAdd1.setOnAction(event -> {
                update(event, selectedNhanKhau);
                try {
                    switchForm(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } else{
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân khẩu cần chỉnh sửa!");
        }
    }

    private void update(ActionEvent actionEvent, NhanKhauModel nhankhau) {
        try{

            String CCCD = tfCCCD1.getText().trim();
            String Ten = tfTen1.getText();
            Date NgaySinh = Date.from(NSinh1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String SDT = tfSDT1.getText().trim();
            boolean latv1ho = laTVHoCheck1.isSelected();
            String MaHoKhau;
            if(latv1ho){
                MaHoKhau = tfMaHK1.getText().trim();
            } else{
                String SoPhong = canHotextField1.getText().trim();
                MaHoKhau = String.valueOf(hoKhauService.layMaHoKhauTuSoPhong(Integer.parseInt(SoPhong)));
            }
            String Quanhe = tfQHe1.getText();
            String Trangthai = trangThaiChoiceBox1.getValue();
            boolean TamVang = TVangCheck1.isSelected();
            String LiDo = "";
            if(TamVang){
                LiDo = tfLido.getText();
            }

            NhanKhauModel nhanKhauModel = new NhanKhauModel(CCCD, Ten, NgaySinh, SDT, Integer.parseInt(MaHoKhau), Quanhe, Trangthai, TamVang);
            nhanKhauModel.setMaNhanKhau(nhankhau.getMaNhanKhau());



            if(nhanKhauService.updateNhanKhau(nhanKhauModel)) {
                // Kiểm tra trạng thái ban đầu của tạm vắng
                if (TamVang && !nhankhau.isTamVang()) { // Nếu tick lần đầu
                    nhanKhauService.updateTamVangBatDau(nhanKhauModel.getMaNhanKhau(), LiDo);
                } else if (!TamVang && nhankhau.isTamVang()) { // Nếu bỏ tick
                    nhanKhauService.updateTamVangKetThuc(nhanKhauModel.getMaNhanKhau());
                    tfLido.clear();
                }
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thay đổi thông tin nhân khẩu thành công!");

            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thay đổi thông tin nhân khẩu thất bại!");
            }

            loadData();
            clearFieldsUpdate();
        } catch (NumberFormatException e){
            System.err.println("Lỗi khi update nhân khẩu: " + e.getMessage());
        }
    }


    private void LichsuTamvang(ActionEvent actionEvent){
        NhanKhauModel selectedNK = NhanKhauTable.getSelectionModel().getSelectedItem();
        if(selectedNK != null) {
            List<TamVangModel> listTamVang = nhanKhauService.getListTamVang(selectedNK.getMaNhanKhau());
            ObservableList<TamVangModel> danhSachTamVang = FXCollections.observableArrayList(listTamVang);
            LichSuTVTable.setItems(danhSachTamVang);
        } else{
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân khẩu cần xem lịch sử tạm vắng!");
        }
    }


    private void handleSearch() {
        String keyword = NKSear.getText().toLowerCase();

        if(keyword.isEmpty()){
            filteredList = nhanKhauService.getListNhanKhau();
        } else if (Pattern.matches("\\d{1,12}", keyword)) {
            if (keyword.length() <= 11) {
                filteredList = nhanKhauService.searchNhanKhauBySoPhong(keyword); // Tìm theo mã nhân khẩu
            } else {
                filteredList = nhanKhauService.searchNhanKhauByCCCD(keyword); // Tìm theo CCCD
            }
        } else {
            // Nếu từ khóa là chữ, tìm theo tên
            filteredList = nhanKhauService.searchNhanKhauByTen(keyword);
        }

        // Hiển thị kết quả
        danhSachNhanKhau.setAll(filteredList);

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

    private void clearFieldsAdd() {
        tfMaHK.clear();
        tfTen.clear();
        tfCCCD.clear();
        tfSDT.clear();
        tfQHe.clear();
        NSinh.setValue(null);
        laTVHoCheck.setSelected(false);
        trangThaiChoiceBox.setValue("Thường trú");
    }

    private void clearFieldsUpdate() {
        tfMaHK.clear();
        tfTen.clear();
        tfCCCD.clear();
        tfSDT.clear();
        tfQHe.clear();
        NSinh.setValue(null);
        TVangCheck1.setSelected(false);
        trangThaiChoiceBox1.setValue("Thường trú");

    }

    public boolean validateInputAdd(){

        if (!Pattern.matches("\\d{7}", tfMaHK.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập mã hộ khẩu hợp lệ!");
            return false;
        }


        if (!Pattern.matches("\\d{12}", tfCCCD.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập CCCD hợp lệ!");
            return false;
        }


        if (tfTen.getText().trim().length() < 2 || tfTen.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập tên hợp lệ!");
            return false;
        }


        if (!Pattern.matches("\\d{10}", tfSDT.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập số điện thoại hợp lệ!");
            return false;
        }

        if (tfQHe.getText().trim().length() < 2 || tfQHe.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập quan hệ hợp lệ!");
            return false;
        }

        if (NSinh.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy chọn ngày sinh!");
            return false;
        }

        return true;
    }

    public boolean validateInputUpdate(){

        if (!Pattern.matches("\\d{7}", tfMaHK1.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập mã hộ khẩu hợp lệ!");
            return false;
        }


        if (!Pattern.matches("\\d{12}", tfCCCD1.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập CCCD hợp lệ!");
            return false;
        }


        if (tfTen1.getText().trim().length() < 2 || tfTen1.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập tên hợp lệ!");
            return false;
        }


        if (!Pattern.matches("\\d{10}", tfSDT1.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập số điện thoại hợp lệ!");
            return false;
        }

        if (tfQHe1.getText().trim().length() < 2 || tfQHe1.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập quan hệ hợp lệ!");
            return false;
        }

        if (NSinh1.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy chọn ngày sinh!");
            return false;
        }

        return true;
    }


}
