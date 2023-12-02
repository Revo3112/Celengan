package Controller;

import Model.DatabaseCheckService;
import Model.RequestNewPassword;
import View.Dashboard.DashboardPage;
import View.Login_Register.LoginPage;
import View.Login_Register.PenentuSaldo;
import View.Login_Register.RegistrationPage;
import View.Login_Register.RequestNewPass;
import View.PanenUang.PanenUang;
import View.Splash_Screen.SplashScreen;
import javafx.stage.Stage;
import Model.TanamUangModel;
import View.Dashboard.TanamUangPage;

// class SceneController digunakan untuk mengatur perpindahan scene
public class SceneController {
    private Stage stage; // Deklarasi property stage

    public SceneController(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        stage.setFullScreen(false); // Set fullscreen menjadi false sehingga tidak fullscreen
        stage.setWidth(1000); // Mengatur lebar panggung menjadi 1000 pixel
        stage.setHeight(500); // Mengatur tinggi panggung menjadi 500 pixel
        // stage.show(); // Menampilkan panggung
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
        System.out.println("Dashboard");
        dashboard.start(); // Menjalankan method start pada objek dashboard
    }

    // Tanam Uang
    public void switchToTanamUang() {
        TanamUangPage tanamUang = new TanamUangPage(this.stage); // Instansiasi class
                                                                 // TanamUangPage ke dalam
                                                                 // variable
        // tanamUang
        tanamUang.start(TanamUangModel.getKategoriPemasukan(), TanamUangModel.getKategoriPengeluaran()); // Menjalankan
                                                                                                         // method start
                                                                                                         // pada objek
                                                                                                         // tanamUang
    }

    // Request New Password
    public void switchToRequestNewPassword() {
        RequestNewPass requestNewPassword = new RequestNewPass(this.stage); // Instansiasi class
                                                                            // RequestNewPassword ke dalam
                                                                            // variabel requestNewPassword
        requestNewPassword.start(); // Menjalankan method start() pada class RequestNewPassword
    }

    // Buat saldo dan mode kritis
    public void switchToBuatSaldoDanModeKritis() {
        PenentuSaldo penentuSaldo = new PenentuSaldo(this.stage); // Instansiasi class PenentuSaldo ke dalam variabel
                                                                  // penentuSaldo
        penentuSaldo.penentuSaldo(); // Menjalankan method penentuSaldo() pada class PenentuSaldo
    }

    // Menambahkan Target
    public void switchToPanenUang() {
        PanenUang tambahTarget = new PanenUang(this.stage); // Instansiasi class TambahTarget ke dalam variabel
                                                            // tambahTarget
        tambahTarget.start(); // Menjalankan method start() pada class TambahTarget
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
        // Instansiasi class SplashScreen ke dalam variabel splashScreen
        SplashScreen splashScreen = new SplashScreen(stage);
        // splashScreen
        splashScreen.start(finalValue); // Menjalankan method start() pada class SplashScreen
    }
}
