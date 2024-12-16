package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class XeUserController implements Initializable {

    @FXML
    private TableColumn<?, ?> BKSCol;

    @FXML
    private TableColumn<?, ?> LXeCol;

    @FXML
    private TextField XeSear;

    @FXML
    private TableView<?> XeTable;

    @FXML
    private AnchorPane XeTablePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
