package Controller;

import Models.HoKhauModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HoKhauController {
    @FXML
    TableColumn<HoKhauModel, String> colMaHoKhau;
    @FXML
    TableColumn<HoKhauModel, String> colMaChuHo;
    @FXML
    TableColumn<HoKhauModel, String> colSoThanhVien;
    @FXML
    TableColumn<HoKhauModel, String> colDiaChi;
    @FXML
    TableView<HoKhauModel> tvHoKhau;
    @FXML
    TextField tfSearch;
    @FXML
    ComboBox<String> cbChooseSearch;

    ObservableList<HoKhauModel> ListValueTableView;
    private List<HoKhauModel> listHoKhau;

    public void showHoKhau() throws ClassNotFoundException,SQLException {

    }

    public void addHoKhau() throws ClassNotFoundException, SQLException {

    }

    public void delHoKhau() throws ClassNotFoundException, SQLException {

    }

    public void searchHoKhau() throws ClassNotFoundException, SQLException {

    }

    public void updateHoKhau() throws ClassNotFoundException, SQLException {

    }

    public void initialize(URL url, ResourceBundle rb) {

    }

}
