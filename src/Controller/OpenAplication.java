package Controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class OpenAplication extends Application {
    private Stage secondaryStage; // Property untuk menampung stage kedua

    public void start(Stage primaryStage) throws Exception {
        this.secondaryStage = new Stage(); // Instansiasi class Stage ke dalam property secondaryStage
        // Menampilkan stage pertama
        Splash sceneController = new Splash(this.secondaryStage); // Instansiasi class Splash ke dalam variabel
        // sceneController
        sceneController.switchToSplashScreen(); // Menjalankan static method switchToSplashScreen()
    }

    public static void main(String[] args) throws Exception {
        launch(args); // Menjalankan program JavaFX
    }
}
