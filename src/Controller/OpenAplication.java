package Controller;

import Model.CheckingData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OpenAplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        // TODO: Add your code here
        // 3. Layout -> Layout;
        Scene scene = new Scene(new VBox(), Color.gray(0.2));
        /*
         * KODE SPLASH SCREEN
         * 
         * 
         */
        primaryStage.setScene(scene);
        CheckingData checkingData = new CheckingData();
        int count = checkingData.Checkdata();
        SceneController sceneController = new SceneController(primaryStage);
        if (count == 0) {
            sceneController.switchToRegistration();
        } else {
            sceneController.switchToLogin();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        /*
         * Tree di java terbagi menjadi beberapa
         * 1. Stage -> Window
         * 2. Scene -> Content
         * 3. Layout -> Layout
         */
    }

}
