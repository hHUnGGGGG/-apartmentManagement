package Service;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DangKiService {
    private void setAlert(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public void CheckAcc(String sdt) throws SQLException {
        TruyVanDataBaseService truyVan = new TruyVanDataBaseService();
        ResultSet resultSet = null;
        resultSet = truyVan.TruyVanDatabase("SELECT TENTAIKHOAN FROM TAIKHOAN");
        while(resultSet.next()) {
            System.out.println("Mã chủ hộ: " + resultSet.getInt("TENTAIKHOAN"));
        }
    }


}
