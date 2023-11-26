package Controller;

import Model.DatabaseCheckService;
import Model.KategoriModel;
import View.Dashboard.DashboardPage;
import View.Dashboard.TanamUangPage;
import View.Login_Register.LoginPage;
import View.Login_Register.RegistrationPage;
import View.Splash_Screen.SplashScreen;
import javafx.stage.Stage;

// class SceneController digunakan untuk mengatur perpindahan scene
public class SceneController {
    private Stage stage; // Deklarasi property stage

    public SceneController(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        stage.setFullScreen(false); // Set fullscreen menjadi false sehingga tidak fullscreen
        stage.setWidth(1000); // Mengatur lebar panggung menjadi 1000 pixel
        stage.setHeight(500); // Mengatur tinggi panggung menjadi 500 pixel
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
        // menentukan apakah akan langsung ke dashboard atau ke login
        loginPage.penentuApakahStartAtauLangsungDashboard();
    }

    // Dashboard
    public void switchToDashboard() {
        DashboardPage dashboard = new DashboardPage(this.stage); // Instansiasi class DashboardPage ke dalam variable
                                                                 // dashboard
        dashboard.start(); // Menjalankan method start pada objek dashboard
    }

    // Tanam Uang
    public void switchToTanamUang() {
        TanamUangPage tanamUang = new TanamUangPage(this.stage); // Instansiasi class TanamUangPage ke dalam variable
                                                                 // tanamUang
        tanamUang.start(KategoriModel.userKategoriPemasukan()); // Menjalankan method start pada objek tanamUang
    }

}

class Splash {
    private Stage stage; // Deklarasi property stage
    public static int finalValue; // Deklarasi property finalValue dengan nilai 0
    public static boolean condition = false; // Deklarasi property condition dengan nilai true

    // Melakukan inisiasi class Splash dengan parameter stage
    public Splash(Stage stage) {
        this.stage = stage;
    }

    /* SPLASH SCREEN */
    public void switchToSplashScreen() {

        // DatabaseCheckService ke dalam
        // variabel databaseCheckService
        SplashScreen splashScreen = new SplashScreen(stage); // Instansiasi class SplashScreen ke dalam variabel
        // splashScreen
        System.out.println("Splash Screen Above");
        splashScreen.start(finalValue); // Menjalankan method start() pada class SplashScreen

        System.out.println("FInal Value = " + finalValue);
        System.out.println("Condition = " + condition);
        // Loading load = new Loading();
        // Menjalankan operasi pengecekan database di backgroun

    }
}
