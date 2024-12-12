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
import java.util.List;
import java.util.ResourceBundle;

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
    private Button BtnSave;

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
    private Label tfMaHK;

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
    private AnchorPane NKTablePane;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private TableView<NhanKhauModel> NhanKhauTable;

    private final NhanKhauService nhanKhauService = new NhanKhauService();
    private final HoKhauService hoKhauService = new HoKhauService();
    private ObservableList<NhanKhauModel> danhSachNhanKhau;

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddNK || event.getSource()==BtnEditNK || event.getSource()==BtnDltNK){
            AddPane.setVisible(true);
            NKTablePane.setVisible(false);
        }else if (event.getSource()==BtnSave){
            AddPane.setVisible(false);
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
        BtnAddNK.setOnAction(this::handleAddNhanKhau);
        BtnDltNK.setOnAction(this::handleDeleteNhanKhau);
        BtnEditNK.setOnAction(this::handleEditNhanKhau);
        NKSear.setOnKeyReleased(event -> handleSearch());
    }

    private void handleAddNhanKhau(ActionEvent actionEvent) {
    }

    private void handleDeleteNhanKhau(ActionEvent actionEvent) {
    }

    private void handleEditNhanKhau(ActionEvent actionEvent) {
    }

    private void handleSearch() {
    }


    private void loadData() {
        List<NhanKhauModel> listNhanKhau = nhanKhauService.getListNhanKhau();
        danhSachNhanKhau = FXCollections.observableArrayList(listNhanKhau);
        NhanKhauTable.setItems(danhSachNhanKhau);
    }
}
