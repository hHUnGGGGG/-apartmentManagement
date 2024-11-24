package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
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
    private TableColumn<?, ?> CCCol;

    @FXML
    private TableColumn<?, ?> MaNKCol;

    @FXML
    private TableColumn<?, ?> MaNopCol1;

    @FXML
    private TableColumn<?, ?> NKAgeCol;

    @FXML
    private TableColumn<?, ?> NKNameCol;

    @FXML
    private TableColumn<?, ?> NKPhoneCol;

    @FXML
    private TextField NKSear;

    @FXML
    private TextField PhoneText;

    @FXML
    private TextField tfCCCD;

    @FXML
    private Label tfMaHK;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
