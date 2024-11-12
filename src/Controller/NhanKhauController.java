package Controller;

import Models.NhanKhauModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NhanKhauController {
    @FXML
    private TableColumn<NhanKhauModel, String> colMaNhanKhau;
    @FXML
    private TableColumn<NhanKhauModel, String> colTen;
    @FXML
    private TableColumn<NhanKhauModel, String> colTuoi;
    @FXML
    private TableColumn<NhanKhauModel, String> colCMND;
    @FXML
    private TableColumn<NhanKhauModel, String> colSDT;
    @FXML
    private TableColumn<NhanKhauModel, String> colMaHo;
    @FXML
    private TableView<NhanKhauModel> tvNhanKhau;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> cbChooseSearch;

    private ObservableList<NhanKhauModel> listValueTableView;
    private List<NhanKhauModel> listNhanKhau;

    public TableView<NhanKhauModel> getTvNhanKhau() {
        return tvNhanKhau;
    }

    public void showNhanKhau() {

    }

    public void searchNhanKhau() {

    }

    public void addNhanKhau() {

    }

    public void delNhanKhau() {

    }

    public void updateNhanKhau() {

    }

    public void initialize(URL url, ResourceBundle rb) {

    }
}
