package Controller;

import Model.DatabaseCheckService;
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
        stage.setFullScreen(false);
    }

    /* LOGIN_REGISTRATION */
    // Registration
    public void switchToRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(this.stage);
        registrationPage.start();
    }

    // Login
    public void switchToLogin() {
        LoginPage loginPage = new LoginPage(this.stage);
        loginPage.start();
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

}

class Splash {
    private Stage stage;

    public Splash(Stage stage) {
        this.stage = stage;
    }

    /* SPLASH SCREEN */
    public void switchToSplashScreen() {
        DatabaseCheckService databaseCheckService = new DatabaseCheckService();
        SplashScreen splashScreen = new SplashScreen(stage);
        splashScreen.start();

        databaseCheckService.setOnSucceeded(e -> {
            SceneController mainScene = new SceneController(stage);
            int count = databaseCheckService.getValue();
            // Menentukan tampilan berikutnya berdasarkan hasil pengecekan
            if (count == 0) {
                mainScene.switchToRegistration();
            } else {
                mainScene.switchToLogin();
            }

            // Menutup splash screen setelah operasi selesai
            splashScreen.hideSplashScreen();
        });
        databaseCheckService.start();
    }
}
