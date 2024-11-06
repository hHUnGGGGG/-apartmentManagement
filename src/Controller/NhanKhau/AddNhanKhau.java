package Controller.NhanKhau;

import Models.NhanKhauModel;
import Service.NhanKhauService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class AddNhanKhau {
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfTen;
    @FXML
    private TextField tfTuoi;
    @FXML
    private TextField tfCmnd;
    @FXML
    private TextField tfSdt;
    @FXML
    private TextField tfMaHoKhau;
    @FXML
    private TextField tfQuanhe;
    @FXML
    private CheckBox cboxChuHo;

    public void AddNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
        //Khai bao bien de so sanh
        Pattern pattern;

        //Kiem tra id nhap vao
        //id la day so tu 1 den 11 chu so
        pattern = Pattern.compile("\\d{1,11}");
        if(!pattern.matcher(tfId.getText()).matches()){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Hãy nhập mã nhân khẩu hợp lệ!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        //Kiem tra ID them moi co trung voi ID da ton tai hay khong
        List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();


    }

}
