package utils;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowsUtils {
    private double x, y;

    public static void DraggableAndTransparent(Stage stage, Scene scene){
        WindowsUtils windowsUtils = new WindowsUtils();
        scene.setOnMousePressed((MouseEvent event) ->{
            windowsUtils.x = event.getSceneX();
            windowsUtils.y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - windowsUtils.x);
            stage.setY(event.getScreenY() - windowsUtils.y);
            stage.setOpacity(.8);
        });

        scene.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });
        if(!stage.isShowing()){
            stage.initStyle(StageStyle.TRANSPARENT);
        }
    }
}
