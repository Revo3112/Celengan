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

    public RegistrationPage(Stage stage) {
        this.stage = stage; // Inisialisasi field stage dengan parameter stage
        // Tambahkan icon ke sebelah kiri
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg"); // Menambah icon pada program
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

        PasswordField fieldvalidatepassword = new PasswordField(); // Instansiasi objek fieldvalidatepassword
        fieldvalidatepassword.setMaxWidth(200); // Mengatur lebar maksimal dari kolom konfirmasi password
        fieldvalidatepassword.setTranslateX(90); // Mengatur koordinat x pada kolom konfirmasi password 
        fieldvalidatepassword.setTranslateY(80); // Mengatur koordinat y pada kolom konfirmasi password

        Label labelUsername = new Label("Username:"); // Instansiasi objek labelUsername
        labelUsername.setTranslateX(-50); // Mengatur koordinat x dari labelUsername
        Label labelPassword = new Label("Password:"); // Instansiasi objek labelPassword
        labelPassword.setTranslateX(-50); // Mengatur koordinat x dari labelPassword
        labelPassword.setTranslateY(40); // Mengatur koordinat y dari labelPassword
        Label labelValidatePassword = new Label("Validate Password:"); // Instansiasi objek labelValidatePassword
        labelValidatePassword.setTranslateX(-76); // Mengatur koordinat x labelValidatePassword
        labelValidatePassword.setTranslateY(80); // Mengatur koordinat y dari labelValidatePassword

        Hyperlink loginlink = new Hyperlink("Login"); // Instansiasi objek loginlink
        loginlink.setTranslateX(175d); // Mengatur koordinat x dari loginlink
        loginlink.setTranslateY(106); // Mengatur koordinat y dari loginlink
        loginlink.setStyle( // Mengatur style dari loginlink
                "-fx-underline: true;" +
                        "-fx-font-family: Verdana"); 
        loginlink.setOnAction(e -> { // Mengatur event dari hyperlink loginlink
            SceneController sceneController = new SceneController(this.stage); // Instansiasi objek sceneController
            sceneController.switchToLogin(); // Menjalankan method switchToLogin()
        });

        Button btnregister = new Button("Register"); // Instansiasi objek btnregister
        btnregister.setTranslateY(140); // Mengatur koordinat y dari btnregister

        // Scene
        StackPane root = new StackPane(); // Instansiasi objek root
        StackPane.setMargin(root, new Insets(20, 0, 0, 0)); // Mengatur margin dari StackPane
        root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword, fieldPassword,
                labelValidatePassword,
                fieldvalidatepassword, loginlink,
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
                fieldvalidatepassword.getText()));
    }

    private void handleregister(String username, String password, String validatepassword) {
        SceneController sceneController = new SceneController(this.stage); // Instansiasi objek sceneController 
        LoginModel login = new LoginModel(); // Instansiasi objek login
        String validate = validatepassword; // Inisialisasi variabel validate dengan nilai dari variabel validatepassword

        if (username.isEmpty() && password.isEmpty()) { // Jika kolom username dan kolom password kosong, maka: 
            applyErrorStyle(fieldUsername, fieldPassword); // Memanggil method applyErrorStyle
        } else if (username.isEmpty()) { // Jika kolom username kosong, maka:
            fieldPassword.setStyle(""); // Set style dari kolom password menjadi default
            applyErrorStyle(fieldUsername); // Memanggil method applyErrorStyle
        } else if (password.isEmpty()) { // Jika kolom password menjadi kosong
            fieldUsername.setStyle(""); // Set style dari kolom username menjadi default
            applyErrorStyle(fieldPassword); // Memanggil method applyErrorStyle
        } else { // Jika terisi semua, maka:
            fieldUsername.setStyle(""); // Set style fieldUsername menjadi default
            fieldPassword.setStyle(""); // Set style fieldPasswrod menjadi default
            if (password.equals(validate)) { // Jika password sama dengan validate, maka:
                System.out.println("Password Match"); // Tampilkan pesan password sesuai

                if (login.registerAccount(username, password)) { // Jika proses register berhasil, maka:
                    sceneController.switchToLogin(); // Pindah ke login
                } else { // Jika proses register tidak berhasil, maka:
                    AlertHelper.alert(password); // Tampilkan pesan kesalahan
                }
            } else { // Jika password tidak sama dengan validate, maka:
                applyErrorStyle(fieldPassword, fieldvalidatepassword); // Ubah kolom menjadi warna merah
                AlertHelper.alert("Password doesn't match"); // Tampilkan error
            }
        }
    }

    private void applyErrorStyle(TextField... fields2) {
        for (TextField field : fields2) { // Melakukan foreach pada setiap element di fields2
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // Ubah style border dari field menjadi berwarna merah
        }
    }
}
