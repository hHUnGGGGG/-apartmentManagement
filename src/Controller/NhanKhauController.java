package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
    private Button BtnAdd;

    @FXML
    private Button BtnSave;

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
    private TableColumn<?, ?> TVangCol;

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

    @FXML
    private CheckBox TVangCheck;

    @FXML
    private AnchorPane NKTablePane;

    @FXML
    private AnchorPane AddPane;

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

    }
}
