package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HoKhauUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> CCTVCol;

    @FXML
    private AnchorPane CHTablePane;

    @FXML
    private TextField HokhauSear;

    @FXML
    private TableColumn<?, ?> NSinhTVCol;

    @FXML
    private TableColumn<?, ?> QheTVCol;

    @FXML
    private TableColumn<?, ?> SdtTVCol;

    @FXML
    private TableColumn<?, ?> TVangCol;

    @FXML
    private TableColumn<?, ?> TenTVCol;

    @FXML
    private TableView<?> ThanhVienTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
