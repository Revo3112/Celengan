package View.register;

import Controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
        Image icon = new Image("Assets/Icons/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        stage.getIcons().add(icon);
    }

    public void start(Scene scene2) {
        if (stage == null) {
            // Handle the case where the stage is null
            return;
        }

        // Create the scene
        Scene scene = new Scene(new VBox(), Color.gray(0.2));

        // Text
        Text text = new Text();
        text.setText("Please Login");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        // Add button to the scene
        Button button1 = new Button("Pindah Register");

        // Set the scene
        VBox root = new VBox();
        scene.setRoot(root);
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1);
        VBox.setMargin(button1, new Insets(20, 0, 0, 0));

        // Handle button click
        button1.setOnAction(e -> {
            SceneController sceneController = new SceneController(stage);
            sceneController.switchToRegistration();
        });

        // Show the scene
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public Parent getRoot() {
        if (stage == null || stage.getScene() == null) {
            return null;
        }
        return stage.getScene().getRoot();
    }

    public Scene getScene() {
        return stage.getScene();
    }
}
