package View.Login_Register;

import Controller.SceneController;
import Model.*;
import Utils.AlertHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// membuat class LoginPage
public class LoginPage {
    private Stage stage; // Deklarasi property stage
    private boolean statusCheckbox; // Deklarasi property statusCheckbox
    private TextField fieldUsername; // Deklarasi property fieldUsername
    private PasswordField passwordField; // Deklarasi property passwordField

    // Melakukan inisiasi class LoginPage dengan parameter stage
    public LoginPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage

        // Set the application icon
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
    }

    public void penentuApakahStartAtauLangsungDashboard() {
        Boolean penentuAkhir = false;
        LoginModel login = new LoginModel(); // Membuat objek login
        penentuAkhir = login.penentuBagianLastUser();
        if (penentuAkhir) {
            SceneController sceneController = new SceneController(this.stage); // Membuat objek sceneController
            sceneController.switchToDashboard(); // Merubah scene menuju dashboard
        } else {
            this.start(); // Menjalankan method start()
        }
    }

    // Menampilkan halaman login
    public void start() {
        this.fieldUsername = new TextField(); // Kode ini akan membuat TextField baru
        this.passwordField = new PasswordField(); // Konstruktor PasswordField untuk membuat PasswordField baru

        // Components
        Text title = new Text(); // Membuat objek text
        title.setText("Please Login"); // Mengatur isi dari text
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text
        title.setTranslateY(-40); // Mengatur posisi dari text pada sumbu y

        fieldUsername.setMaxWidth(200); // Mengatur lebar maximum dari field username
        fieldUsername.setTranslateX(200); // Mengatur posisi x dari field username

        // Mengatur lebar maximum dari field password
        passwordField.setMaxWidth(200);

        // Mengatur posisi x dari field password
        passwordField.setTranslateX(200);

        // Mengatur posisi y dari field password
        passwordField.setTranslateY(40);

        // Membuat label "Username:"
        Label labelUsername = new Label("Username:");
        labelUsername.setTranslateX(-50); // Mengatur posisi x dari label username

        // Membuat label "Password:"
        Label labelPassword = new Label("Password:");
        labelPassword.setTranslateX(-50); // Mengatur posisi x dari label password
        labelPassword.setTranslateY(40); // Mengatur posisi y dari label password

        // Membuat Hyperlink "Register" untuk pindah ke halaman registrasi
        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setTranslateX(280); // Mengatur posisi x dari registerlink
        registerLink.setTranslateY(80); // Mengatur posisi y dari registerlink
        registerLink.setStyle( // Mengatur style pada tulisan register link
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana");
        registerLink.setOnAction(e -> { // Menginisiasi ketika registet link di tekan
            SceneController sceneController = new SceneController(this.stage); // Membuat objek scenecontroller
            sceneController.switchToRegistration(); // Merubah stage menggunakan scene controller menuju registrasi
        });

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setTranslateX(280);
        forgotPasswordLink.setTranslateY(120);
        forgotPasswordLink.setStyle(
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana");
        forgotPasswordLink.setOnAction(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToRequestNewPassword();
        });

        // Membuat CheckBox "Remember Me"
        CheckBox checkbox = new CheckBox("Remember Me");
        checkbox.setTranslateX(-30); // mengatur posisi x pada checkbox
        checkbox.setTranslateY(80); // mengatur posisi y pada checkbox
        checkbox.setOnAction(e -> { // Menjalankan kondisi ketika checkbox ditekan
            if (checkbox.isSelected()) {
                this.statusCheckbox = true; // Merubah status checkbox menjadi true
            } else {
                this.statusCheckbox = false; // Merubah status checkbox menjadi false
            }
        });

        // Membuat Button "Login"
        Button btnLogin = new Button("Login");
        btnLogin.setTranslateY(120); // Mengatur posisi y dari tombol login

        // Membuat root container menggunakan StackPane
        StackPane root = new StackPane();
        // Mengatur margin dari StackPane
        // Menambahkan semua item kedalam Stackpane
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, passwordField, registerLink,
                forgotPasswordLink,
                checkbox,
                btnLogin);

        // Membuat Scene dengan latar belakang abu-abu
        Scene scene = new Scene(root, Color.gray(0.2));

        // Memberikan judul pada stage
        this.stage.setTitle("Register");

        // Menetapkan scene pada stage
        this.stage.setScene(scene);

        // Menampilkan stage
        this.stage.show();

        // Menangani klik tombol "Login"
        btnLogin.setOnAction(e -> handleLogin(fieldUsername.getText(), passwordField.getText()));

    }

    // Metode untuk melakukan pengecekan ke database dan kondisi
    private void handleLogin(String username, String password) {
        LoginModel login = new LoginModel(); // Membuat objek login
        SceneController sceneController = new SceneController(this.stage); // Membuat objek sceneController

        // Mengatur kondisi login antara username dan password baik itu menuju database
        // atau mengecek kondisi field
        if (login.isValidated(username, password, statusCheckbox)) {
            sceneController.switchToDashboard(); // Merubah scene menuju dashboard
        } else if (username.isEmpty() && password.isEmpty()) {
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(fieldUsername, passwordField);
        } else if (username.isEmpty()) {
            // Mereset error style passwordField menjadi normal
            passwordField.setStyle("");
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(fieldUsername);
        } else if (password.isEmpty()) {
            // Mereset error style fieldUsername menjadi normal
            fieldUsername.setStyle("");
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(passwordField);
        } else {
            // Mereset error style fieldUsername menjadi normal
            fieldUsername.setStyle("");
            // Mereset error style passwordField menjadi normal
            passwordField.setStyle("");
            // Memunculkan pesan kesalahan
            AlertHelper.alert("Username or password is incorrect.");
        }

    }

    // Membuat metode untuk menerapkan error style
    private void applyErrorStyle(TextField... fields2) { // Menampung berbagai macam field
        for (TextField field : fields2) { // Melakukan loop untuk setiap field yang ada
            // Mengimplementasikan error style ke setiap field
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}
