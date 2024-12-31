package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PhiThuongNienUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> LoaiPhiCol;

    @FXML
    private TableColumn<?, ?> MaPhiCol;

    @FXML
    private TextField PTNSear;

    @FXML
    private TableView<?> PTNTable;

    @FXML
    private AnchorPane PTNTablePane;

    @FXML
    private TableColumn<?, ?> SoTienCol;

    @FXML
    private TableColumn<?, ?> TenPhiCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
