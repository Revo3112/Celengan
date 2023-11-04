package Controller;

import Model.CheckingData;
import javafx.application.Application;
import javafx.stage.Stage;

public class OpenAplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        // TODO: Add your code here
        // 3. Layout -> Layout;
        CheckingData checkingData = new CheckingData();
        int count = checkingData.Checkdata();
        if (count == 0) {
            SceneController sceneController = new SceneController();
            sceneController.switchToRegistration();
        } else {
            SceneController sceneController = new SceneController();
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
