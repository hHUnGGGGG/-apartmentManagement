package Controller;

import Models.HoKhauModel;
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
import java.util.Date;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HoKhauController implements Initializable {

    @FXML
    private AnchorPane AddHKPane;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane AddTVPane;

    @FXML
    private Label AgeText;

    @FXML
    private Button BtnAddHK;

    @FXML
    private Button BtnAddHo;

    @FXML
    private Button BtnAddTV;

    @FXML
    private Button BtnAddTV1;

    @FXML
    private Button BtnDltHK;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnThoat;

    @FXML
    private Button BtnThoat1;

    @FXML
    private Button BtnThoatXemChiTiet;

    @FXML
    private Button BtnXemChiTiet;

    @FXML
    private TableColumn<?, ?> CCCDCol;

    @FXML
    private TableColumn<?, ?> CCTVCol;

    @FXML
    private TableColumn<?, ?> CCTVCol1;

    @FXML
    private AnchorPane CHTablePane;

    @FXML
    private TableView<NhanKhauModel> HoKhauTable;

    @FXML
    private TextField HokhauSear;

    @FXML
    private Label MaCHlb;

    @FXML
    private Label MaCHlb1;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private Label MaHKlb;

    @FXML
    private DatePicker NSinh;

    @FXML
    private DatePicker NSinh1;

    @FXML
    private Label NSinh2;

    @FXML
    private TableColumn<?, ?> NSinhTVCol;

    @FXML
    private TableColumn<?, ?> NSinhTVCol1;

    @FXML
    private Label QHeText;

    @FXML
    private TableColumn<?, ?> QheTVCol;

    @FXML
    private TableColumn<?, ?> QheTVCol1;

    @FXML
    private TableColumn<?, ?> SDTCol;

    @FXML
    private TextField SdtCHtf;

    @FXML
    private TableColumn<?, ?> SdtTVCol;

    @FXML
    private TableColumn<?, ?> SdtTVCol1;

    @FXML
    private CheckBox TVangCheck;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TableColumn<?, ?> TVangCol1;

    @FXML
    private TableColumn<?, ?> TenCHCol;

    @FXML
    private TextField TenCHtf;

    @FXML
    private TableColumn<?, ?> TenTVCol;

    @FXML
    private TableColumn<?, ?> TenTVCol1;

    @FXML
    private TableView<NhanKhauModel> ThanhVienTable;

    @FXML
    private TableView<NhanKhauModel> ThanhVienXemChiTietTable;

    @FXML
    private Label canHoLable;

    @FXML
    private TextField cccdCHtf;

    @FXML
    private Label dienTichLable;

    @FXML
    private Label oToLable;

    @FXML
    private TableColumn<?, ?> soCanHoCol;

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfQHe;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;

    @FXML
    private Label xeMayLable;

    @FXML
    private AnchorPane xemChiTietPane;

    @FXML
    private ChoiceBox<Integer> tangChoiceBox;

    @FXML
    private ChoiceBox<Integer> soPhongChoiceBox;

    @FXML
    private CheckBox laMotHocheckBox;

    @FXML
    private TableColumn<?, ?> MaNKTVCol;

    @FXML
    private ChoiceBox<String> trangThaiChoiceBox;

    @FXML
    private Label trangThaiLable;

    private final HoKhauService hoKhauService = new HoKhauService();
    private final NhanKhauService nhanKhauService = new NhanKhauService();
    private NhanKhauController nhanKhauController;
    private ObservableList<NhanKhauModel> danhSachHoKhau;

    private boolean isAddHoClicked = false;

    public void switchForm3(ActionEvent event){
        if(event.getSource() == BtnSave){
            AddPane.setVisible(false);
            CHTablePane.setVisible(true);
        }
    }


    public void switchForm(ActionEvent event) throws SQLException {
        if (event.getSource() == BtnAddHK){
            AddPane.setVisible(true);
            ThanhVienTable.getItems().clear();
            CHTablePane.setVisible(false);
        }else if (event.getSource() == BtnSave && validateInputCH()) {
            AddPane.setVisible(true);
            CHTablePane.setVisible(false);
        } else if (event.getSource() == BtnSave && !validateInputCH()) {
            AddPane.setVisible(false);
            CHTablePane.setVisible(true);
        } else if (event.getSource() == BtnThoat) {
            AddPane.setVisible(false);
            CHTablePane.setVisible(true);
        } else if (event.getSource() == BtnXemChiTiet) {
            xemChiTietPane.setVisible(true);
            CHTablePane.setVisible(false);
        } else if (event.getSource() == BtnThoatXemChiTiet) {
            xemChiTietPane.setVisible(false);
            CHTablePane.setVisible(true);
        }
    }


    public void switchForm2(ActionEvent event){
        if (event.getSource() == BtnAddTV1){
            AddTVPane.setVisible(true);
            AddHKPane.setVisible(false);
        }else if (event.getSource() == BtnAddTV && validateInputTV()){
            AddTVPane.setVisible(true);
            AddHKPane.setVisible(false);
        }else if(event.getSource() == BtnAddTV && !validateInputTV()){
            AddTVPane.setVisible(false);
            AddHKPane.setVisible(true);
        } else if (event.getSource() == BtnThoat1) {
            AddTVPane.setVisible(false);
            AddHKPane.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cấu hình các cột trong bảng TableView
        MaHKCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        TenCHCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        CCCDCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        SDTCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        soCanHoCol.setCellValueFactory(new PropertyValueFactory<>("soPhong"));

        TenTVCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        CCTVCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NSinhTVCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        SdtTVCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        QheTVCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        TenTVCol1.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        CCTVCol1.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        NSinhTVCol1.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        SdtTVCol1.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        QheTVCol1.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));
        TVangCol1.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        MaNKTVCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));

        for (int i = 1; i <= 24; i++) {
            tangChoiceBox.getItems().add(i);
        }

        tangChoiceBox.setValue(1); // Chọn giá trị mặc định là 1

        List<Integer> canHo = hoKhauService.listCanHo(tangChoiceBox.getValue());
        soPhongChoiceBox.getItems().addAll(canHo);
        soPhongChoiceBox.setValue(canHo.get(0));
        //Load dữ liệu ban đầu
        loadDataHK();

        //gán sự kiện cho các nút

        BtnAddHo.setOnAction(this::handleAddHoClicked);
        BtnSave.setOnAction(this::handleSaveClicked);
        BtnDltHK.setOnAction(this::handleDeleteHoKhau);
        HokhauSear.setOnKeyReleased(event -> handleSearch());
        BtnXemChiTiet.setOnAction(this::handleXemChiTiet);
        BtnAddTV.setOnAction(this::handleAddTV);
        tangChoiceBox.setOnAction(this::handleChonPhong);
    }

    private void handleAddHoClicked(ActionEvent event){
        //đánh dấu nút đã được nhấn
          handleAddHoKhau(event);
          isAddHoClicked = true;
    }

    private void handleSaveClicked(ActionEvent event){
        try{
            if(isAddHoClicked){
                // Nếu BtnAddHo đã được nhấn, chỉ chuyển đổi giao diện
                switchForm3(event);
            } else {
                // Nếu BtnAddHo chưa được nhấn, thực hiện thêm hộ khẩu và chuyển đổi giao diện
                handleAddHoKhau(event);
                switchForm(event);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            isAddHoClicked = false;
        }
    }


    private void loadDataHK() {
        // Lấy dữ liệu từ getListHoKhau
        List<NhanKhauModel> datahk = hoKhauService.getListHoKhau();

        // Tạo ObservableList từ data
        ObservableList<NhanKhauModel> danhSachChuHo = FXCollections.observableArrayList(datahk);

        // Đặt dữ liệu vào TableView
        HoKhauTable.setItems(danhSachChuHo);

    }

    private void loadDataTV() throws SQLException {
        List<NhanKhauModel> datank = nhanKhauService.getTVtrongHK(laMotHocheckBox.isSelected());

        ObservableList<NhanKhauModel> danhSachThanhvien = FXCollections.observableArrayList(datank);

        ThanhVienTable.setItems(danhSachThanhvien);

    }


    private void handleAddHoKhau(ActionEvent actionEvent) {

        try {
            if (validateInputCH()) return;


            String CCCDChuHo = cccdCHtf.getText().trim();
            String TenChuHo = TenCHtf.getText();
            Date NgaySinhChuHo = Date.from(NSinh1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String SDTChuHo = SdtCHtf.getText().trim();
            int MaHoKhau;
            boolean laMotHo = laMotHocheckBox.isSelected();
            if(laMotHo) MaHoKhau = hoKhauService.getMaxMaHoKhau() + 100;
            else MaHoKhau = hoKhauService.getMaxMaHoKhauTamTru() + 100;
            String QuanHe = "Chủ Hộ";
            boolean Trangthai = TVangCheck.isSelected();

            HoKhauModel hoKhau = new HoKhauModel();
            NhanKhauModel chuHo = new NhanKhauModel(MaHoKhau, CCCDChuHo, TenChuHo, NgaySinhChuHo, SDTChuHo, QuanHe, Trangthai);

            if (hoKhauService.addHoKhau(hoKhau, laMotHo)) {
                if(nhanKhauService.addChuHo(chuHo)) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm hộ khẩu thành công!");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm hộ khẩu thất bại!");
            }
            hoKhauService.luuCanHo(soPhongChoiceBox.getValue(), MaHoKhau);
            loadDataHK();
            loadDataTV();
            clearFieldsHK();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void handleDeleteHoKhau(ActionEvent actionEvent) {
        NhanKhauModel selectedHoKhau = HoKhauTable.getSelectionModel().getSelectedItem();
        int maHoKhau = hoKhauService.layMaHoKhauTuSoPhong(selectedHoKhau.getSoPhong());
        System.out.println(maHoKhau);
        if(selectedHoKhau != null) {
            int maCanHo = hoKhauService.maCanHo(maHoKhau);
            int tangCanHo = hoKhauService.TangCanHo(maHoKhau);
            int soCanHo = hoKhauService.soCanHo(maHoKhau);
            if(hoKhauService.delHoKhau(maHoKhau)){
                hoKhauService.themCanHo(maCanHo, tangCanHo, soCanHo);
                loadDataHK();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa hộ khẩu thành công!");
            } else{
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa hộ khẩu thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng 1 chọn hộ!");
        }
    }


    private void handleSearch() {
        String keyword = HokhauSear.getText().toLowerCase();

        List<NhanKhauModel> filteredList;

        // Kiểm tra loại tìm kiếm dựa trên keyword
        if (Pattern.matches("\\d{1,12}", keyword)) {
            // Nếu từ khóa là số, tìm theo mã nhân khẩu hoặc CCCD
            if (keyword.length() <= 11) {
                filteredList = hoKhauService.searchHoKhaubyId(keyword); // Tìm theo mã nhân khẩu
            } else {
                filteredList = hoKhauService.searchHoKhauByCCCD(keyword); // Tìm theo CCCD
            }
        } else {
            // Nếu từ khóa là chữ, tìm theo tên
            filteredList = hoKhauService.searchHoKhauByTen(keyword);
        }

        //Hiển thị kết quả
        danhSachHoKhau.setAll(filteredList);

        if (filteredList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Không tìm thấy hộ khẩu phù hợp!");
        }

    }


    private void handleAddTV(ActionEvent actionEvent){

        try {

            String CCCDTV = tfCCCD.getText().trim();
            String TenTV = tfTen.getText();
            Date NgaySinhTV = Date.from(NSinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String SDTTV = tfSDT.getText().trim();
            int MaHK = hoKhauService.getMaxMaHoKhau();
            String QuanHe = tfQHe.getText();
            boolean Trangthai = TVangCheck.isSelected();

            NhanKhauModel thanhVien = new NhanKhauModel(MaHK, CCCDTV, TenTV, NgaySinhTV, SDTTV, QuanHe, Trangthai);

            if(nhanKhauService.addNhanKhau(thanhVien)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân khẩu thành công!");
            } else{
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm nhân khẩu thất bại!");
            }
            loadDataTV();
            clearFieldsTV();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadChiTietHo(int maHoKhau) {
        ObservableList<NhanKhauModel> danhSachThanhvien = FXCollections.observableArrayList(hoKhauService.getListNhanKhauTrongHo(maHoKhau));
        ThanhVienXemChiTietTable.setItems(danhSachThanhvien);
        canHoLable.setText(String.valueOf(hoKhauService.soCanHo(maHoKhau)));
        dienTichLable.setText(String.valueOf(hoKhauService.dienTichCanHo(maHoKhau)));
        xeMayLable.setText(String.valueOf(hoKhauService.soXeMayCuaHo(maHoKhau)) + " )");
        oToLable.setText(String.valueOf(hoKhauService.soOToCuaHo(maHoKhau)) + " )");
    }

    private void handleXemChiTiet(ActionEvent event) {
        NhanKhauModel selectedHoKhau = HoKhauTable.getSelectionModel().getSelectedItem();

        if(selectedHoKhau != null) {
            loadChiTietHo(hoKhauService.layMaHoKhauTuSoPhong(selectedHoKhau.getSoPhong()));
            try {
                switchForm(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng 1 chọn hộ!");
        }
    }

    private void handleChonPhong(ActionEvent event) {
        soPhongChoiceBox.getItems().clear();
        List<Integer> canHo = hoKhauService.listCanHo(tangChoiceBox.getValue());
        soPhongChoiceBox.getItems().addAll(canHo);
        soPhongChoiceBox.setValue(canHo.get(0));
    }

    public boolean validateInputCH() throws SQLException {

        if (!Pattern.matches("\\d{12}", cccdCHtf.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập CCCD hợp lệ!");
            return true;
        }


        if (TenCHtf.getText().trim().length() < 2 || TenCHtf.getText().trim().length() > 60) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập tên hợp lệ!");
            return true;
        }


        if (!Pattern.matches("\\d{10}", SdtCHtf.getText().trim())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy nhập số điện thoại hợp lệ!");
            return true;
        }

        // Kiểm tra ngày sinh
        if (NSinh1.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy chọn ngày sinh!");
            return true;
        }

        return false;
    }


    public boolean validateInputTV(){

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

        // Kiểm tra ngày sinh
        if (NSinh.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Hãy chọn ngày sinh!");
            return true;
        }

        return false;
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void clearFieldsHK() {
        TenCHtf.clear();
        cccdCHtf.clear();
        SdtCHtf.clear();
        NSinh1.setValue(null);
        TVangCheck.setSelected(false);
    }

    private void clearFieldsTV() {
        tfTen.clear();
        tfCCCD.clear();
        tfSDT.clear();
        tfQHe.clear();
        NSinh.setValue(null);
        TVangCheck.setSelected(false);
    }

    public ObservableList<NhanKhauModel> getDanhSachHoKhau() {
        return danhSachHoKhau;
    }

    public void setDanhSachHoKhau(ObservableList<NhanKhauModel> danhSachHoKhau) {
        this.danhSachHoKhau = danhSachHoKhau;
    }

    public TableView<NhanKhauModel> getHoKhauTable() {
        return HoKhauTable;
    }

    public void setHoKhauTable(TableView<NhanKhauModel> hoKhauTable) {
        HoKhauTable = hoKhauTable;
    }

    public TableView<NhanKhauModel> getThanhVienTable() {
        return ThanhVienTable;
    }

    public void setThanhVienTable(TableView<NhanKhauModel> thanhVienTable) {
        ThanhVienTable = thanhVienTable;
    }
}


