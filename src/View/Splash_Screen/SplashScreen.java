package View.Splash_Screen;

import java.io.IOException;

/* MAIN */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

/* FXML LOADER */
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

/* GEOMETRY */

/* COLOR & SHAPE */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* FONT & TEXT */
import javafx.scene.text.Text;

/* STAGE */
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/* IMAGE */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashScreen {
    Stage stage;
    Parent root;

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        try {
            root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));

            double rectArcWidth = 60;
            double rectArcHeight = 60;

            Rectangle outBackground = new Rectangle(950, 600);
            outBackground.setArcWidth(rectArcWidth);
            outBackground.setArcHeight(rectArcHeight);
            outBackground.setFill(Color.WHITE);

            StackPane background = new StackPane(outBackground);

            Scene scene = new Scene(background, Color.TRANSPARENT);

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
