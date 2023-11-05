package View.Login_Register;

import Controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
// import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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

        // Components
        Text title = new Text();
        title.setText("Please Login");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);
        title.setTranslateY(-40);

        Button btnLogin = new Button("Login");
        btnLogin.setTranslateY(80);

        TextField fieldUsername = new TextField();
        fieldUsername.setMaxWidth(200);
        fieldUsername.setTranslateX(90);
        TextField fieldPassword = new TextField();
        fieldPassword.setMaxWidth(200);
        fieldPassword.setTranslateX(90);
        fieldPassword.setTranslateY(40);

        Label labelUsername = new Label("Username:");
        labelUsername.setTranslateX(-50);
        Label labelPassword = new Label("Password:");
        labelPassword.setTranslateX(-50);
        labelPassword.setTranslateY(40);

        // Scene
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, fieldPassword, btnLogin);

        Scene scene = new Scene(root, Color.gray(0.2));
        stage.setScene(scene);
        // stage.setFullScreen(true);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();

        // Handle button click
        btnLogin.setOnAction(e -> {
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
