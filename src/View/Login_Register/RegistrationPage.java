package View.Login_Register;

import java.util.ArrayList;
import java.util.List;

import Controller.SceneController;
import Model.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegistrationPage {

    private Stage stage; // Deklarasi property stage
    SceneController sceneController; // sceneController utk pages switching

    // cursors
    private Cursor hand = Cursor.cursor("HAND");
    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor defaultCursor = Cursor.cursor("DEFAULT");

    private boolean isMousePressed = false;
    private boolean isWindowMax = true;
    private double xOffset = 0;
    private double yOffset = 0;

    // error status
    boolean hLogErrStat = false;

    private boolean animationPlayed = false;

    // panes
    StackPane mainContent1;
    StackPane mainContent2;

    // timelines
    Timeline hopAyamBetina_IN; // ayam netral melompat ke dalam
    Timeline hopAyamBetina_OUT; // ayam netral keluar (selesai)
    Timeline hopAyamBetina2_IN; // ayam netral melompat ke dalam
    Timeline hopAyamBetina2_OUT; // ayam netral keluar (selesai)
    Timeline welcomeAyam_IN; // membuat ayam welcome keluar
    Timeline ayamError_IN; // ayam error melompat ke dalam
    Timeline ayamError_OUT; // ayam error keluar (selesai)
    Timeline awanError_IN; // ayam error masuk
    Timeline ayamHand_IN; // tangan ayam untuk password ke dalam
    Timeline ayamHand_OUT; // tangan ayam untuk password ke luar
    Timeline ayamHand2_IN; // tangan ayam untuk password ke dalam
    Timeline ayamHand2_OUT; // tangan ayam untuk password ke luar
    Timeline fadeChatAyam_IN; // chat ayam netral ke dalam
    Timeline fadeChatAyam_OUT; // chat ayam netral keluar (selesai)
    Timeline fadeChatAyam2_IN; // chat ayam netral ke dalam
    Timeline fadeChatAyam2_OUT; // chat ayam netral keluar (selesai)

    // Lists timeline
    List<Timeline> listAyamNetral_IN;
    List<Timeline> listAyamNetral_OUT;
    List<Timeline> listAyamError_IN;
    List<Timeline> listAyamError_OUT;

    // deklarasi path asset error
    String logRegPath = "/Assets/View/Login_Register/";
    // animasi ayam error/deklarasi image
    ImageView ayamError = createImage(logRegPath + "ayamMarah.png", 180, 180);
    ImageView awanError = createImage(logRegPath + "awan.png", 350, 350);
    Duration ayamErrorHopDur = Duration.millis(600);
    Duration awanErrorHopDur = Duration.millis(2300);
    double ayamErrorOriginY = ayamError.getTranslateY();
    double ayamErrorTargetY = ayamErrorOriginY - 250;
    double awanErrorOriginY = awanError.getTranslateY();
    double awanErrorTargetY = awanErrorOriginY - 500;
    List<ImageView> listChatErrorAyam;

    // transitions
    private FadeTransition fadeTransition1;
    private FadeTransition fadeTransition2;

    // drag
    private double dragStartX;

    // dots
    Circle dot1;
    Circle dot2;

    StackPane root = new StackPane(); // stackpane root

    public RegistrationPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        sceneController = new SceneController(stage);
        // Set the application icon
        Image icon = new Image("Assets/View/Splash_Screen/images/logo/celengan_image_logo.png");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
        this.stage.setTitle("Celengan");
    }

    // Menampilkan halaman login
    public void start() {
        /* UI/UX : NULLPTR */
        // FADING (CAROUSEL)
        fadeTransition1 = createFadeTransition(mainContent1, 1.0, 0.0);
        fadeTransition2 = createFadeTransition(mainContent2, 0.0, 1.0);
        // DOT CAROUSEL
        Circle dot1 = createDot(7, "263940");
        Circle dot2 = createDot(7, "7795FF");
        HBox dots = new HBox(10, dot1, dot2);
        dots.setAlignment(Pos.CENTER);
        StackPane dotCarousel = new StackPane(dots);
        dotCarousel.setTranslateY(213);

        // Memainkan welcoming greet dari ayam kesukaan
        ImageView welcomeAyam = createImage(logRegPath + "chickenWelcome.png", 350, 350);
        double welcomeAyamOriginX = welcomeAyam.getTranslateX();
        double targetAyamOriginX = welcomeAyamOriginX - 300;
        // animasi ayam welcome greet
        welcomeAyam_IN = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(welcomeAyam.translateXProperty(), welcomeAyamOriginX, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(welcomeAyam.translateXProperty(), targetAyamOriginX)),
                new KeyFrame(Duration.millis(3500), // Add a delay of 1200 milliseconds
                        new KeyValue(welcomeAyam.translateXProperty(), welcomeAyamOriginX, Interpolator.EASE_BOTH)));

        /* BAGIAN MMC (MINIMIZE, MAXIMIZE, CLOSE) */
        // membuat closeButton
        Button closeButton = createButton(35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");

        // membuat maximizeButton
        Button maximizeButton = createButton(35, 35, " ", "FFFFFF", 23, "Poppins", 30, "0F181B");
        // set graphic untuk label maximize
        Rectangle maxIcon = new Rectangle(14, 14);
        maxIcon.setFill(Color.TRANSPARENT);
        maxIcon.setStroke(Color.valueOf("#0F181B"));
        maxIcon.setStrokeWidth(4);
        maxIcon.setTranslateX(maximizeButton.getTranslateX() + 5);
        maximizeButton.setGraphic(maxIcon);

        // membuat minimizeButton
        Button minimizeButton = createButton(35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");

        // BUTTON EVENTS
        // CLOSE BUTTON: saat dipencet maka close button akan menstop aplikasi
        closeButton.setOnMouseClicked(closeEvent -> stage.hide());
        // saat di hover maka cursor berbeda
        closeButton.setOnMouseEntered(closeEvent -> {
            closeButton.getScene().setCursor(hand);
            updateButton(closeButton, 35, 35, "X", "6A1B1B", 23, "Poppins", 30, "0F181B");
        });
        closeButton.setOnMouseExited(closeEvent -> {
            closeButton.getScene().setCursor(defaultCursor);
            updateButton(closeButton, 35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");
        });

        // MAXIMIZE BUTTON: saat dipencet maka akan mengecilkan windows
        maximizeButton.setOnMouseClicked(maxEvent -> {
            if (stage.isMaximized()) {
                stage.setWidth(stage.getWidth() - 100);
                stage.setHeight(stage.getHeight() - 100);
                stage.setMaximized(false);
                isWindowMax = false;
                System.out.println("IsWindMax =" + isWindowMax);
                root.setOnMousePressed(e -> {
                    if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
                        isMousePressed = true;
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();
                    }
                });
                // saat dilepas
                root.setOnMouseReleased(e -> {
                    root.getScene().setCursor(defaultCursor);
                    isMousePressed = false;
                });
                // saat ditarik
                root.setOnMouseDragged(e -> {
                    if (isMousePressed && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
                        stage.setX(e.getScreenX() - xOffset);
                        stage.setY(e.getScreenY() - yOffset);
                    }
                });
            } else {
                isWindowMax = true;
                System.out.println("IsWindMax =" + isWindowMax);
                stage.setMaximized(true);
            }
        });

        // saat di hover maka cursor berbeda
        maximizeButton.setOnMouseEntered(closeEvent -> {
            maximizeButton.getScene().setCursor(hand);
            updateButton(maximizeButton, 35, 35, "", "959595", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });
        maximizeButton.setOnMouseExited(closeEvent -> {
            maximizeButton.getScene().setCursor(defaultCursor);
            updateButton(maximizeButton, 35, 35, "", "FFFFFF", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });

        // MINIMIZE BUTTON: saat dipencet akan menutup sementara window
        minimizeButton.setOnMouseClicked(maxEvent -> {
            stage.setIconified(true);
        });
        // saat di hover maka cursor berbeda
        minimizeButton.setOnMouseEntered(closeEvent -> {
            minimizeButton.getScene().setCursor(hand);
            updateButton(minimizeButton, 35, 35, "-", "959595", 23, "Poppins", 30, "0F181B");
        });
        minimizeButton.setOnMouseExited(closeEvent -> {
            minimizeButton.getScene().setCursor(defaultCursor);
            updateButton(minimizeButton, 35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");
        });

        /* BAGIAN DROPDOWN LOGIN */
        // membuat dropdown button
        ComboBox<String> dropDownBtn = new ComboBox<>();
        dropDownBtn.setMinWidth(20);
        dropDownBtn.setMinHeight(40);
        dropDownBtn.setPromptText("Register Page");
        dropDownBtn.setItems(FXCollections.observableArrayList("Login Page", "Register Page", "Request New Password"));
        dropDownBtn.setStyle(
                "-fx-background-radius: 130;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-font: 16 Poppins;" +
                        "-fx-border-color: #000000; " + // Set the stroke color
                        "-fx-border-width: 3px;" +
                        "-fx-border-radius: 90;");
        // logic dropdownButton
        dropDownBtn.setOnAction(e -> {
            String selectedItem = dropDownBtn.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Perform the switch after the fade out animation completes
                switch (selectedItem) {
                    case "Login Page":
                        sceneController.switchToLogin();
                        break;
                    case "Request New Password":
                        sceneController.switchToRequestNewPassword();
                        break;
                    default:
                        // Handle other cases if needed
                }
            }
        });

        // BUTTON ORGANIZER
        // memasukkan button ke dalam HBox
        HBox mmcButton = new HBox(5); // 5 spacing
        mmcButton.getChildren().addAll(minimizeButton, maximizeButton, closeButton);
        mmcButton.setMaxWidth(150);
        mmcButton.setAlignment(Pos.TOP_RIGHT);
        // memasukkan dropdown ke dalam HBox
        HBox dropDownLogin = new HBox(20);
        dropDownLogin.getChildren().add(dropDownBtn);
        dropDownLogin.setAlignment(Pos.TOP_LEFT);
        // menggabungkan kedua HBox ke dalam satu HBox
        HBox topElements = new HBox(500);
        topElements.getChildren().addAll(dropDownLogin, mmcButton);
        topElements.setStyle("-fx-padding: 16, 16, 0, 16;"); // padding: top left bottom right
        HBox.setHgrow(dropDownLogin, Priority.ALWAYS);
        topElements.setAlignment(Pos.TOP_RIGHT);
        // menggabungkan topElements ke section
        VBox topSection = new VBox(topElements);

        /* BAGIAN MAIN CONTENT 1 */
        // membuat base main content
        Rectangle backgroundBase = createRect(490, 360, 60, 60, "0F181B");
        Rectangle mainBase = createRect(backgroundBase.getWidth(), backgroundBase.getHeight(), 60, 60,
                "263940");
        // menambahkan stroke pada mainBase
        mainBase.setStroke(Color.valueOf("#0F181B"));
        mainBase.setStrokeWidth(3);
        // mengubah posisi mainBase agar turun sedikit dari backgroundBase
        mainBase.setTranslateY(backgroundBase.getTranslateY() - 15);
        // menggabungkan backgroundBase dengan mainBase menjadi satu group
        Group base = new Group(backgroundBase, mainBase);
        // MAIN ELEMENTS: elemen di dalam main base
        // USERNAME TEXT: bagian informasi text username dan icon
        Text username = createText("username", 25, "Poppins", "7795FF");
        ImageView usr_icon = createImage("/Assets/View/Login_Register/registUsername.png", 40, 40);
        // menambah masing-masing username elements ke hbox
        HBox usernameText = new HBox(5);
        usernameText.getChildren().addAll(usr_icon, username);
        usernameText.setStyle("-fx-padding: 0 0 10 40;");
        // USERNAME FIELD: bagian yang dapat diinput username
        TextField inputUsername = new TextField();
        inputUsername.setMaxWidth(mainBase.getWidth() - 80);
        inputUsername.setMaxHeight(120);
        inputUsername.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // PASSWORD TEXT: bagian informasi text password dan icon
        Text password = createText("password", 25, "Poppins", "7795FF");
        ImageView pwd_icon = createImage("/Assets/View/Login_Register/registPassword.png", 40, 40);
        ImageView pwd_icon2 = createImage("/Assets/View/Login_Register/registPassword.png", 40, 40);
        // menambah masing-masing elemen password elements ke hbox
        HBox passwordText = new HBox(5);
        passwordText.getChildren().addAll(pwd_icon, password);
        passwordText.setStyle("-fx-padding: 10 0 10 40;");
        // PASSWORD FIELD: bagian yang dapat diinput password
        PasswordField inputPassword = new PasswordField();
        inputPassword.setMaxWidth(mainBase.getWidth() - 90);
        inputPassword.setMaxHeight(40);
        inputPassword.setStyle(
                "-fx-background-radius: 90;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");

        // ADDITIONAL OPTIONS 1: berisi lupa password? dan ingatkan saya
        Hyperlink lupaPassword = new Hyperlink("Lupa Password?");
        lupaPassword.setStyle(
                "-fx-text-fill: #FF7474;" +
                        "-fx-font: 14 Poppins;");
        CheckBox ingatkanSaya = new CheckBox("Ingatkan Saya");
        ingatkanSaya.setStyle(
                "-fx-text-fill: #AD7AFF;" +
                        "-fx-font: 14 Poppins;" +
                        "-fx-mark-color: #AD7AFF;");
        // menggabungkan elemen addoptions
        HBox addOption = new HBox(150);
        addOption.getChildren().addAll(lupaPassword, ingatkanSaya);
        addOption.setStyle("-fx-padding: 10 0 10 40;");
        // BUTTON LOGIN: seluruh tombol masuk dan belum punya akun?
        // button login
        Button masukLogin = createButton(120, 50, "Lanjut", "7795FF", 22, "Poppins", 30, "141F23");
        Rectangle masukLoginBG = new Rectangle();

        // button register
        Button registButton = createButton(280, 50, "Sudah Punya Akun?", "141F23", 22, "Poppins", 30, "7795FF");
        Rectangle registButtonBG = new Rectangle();

        // menyatukan elemen login ke satu group
        Group loginButton = new Group(masukLogin, masukLoginBG);
        // LOGIN BUTTON EVENTS: saat di hover atau di klik
        // login button
        masukLogin.setOnMouseEntered(e -> {
            masukLogin.getScene().setCursor(hand);
            System.out.println("Username: " + inputUsername.getText());
            System.out.println("Password: " + inputPassword.getText());
            updateButton(masukLogin, 120, 50, "Lanjut", "495C9E", 22, "Poppins", 30, "141F23");
        });
        masukLogin.setOnMouseExited(e -> {
            masukLogin.getScene().setCursor(defaultCursor);
            updateButton(masukLogin, 120, 50, "Lanjut", "7795FF", 22, "Poppins", 30, "141F23");
        });
        masukLogin.setOnAction(e -> {
            if (awanError_IN != null) {
                awanError_IN.stop(); // Stop the animation if it's already running
                awanError.translateYProperty().set(awanErrorOriginY); // Reset translateY to original Y position
            }
            System.out.println("Login button clicked!");
            System.out.println(
                    "Before handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());
            // hLogErrStat = handleLogin(
            // inputUsername.getText(),
            // inputPassword.getText(),
            // ingatkanSaya,
            // inputUsername,
            // inputPassword,
            // listAyamNetral_OUT,
            // listChatErrorAyam,
            // mainContent1,
            // ayamError_IN,
            // awanError_IN);
            System.out.println(
                    "After handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());

            awanError_IN.play();
        });
        // Register Button
        registButton.setOnMouseEntered(e -> {
            registButton.getScene().setCursor(hand);
            updateButton(registButton, 280, 50, "Sudah Punya Akun?", "0B1113", 22, "Poppins", 30, "7795FF");
        });
        registButton.setOnMouseExited(e -> {
            registButton.getScene().setCursor(defaultCursor);
            updateButton(registButton, 280, 50, "Sudah Punya Akun?", "141F23", 22, "Poppins", 30, "7795FF");
        });
        registButton.setOnAction(e -> {
            // pergi ke registration page
            sceneController.switchToRegistration();
        });

        // menyatukan elemen register ke satu group
        Group registerButton = new Group(registButton, registButtonBG);
        // memasukkan loginButton ke HBox
        HBox loginBtn = new HBox();
        loginBtn.getChildren().add(loginButton);
        // memasukkan registerButton ke HBox
        HBox registBtn = new HBox();
        registBtn.getChildren().add(registerButton);
        // menggabungkan keduanya ke VBox
        HBox loginRegistButton = new HBox(10);
        loginRegistButton.getChildren().addAll(loginBtn, registBtn);
        loginRegistButton.setStyle("-fx-padding: 10 0 10 40;");

        addOption.setVisible(false);
        // menggabungkan seluruh elements
        VBox contentElements = new VBox();
        contentElements.getChildren().addAll(usernameText, inputUsername, passwordText, inputPassword, addOption,
                loginRegistButton);
        contentElements.setAlignment(Pos.CENTER);
        contentElements.setMaxWidth(mainBase.getWidth());
        contentElements.setMaxHeight(200);
        /* END OF MAIN CONTENT 1 */

        /* BAGIAN MAIN CONTENT 2 */
        // membuat base2 main content2
        Rectangle backgroundBase2 = createRect(490, 360, 60, 60, "0F181B");
        Rectangle mainBase2 = createRect(backgroundBase2.getWidth(), backgroundBase2.getHeight(), 60, 60,
                "263940");
        // menambahkan stroke pada mainBase2
        mainBase2.setStroke(Color.valueOf("#0F181B"));
        mainBase2.setStrokeWidth(3);
        // mengubah posisi mainBase2 agar turun sedikit dari backgroundBase2
        mainBase2.setTranslateY(backgroundBase2.getTranslateY() - 15);
        // menggabungkan backgroundBase2 dengan mainBase2 menjadi satu group
        Group base2 = new Group(backgroundBase2, mainBase2);
        // MAIN ELEMENTS2: elemen di dalam main base 2
        // VALIDASI PASSWORD TEXT: bagian informasi text password dan icon
        Text validasiPassword = createText("validasi password", 25, "Poppins", "7795FF");
        // menambah masing-masing elemen password elements ke hbox
        HBox validasiPassText = new HBox(5);
        validasiPassText.getChildren().addAll(pwd_icon2, validasiPassword);
        validasiPassText.setStyle("-fx-padding: 10 0 10 40;");
        // PASSWORD FIELD: bagian yang dapat diinput password
        PasswordField inputValidPass = new PasswordField();
        inputValidPass.setMaxWidth(mainBase2.getWidth() - 90);
        inputValidPass.setMaxHeight(40);
        inputValidPass.setStyle(
                "-fx-background-radius: 90;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // KEY BARU TEXT: bagian informasi text key dan validasi password
        Text keyBaru = createText("key baru", 25, "Poppins", "7795FF");
        ImageView keyIcon = createImage("/Assets/View/Login_Register/registKey.png", 30, 30);
        // menambah masing-masing username elements ke hbox
        HBox keyText = new HBox(5);
        keyText.getChildren().addAll(keyIcon, keyBaru);
        keyText.setStyle("-fx-padding: 0 0 10 40;");
        // KEY INPUT FIELD: bagian yang dapat diinput key baru
        TextField inputKey = new TextField();
        inputKey.setMaxWidth(mainBase2.getWidth() - 80);
        inputKey.setMaxHeight(120);
        inputKey.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // ADDITIONAL OPTIONS 2: berisi lupa password? dan ingatkan saya
        Hyperlink lupaPassword2 = new Hyperlink("Lupa Password?");
        lupaPassword2.setStyle(
                "-fx-text-fill: #FF7474;" +
                        "-fx-font: 14 Poppins;");
        CheckBox ingatkanSaya2 = new CheckBox("Ingatkan Saya");
        ingatkanSaya2.setStyle(
                "-fx-text-fill: #AD7AFF;" +
                        "-fx-font: 14 Poppins;" +
                        "-fx-mark-color: #AD7AFF;");
        // menggabungkan elemen addoptions
        HBox addOption2 = new HBox(150);
        addOption2.getChildren().addAll(lupaPassword2, ingatkanSaya2);
        addOption2.setStyle("-fx-padding: 10 0 10 40;");
        // BUTTON DAFTAR: seluruh tombol daftar dan sudah punya akun?
        // button daftar
        Button masukDaftar = createButton(120, 50, "Daftar", "7795FF", 22, "Poppins", 30, "141F23");
        Rectangle masukDaftarBG = new Rectangle();
        // button register
        Button registButton2 = createButton(280, 50, "Sudah Punya Akun?", "141F23", 22, "Poppins", 30, "7795FF");
        Rectangle registButtonBG2 = new Rectangle();

        // menyatukan elemen login ke satu group
        Group daftarButton = new Group(masukDaftar, masukDaftarBG);
        // menyatukan elemen register ke stau group
        Group registerButton2 = new Group(registButton2, registButtonBG2);

        // LOGIN BUTTON EVENTS: saat di hover atau di klik
        // login button
        masukDaftar.setOnMouseEntered(e -> {
            masukDaftar.getScene().setCursor(hand);
            System.out.println("Username: " + inputUsername.getText());
            System.out.println("Password: " + inputPassword.getText());
            updateButton(masukDaftar, 120, 50, "Daftar", "495C9E", 22, "Poppins", 30, "141F23");
        });
        masukDaftar.setOnMouseExited(e -> {
            masukDaftar.getScene().setCursor(defaultCursor);
            updateButton(masukDaftar, 120, 50, "Daftar", "7795FF", 22, "Poppins", 30, "141F23");
        });
        masukDaftar.setOnAction(e -> {
            if (awanError_IN != null) {
                awanError_IN.stop(); // Stop the animation if it's already running
                awanError.translateYProperty().set(awanErrorOriginY); // Reset translateY to original Y position
            }
            System.out.println("Daftar button clicked!");
            System.out.println(
                    "Before handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());
            // hLogErrStat = handleLogin(
            // inputUsername.getText(),
            // inputPassword.getText(),
            // ingatkanSaya,
            // inputUsername,
            // inputPassword,
            // listAyamNetral_OUT,
            // listChatErrorAyam,
            // mainContent1,
            // ayamError_IN,
            // awanError_IN);
            System.out.println(
                    "After handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());

            awanError_IN.play();
        });
        // memasukkan daftarButton ke HBox
        HBox daftarBtn = new HBox();
        daftarBtn.getChildren().add(daftarButton);
        // memasukkan registerButton ke HBox
        HBox registBtn2 = new HBox();
        registBtn2.getChildren().add(registerButton2);
        // menggabungkan keduanya ke VBox
        HBox daftarLoginButton = new HBox(10);
        daftarLoginButton.getChildren().addAll(daftarBtn, registBtn2);
        daftarLoginButton.setStyle("-fx-padding: 10 0 10 40;");

        addOption2.setVisible(false);
        // menggabungkan seluruh elements
        VBox contentElements2 = new VBox();
        contentElements2.getChildren().addAll(validasiPassText, inputValidPass, keyText, inputKey, addOption2,
                daftarLoginButton);
        contentElements2.setAlignment(Pos.CENTER);
        contentElements2.setMaxWidth(mainBase2.getWidth());
        contentElements2.setMaxHeight(200);
        /* END OF MAIN CONTENT 2 */
        /* CAROUSEL MAIN CONTENT */
        // membuat fade transisi

        // LOGO: logo celengan
        // inisiasi logo
        ImageView logo = createImage("/Assets/View/Splash_Screen/images/logo/celengan_image_logo.png",
                60, 60);
        // inisiasi logo text
        Text logoText = createText("Celengan", 40, "Poppins", "FFFFFF");
        // memasukkan keduanya di HBox
        HBox logoElements = new HBox(5);
        logoElements.getChildren().addAll(logo, logoText);
        logoElements.setAlignment(Pos.BOTTOM_CENTER);
        // menggabungkan semua elemen logo ke stackPane
        StackPane logoCelengan = new StackPane(logoElements);
        logoCelengan.setMaxHeight(50);
        logoCelengan.setTranslateY(logoCelengan.getTranslateY() + 270);

        // inisiasi logo 2
        ImageView logo2 = createImage("/Assets/View/Splash_Screen/images/logo/celengan_image_logo.png",
                60, 60);
        // inisiasi logo text
        Text logoText2 = createText("Celengan", 40, "Poppins", "FFFFFF");
        // memasukkan keduanya di HBox
        HBox logoElements2 = new HBox(5);
        logoElements2.getChildren().addAll(logo2, logoText2);
        logoElements2.setAlignment(Pos.BOTTOM_CENTER);
        // menggabungkan semua elemen logo ke stackPane
        StackPane logoCelengan2 = new StackPane(logoElements2);
        logoCelengan2.setMaxHeight(50);
        logoCelengan2.setTranslateY(logoCelengan2.getTranslateY() + 270);

        // AYAM: animasi ayam
        ImageView ayamBetina = createImage("/Assets/View/Login_Register/chickenRegist.png", 180, 180);
        double ayamOriginY = ayamBetina.getTranslateY();
        // timeline animasi untuk hop in ayam betina
        hopAyamBetina_IN = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina.translateYProperty(), ayamOriginY - 250,
                                Interpolator.EASE_BOTH)));
        // timeline animasi untuk hop out ayam betina
        hopAyamBetina_OUT = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina.translateYProperty(), ayamOriginY,
                                Interpolator.EASE_BOTH)));
        // CHAT AYAM: animasi chat ayam
        ImageView chatAyam = createImage("/Assets/View/Login_Register/chatInfoCar1.png", 250, 250);
        chatAyam.setTranslateY(chatAyam.getTranslateY() - 280);
        chatAyam.setTranslateX(chatAyam.getTranslateX() - 210);
        chatAyam.setOpacity(0);
        // timeline animasi untuk chat ayam fade in
        fadeChatAyam_IN = new Timeline(
                new KeyFrame(Duration.millis(700),
                        new KeyValue(chatAyam.opacityProperty(), 1)));
        // timeline animasi untuk chat ayam fade out
        fadeChatAyam_OUT = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(chatAyam.opacityProperty(), 0)));
        // TANGAN AYAM: animasi tangan ayam saat password field diketik
        ImageView ayamHand = createImage("/Assets/View/Login_Register/chickenHandRegist.png", 170, 170);
        double ayamHandOriginY = ayamHand.getTranslateY();
        ayamHand_IN = new Timeline(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(ayamHand.translateYProperty(), ayamHandOriginY - 220,
                                Interpolator.EASE_BOTH)));
        ayamHand_OUT = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(ayamHand.translateYProperty(), ayamHandOriginY,
                                Interpolator.EASE_BOTH)));
        // AYAM 2: animasi ayam
        ImageView ayamBetina2 = createImage("/Assets/View/Login_Register/chickenRegist.png", 180, 180);
        double ayamOriginY2 = ayamBetina.getTranslateY();
        // timeline animasi untuk hop in ayam betina
        hopAyamBetina2_IN = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina2.translateYProperty(), ayamOriginY2 - 250,
                                Interpolator.EASE_BOTH)));
        // timeline animasi untuk hop out ayam betina
        hopAyamBetina2_OUT = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina2.translateYProperty(), ayamOriginY2,
                                Interpolator.EASE_BOTH)));
        // CHAT AYAM 2: animasi chat ayam 2
        ImageView chatAyam2 = createImage("/Assets/View/Login_Register/chatInfoCar2.png", 250, 250);
        chatAyam2.setTranslateY(chatAyam2.getTranslateY() - 280);
        chatAyam2.setTranslateX(chatAyam2.getTranslateX() - 210);
        chatAyam2.setOpacity(0);
        // timeline animasi untuk chat ayam fade in
        fadeChatAyam2_IN = new Timeline(
                new KeyFrame(Duration.millis(700),
                        new KeyValue(chatAyam2.opacityProperty(), 1)));
        // timeline animasi untuk chat ayam fade out
        fadeChatAyam2_OUT = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(chatAyam2.opacityProperty(), 0)));
        // TANGAN AYAM 2: animasi tangan ayam saat password field diketik
        ImageView ayamHandValidasi = createImage("/Assets/View/Login_Register/chickenHandValidate.png", 210, 210);
        ayamHandValidasi.setTranslateX(130);
        double ayamHandOriginY2 = ayamHand.getTranslateY();
        ayamHand2_IN = new Timeline(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(ayamHandValidasi.translateYProperty(), ayamHandOriginY2 - 250,
                                Interpolator.EASE_BOTH)));
        ayamHand2_OUT = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(ayamHandValidasi.translateYProperty(), ayamHandOriginY2,
                                Interpolator.EASE_BOTH)));
        // ANIMASI ERROR AYAM:
        // animasi ayam error masuk
        ayamError_IN = new Timeline(
                new KeyFrame(ayamErrorHopDur,
                        new KeyValue(ayamError.translateYProperty(), ayamErrorTargetY,
                                Interpolator.EASE_BOTH)));
        // animasi ayam error keluar
        ayamError_OUT = new Timeline(
                new KeyFrame(ayamErrorHopDur,
                        new KeyValue(ayamError.translateYProperty(), ayamErrorOriginY,
                                Interpolator.EASE_BOTH)));
        // animasi awan error masuk
        awanError_IN = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(awanError.translateYProperty(), awanErrorOriginY)),
                new KeyFrame(awanErrorHopDur,
                        new KeyValue(awanError.translateYProperty(), awanErrorTargetY,
                                Interpolator.EASE_BOTH)));
        // // saat selesai di reset agar tidak stack
        // awanError_IN.setOnFinished(e -> {
        // awanError_IN = null;
        // });

        // animasi text ayam error
        listChatErrorAyam = new ArrayList<>();
        // menambahkan error password (chat)
        listChatErrorAyam.add(createImage(logRegPath + "passwordKosong.png", 200, 200)); // password
                                                                                         // kosong
        listChatErrorAyam.add(createImage(logRegPath + "passwordSalah.png", 200, 200)); // password
                                                                                        // salah
        // menambahkan error username (chat)
        listChatErrorAyam.add(createImage(logRegPath + "usernameKosong.png", 200, 200)); // username
                                                                                         // kosong
        listChatErrorAyam.add(createImage(logRegPath + "usernameSalah.png", 200, 200)); // username
                                                                                        // salah
        // menambahkan error keduanya
        listChatErrorAyam.add(createImage(logRegPath + "usernamePasswordKosong.png", 200, 200)); // username
                                                                                                 // dan
                                                                                                 // password
        // kosong 4
        listChatErrorAyam.add(createImage(logRegPath + "usernamePasswordSalah.png", 200, 200)); // username
                                                                                                // dan
                                                                                                // password
                                                                                                // 5
        // kosong
        // List ayam-ayam netral IN
        listAyamNetral_IN = new ArrayList<>();
        listAyamNetral_IN.add(hopAyamBetina_IN);
        listAyamNetral_IN.add(fadeChatAyam_IN);
        listAyamNetral_IN.add(ayamHand_IN);
        // List ayam-ayam netral OUT
        listAyamNetral_OUT = new ArrayList<>();
        listAyamNetral_OUT.add(hopAyamBetina_OUT);
        listAyamNetral_OUT.add(fadeChatAyam_OUT);
        listAyamNetral_OUT.add(ayamHand_OUT);
        // List ayam-ayam error IN
        listAyamError_IN = new ArrayList<>();
        listAyamError_IN.add(ayamError_IN);
        // List ayam-ayam error OUT
        listAyamError_OUT = new ArrayList<>();
        listAyamError_OUT.add(ayamError_OUT);

        /* MEMASUKKAN MAINCONTENT1 */
        // memasukkan seluruh elemen ke dalam stackPane mainContent1
        mainContent1 = new StackPane(welcomeAyam, chatAyam, ayamBetina, ayamHand, ayamError, awanError,
                base, contentElements, logoCelengan);
        mainContent1.setMaxSize(600, 300);
        /* MEMASUKKAN MAINCONTENT2 */
        mainContent2 = new StackPane(chatAyam2, ayamBetina2, ayamHandValidasi,
                base2, contentElements2, logoCelengan2);
        mainContent2.setMaxSize(600, 300);

        // GENERAL EVENTS: events yang mencakup semua
        inputPassword.setOnKeyTyped(e -> {
            if (!hLogErrStat) {
                ayamHand_IN.play();
            }
        });
        inputUsername.setOnMousePressed(e -> {
            ayamHand_OUT.play();
        });
        /* MAIN CONTENT 1 EVENTS */
        mainContent1.setOnMouseEntered(e -> {
            if (!hLogErrStat) {
                hopAyamBetina_IN.play();
                fadeChatAyam_IN.play();
            }
        });
        mainContent1.setOnMousePressed(event -> dragStartX = event.getSceneX());
        mainContent1.setOnMouseDragged(e -> {
            handleDragged(e);
            updateDotColors(dot1, dot2);
        });
        /* MAIN CONTENT 2 EVENTS */
        mainContent2.setOnMouseEntered(e -> {
            if (!hLogErrStat) {
                hopAyamBetina2_IN.play();
                ayamHand2_IN.play();
                fadeChatAyam2_IN.play();
            }
        });
        mainContent2.setOnMousePressed(event -> dragStartX = event.getSceneX());
        mainContent2.setOnMouseDragged(e -> {
            handleDragged(e);
            updateDotColors(dot2, dot1);
        });
        /* ROOT */
        root.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                hopAyamBetina_OUT.play();
                fadeChatAyam_OUT.play();
                ayamHand_OUT.play();
            }
        });
        root.setOnMouseEntered(e -> {
            if (!animationPlayed) {
                System.out.println("playing animation...");
                welcomeAyam_IN.play();
                System.out.println("playing out...");
                animationPlayed = true;
                System.out.println("passed out...");
            }
        });

        // root stackPane
        root.getChildren().addAll(dotCarousel, topSection, mainContent1);
        root.setStyle("-fx-background-color: #141F23;");

        // Membuat Scene dengan latar belakang transparent
        Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
        scene.getStylesheets()
                .addAll("https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");
        // Memberikan judul pada stage
        stage.setMaximized(true);
        stage.setScene(scene); // menetapkan scene dari sebuah stage
        stage.show();
        /* END OF UI/UX */
    }

    /* CAROUSEL METHODS */
    // Create a method to create a dot
    private Circle createDot(double radius, String color) {
        Circle dot = new Circle(radius, Color.valueOf(color));
        return dot;
    }

    // Create a method to update dot colors based on the active dot
    private void updateDotColors(Circle activeDot, Circle inactiveDot) {
        activeDot.setFill(Color.valueOf("#7795FF"));
        inactiveDot.setFill(Color.valueOf("#0F181B"));
    }

    /* DRAG METHODS */
    // membuat method untuk drag carousel
    private void handleDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - dragStartX;
        if (deltaX > 50) {
            if (!root.getChildren().contains(mainContent2)) {
                root.getChildren().add(mainContent2);
            }
            fadeTransition1.play();
            fadeTransition2.play();
            root.getChildren().remove(mainContent1);
            dragStartX = event.getSceneX();
        } else if (deltaX < -50) {
            if (!root.getChildren().contains(mainContent1)) {
                root.getChildren().add(mainContent1);
            }
            fadeTransition1.play();
            fadeTransition2.play();
            root.getChildren().remove(mainContent2);
            dragStartX = event.getSceneX();
        }
    }

    /* TRANSITION METHODS */
    // membuat fade transisi
    private FadeTransition createFadeTransition(StackPane node, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        return fadeTransition;
    }

    /* BUTTON METHODS */
    // method untuk mengupdate button
    private void updateButton(Button btn, int width, int height, String text, String bgColor,
            int fontSize, String font,
            int radius, String textFill) {
        btn.setPrefSize(width, height);
        btn.setText(text);
        btn.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
    }

    // method untuk membuat button
    private Button createButton(int width, int height, String text, String bgColor, int fontSize,
            String font,
            int radius, String textFill) {
        Button button = new Button();
        button.setPrefSize(width, height);
        button.setText(text);
        button.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
        return button;
    }

    /* MAIN CONTENT METHODS */
    // method membuat Image
    private ImageView createImage(String imgPath, double width, double height) {

        ImageView imageView = new ImageView();
        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg); // menetapkan gambar
        imageView.setFitWidth(width); // menetapkan panjang gambar
        imageView.setFitHeight(height); // menetapkan tinggi gambar

        return imageView;
    }

    // method membuat text
    private Text createText(String text, int textSize, String textFont, String textFill) {
        Text txt = new Text(text);
        txt.setStyle("-fx-font: " + textSize + " " + textFont + ";");
        txt.setFill(Color.valueOf("#" + textFill));
        return txt;
    }

    // method membuat rectangle
    private Rectangle createRect(double width, double height, double arcWidth, double arcHeight,
            String fill) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(Color.valueOf("#" + fill));
        return rect;
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

    // private void handleregister(String username, String password, String
    // validatepassword, String pincode) {
    // SceneController sceneController = new SceneController(this.stage);
    // LoginModel login = new LoginModel();
    // String validate = validatepassword;

    // if (username.isEmpty() && password.isEmpty() && validate.isEmpty() &&
    // pincode.isEmpty()) {
    // // applyErrorStyle(fieldUsername, fieldPassword, fieldvalidatepassword,
    // // fieldpincode);
    // AlertHelper.alert("Username dan password tidak boleh kosong.");
    // } else if (username.isEmpty()) {
    // // applyErrorStyle(fieldUsername);
    // AlertHelper.alert("Username tidak boleh kosong.");
    // } else if (password.isEmpty()) {
    // // applyErrorStyle(fieldPassword);
    // AlertHelper.alert("Password tidak boleh kosong.");
    // } else if (pincode.isEmpty()) {
    // // applyErrorStyle(fieldpincode);
    // AlertHelper.alert("Pin Code tidak boleh kosong.");
    // } else if (validate.isEmpty()) {
    // // applyErrorStyle(fieldvalidatepassword);
    // AlertHelper.alert("Konfirmasi password tidak boleh kosong.");
    // } else {
    // if (password.equals(validate)) {
    // if (isPasswordSecure(password)) {
    // System.out.println("Password Match");

    // if (login.registerAccount(username, password, pincode)) {
    // sceneController.switchToLogin();
    // } else {
    // AlertHelper.alert("Gagal mendaftarkan akun.");
    // }
    // } else {
    // String massage = "Password tidak memenuhi kriteria keamanan.\n" +
    // "Kriteria:\n" +
    // "- Minimal 8 karakter\n" +
    // "- Mengandung setidaknya satu huruf besar (A-Z)\n" +
    // "- Mengandung setidaknya satu huruf kecil (a-z)\n" +
    // "- Mengandung setidaknya satu angka (0-9)\n" +
    // "- Mengandung setidaknya satu karakter khusus
    // (!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?])";
    // // applyErrorStyle(fieldPassword);
    // AlertHelper.alert(massage);
    // }
    // } else {
    // // applyErrorStyle(fieldPassword, fieldvalidatepassword);
    // AlertHelper.alert("Password tidak sesuai.");
    // }
    // }
    // }

    // Membuat metode untuk menerapkan error style
    private void applyErrorStyle(TextField textField, PasswordField passwordField, int switcher) { // Menampung berbagai
                                                                                                   // macam field
        if (switcher == 1) {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        } else if (switcher == 2) {
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        } else {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        }
    }

    // membuat metode untuk menghapus error style
    private void removeErrorStyle(TextField textField, PasswordField passwordField, int switcher) { // Menampung
        // macam field
        if (switcher == 1) {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        } else if (switcher == 2) {
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        } else {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        }
    }
}