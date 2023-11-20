package View.Login_Register;

import Controller.SceneController;
import Model.LoginModel;
import Utils.AlertHelper;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.control.PasswordField;

public class RegistrationPage {

    private Stage stage;
    private TextField fieldUsername;
    private PasswordField fieldPassword;
    private PasswordField fieldvalidatepassword;

    public RegistrationPage(Stage stage) {
        this.stage = stage;
        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        stage.getIcons().add(icon);
    }

    public void start() {
        // Components
        Text title = new Text();
        title.setText("Please Register");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);
        title.setTranslateY(-40);

        fieldUsername = new TextField();
        fieldUsername.setMaxWidth(200);
        fieldUsername.setTranslateX(90);

        fieldPassword = new PasswordField(); // Fixed typo here
        fieldPassword.setMaxWidth(200);
        fieldPassword.setTranslateX(90);
        fieldPassword.setTranslateY(40);

        PasswordField fieldvalidatepassword = new PasswordField();
        fieldvalidatepassword.setMaxWidth(200);
        fieldvalidatepassword.setTranslateX(90);
        fieldvalidatepassword.setTranslateY(80);

        Label labelUsername = new Label("Username:");
        labelUsername.setTranslateX(-50);
        Label labelPassword = new Label("Password:");
        labelPassword.setTranslateX(-50);
        labelPassword.setTranslateY(40);
        Label labelValidatePassword = new Label("Validate Password:");
        labelValidatePassword.setTranslateX(-76);
        labelValidatePassword.setTranslateY(80);

        Hyperlink loginlink = new Hyperlink("Login");
        loginlink.setTranslateX(175d);
        loginlink.setTranslateY(106);
        loginlink.setStyle(
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana");
        loginlink.setOnAction(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToLogin();
        });

        Button btnregister = new Button("Register");
        btnregister.setTranslateY(140);

        // Scene
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, fieldPassword,
                labelValidatePassword,
                fieldvalidatepassword, loginlink,
                btnregister);

        Scene scene = new Scene(root, Color.gray(0.2));
        // stage.setFullScreen(true);
        this.stage.setTitle("Register");
        this.stage.setScene(scene);
        this.stage.show();

        // Handle button click
        btnregister.setOnAction(e -> handleregister(
                fieldUsername.getText(),
                fieldPassword.getText(),
                fieldvalidatepassword.getText()));
    }

    private void handleregister(String username, String password, String validatepassword) {
        SceneController sceneController = new SceneController(this.stage);
        LoginModel login = new LoginModel();
        String validete = validatepassword;

        if (username.isEmpty() && password.isEmpty()) {
            applyErrorStyle(fieldUsername, fieldPassword);
        } else if (username.isEmpty()) {
            fieldPassword.setStyle("");
            applyErrorStyle(fieldUsername);
        } else if (password.isEmpty()) {
            fieldUsername.setStyle("");
            applyErrorStyle(fieldPassword);
        } else {
            fieldUsername.setStyle("");
            fieldPassword.setStyle("");
            if (password.equals(validete)) {
                System.out.println("Password Match");

                if (login.registerAccount(username, password)) {
                    sceneController.switchToLogin();
                } else {
                    AlertHelper.alert(password);
                }
            } else {
                applyErrorStyle(fieldPassword, fieldvalidatepassword);
                AlertHelper.alert("Password doesn't match");
            }
        }
    }

    private void applyErrorStyle(TextField... fields2) {
        for (TextField field : fields2) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}
