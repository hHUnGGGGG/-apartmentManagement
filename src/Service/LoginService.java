package Service;

import Run.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginService {
    public void DangKi(AnchorPane AP) {
        Stage dangKi = new Stage();
        dangKi.initModality(Modality.APPLICATION_MODAL);// bắt người dùng phải bấm ok hoặc cancel để trở về
        dangKi.setTitle("Đăng kí tài khoản mới");

        Label labelTaiKhoan = new Label("Tài khoản");
        Label labelMatKhau = new Label("Mật khẩu");
        Label labelNhapLaiMatKhau = new Label("Nhập lại mật khẩu");

        TextField textTaiKhoan = new TextField();
        PasswordField textMatKhau = new PasswordField();
        PasswordField textNhapLaiMatKhau = new PasswordField();

        // Tạo TextField để hiển thị mật khẩu
        TextField textField = new TextField();
        textField.setVisible(false); // Ẩn TextField ban đầu
        textField.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        GridPane grid = new GridPane();

        // Tạo ToggleButton để hiển thị mật khẩu
        ToggleButton toggleButton = hienThiMatKhau(grid, textMatKhau, 1, 2);
        ToggleButton toggleButton1 = hienThiMatKhau(grid, textNhapLaiMatKhau, 1, 4);

        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        okButton.setOnAction(e -> checkDangKi(textTaiKhoan.getText(), textMatKhau.getText(), textNhapLaiMatKhau.getText()));

        cancelButton.setOnAction(e -> dangKi.close());

        grid.add(labelTaiKhoan, 0 , 0);
        grid.add(textTaiKhoan, 1, 0);
        grid.add(labelMatKhau, 0, 2);
        grid.add(textMatKhau, 1, 2);
        grid.add(toggleButton, 2, 2);
        grid.add(labelNhapLaiMatKhau, 0 , 4);
        grid.add(toggleButton1, 2, 4);
        grid.add(textNhapLaiMatKhau, 1, 4);
        grid.add(okButton, 0, 5);
        grid.add(cancelButton, 1, 5);

        Scene createCardScene = new Scene(grid, 300, 300);
        dangKi.setScene(createCardScene);
        dangKi.showAndWait();
    }

    public boolean CheckLogin(String taiKhoan, String matKhau) {
        //Kiem tra tai khoan
        //Kiem tra mat khau
         return false;
    }

    public void load(Button login) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(""));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Thay đổi scene
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            newStage.setTitle("");
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(true);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkDangKi(String taiKhoan, String matKhau, String nhacLaiMatKhau) {
        //Kiem tra xem tai khoan mat khau co hop le k(regular expression)
        if(taiKhoan.matches("[A-Za-z1-9]+") && matKhau.equals(nhacLaiMatKhau)) {//Nếu tài khoản chưa tồn tại trong CSDL và phải đúng cú pháp thì tạo k thì k tạo

        }

        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("không thể tạo tài khoản");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản đã tồn tại hoặc không đúng kí tự");

            // Hiển thị hộp thoại
            alert.showAndWait();
        }
    }

    public ToggleButton hienThiMatKhau(GridPane grid, PasswordField textMatKhau, int i, int j) {
        TextField textField = new TextField();
        textField.setVisible(false); // Ẩn TextField ban đầu
        textField.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        ToggleButton toggleButton = new ToggleButton("Show Password");
        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                // Hiển thị mật khẩu
                grid.getChildren().remove(textMatKhau);
                textField.setVisible(true);
                textField.setText(textMatKhau.getText());
                textField.setEditable(false);
                grid.add(textField, i, j);
            } else {
                // Hiển thị mật khẩu
                grid.getChildren().remove(textField);
                textField.setVisible(false);
                textMatKhau.setText(textField.getText());
                grid.add(textMatKhau, i, j);
            }
        });
        return toggleButton;
    }
}
