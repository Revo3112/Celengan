package View.Login_Register;

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
        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        stage.getIcons().add(icon);
    }

    public void start() {
        // Buat scene dengan ukuran fleksibel

        // Text
        Text text = new Text();
        text.setText("Please Register");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Register");

        // Scene
        VBox root = new VBox();
        root.getChildren().add(text);
        root.getChildren().addAll(button1);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, Color.gray(0.2));
        scene.setRoot(root);

        // Tampilkan scene
        stage.setScene(scene);
        // stage.setFullScreen(true);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();

        button1.setOnAction(e -> {
            // stage and scene
            SceneController sceneController = new SceneController(stage);
            sceneController.switchToLogin();
        });
    }

    // public Parent getRoot() {
    // System.out.println(stage.getScene().getRoot());
    // return stage.getScene().getRoot();
    // }

    // public Scene getScene() {
    // return stage.getScene();
    // }

}
