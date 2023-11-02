package View.register;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

/**
 * import javafx.scene.Group;
 * import javafx.scene.Node;
 * register
 */

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Buat scene dengan ukuran fleksibel
        Scene scene = new Scene(new VBox(), 400, 400);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Register");
        VBox vBox = new VBox();

        vBox.getChildren().addAll(button1);
        VBox.setMargin(button1, new Insets(20, 0, 0, scene.getWidth() / 2));
        scene.setRoot(vBox);

        // Tampilkan scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        primaryStage.show();

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
