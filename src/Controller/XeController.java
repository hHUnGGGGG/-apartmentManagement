package Controller;

import Models.KhoanThuModel;
import Models.XeModel;
import Service.DataSharingService;
import Service.XeService;
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

public class XeController implements Initializable {

    @FXML
    private AnchorPane AddPane;

    @FXML
    private TableColumn<?, ?> BKSCol;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnAddXe;

    @FXML
    private Button BtnDltXe;

    @FXML
    private Button BtnThoat;

    @FXML
    private TableColumn<?, ?> LXeCol;

    @FXML
    private Label MaHK;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TextField XeSear;

    @FXML
    private TableView<XeModel> XeTable;

    @FXML
    private AnchorPane XeTablePane;

    @FXML
    private TextField tfBks;

    @FXML
    private TextField tfLXe;

    @FXML
    private TextField tfMaHK;

    private XeService xeService = new XeService();
    private ObservableList<XeModel> danhSachXe;

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource()==BtnAddXe){
            AddPane.setVisible(true);
            XeTablePane.setVisible(false);
        }else if (event.getSource()==BtnThoat){
            AddPane.setVisible(false);
            XeTablePane.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BKSCol.setCellValueFactory(new PropertyValueFactory<>("bienSo"));
        LXeCol.setCellValueFactory(new PropertyValueFactory<>("loaiXe"));
        MaHKCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));

        loadData();

        BtnAdd.setOnAction(this::handleAddXe);
        BtnDltXe.setOnAction(this::handleDeleteXe);
    }

    private void loadData() {
        List<XeModel> danhSach = xeService.layDanhSachXe(); // Lấy dữ liệu từ DAO
        danhSachXe = FXCollections.observableArrayList(danhSach);
        XeTable.setItems(danhSachXe); // Gán dữ liệu cho bảng
    }

    private void clearField() {
        tfMaHK.clear();
        tfBks.clear();
        tfLXe.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void handleAddXe(ActionEvent event) {
        try {
            String bienSo= tfBks.getText();
            String loaiXe= tfLXe.getText();
            int maHoKhau= Integer.parseInt(tfMaHK.getText());

            XeModel xe = new XeModel(bienSo,loaiXe,maHoKhau);

            if(xeService.themXe(xe)) {
                danhSachXe.add(xe);
                loadData();
                clearField();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm xe thành công!");
                DataSharingService.getInstance().notifyDataChanged();
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Thêm xe thất bại, dữ liệu nhập không hợp lệ");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu nhập không hợp lệ!");
        }
    }

    private void handleDeleteXe(ActionEvent event) {
        XeModel selectedXe = XeTable.getSelectionModel().getSelectedItem();
        if (selectedXe != null) {
            if (xeService.xoaXe(selectedXe.getBienSo())) {
                danhSachXe.remove(selectedXe); // Xóa khỏi danh sách hiển thị
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa xe thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa xe thất bại!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn xe cần xóa!");
        }
    }


}
