package View.register;

import Controller.SceneController;
// import javafx.scene.Parent;
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

public class Registration_form {

    public Stage stage;

    public Registration_form(Stage stage) {
        this.stage = stage;
    }

    public void start(Scene scene2) {
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
        Image icon = new Image("Assets/Icons/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        stage.getIcons().add(icon);
        stage.setHeight(500);
        stage.setWidth(500);
        // primaryStage.setFullScreen(true);

        // Scene
        VBox root = new VBox();
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1);
        VBox.setMargin(button1, new Insets(20, 0, 0, scene.getWidth() / 2));

        button1.setOnAction(e -> {
            // stage and scene
            SceneController sceneController = new SceneController(stage);
            sceneController.switchToLogin();
        });

        // Tampilkan scene
        scene2.setRoot(root);
        stage.setTitle("Register");
        stage.setScene(scene2);
        stage.show();
    }

    // public Parent getRoot() {
    // System.out.println(stage.getScene().getRoot());
    // return stage.getScene().getRoot();
    // }

    // public Scene getScene() {
    // return stage.getScene();
    // }

}
