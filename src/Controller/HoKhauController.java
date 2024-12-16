package Controller;

import Models.HoKhauChuHoModel;
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
    private AnchorPane CHTablePane;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane AddHKPane;

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
    private Button BtnEditHK;

    @FXML
    private Button BtnSave;

    @FXML
    private TableColumn<?, ?> CCCDCol;

    @FXML
    private TextField HokhauSear;

    @FXML
    private TableColumn<?, ?> MaCHCol;

    @FXML
    private Label MaCHlb;

    @FXML
    private Label QHeText;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private Label MaHKlb;

    @FXML
    private TableColumn<?, ?> SDTCol;

    @FXML
    private TextField SdtCHtf;

    @FXML
    private CheckBox TVangCheck;

    @FXML
    private TableColumn<?, ?> TenCHCol;

    @FXML
    private TableColumn<?, ?> CCTVCol;

    @FXML
    private TableColumn<?, ?> NSinhTVCol;

    @FXML
    private TableColumn<?, ?> SdtTVCol;

    @FXML
    private TableColumn<?, ?> TenTVCol;

    @FXML
    private TableColumn<?, ?> QheTVCol;

    @FXML
    private TableView<?> ThanhVienTable;

    @FXML
    private TextField TenCHtf;

    @FXML
    private TextField cccdCHtf;

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;

    @FXML
    private TextField tfQHe;

    @FXML
    private DatePicker NSinh;

    @FXML
    private DatePicker NSinh1;

    @FXML
    private TableView<HoKhauChuHoModel> HoKhauTable;

    private final HoKhauService hoKhauService = new HoKhauService();
    private final NhanKhauService nhanKhauService = new NhanKhauService();


    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddHK || event.getSource()==BtnEditHK){
            AddPane.setVisible(true);
            CHTablePane.setVisible(false);
        }else if (event.getSource()==BtnSave){
            AddPane.setVisible(false);
            CHTablePane.setVisible(true);
        }
    }


    public void switchForm2(ActionEvent event){
        if (event.getSource()==BtnAddTV1 ){
            AddTVPane.setVisible(true);
            AddHKPane.setVisible(false);
        }else if (event.getSource()==BtnAddTV){
            AddTVPane.setVisible(false);
            AddHKPane.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Cấu hình các cột trong bảng TableView
        MaHKCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        MaCHCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        TenCHCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        CCCDCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        SDTCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));

//        TenTVCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
//        CCTVCol.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
//        NSinhTVCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
//        SdtTVCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
//        QheTVCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));
        
        //Load dữ liệu ban đầu
        loadData();

        //gán sự kiện cho các nút
        BtnSave.setOnAction(event -> {
            handleAddHoKhau(event);
            switchForm(event); // Thay đổi giao diện
        });

        BtnDltHK.setOnAction(this::handleDeleteHoKhau);
        BtnEditHK.setOnAction(this::handleEditHoKhau);
        HokhauSear.setOnKeyReleased(event -> handleSearch());

        BtnAddTV.setOnAction(this::handleAddTV);

    }


    private void loadData() {
        // Lấy dữ liệu từ getListHoKhau
        List<HoKhauChuHoModel> data = hoKhauService.getListHoKhau();

        // Tạo ObservableList từ data
        ObservableList<HoKhauChuHoModel> danhSachChuHo = FXCollections.observableArrayList(data);

        // Đặt dữ liệu vào TableView
        HoKhauTable.setItems(danhSachChuHo);
    }


    private void handleAddHoKhau(ActionEvent actionEvent) {

                try {
                    if (validateInputCH()) return;

                    String CCCDChuHo = cccdCHtf.getText().trim();
                    String TenChuHo = TenCHtf.getText();
                    Date NgaySinhChuHo = Date.from(NSinh1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    String SDTChuHo = SdtCHtf.getText().trim();
                    int MaHoKhau = hoKhauService.getMaxMaHoKhau() + 100;
                    String QuanHe = "Chủ Hộ";
                    boolean Trangthai = TVangCheck.isSelected();

                    NhanKhauModel chuHo = new NhanKhauModel(CCCDChuHo, TenChuHo, NgaySinhChuHo, SDTChuHo, MaHoKhau, QuanHe, Trangthai);
                    HoKhauModel hoKhau = new HoKhauModel();

                    loadData();

                    if (hoKhauService.addHoKhau(hoKhau) && nhanKhauService.addNhanKhau(chuHo)) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm hộ khẩu thành công!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm hộ khẩu thất bại!");
                    }
                } catch (NumberFormatException | SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
                }
    }


    private void handleDeleteHoKhau(ActionEvent actionEvent) {

        HoKhauChuHoModel selectedHoKhau = HoKhauTable.getSelectionModel().getSelectedItem();
        if(selectedHoKhau != null) {
            if(confirmDeletion()){
                if(hoKhauService.delHoKhau(selectedHoKhau.getMaHoKhau()) && nhanKhauService.delNhanKhauHoKhau(selectedHoKhau.getMaHoKhau())){
                    loadData();
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa hộ khẩu thành công!");
                } else{
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa hộ khẩu thất bại!");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng 1 chọn hộ!");
        }
    }

    private void handleEditHoKhau(ActionEvent actionEvent) {

    }

    private void handleSearch() {

    }


    private void handleAddTV(ActionEvent actionEvent){

        try {
            if(validateInputTV()) return;

            String CCCDTV = tfCCCD.getText().trim();
            String TenTV = tfTen.getText();
            Date NgaySinhTV = Date.from(NSinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String SDTTV = tfSDT.getText().trim();
            int MaHK = hoKhauService.getMaxMaHoKhau() + 100;
            String QuanHe = QHeText.getText();
            boolean Trangthai = TVangCheck.isSelected();

            NhanKhauModel thanhVien = new NhanKhauModel(CCCDTV, TenTV, NgaySinhTV, SDTTV, MaHK , QuanHe, Trangthai);

            loadData();

            if(nhanKhauService.addNhanKhau(thanhVien)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân khẩu thành công!");
            } else{
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm nhân khẩu thất bại!");
            }
        } catch (NumberFormatException | SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
        }
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

        return false;
    }


    public boolean validateInputTV() throws SQLException {

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


    private boolean confirmDeletion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
        alert.setContentText("Hành động này không thể hoàn tác!");
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}


