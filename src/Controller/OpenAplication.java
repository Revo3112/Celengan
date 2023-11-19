package Controller;

import Model.LoginModel;
import View.Splash_Screen.SplashScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import Model.DatabaseCheckService;

public class OpenAplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        // TODO: Add your code here
        SceneController sceneController = new SceneController(primaryStage);
        DatabaseCheckService databaseCheckService = new DatabaseCheckService();
        sceneController.switchToSplashScreen();

        // Membuat Timeline untuk mengatur durasi animasi splash screen
        Timeline splashScreenTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // Menutup splash screen setelah animasi selesai
            SplashScreen splashScreen = new SplashScreen(new Stage());
            splashScreen.hideSplashScreen();
        }));
        splashScreenTimeline.play();

        databaseCheckService.setOnSucceeded(e -> {
            int count = databaseCheckService.getValue();
            // Menentukan tampilan berikutnya berdasarkan hasil pengecekan
            if (count == 0) {
                sceneController.switchToRegistration();
            } else {
                sceneController.switchToLogin();
            }

            // Stop animasi splash screen setelah operasi selesai
            splashScreenTimeline.stop();
        });

        databaseCheckService.start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
