package View.Splash_Screen;

import java.io.IOException;

/* MAIN */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;

/* FXML LOADER */
import javafx.fxml.FXMLLoader;

/* STAGE */
import javafx.stage.Stage;

public class SplashScreen {
    Stage stage;
    Parent root;

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        try {
            root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        this.stage.setScene(scene);
        this.stage.show();
    }
}
