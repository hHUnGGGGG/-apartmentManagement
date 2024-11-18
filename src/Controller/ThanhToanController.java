package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ThanhToanController implements Initializable {

    @FXML
    private Button BtnAdd4;

    @FXML
    private Button BtnEdit4;

    @FXML
    private Button Btndlt4;

    @FXML
    private TableColumn<?, ?> MaHK3Col;

    @FXML
    private TextField MaHKNop;

    @FXML
    private TableColumn<?, ?> MaPhiNCol;

    @FXML
    private TextField MaPhiNop;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private TextField NopSear;

    @FXML
    private TableColumn<?, ?> PThucCol;

    @FXML
    private ChoiceBox PThucNop;

    private String[] pthuc = {"Tiền mặt","Chuyển Khoản"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean b = PThucNop.getItems().addAll(pthuc);
    }
}
