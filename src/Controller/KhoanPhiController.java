package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class KhoanPhiController implements Initializable {

    @FXML
    private Button BtnAddPhi;

    @FXML
    private Button BtnDltPhi;

    @FXML
    private Button BtnEditPhi;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TextField DonGiatf;

    @FXML
    private TableColumn<?, ?> HanNopCol;

    @FXML
    private DatePicker HanNoptf;

    @FXML
    private TextField KTSear;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TextField LoaiPhitf;

    @FXML
    private TableColumn<?, ?> MaHKCol2;

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
    private TableColumn<?, ?> ThangNopCol;

    @FXML
    private TextField ThangNoptf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
