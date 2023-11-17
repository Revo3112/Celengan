package View.Login_Register;

import Controller.SceneController;
import Model.*;
import Utils.AlertHelper;
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
    private TextField fieldUsername;
    private PasswordField passwordField;

    public LoginPage(Stage stage) {
        this.stage = stage;

        // Set the application icon
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        this.stage.getIcons().add(icon);
    }

    public void start() {
        this.fieldUsername = new TextField(); // Tambahkan ini
        this.passwordField = new PasswordField(); // Tambahkan ini

        // Components
        Text title = new Text();
        title.setText("Please Login");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);
        title.setTranslateY(-40);

        fieldUsername.setMaxWidth(200);
        fieldUsername.setTranslateX(200);

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
        btnLogin.setOnAction(e -> handleLogin(fieldUsername.getText(), passwordField.getText()));
    }

    private void handleLogin(String username, String password) {
        LoginModel login = new LoginModel();
        SceneController sceneController = new SceneController(this.stage);

        if (login.isValidated(username, password, statusCheckbox)) {
            sceneController.switchToDashboard();
        } else if (username.isEmpty() && password.isEmpty()) {
            applyErrorStyle(fieldUsername, passwordField);
        } else if (username.isEmpty()) {
            passwordField.setStyle("");
            applyErrorStyle(fieldUsername);
        } else if (password.isEmpty()) {
            fieldUsername.setStyle("");
            applyErrorStyle(passwordField);
        } else {
            fieldUsername.setStyle("");
            passwordField.setStyle("");
            AlertHelper.alert("Username or password is incorrect.");
        }

    }

    private void applyErrorStyle(TextField... fields2) {
        for (TextField field : fields2) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}
