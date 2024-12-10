package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private Label MaHKlb;

    @FXML
    private TextField PhoneText;

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
    private TableView<?> ThanhVienTable;

    @FXML
    private TextField TenCHtf;

    @FXML
    private TextField cccdCHtf;

    @FXML
    private TextField tfCCCD;

    @FXML
    private Label tfMaHK;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfTen;

    public void switchForm(ActionEvent event){
        if (event.getSource()==BtnAddHK || event.getSource()==BtnEditHK || event.getSource()==BtnDltHK){
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

    }
}
