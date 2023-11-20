package Controller;

import Model.DatabaseCheckService;
import View.Dashboard.DashboardPage;
import View.Dashboard.TanamUangPage;
import View.Login_Register.LoginPage;
import View.Login_Register.RegistrationPage;
import View.Splash_Screen.SplashScreen;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// class SceneController digunakan untuk mengatur perpindahan scene
public class SceneController {
    private Stage stage; // Deklarasi property stage

    public SceneController(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        StackPane root = new StackPane(); // Instansiasi StackPane ke dalam variabel root
        stage.setFullScreen(false); // Set fullscreen menjadi false sehingga tidak fullscreen
        Scene scene = new Scene(root); // Membuat Scene baru dengan simpul akar
        stage.setWidth(1000); // Mengatur lebar panggung menjadi 1000 pixel
        stage.setHeight(500); // Mengatur tinggi panggung menjadi 500 pixel
        stage.setScene(scene); // Mengatur scene ke dalam stage
    }

    /* LOGIN_REGISTRATION */
    // Registration
    public void switchToRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(this.stage); // Instansiasi class RegistrationPage ke
                                                                              // dalam variabel registrationPage
        registrationPage.start(); // Menjalankan method start() pada class RegistrationPage
    }

    // Login
    public void switchToLogin() {
        LoginPage loginPage = new LoginPage(this.stage); // Instansiasi class LoginPage
        loginPage.start(); // Menjalankan method start() pada object loginPage
    }

    // Dashboard
    public void switchToDashboard() {
        DashboardPage dashboard = new DashboardPage(this.stage); // Instansiasi class DashboardPage ke dalam variable
                                                                 // dashboard
        dashboard.start(); // Menjalankan method start pada objek dashboard
    }

    // Tanam Uang
    public void switchToTanamUang() {
        TanamUangPage tanamUang = new TanamUangPage(this.stage);
        tanamUang.start();
    }

}

class Splash {
    private Stage stage; // Deklarasi pero

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
