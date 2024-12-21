package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PhiThuongNienController implements Initializable {

    @FXML
    private Button BtnEditPhi;

    @FXML
    private Button BtnSave;

    @FXML
    private AnchorPane EditPane;

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

    @FXML
    private TextField tfSoTien;

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource()==BtnEditPhi){
            PTNTablePane.setVisible(false);
            EditPane.setVisible(true);
        }else if (event.getSource()==BtnSave){
            PTNTablePane.setVisible(true);
            EditPane.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
