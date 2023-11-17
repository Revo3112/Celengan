package View.Login_Register;

import Controller.SceneController;
import Model.*;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage {
    private Stage stage;
    private boolean statusCheckbox;

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
        fieldUsername.setTranslateX(200);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);
        passwordField.setTranslateX(200);
        passwordField.setTranslateY(40);

        Label labelUsername = new Label("Username:");
        labelUsername.setTranslateX(-50);
        Label labelPassword = new Label("Password:");
        labelPassword.setTranslateX(-50);
        labelPassword.setTranslateY(40);

        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setTranslateX(280);
        registerLink.setTranslateY(80);
        registerLink.setStyle(
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana");
        registerLink.setOnAction(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToRegistration();
        });

        CheckBox checkbox = new CheckBox("Remember Me");
        checkbox.setTranslateX(-30);
        checkbox.setTranslateY(80);
        checkbox.setOnAction(e -> {
            if (checkbox.isSelected()) {
                this.statusCheckbox = true;
            } else {
                this.statusCheckbox = false;
            }
        });

        Button btnLogin = new Button("Login");
        btnLogin.setTranslateY(120);

        // Scene
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, passwordField, registerLink,
                checkbox,
                btnLogin);

        Scene scene = new Scene(root, Color.gray(0.2));
        // stage.setFullScreen(true);
        this.stage.setTitle("Register");
        this.stage.setScene(scene);
        this.stage.show();

        // Handle button click
        btnLogin.setOnAction(e -> {

            String username = fieldUsername.getText();
            String password = passwordField.getText();

            LoginModel login = new LoginModel();
            SceneController sceneController = new SceneController(this.stage);

            if (login.isValidated(username, password, statusCheckbox)) {
                sceneController.switchToDashboard();
            } else {
                sceneController.switchToRegistration();
            }

        });
    }
}
