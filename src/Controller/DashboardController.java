package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label AccountLabel;

    @FXML
    private Label AgeText;

    @FXML
    private Button BtnAdd4;

    @FXML
    private Button BtnAddHK;

    @FXML
    private Button BtnAddNK;

    @FXML
    private Button BtnAddPhi;

    @FXML
    private Button BtnClose;

    @FXML
    private Button BtnDltHK;

    @FXML
    private Button BtnDltNK;

    @FXML
    private Button BtnDltPhi;

    @FXML
    private Button BtnEdit4;

    @FXML
    private Button BtnEditHK;

    @FXML
    private Button BtnEditNK;

    @FXML
    private Button BtnEditPhi;

    @FXML
    private Button BtnHokhau;

    @FXML
    private Button BtnKhoanphi;

    @FXML
    private Button BtnMin;

    @FXML
    private Button BtnNhankhau;

    @FXML
    private Button BtnThanhToan;

    @FXML
    private Button BtnThongke;

    @FXML
    private Button Btndlt4;

    @FXML
    private TableColumn<?, ?> CCCol;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TextField DonGiatf;

    @FXML
    private TableColumn<?, ?> HanNopCol;

    @FXML
    private DatePicker HanNoptf;

    @FXML
    private AnchorPane HoKhau;

    @FXML
    private TextField HokhauSear;

    @FXML
    private TextField KTSear;

    @FXML
    private AnchorPane KhoanPhi;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TextField LoaiPhitf;

    @FXML
    private TableColumn<?, ?> MaCHCol;

    @FXML
    private TableColumn<?, ?> MaHK3Col;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TableColumn<?, ?> MaHKCol2;

    @FXML
    private TextField MaHKNop;

    @FXML
    private TextField MaHKtf2;

    @FXML
    private TableColumn<?, ?> MaNKCol;

    @FXML
    private TableColumn<?, ?> MaNopCol1;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> MaPhiNCol;

    @FXML
    private TextField MaPhiNop;

    @FXML
    private TextField MaPhitf;

    @FXML
    private TableColumn<?, ?> NKAgeCol;

    @FXML
    private TableColumn<?, ?> NKNameCol;

    @FXML
    private TableColumn<?, ?> NKPhoneCol;

    @FXML
    private TextField NKSear;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private AnchorPane NhanKhau;

    @FXML
    private TextField NopSear;

    @FXML
    private TableColumn<?, ?> PThucCol;

    @FXML
    private MenuButton PThucNop;

    @FXML
    private TextField PhoneText;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TextField TenPhitf;

    @FXML
    private TableColumn<?, ?> ThangNopCol;

    @FXML
    private TextField ThangNoptf;

    @FXML
    private AnchorPane ThanhToan;

    @FXML
    private AnchorPane ThongKe;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfMaChuHo;

    @FXML
    private Label tfMaHK;

    @FXML
    private TextField tfMaHoKhau;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;


    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnHokhau){
            HoKhau.setVisible(true);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnNhankhau){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(true);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnKhoanphi){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(true);
            ThanhToan.setVisible(false);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnThanhToan){
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(true);
            ThongKe.setVisible(false);
        }else if (event.getSource()==BtnThongke) {
            HoKhau.setVisible(false);
            NhanKhau.setVisible(false);
            KhoanPhi.setVisible(false);
            ThanhToan.setVisible(false);
            ThongKe.setVisible(true);
        }
    }

    public void Close(){
        System.exit(0);
    }

    public void Minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
