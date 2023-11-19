package Controller;

import View.Dashboard.DashboardPage;
import View.Dashboard.TanamUangPage;
import View.Login_Register.LoginPage;
import View.Login_Register.RegistrationPage;
import View.Splash_Screen.SplashScreen;
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    /* LOGIN_REGISTRATION */
    // Registration
    public void switchToRegistration() {
        RegistrationPage registration_form = new RegistrationPage(this.stage);
        // start method
        registration_form.start();
    }

    // Login
    public void switchToLogin() {
        LoginPage login_form = new LoginPage(this.stage);
        login_form.start();
    }

    // Dashboard
    public void switchToDashboard() {
        DashboardPage dashboard = new DashboardPage(this.stage);
        dashboard.start();
    }

    // Tanam Uang
    public void switchToTanamUang() {
        TanamUangPage tanamUang = new TanamUangPage(this.stage);
        tanamUang.start();
    }

    /* SPLASH SCREEN */
    public void switchToSplashScreen() {
        SplashScreen splashScreen = new SplashScreen(this.stage);
        splashScreen.start();
    }

}
