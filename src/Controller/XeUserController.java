package Controller;

import Controller.DangNhapVaDangKi.LoginController;
import Models.XeModel;
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

public class XeUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> BKSCol;

    @FXML
    private TableColumn<?, ?> LXeCol;

    @FXML
    private TextField XeSear;

    @FXML
    private TableView<XeModel> XeTable;

    @FXML
    private AnchorPane XeTablePane;

    private LoginController loginController = new LoginController();
    private XeService xeService = new XeService();
    private ObservableList<XeModel> danhSachXe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BKSCol.setCellValueFactory(new PropertyValueFactory<>("bienSo"));
        LXeCol.setCellValueFactory(new PropertyValueFactory<>("loaiXe"));

        loadData();

    }

    private void loadData() {
        List<XeModel> danhSach = xeService.layDanhSachXeTheoHo(loginController.getMaHoKhauUser()); // Lấy dữ liệu từ DAO
        danhSachXe = FXCollections.observableArrayList(danhSach);
        XeTable.setItems(danhSachXe); // Gán dữ liệu cho bảng
    }
}
