package Controller;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import View.register.Login_form;
import View.register.Registration_form;

public class SceneController {
    private VBox root;
    private Stage stage;
    private Scene scene;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void switchToRegistration() {
        Registration_form registration_form = new Registration_form(stage);
        // Stage registrationStage = new Stage(); // create a new Stage object
        // registration_form.start(registrationStage); // pass the Stage object to the
        // start method
        root = new VBox();
        scene = new Scene(root, 1000, 500);
        registration_form.start(scene);
    }

    public void switchToLogin() {
        Login_form login_form = new Login_form(stage);
        // Stage loginStage = new Stage(); // create a new Stage object
        // login_form.start(loginStage); // pass the Stage object to the start method
        root = new VBox();
        scene = new Scene(root, 50, 50);
        login_form.start(scene);
    }

}
