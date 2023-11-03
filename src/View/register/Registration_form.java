package View.register;

import Controller.SceneController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.*;

/**
 * import javafx.scene.Group;
 * import javafx.scene.Node;
 * register
 */

public class Registration_form extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        // Buat scene dengan ukuran fleksibel
        Scene scene = new Scene(new VBox(), Color.gray(0.2));

        // Text
        Text text = new Text();
        text.setText("Please Register");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Register");

        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/Icons/pixelart.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        // primaryStage.setFullScreen(true);

        // Scene
        VBox root = new VBox();
        scene.setRoot(root);
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1);
        VBox.setMargin(button1, new Insets(20, 0, 0, scene.getWidth() / 2));

        this.stage = primaryStage;

        button1.setOnAction(e -> {
            SceneController sceneController = new SceneController(stage, scene);
            primaryStage.close();
            sceneController.switchToLogin();

        });

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

    public Parent getRoot() {
        System.out.println(stage.getScene().getRoot());
        return stage.getScene().getRoot();
    }

}
