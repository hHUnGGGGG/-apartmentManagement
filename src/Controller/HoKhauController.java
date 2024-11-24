package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HoKhauController implements Initializable {

    @FXML
    private Button BtnAddHK;

    @FXML
    private Button BtnDltHK;

    @FXML
    private Button BtnEditHK;

    @FXML
    private TextField HokhauSear;

    @FXML
    private TableColumn<?, ?> MaCHCol;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TextField tfMaChuHo;

    @FXML
    private TextField tfMaHoKhau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
