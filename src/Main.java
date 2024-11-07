import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo một nút
        Button button = new Button("Click Me!");

        // Thêm hành động cho nút khi nhấn
        button.setOnAction(event -> {
            System.out.println("Button was clicked!");
        });

        // Tạo một layout và thêm nút vào
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        // Tạo một scene
        Scene scene = new Scene(layout, 300, 200);

        // Thiết lập stage
        primaryStage.setTitle("JavaFX Button Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}