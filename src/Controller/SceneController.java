package Controller;

import View.Login_Register.Login_form;
import View.Login_Register.Registration_form;
import View.Splash_Screen.SplashScreen;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneController {
    private VBox root;
    private Stage stage;
    private Scene scene;

    public SceneController(Stage stage) {
        this.stage = stage;
    }
    /* LOGIN_REGISTRATION */
    // Registration
    public void switchToRegistration() {
        Registration_form registration_form = new Registration_form(this.stage);
        // Stage registrationStage = new Stage(); // create a new Stage object
        // registration_form.start(registrationStage); // pass the Stage object to the
        // start method
        root = new VBox();
        scene = new Scene(root, 500, 500);
        registration_form.start(scene);
    }
    // Login
    public void switchToLogin() {
        Login_form login_form = new Login_form(this.stage);
        // Stage loginStage = new Stage(); // create a new Stage object
        // login_form.start(loginStage); // pass the Stage object to the start method
        root = new VBox();
        scene = new Scene(root, 500, 500);
        login_form.start(scene);
    }
    
    /* SPLASH SCREEN */
    public void switchToSplashScreen() {
        SplashScreen splashScreen = new SplashScreen(this.stage);
    }

}
