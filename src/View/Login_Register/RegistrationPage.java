package View.Login_Register;

import Controller.SceneController;
import Model.LoginModel;
import Utils.AlertHelper;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.control.PasswordField;

public class RegistrationPage {

    private Stage stage; // Deklarasi field stage
    private TextField fieldUsername; // Deklarasi field untuk username
    private PasswordField fieldPassword; // Deklarasi field untuk password
    private PasswordField fieldvalidatepassword; // Deklarasi field untuk konfirmasi password
    private TextField fieldpincode; // Deklarasi field untuk pincode

    public RegistrationPage(Stage stage) {
        this.stage = stage; // Inisialisasi field stage dengan parameter stage
        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg"); // Menambah icon
                                                                                                        // pada program
        stage.getIcons().add(icon); // Memasukkan icon ke dalam stage
    }

    public void start() {
        // Components
        Text title = new Text(); // Instansiasi objek text
        title.setText("Please Register"); // Mengatur isi dari text
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text
        title.setTranslateY(-40); // Mengatur koordinat y dari text

        fieldUsername = new TextField(); // Instansiasi objek fieldUsername
        fieldUsername.setMaxWidth(200); // Mengatur lebar maksimal dari kolom username
        fieldUsername.setTranslateX(90); // Mengatur koordinat x dari kolom username

        fieldPassword = new PasswordField(); // Instansiasi objek fieldPassword
        fieldPassword.setMaxWidth(200); // Mengatur lebar maksimal dari kolom password
        fieldPassword.setTranslateX(90); // Mengatur koordinat x dari kolom password
        fieldPassword.setTranslateY(40); // Mengatur koordinat y dari kolom password

        fieldvalidatepassword = new PasswordField(); // Instansiasi objek fieldvalidatepassword
        fieldvalidatepassword.setMaxWidth(200); // Mengatur lebar maksimal dari kolom konfirmasi password
        fieldvalidatepassword.setTranslateX(90); // Mengatur koordinat x pada kolom konfirmasi password
        fieldvalidatepassword.setTranslateY(80); // Mengatur koordinat y pada kolom konfirmasi password

        fieldpincode = new TextField(); // Instansiasi objek fieldpincode
        fieldpincode.setMaxWidth(200); // Mengatur lebar maksimal dari kolom pincode
        fieldpincode.setTranslateX(90); // Mengatur koordinat x dari kolom pincode
        fieldpincode.setTranslateY(120); // Mengatur koordinat y dari kolom pincode

        Label labelUsername = new Label("Username:"); // Instansiasi objek labelUsername
        labelUsername.setTranslateX(-50); // Mengatur koordinat x dari labelUsername
        Label labelPassword = new Label("Password:"); // Instansiasi objek labelPassword
        labelPassword.setTranslateX(-50); // Mengatur koordinat x dari labelPassword
        labelPassword.setTranslateY(40); // Mengatur koordinat y dari labelPassword
        Label labelValidatePassword = new Label("Validate Password:"); // Instansiasi objek labelValidatePassword
        labelValidatePassword.setTranslateX(-76); // Mengatur koordinat x labelValidatePassword
        labelValidatePassword.setTranslateY(80); // Mengatur koordinat y dari labelValidatePassword
        Label labelpincode = new Label("Pin Code:"); // Instansiasi objek labelpincode
        labelpincode.setTranslateX(-50); // Mengatur koordinat x dari labelpincode
        labelpincode.setTranslateY(120); // Mengatur koordinat y dari labelpincode

        Hyperlink loginlink = new Hyperlink("Login"); // Instansiasi objek loginlink
        loginlink.setTranslateX(175); // Mengatur koordinat x dari loginlink
        loginlink.setTranslateY(148); // Mengatur koordinat y dari loginlink
        loginlink.setStyle( // Mengatur style dari loginlink
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana");
        loginlink.setOnAction(e -> { // Mengatur event dari hyperlink loginlink
            SceneController sceneController = new SceneController(this.stage); // Instansiasi objek sceneController
            sceneController.switchToLogin(); // Menjalankan method switchToLogin()
        });

        Button btnregister = new Button("Register"); // Instansiasi objek btnregister
        btnregister.setTranslateY(170); // Mengatur koordinat y dari btnregister

        // Scene
        StackPane root = new StackPane(); // Instansiasi objek root
        StackPane.setMargin(root, new Insets(20, 0, 0, 0)); // Mengatur margin dari StackPane
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, fieldPassword,
                labelValidatePassword,
                fieldvalidatepassword, fieldpincode, labelpincode, loginlink,
                btnregister); // Menambah elemen pada root node

        Scene scene = new Scene(root, Color.gray(0.2)); // Instansiasi objek scene
        // stage.setFullScreen(true);
        this.stage.setTitle("Register"); // Mengatur isi dari title
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.show(); // Menampilkan stage

        // Handle button click
        btnregister.setOnAction(e -> handleregister(
                fieldUsername.getText(),
                fieldPassword.getText(),
                fieldvalidatepassword.getText(),
                fieldpincode.getText()));
    }

    private boolean isPasswordSecure(String password) {
        // Atur aturan keamanan sesuai kebutuhan
        // Contoh: Minimal 8 karakter, kombinasi huruf besar, huruf kecil, angka, dan
        // karakter khusus
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private void handleregister(String username, String password, String validatepassword, String pincode) {
        SceneController sceneController = new SceneController(this.stage);
        LoginModel login = new LoginModel();
        String validate = validatepassword;

        if (username.isEmpty() && password.isEmpty() && validate.isEmpty() && pincode.isEmpty()) {
            applyErrorStyle(fieldUsername, fieldPassword, fieldvalidatepassword, fieldpincode);
            AlertHelper.alert("Username dan password tidak boleh kosong.");
        } else if (username.isEmpty()) {
            fieldPassword.setStyle("");
            fieldvalidatepassword.setStyle("");
            fieldpincode.setStyle("");
            applyErrorStyle(fieldUsername);
            AlertHelper.alert("Username tidak boleh kosong.");
        } else if (password.isEmpty()) {
            fieldUsername.setStyle("");
            fieldvalidatepassword.setStyle("");
            fieldpincode.setStyle("");
            applyErrorStyle(fieldPassword);
            AlertHelper.alert("Password tidak boleh kosong.");
        } else if (pincode.isEmpty()) {
            fieldUsername.setStyle("");
            fieldPassword.setStyle("");
            fieldvalidatepassword.setStyle("");
            applyErrorStyle(fieldpincode);
            AlertHelper.alert("Pin Code tidak boleh kosong.");
        } else if (validate.isEmpty()) {
            fieldUsername.setStyle("");
            fieldPassword.setStyle("");
            fieldpincode.setStyle("");
            applyErrorStyle(fieldvalidatepassword);
            AlertHelper.alert("Konfirmasi password tidak boleh kosong.");
        } else {
            fieldUsername.setStyle("");
            fieldPassword.setStyle("");
            fieldvalidatepassword.setStyle("");
            fieldpincode.setStyle("");
            if (password.equals(validate)) {
                if (isPasswordSecure(password)) {
                    System.out.println("Password Match");

                    if (login.registerAccount(username, password, pincode)) {
                        sceneController.switchToLogin();
                    } else {
                        AlertHelper.alert("Gagal mendaftarkan akun.");
                    }
                } else {
                    String massage = "Password tidak memenuhi kriteria keamanan.\n" +
                            "Kriteria:\n" +
                            "- Minimal 8 karakter\n" +
                            "- Mengandung setidaknya satu huruf besar (A-Z)\n" +
                            "- Mengandung setidaknya satu huruf kecil (a-z)\n" +
                            "- Mengandung setidaknya satu angka (0-9)\n" +
                            "- Mengandung setidaknya satu karakter khusus (!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?])";
                    applyErrorStyle(fieldPassword);
                    AlertHelper.alert(massage);
                }
            } else {
                applyErrorStyle(fieldPassword, fieldvalidatepassword);
                AlertHelper.alert("Password tidak sesuai.");
            }
        }
    }

    private void applyErrorStyle(TextField... fields2) {
        for (TextField field : fields2) { // Melakukan foreach pada setiap element di fields2
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // Ubah style border dari field menjadi
                                                                             // berwarna merah
        }
    }
}
