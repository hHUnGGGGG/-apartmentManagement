package Run;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
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

        //okButton.setOnAction(e -> checkDangKi(textTaiKhoan.getText(), textMatKhau.getText()));

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

    public static void main(String[] args) {
        launch(args);
    }
}