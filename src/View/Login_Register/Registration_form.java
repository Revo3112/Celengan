package View.Login_Register;

import Controller.SceneController;
// import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        text.setTranslateY(-40);

        // Tambahkan button ke dalam scene
        Button but_regis = new Button("Register");
        but_regis.setTranslateY(120);

        // Textfield username
        TextField fieldUsername = new TextField();
        fieldUsername.setMaxWidth(200);
        fieldUsername.setTranslateX(90);
        // Textfield password
        TextField fieldPassword = new TextField();
        fieldPassword.setMaxWidth(200);
        fieldPassword.setTranslateX(90);
        fieldPassword.setTranslateY(40);
        // Textfield reinput password
        TextField fieldReinputPassword = new TextField();
        fieldReinputPassword.setMaxWidth(200);
        fieldReinputPassword.setTranslateX(90);
        fieldReinputPassword.setTranslateY(80);

        // Label username
        Label labelUsername = new Label("Username:");
        labelUsername.setTranslateX(-50);
        // Label password
        Label labelPassword = new Label("Password:");
        labelPassword.setTranslateX(-50);
        labelPassword.setTranslateY(40);
        // Label reinput password
        Label labelReinputPassword = new Label("Reinput Password:");
        labelReinputPassword.setTranslateX(-70);
        labelReinputPassword.setTranslateY(80);

        // Scene
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(text, but_regis, labelUsername, fieldUsername, labelPassword, fieldPassword,
                fieldReinputPassword, labelReinputPassword);

        Scene scene = new Scene(root, Color.gray(0.2));
        scene.setRoot(root);

        // Tampilkan scene
        stage.setScene(scene);
        // stage.setFullScreen(true);
        stage.setTitle("Register");
        stage.show();

        but_regis.setOnAction(e -> {
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
