package Controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class OpenAplication extends Application {
    private Stage secondaryStage;

    public void start(Stage primaryStage) throws Exception {
        this.secondaryStage = new Stage();
        // Menampilkan stage pertama
        Splash sceneController = new Splash(secondaryStage);
        sceneController.switchToSplashScreen();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
