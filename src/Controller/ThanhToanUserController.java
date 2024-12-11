package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ThanhToanUserController implements Initializable {

    @FXML
    private Button BtnCf;

    @FXML
    private TableColumn<?, ?> DonGiaCol;

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TableColumn<?, ?> MaHK3Col;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TableColumn<?, ?> NgayNopCol;

    @FXML
    private TextField NopSear;

    @FXML
    private TableColumn<?, ?> TThaiCol;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @FXML
    private TableColumn<?, ?> TenCHCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
