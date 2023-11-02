package Controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.register.Login_form;
import View.register.Registration_form;

public class SceneController {
    private Stage root;
    private Scene scene;

    public SceneController(Stage stage, Scene scene) {
        this.root = root;
        this.scene = scene;
    }

    public void switchToRegistration() {
        Registration_form registration_form = new Registration_form();
        Stage registrationStage = new Stage(); // create a new Stage object
        registration_form.start(registrationStage); // pass the Stage object to the start method
        Parent registrationRoot = registration_form.getRoot();
        Scene registrationScene = new Scene(registrationRoot, 500, 500);
        registrationStage.setScene(registrationScene);
    }

    public void switchToLogin() {
        Login_form login_form = new Login_form();
        Stage loginStage = new Stage(); // create a new Stage object
        login_form.start(loginStage); // pass the Stage object to the start method
        Parent loginRoot = login_form.getRoot();
        Scene loginScene = new Scene(loginRoot, 500, 500);
        loginStage.setScene(loginScene);
    }
}
