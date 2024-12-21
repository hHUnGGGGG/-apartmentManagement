package Controller;

import Models.KhoanThuModel;
import Service.KhoanThuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class KhoanPhiTuNguyenController implements Initializable {
    @FXML
    private Button BtnAddPhi;

    @FXML
    private Button BtnDltPhi;

    @FXML
    private Button BtnEditPhi;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnSave1;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TextField DonGiatf;

    @FXML
    private TextField DonGiatf1;

    @FXML
    private TableColumn<?, ?> HanNopCol;

    @FXML
    private DatePicker HanNoptf;

    @FXML
    private DatePicker HanNoptf1;

    @FXML
    private TextField KTSear;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TextField LoaiPhitf;

    @FXML
    private TextField LoaiPhitf1;

    @FXML
    private TextField MaHKtf2;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TextField MaPhitf;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TextField TenPhitf;

    @FXML
    private TextField TenPhitf1;

    @FXML
    private TableColumn<?, ?> ThangNopCol;

    @FXML
    private TextField ThangNoptf;

    @FXML
    private TextField ThangNoptf1;

    @FXML
    private TableView<KhoanThuModel> PhiTable;

    @FXML
    private AnchorPane AddPane;

    @FXML
    private AnchorPane EditPane;

    @FXML
    private AnchorPane KPTablePane;

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddPhi){
            AddPane.setVisible(true);
            KPTablePane.setVisible(false);
            EditPane.setVisible(false);
        }else if (event.getSource()==BtnSave ){
            AddPane.setVisible(false);
            KPTablePane.setVisible(true);
            EditPane.setVisible(false);
        }else if (event.getSource()==BtnEditPhi) {
            KhoanThuModel selectedPhi = PhiTable.getSelectionModel().getSelectedItem();
            if (selectedPhi != null) {
                AddPane.setVisible(false);
                KPTablePane.setVisible(false);
                EditPane.setVisible(true);
            }
        }
    }

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
        ThangNopCol.setCellValueFactory(new PropertyValueFactory<>("thangNop"));
    }
}
