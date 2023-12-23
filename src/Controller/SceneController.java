package Controller;

import View.Dashboard.DashboardPage;
import View.Dashboard.Features.PanenUang;
import View.Dashboard.Features.PantauUangPage;
import View.Dashboard.Features.TanamUangPage;
import View.Login_Register.LoginPage;
import View.Login_Register.RegistrationPage;
import View.Login_Register.RequestNewPass;
import View.Splash_Screen.SplashScreen;
import javafx.stage.Stage;
import Model.TanamUangModel;

// class SceneController digunakan untuk mengatur perpindahan scene
public class SceneController {
    private Stage stage; // Deklarasi property stage

    public SceneController(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.show(); // Menampilkan panggung
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
        tanamUang.start(TanamUangModel.getKategoriTanamUang("pemasukan"),
                TanamUangModel.getKategoriTanamUang("pengeluaran")); // Menjalankan

        String[] pemasukan = TanamUangModel.getKategoriTanamUang("pemasukan");
        String[] pengeluaran = TanamUangModel.getKategoriTanamUang("pengeluaran");

        System.out.println("=========PEMASUKAN========");
        for (int i = 0; i < pemasukan.length; i++) {
            System.out.println(pemasukan[i]);
        }

        System.out.println("=========PENGELUARAN========");
        for (int i = 0; i < pengeluaran.length; i++) {
            System.out.println(pengeluaran[i]);
        }
        // method start
        // pada objek
        // tanamUang
    }

    public void switchToPantauUang() {
        PantauUangPage pantauUang = new PantauUangPage(this.stage);
        pantauUang.start();
    }

    // Request New Password
    public void switchToRequestNewPassword() {
        RequestNewPass requestNewPassword = new RequestNewPass(this.stage); // Instansiasi class
                                                                            // RequestNewPassword ke dalam
                                                                            // variabel requestNewPassword
        requestNewPassword.start(); // Menjalankan method start() pada class RequestNewPassword
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
