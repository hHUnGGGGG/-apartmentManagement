package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class XeController implements Initializable {

    @FXML
    private AnchorPane AddPane;

    @FXML
    private TableColumn<?, ?> BKSCol;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnAddXe;

    @FXML
    private Button BtnDltXe;

    @FXML
    private Button BtnThoat;

    @FXML
    private TableColumn<?, ?> LXeCol;

    @FXML
    private Label MaHK;

    @FXML
    private TableColumn<?, ?> MaHKCol;

    @FXML
    private TextField XeSear;

    @FXML
    private TableView<?> XeTable;

    @FXML
    private AnchorPane XeTablePane;

    @FXML
    private TextField tfBks;

    @FXML
    private TextField tfLXe;

    @FXML
    private TextField tfMaHK;

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource()==BtnAddXe){
            AddPane.setVisible(true);
            XeTablePane.setVisible(false);
        }else if (event.getSource()==BtnThoat){
            AddPane.setVisible(false);
            XeTablePane.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
