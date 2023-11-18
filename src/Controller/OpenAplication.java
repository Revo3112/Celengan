package Controller;

import Model.LoginModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OpenAplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        // TODO: Add your code here
        // 3. Layout -> Layout;
        // VBox root = new VBox(); // (!) affect splash screen
        // Scene scene = new Scene(root, Color.gray(0.2)); // (!) affect splash screen
        /*
         * KODE SPLASH SCREEN
         * 
         * 
         */
        // primaryStage.setScene(scene); // (!) affect splash screen
        // primaryStage.setHeight(500); // (!) affect splash screen
        // primaryStage.setWidth(1000); // (!) affect splash screen
        // LoginModel checkingData = new LoginModel();
        // int count = checkingData.checkData();
        SceneController sceneController = new SceneController(primaryStage);
        // SPLASH SCREEN (UPDATED)
        sceneController.switchToSplashScreen();
        // if (count == 0) {
        // sceneController.switchToRegistration();
        // } else {
        // sceneController.switchToLogin();
        // }
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
