package View.Login_Register;

import Controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
// import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_form {
    private Stage stage;

    public Login_form(Stage stage) {
        this.stage = stage;

        // Set the application icon
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        stage.getIcons().add(icon);
    }

    public void start() {

        // Text
        Text text = new Text();
        text.setText("Please Login");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Login");

        // Scene
        VBox root = new VBox();
        root.getChildren().add(text);
        root.getChildren().addAll(button1);
        root.setAlignment(Pos.CENTER);
        // Buat scene dengan ukuran fleksibel
        Scene scene = new Scene(root, Color.gray(0.2));
        scene.setRoot(root);

        stage.setScene(scene);
        // stage.setFullScreen(true);

        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
        root.setPrefWidth(stage.getWidth());

        // Handle button click
        button1.setOnAction(e -> {
            SceneController sceneController = new SceneController(stage);
            sceneController.switchToRegistration();
        });
    }

    // public Parent getRoot() {
    // if (stage == null || stage.getScene() == null) {
    // return null;
    // }
    // return stage.getScene().getRoot();
    // }

    // public Scene getScene() {
    // return stage.getScene();
    // }
}
