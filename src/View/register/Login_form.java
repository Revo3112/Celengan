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
    public Stage stage;

    public void start(Stage primaryStage) {
        // Buat scene dengan ukuran fleksibel
        Scene scene = new Scene(new VBox(), Color.gray(0.2));

        // Text
        Text text = new Text();
        text.setText("Please Login");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Pindah Register");

        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/Icons/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        // primaryStage.setFullScreen(true);

        // Scene
        VBox root = new VBox();
        scene.setRoot(root);
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1);
        VBox.setMargin(button1, new Insets(20, 0, 0, scene.getWidth() / 2));

        button1.setOnAction(e -> {
            SceneController sceneController = new SceneController();
            primaryStage.close();
            root.getChildren().clear();
            sceneController.switchToRegistration();
        });

        this.stage = primaryStage;
        // Tampilkan scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public Parent getRoot() {
        System.out.println(stage.getScene().getRoot());
        return stage.getScene().getRoot();
    }

    public Scene getScene() {
        return stage.getScene();
    }

}
