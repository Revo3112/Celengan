package View.Login_Register;

import Controller.SceneController;
import Model.*;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage {
    private Stage stage;

    public LoginPage(Stage stage) {
        this.stage = stage;

        // Set the application icon
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        this.stage.getIcons().add(icon);
    }

    public void start() {

        // Components
        Text title = new Text();
        title.setText("Please Login");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);
        title.setTranslateY(-40);

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

        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setTranslateX(-50);
        registerLink.setTranslateY(80);
        registerLink.setStyle(
            "-fx-underline: true;" + 
            "-fx-font-family: Verdana"
        );
        registerLink.setOnAction(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToRegistration();
        });

        Button btnLogin = new Button("Login");
        btnLogin.setTranslateY(120);

        // Scene
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, fieldPassword, registerLink, btnLogin);

        Scene scene = new Scene(root, Color.gray(0.2));
        // stage.setFullScreen(true);
        this.stage.setTitle("Register");
        this.stage.setScene(scene);
        this.stage.show();

        // Handle button click
        btnLogin.setOnAction(e -> {

            String username = fieldUsername.getText();
            String password = fieldPassword.getText();
            
            LoginModel login = new LoginModel();
            SceneController sceneController = new SceneController(this.stage);

            if (login.isValidated(username, password)) {
                sceneController.switchToDashboard();
            } else {
                sceneController.switchToRegistration();
            }

        });
    }
}
